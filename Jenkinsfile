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

        stage('K8s Build & Deploy') {
            steps {
                script {
                    echo 'Building Docker Image...'
                    sh 'docker build -t demouser-app:latest .'

                    echo 'Loading Image into Minikube...'
                    sh 'minikube image load demouser-app:latest'

                    echo 'Cleaning up existing K8s deployment...'
                    sh 'kubectl delete -f deployment.yaml --ignore-not-found'

                    echo 'Applying New Kubernetes Manifests...'
                    sh 'kubectl apply -f deployment.yaml'

                    // Wait for pods to be ready
                    sh 'kubectl rollout status deployment/demouser-deployment'
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