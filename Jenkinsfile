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
                  docker run -d --rm \
                    --name ${DAST_CONTAINER} \
                    -p ${INTERNAL_PORT}:8081 \
                    -e DB_URL=jdbc:h2:mem:testdb \
                    -e DB_USERNAME=sa \
                    -e DB_PASSWORD= \
                    ${IMAGE_NAME}:latest

                  sleep 15
                '''
            }
        }

        stage('OWASP ZAP DAST') {
            steps {
                sh '''
                  docker run --rm --network=host \
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
                    -e DB_URL=jdbc:mysql://db:3306/db_kf_maintenance \
                    -e DB_USERNAME=appuser \
                    -e DB_PASSWORD=******** \
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
