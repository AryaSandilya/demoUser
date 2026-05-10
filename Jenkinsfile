pipeline {
    agent any

    tools {
        maven 'Maven'
        jdk 'Java'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/AryaSandilya/demoUser.git'
            }
        }

        stage('Build') {
            steps {
                echo 'Building the application...'
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Test') {
            steps {
                echo 'Running unit tests...'
                sh 'mvn test'
            }
        }

        stage('Docker Build & Deploy') {
            steps {
                script {
                    echo 'Cleaning up port 8081...'
                    // Kill any manual java processes on 8081
                    sh 'sudo fuser -k 8081/tcp || true'

                    echo 'Building Docker Image...'
                    sh 'docker build -t demouser-app:latest .'

                    echo 'Cleaning up old containers...'
                    // Stop and remove the old container
                    sh 'docker stop demouser-container || true'
                    sh 'docker rm demouser-container || true'

                    echo 'Starting New Docker Container...'
                    // Run the new container
                    sh 'docker run -d --name demouser-container -p 8081:8081 demouser-app:latest'
                }
            }
        }
}
    post {
        success {
            echo 'Docker Deployment successful! App is at http://44.201.115.126:8081'
        }
        failure {
            echo 'Build failed. Check the console output for details.'
        }
    }
}