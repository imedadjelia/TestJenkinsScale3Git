pipeline {
    agent any

    environment {
        MAVEN_HOME = 'C:/apache-maven-3.9.9-bin/apache-maven-3.9.9'
        PATH = "${MAVEN_HOME}/bin:${env.PATH}"
        API_BASE_URL = 'https://api.zephyrscale.smartbear.com/v2'
        API_TOKEN = 'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJjb250ZXh0Ijp7ImJhc2VVcmwiOiJodHRwczovL2l0LXN0dWRlbnRzLXRlYW0tbDA0bmxqOWcuYXRsYXNzaWFuLm5ldCIsInVzZXIiOnsiYWNjb3VudElkIjoiNzEyMDIwOmRlMmNmZWRhLTIwYjgtNDZjNC04MWQyLThiMjg3YTZhMzFmOSIsInRva2VuSWQiOiJlZDRmODdhYS0yNTY0LTQ2MTctODk0OS05NTQwZDUyNWJiMjcifX0sImlzcyI6ImNvbS5rYW5vYWgudGVzdC1tYW5hZ2VyIiwic3ViIjoiMDM3NGE5NzgtYzY1Ni0zZTY0LTk0NTgtMGQwNWU4YTExOTRjIiwiZXhwIjoxNzY3Nzc2MTEwLCJpYXQiOjE3MzYyNDAxMTB9._VLeIDiSiy3c2dJiUfS7m-msFamG7G8uSUUWV5ueYds' // Remplacez par votre jeton API Zephyr Scale
        PROJECT_KEY = 'PSLIB' // Remplacez par la clé de votre projet Jira
        TEST_CASE_KEY = 'PSLIB-T13' // Remplacez par la clé de votre cas de test
        TEST_CYCLE_KEY = 'PSLIB-R5' // Remplacez par la clé de votre cycle de test
        VERSION_KEY = '1.0' // Version associée au projet
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

        stage('Report to Zephyr Scale') {
            steps {
                script {
                    def payload = [
                        projectKey     : "${PROJECT_KEY}",
                        versionKey     : "${VERSION_KEY}",
                        testCaseKey    : "${TEST_CASE_KEY}",
                        testCycleKey   : "${TEST_CYCLE_KEY}",
                        statusName     : "PASS", // Peut être adapté selon votre workflow
                        executionStatus: "PASS", // Remplacez par le statut réel du test (PASS, FAIL, etc.)
                         // Exemple de date de fin
                    ]

                    def response = httpRequest(
                        acceptType: 'APPLICATION_JSON',
                        contentType: 'APPLICATION_JSON',
                        httpMode: 'POST',
                        url: "${API_BASE_URL}/testexecutions",
                        customHeaders: [
                            [name: 'Authorization', value: "Bearer ${API_TOKEN}"]
                        ],
                        requestBody: groovy.json.JsonOutput.toJson(payload)
                    )

                    echo "Réponse de l'API Zephyr Scale : ${response.content}"
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
