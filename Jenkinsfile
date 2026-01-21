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

        stage('SonarQube Quality Gate') {
            steps {
                echo '⏳ Waiting for SonarQube Quality Gate...'
                timeout(time: 15, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
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
                echo '🚀 Deploying to production...'
                sh '''
                    docker stop ${PROD_CONTAINER} || true
                    docker rm ${PROD_CONTAINER} || true

                    docker run -d \
                      --name ${PROD_CONTAINER} \
                      -p ${PROD_PORT}:8081 \
                      --restart unless-stopped \
                      ${IMAGE_NAME}:latest
                '''
            }
        }

        stage('Post-Deploy Health Check') {
            steps {
                echo '🩺 Running production health check...'
                sh '''
                    sleep 10
                    curl -f ${PROD_URL}/actuator/health || exit 1
                    echo "✅ Production application is healthy"
                '''
            }
        }
    }

    post {
        always {
            echo '🧹 Cleaning up DAST container...'
            sh 'docker stop ${DAST_CONTAINER} || true'

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
