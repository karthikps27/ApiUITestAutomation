pipeline {
    agent {
        docker {
            image "apiuitestautomation_test_local:latest"
        }
    }

    stages {
        stage('Clean and Build') {
            steps {
                sh 'gradle clean build'
            }
        }

        stage('Test Run'){
            steps {
                sh 'gradle task runTests'
            }
        }

        stage('reports') {
            steps {
                script {
                    allure([
                            includeProperties: false,
                            jdk: '',
                            properties: [],
                            reportBuildPolicy: 'ALWAYS',
                            results: [[path: 'build/allure-results']]
                    ])
                }
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