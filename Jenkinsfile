pipeline {
    agent any
    tools {
        jdk 'jdk17'
        maven 'maven3'
    }
        triggers {
            cron('H 2 * * 1-5')
        }
    stages {
        stage('Test') {
            steps {
                bat 'mvn clean test -Dheadless=true'
            }
        }
    }
    post {
        always {
            junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
        }
    }
}