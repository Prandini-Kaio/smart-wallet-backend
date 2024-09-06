pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                // Clona o repositório da branch específica
                checkout scm
            }
        }

        stage('Build and Run Docker Compose') {
            steps {
                script {
                    // Construir e rodar os containers usando Docker Compose
                    sh 'docker-compose up --build -d'
                }
            }
        }
    }

    post {
        always {
            // Exibe os containers em execução após o build
            sh 'docker ps'
        }

        failure {
            // Se algo falhar, exibe os logs dos containers
            sh 'docker-compose logs'
        }
    }
}
