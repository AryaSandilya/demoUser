pipeline {
    agent any

    tools {
        // Ensure these names match your 'Global Tool Configuration'
        maven 'Maven'
        jdk 'Java'
    }

    environment {
        // Replace with your actual Docker Hub username
        DOCKER_HUB_USER = 'rahulraj41'
        IMAGE_NAME = 'demouser-app'
        // Uses the Jenkins build number as a unique version tag
        IMAGE_TAG = "${env.BUILD_NUMBER}"
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/AryaSandilya/demoUser.git'
            }
        }

        stage('Build & Test') {
            steps {
                echo 'Compiling and Packaging Application...'
                sh 'mvn clean package -DskipTests'

                echo 'Running Unit Tests...'
                sh 'mvn test'
            }
        }

        stage('Docker Push to Registry') {
            steps {
                script {
                    // Uses the 'docker-hub-creds' we created in Jenkins Credentials
                    withCredentials([usernamePassword(credentialsId: 'docker-hub-creds', passwordVariable: 'DOCKER_PASS', usernameVariable: 'DOCKER_USER')]) {
                        echo 'Logging into Docker Hub...'
                        sh "docker login -u ${DOCKER_USER} -p ${DOCKER_PASS}"

                        echo "Building Image: ${DOCKER_HUB_USER}/${IMAGE_NAME}:${IMAGE_TAG}"
                        // Build two tags: one unique build number and one 'latest'
                        sh "docker build -t ${DOCKER_HUB_USER}/${IMAGE_NAME}:${IMAGE_TAG} -t ${DOCKER_HUB_USER}/${IMAGE_NAME}:latest ."

                        echo 'Pushing Images to Docker Hub...'
                        sh "docker push ${DOCKER_HUB_USER}/${IMAGE_NAME}:${IMAGE_TAG}"
                        sh "docker push ${DOCKER_HUB_USER}/${IMAGE_NAME}:latest"
                    }
                }
            }
        }

        stage('Kubernetes Deployment') {
            steps {
                script {
                    echo "Updating deployment.yaml with new image version: ${IMAGE_TAG}"

                    // Dynamically update the image in your YAML file to use the new build version
                    sh "sed -i 's|image: .*|image: ${DOCKER_HUB_USER}/${IMAGE_NAME}:${IMAGE_TAG}|g' deployment.yaml"

                    echo 'Applying Manifests to Minikube...'
                    sh "kubectl apply -f deployment.yaml"

                    echo 'Verifying Deployment Status...'
                    sh "kubectl rollout status deployment/demouser-deployment"
                }
            }
        }
    }

    post {
        success {
            echo "Successfully Deployed Build #${env.BUILD_NUMBER}!"
            echo "Access App at: http://44.201.115.126:8081/users/1"
        }
        failure {
            echo 'Pipeline failed. Check the console output for errors.'
        }
    }
}