pipeline {
    agent any

    tools {
        jdk 'jdk17'
        maven 'maven-3.9.12'
    }

    environment {
        // Sonar
        SONAR_SCANNER_HOME = tool 'SonarQube Scanner'

        // Docker
        IMAGE_NAME     = 'kf-maintenance-backend'
        DAST_CONTAINER = 'kf-backend-dast'
        PROD_CONTAINER = 'kf-backend-prod'

        // Ports
        INTERNAL_PORT = '2000'
        PROD_PORT     = '8081'

        // URLs
        DAST_URL = "http://127.0.0.1:${INTERNAL_PORT}"
        PROD_URL = "http://127.0.0.1:${PROD_PORT}"
    }

    stages {

        stage('Checkout Code') {
            steps {
                echo '📥 Checking out source code...'
                checkout scm
            }
        }

        stage('Build JAR') {
            steps {
                echo '🏗️ Building executable JAR...'
                sh '''
                    java -version
                    mvn -version
                    mvn clean package -DskipTests
                '''
            }
        }

        stage('SonarQube SAST') {
            steps {
                echo '🔍 Running SonarQube SAST...'
                withSonarQubeEnv('sonarqube') {
                    sh """
                        ${SONAR_SCANNER_HOME}/bin/sonar-scanner \
                          -Dsonar.projectKey=kf-maintenance-backend \
                          -Dsonar.projectName=kf-maintenance-backend \
                          -Dsonar.projectVersion=${BUILD_NUMBER} \
                          -Dsonar.sources=src/main/java \
                          -Dsonar.java.binaries=target
                    """
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                echo '🐳 Building Docker image...'
                sh '''
                    docker build -t ${IMAGE_NAME}:latest .
                '''
            }
        }

        stage('Start App for DAST') {
            steps {
                echo '🚀 Starting temporary container for DAST...'
                sh '''
                    docker run -d --rm \
                      --name ${DAST_CONTAINER} \
                      -p ${INTERNAL_PORT}:8081 \
                      -e SPRING_PROFILES_ACTIVE=ci \
                      ${IMAGE_NAME}:latest

                    echo "⏳ Waiting for application to be ready..."
                    for i in {1..15}; do
                        if curl -s http://127.0.0.1:${INTERNAL_PORT}/actuator/health | grep UP; then
                            echo "✅ DAST app is healthy"
                            break
                        fi
                        sleep 5
                    done
                '''
            }
        }

        stage('OWASP ZAP DAST') {
            steps {
                echo '🛡️ Running OWASP ZAP DAST...'
                sh '''
                    docker run --rm --network=host \
                      -u 0 \
                      -v "$(pwd)":/zap/wrk \
                      ghcr.io/zaproxy/zaproxy:stable \
                      zap-baseline.py \
                      -t ${DAST_URL} \
                      -r zap-report.html \
                      -w zap-warn.txt \
                      -x zap-report.xml \
                      -I
                '''
            }
        }

        stage('Deploy to Production') {
            steps {
                echo '🚀 Deploying backend using MySQL (Production)...'
        
                withCredentials([
                    string(credentialsId: 'MYSQL_USERNAME', variable: 'DB_CREDS_USR'),
                    string(credentialsId: 'MYSQL_PASSWORD', variable: 'DB_CREDS_PSW'),
                    string(credentialsId: 'MAIL_USERNAME', variable: 'MAIL_CREDS_USR'),
                    string(credentialsId: 'GMAIL_APP_PASSWORD', variable: 'MAIL_CREDS_PSW')
                ]) {
                    sh '''
                        docker stop ${PROD_CONTAINER} || true
                        docker rm ${PROD_CONTAINER} || true
        
                        docker run -d \
                          --name ${PROD_CONTAINER} \
                          -p 8081:8081 \
                          --restart unless-stopped \
                          -e SPRING_PROFILES_ACTIVE=prod \
                          -e DB_URL=jdbc:mysql://host.docker.internal:3306/db_kf_maintenance?allowPublicKeyRetrieval=true&useSSL=false \
                          -e DB_USERNAME=${DB_CREDS_USR} \
                          -e DB_PASSWORD=${DB_CREDS_PSW} \
                          -e MAIL_USERNAME=${MAIL_CREDS_USR} \
                          -e MAIL_PASSWORD=${MAIL_CREDS_PSW} \
                          ${IMAGE_NAME}:latest
        
                        echo "📦 Container started, waiting 10 seconds..."
                        sleep 10
        
                        echo "📜 Application logs:"
                        docker logs ${PROD_CONTAINER}
                    '''
                }
            }
        }

        stage('Post-Deploy Health Check') {
            steps {
                echo '🩺 Waiting for application to become healthy...'
                sh '''
                for i in {1..12}; do
                    RESPONSE=$(curl -s http://127.0.0.1:8081/actuator/health || true)
                    echo "Health response: $RESPONSE"
        
                    if echo "$RESPONSE" | grep -q '"status":"UP"'; then
                        echo "✅ Application is healthy"
                        exit 0
                    fi
        
                    echo "⏳ App not ready yet... retrying ($i/12)"
                    sleep 5
                done
        
                echo "❌ Application failed to become healthy"
                exit 1
                '''
            }
        }
    }

    post {
        always {
            echo '🧹 Cleaning up DAST container...'
            sh 'docker stop ${DAST_CONTAINER} || true'
            emailext(
                to: 'durveshsshendokar@gmail.com',
                from: 'durveshsshendokar@gmail.com',
                subject: "DevSecOps Pipeline Result: ${currentBuild.currentResult} | ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                mimeType: 'text/html',
                body: """
                <html>
                <body style="font-family: Arial, sans-serif;">
            
                <h2 style="color:#2F80ED;">DevSecOps Pipeline Report</h2>
            
                <table cellpadding="6" cellspacing="0" border="0">
                    <tr><td><b>Job</b></td><td>${env.JOB_NAME}</td></tr>
                    <tr><td><b>Build Number</b></td><td>#${env.BUILD_NUMBER}</td></tr>
                    <tr><td><b>Status</b></td><td><b>${currentBuild.currentResult}</b></td></tr>
                    <tr><td><b>Triggered By</b></td><td>GitHub Push</td></tr>
                </table>
            
                <br/>
            
                <p>
                    🔗 <b>Jenkins Build:</b><br/>
                    <a href="${env.BUILD_URL}">${env.BUILD_URL}</a>
                </p>
            
                <hr/>
            
                <h3>📊 Security & Code Quality Reports</h3>
                <ul>
                    <li>
                        <b>SonarQube Dashboard</b> →
                        <a href="http://4.213.97.72:9000/dashboard?id=kf-maintenance-backend">
                            View Report
                        </a>
                    </li>
            
                    <li>
                        <b>OWASP ZAP DAST Report</b> →
                        <a href="${env.BUILD_URL}artifact/zap-report.html">
                            View Report
                        </a>
                    </li>
                </ul>
            
                <p style="color:gray; font-size:12px;">
                    ⚠️ Security reports are stored as Jenkins artifacts to avoid email size limits.
                </p>
            
                <br/>
                <p style="color:#555;">
                    — Jenkins DevSecOps Pipeline
                </p>
            
                </body>
                </html>
                """
            )

            archiveArtifacts artifacts: '''
                zap-report.html,
                zap-warn.txt,
                zap-report.xml
            ''', allowEmptyArchive: true
        }

        success {
            echo '✅ Backend DevSecOps pipeline completed successfully'
        }

        failure {
            echo '❌ Backend pipeline failed'
        }
    }
}
