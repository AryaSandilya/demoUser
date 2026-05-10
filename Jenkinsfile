pipeline {
    agent any

    tools {
        // Ensure these names match what you have in 'Global Tool Configuration'
        maven 'Maven'
        jdk 'Java'
    }

    stages {
        stage('Checkout') {
            steps {
                // This pulls the code from the Git URL you configured
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

        stage('Archive Artifacts') {
            steps {
                // Archives the JAR file so you can download it from the Jenkins UI
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }
        stage('Deploy') {
    steps {
        sh '''
            # Kill any old process on 8081 to avoid "Address already in use"
            sudo fuser -k 8081/tcp || true

            # Start the new version and tell Jenkins NOT to kill it
            export JENKINS_NODE_COOKIE=dontKillMe
            nohup java -jar target/*.jar --server.port=8081 > app_log.txt 2>&1 &
        '''
    }
 }
}
    post {
        success {
            echo 'Build and Test completed successfully!'
        }
        failure {
            echo 'Build failed. Check the console output for details.'
        }
    }
}