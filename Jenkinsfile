pipeline {
    agent any

    tools {
        jdk 'jdk17'
        maven 'maven-3.9.12'
    }

    environment {
        SONAR_SCANNER_HOME = tool 'SonarQube Scanner'

        IMAGE_NAME     = 'kf-maintenance-backend'
        DAST_CONTAINER = 'kf-backend-dast'
        PROD_CONTAINER = 'kf-backend-prod'

        DAST_PORT = '2000'
        PROD_PORT = '8081'

        APP_DAST_URL = "http://127.0.0.1:${DAST_PORT}"
    }

    stages {

        stage('Checkout Code') {
            steps {
                echo '📥 Checking out backend source code...'
                checkout scm
            }
        }

        stage('Maven Clean & Compile') {
            steps {
                echo '🧹 Cleaning and compiling project...'
                sh '''
                    java -version
                    mvn -version
                    mvn clean compile -DskipTests
                '''
            }
        }

        stage('Run Unit Tests (No DB)') {
            steps {
                echo '🧪 Running unit tests (H2 in-memory)...'
                sh '''
                    mvn test \
                    -Dspring.profiles.active=ci \
                    -Dspring.datasource.url=jdbc:h2:mem:testdb \
                    -Dspring.jpa.hibernate.ddl-auto=none
                '''
            }
        }

        stage('SonarQube SAST Analysis') {
            steps {
                echo '🔍 Running SonarQube SAST...'
                withSonarQubeEnv('sonarqube') {
                    sh """
                        ${SONAR_SCANNER_HOME}/bin/sonar-scanner \
                        -Dsonar.projectKey=kf-maintenance \
                        -Dsonar.projectName=kf-maintenance-backend \
                        -Dsonar.projectVersion=${BUILD_NUMBER} \
                        -Dsonar.sources=src/main/java \
                        -Dsonar.tests=src/test/java \
                        -Dsonar.java.binaries=target
                    """
                }
            }
        }

        stage('SonarQube Quality Gate') {
            steps {
                echo '⏳ Waiting for SonarQube Quality Gate...'
                timeout(time: 15, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: false
                }
            }
        }

        stage('Package Artifact') {
            steps {
                echo '📦 Packaging executable JAR...'
                sh 'mvn package -DskipTests'
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

        stage('Start App for DAST (Internal)') {
            steps {
                echo '🚀 Starting temporary container for DAST...'
                sh '''
                    docker stop ${DAST_CONTAINER} || true
                    docker rm ${DAST_CONTAINER} || true

                    docker run -d \
                      --name ${DAST_CONTAINER} \
                      -p ${DAST_PORT}:8081 \
                      ${IMAGE_NAME}:latest

                    for i in {1..15}; do
                        if curl -s ${APP_DAST_URL}/actuator/health | grep UP; then
                            echo "✅ Application ready for DAST"
                            exit 0
                        fi
                        echo "⏳ Waiting for application..."
                        sleep 5
                    done

                    echo "❌ Application failed to start"
                    exit 1
                '''
            }
        }

        stage('OWASP ZAP DAST') {
            steps {
                echo '🛡️ Running OWASP ZAP DAST scan...'
                sh '''
                    docker run --rm --network=host \
                      -v "$(pwd)":/zap/wrk \
                      ghcr.io/zaproxy/zaproxy:stable \
                      zap-baseline.py \
                      -t ${APP_DAST_URL} \
                      -r zap-report.html \
                      -x zap-report.xml \
                      -I
                '''
            }
        }

        stage('ZAP Quality Gate') {
            steps {
                echo '🚦 Evaluating ZAP results...'
                sh '''
                    HIGH=$(grep -c "<riskcode>3</riskcode>" zap-report.xml || true)

                    if [ "$HIGH" -gt 0 ]; then
                        echo "❌ High-risk vulnerabilities found"
                        exit 1
                    fi

                    echo "✅ ZAP Quality Gate passed"
                '''
            }
        }

        stage('Deploy to Production (8081)') {
            steps {
                echo '🚀 Deploying backend to production...'
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
                echo '🩺 Verifying production health...'
                sh '''
                    sleep 5
                    curl -f http://127.0.0.1:${PROD_PORT}/actuator/health
                '''
            }
        }
    }

    post {
        always {
            echo '🧹 Cleaning up DAST container...'
            sh '''
                docker stop ${DAST_CONTAINER} || true
                docker rm ${DAST_CONTAINER} || true
            '''

            archiveArtifacts artifacts: 'target/*.jar,zap-report.html,zap-report.xml', allowEmptyArchive: true
        }

        success {
            echo '✅ Backend CI/CD pipeline completed successfully'
        }

        failure {
            echo '❌ Backend CI/CD pipeline failed'
        }
    }
}
