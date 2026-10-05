pipeline {
    agent any

    environment {
        TAG = "${BUILD_NUMBER}"
    }

    stages {
        // ---------- CI ----------
        stage('GIT') {
            steps {
                checkout scm
            }
        }

        stage('Compile') {
            steps {
                dir('backend') {
                    sh 'mvn -B clean compile'
                }
            }
        }

        stage('SonarQube') {
            steps {
                dir('backend') {
                    withSonarQubeEnv('SonarQube') {
                        sh 'mvn -B org.sonarsource.scanner.maven:sonar-maven-plugin:5.1.0.4751:sonar -Dsonar.projectKey=Gestion-Projets'
                    }
                }
                timeout(time: 5, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }

        stage('Tests') {
            steps {
                dir('backend') {
                    sh 'mvn -B test'
                }
            }
        }

        stage('Package') {
            steps {
                dir('backend') {
                    sh 'mvn -B package -DskipTests'
                }
            }
        }

        // ---------- CD ----------
        stage('Build Images') {
            steps {
                sh 'docker compose build'
            }
        }

        stage('Push DockerHub') {
            steps {
                withCredentials([usernamePassword(
                        credentialsId: 'dockerhub-credentials',
                        usernameVariable: 'HUB_USER',
                        passwordVariable: 'HUB_PASS')]) {
                    sh 'echo $HUB_PASS | docker login -u $HUB_USER --password-stdin'
                    sh 'docker compose push backend frontend'
                }
            }
        }

        stage('Deploy') {
            steps {
                sh 'docker compose up -d'
            }
        }
    }

    post {
        always {
            sh 'docker logout || true'
        }
    }
}
