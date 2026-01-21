pipeline {
    agent any

    tools {
        jdk 'jdk17'
        maven 'maven-3.9.12'
    }

    environment {
        SONAR_SCANNER_HOME = tool 'SonarQube Scanner'

        IMAGE_NAME = 'kf-maintenance-backend'
        DAST_CONTAINER = 'kf-backend-dast'
        PROD_CONTAINER = 'kf-backend-prod'

        INTERNAL_PORT = '2000'
        PROD_PORT = '8081'

        APP_URL = "http://127.0.0.1:${INTERNAL_PORT}"
    }

    stages {

        stage('Checkout Code') {
            steps {
                checkout scm
            }
        }

        stage('Build JAR') {
            steps {
                sh '''
                  mvn clean package -DskipTests
                '''
            }
        }

        stage('SonarQube SAST') {
            steps {
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
                sh '''
                  docker build -t ${IMAGE_NAME}:latest .
                '''
            }
        }

        stage('Start App for DAST') {
            steps {
                sh '''
                  docker stop ${DAST_CONTAINER} || true
                  docker rm ${DAST_CONTAINER} || true

                  docker run -d --rm \
                    --name ${DAST_CONTAINER} \
                    -p ${INTERNAL_PORT}:8081 \
                    -e SPRING_PROFILES_ACTIVE=ci \
                    ${IMAGE_NAME}:latest

                  echo "Waiting for application to start..."
                  for i in {1..10}; do
                    if curl -s http://127.0.0.1:${INTERNAL_PORT} >/dev/null; then
                      echo "Application is up"
                      break
                    fi
                    sleep 5
                  done
                '''
            }
        }

        stage('OWASP ZAP DAST') {
            steps {
                sh '''
                  docker run --rm --network=host \
                    -u 0 \
                    -v "$(pwd)":/zap/wrk \
                    ghcr.io/zaproxy/zaproxy:stable \
                    zap-baseline.py \
                    -t ${APP_URL} \
                    -r zap-report.html \
                    -x zap-report.xml \
                    -I
                '''
            }
        }

        stage('Deploy Production') {
            steps {
                sh '''
                  docker stop ${PROD_CONTAINER} || true
                  docker rm ${PROD_CONTAINER} || true

                  docker run -d \
                    --name ${PROD_CONTAINER} \
                    -p ${PROD_PORT}:8081 \
                    --restart unless-stopped \
                    -e SPRING_PROFILES_ACTIVE=prod \
                    ${IMAGE_NAME}:latest
                '''
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: 'zap-report.*', allowEmptyArchive: true
        }
    }
}
