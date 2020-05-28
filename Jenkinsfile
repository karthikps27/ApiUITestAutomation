pipeline {
    agent {
        docker {
            image "apiuiautomation:latest"
        }
    }

    stages {
        stage('Clean and Build') {
            steps {
                sh 'gradle clean'
            }
        }

        stage('Test Run'){
            steps {
                sh 'gradle task runTests'
            }
        }
    }

    post {
        always {
               //sh 'gradle allureReport'
               //   script {
               //       allure([
               //           includeProperties: false,
               //          jdk: '',
               //          properties: [],
               //          reportBuildPolicy: 'ALWAYS',
               //        results: [[path: 'build/allure-results']]
               //   ])
               //}
            publishHTML target: [
                allowMissing: false,
                alwaysLinkToLastBuild: true,
                keepAll: false,
                reportDir: 'build/reports/tests/runTests',
                reportFiles: 'index.html',
                reportName: 'Gradle Report'
            ]
        }
    }
}