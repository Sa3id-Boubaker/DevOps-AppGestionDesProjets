pipeline {
    agent any

    environment {
        TAG = "${BUILD_NUMBER}"
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

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
