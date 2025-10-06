pipeline {
    agent any

    tools {
        maven 'Maven3'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                echo 'Building the project...'
                bat 'mvn clean compile'
            }
        }

        stage('Test') {
            steps {
                echo 'Running tests with coverage...'

                bat 'mvn test jacoco:report'

                bat 'dir target /s'
            }
        }
    }

    post {
        always {
            junit '**/target/surefire-reports/*.xml'

            publishHTML([
                reportDir: 'target/site/jacoco',
                reportFiles: 'index.html',
                reportName: 'JaCoCo Coverage Report',
                alwaysLinkToLastBuild: true,
                keepAll: true,
                allowMissing: true
            ])

            echo 'Pipeline completed with tests and coverage.'
        }
    }
}