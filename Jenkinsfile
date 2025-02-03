pipeline {
    agent any

    environment {
        MAVEN_HOME = 'C:/apache-maven-3.9.9-bin/apache-maven-3.9.9'
        PATH = "${MAVEN_HOME}/bin:${env.PATH}"
        API_BASE_URL = 'https://api.zephyrscale.smartbear.com/v2'
        API_TOKEN = 'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJjb250ZXh0Ijp7ImJhc2VVcmwiOiJodHRwczovL2l0LXN0dWRlbnRzLXRlYW0tbDA0bmxqOWcuYXRsYXNzaWFuLm5ldCIsInVzZXIiOnsiYWNjb3VudElkIjoiNzEyMDIwOmRlMmNmZWRhLTIwYjgtNDZjNC04MWQyLThiMjg3YTZhMzFmOSIsInRva2VuSWQiOiJlZDRmODdhYS0yNTY0LTQ2MTctODk0OS05NTQwZDUyNWJiMjcifX0sImlzcyI6ImNvbS5rYW5vYWgudGVzdC1tYW5hZ2VyIiwic3ViIjoiMDM3NGE5NzgtYzY1Ni0zZTY0LTk0NTgtMGQwNWU4YTExOTRjIiwiZXhwIjoxNzY3Nzc2MTEwLCJpYXQiOjE3MzYyNDAxMTB9._VLeIDiSiy3c2dJiUfS7m-msFamG7G8uSUUWV5ueYds' 
        PROJECT_KEY = 'PSLIB'
        CUCUMBER_JSON_PATH = 'target/cucumber-report.json'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build with Maven') {
            steps {
                bat 'mvn clean test'
            }
        }

        stage('Report Cucumber Results to Zephyr Scale') {
            steps {
                script {
                    def apiUrl = "${API_BASE_URL}/automations/executions/cucumber?projectKey=${PROJECT_KEY}"

                    def response = httpRequest(
                        acceptType: 'APPLICATION_JSON',
                        contentType: 'MULTIPART_FORM_DATA',
                        httpMode: 'POST',
                        url: apiUrl,
                        customHeaders: [
                            [name: 'Authorization', value: "Bearer ${API_TOKEN}"]
                        ],
                        uploadFile: CUCUMBER_JSON_PATH 
                    )

                    echo "Réponse de l'API Zephyr Scale : ${response.content}"
                }
            }
        }

        stage('Additional Build Stage') {
            steps {
                echo 'Building...'
            }
            post {
                always {
                    jiraSendBuildInfo site: 'https://it-students-team-l04nlj9g.atlassian.net/browse/PSLIB-4'
                }
            }
        }
    }

    post {
        always {
            echo 'Pipeline terminée'
        }
        success {
            echo 'La pipeline a réussi !'
        }
        failure {
            echo 'La pipeline a échoué !'
        }
    }
}
