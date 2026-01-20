pipeline {
    agent any

    tools {
        jdk 'jdk17'
        maven 'maven-3.9.12'
    }

    environment {
        SONAR_SCANNER_HOME = tool 'SonarQube Scanner'
    }

    stages {

        stage('Checkout Code') {
            steps {
                echo '📥 Checking out backend source code...'
                checkout scm
            }
        }
        stage('Check Java Home') {
            steps {
                sh '''
                    echo "JAVA_HOME is: $JAVA_HOME"
                    which java
                    java -version
                '''
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
                echo '🧪 Running unit tests (DB skipped)...'
                sh '''
                    mvn test \
                    -Dspring.profiles.active=ci \
                    -Dspring.jpa.hibernate.ddl-auto=none \
                    -Dspring.datasource.url=jdbc:h2:mem:testdb
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
                timeout(time: 5, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }

        stage('Package Artifact') {
            steps {
                echo '📦 Packaging application JAR...'
                sh '''
                    mvn package -DskipTests
                '''
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: 'target/*.jar', allowEmptyArchive: false

            emailext(
                to: 'durveshsshendokar@gmail.com',
                subject: "Backend CI Pipeline: ${currentBuild.currentResult} | ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                mimeType: 'text/html',
                body: """
                <h2>Backend CI Pipeline Report</h2>
                <p><b>Status:</b> ${currentBuild.currentResult}</p>
                <p><b>Job:</b> ${env.JOB_NAME}</p>
                <p><b>Build:</b> #${env.BUILD_NUMBER}</p>
                <p><a href="${env.BUILD_URL}">View Build</a></p>
                <p><a href="http://SONAR_URL/dashboard?id=kf-maintenance">SonarQube Dashboard</a></p>
                """
            )
        }

        success {
            echo '✅ Backend CI pipeline completed successfully'
        }

        failure {
            echo '❌ Backend CI pipeline failed'
        }
    }
}
