pipeline {
    agent any // Exécute sur n'importe quel agent disponible

    environment {
        MAVEN_HOME = 'C:/apache-maven-3.9.9-bin/apache-maven-3.9.9'
        // Remplacez ce chemin par l'emplacement de Maven sur l'agent
        PATH = "${MAVEN_HOME}/bin:${env.PATH}"
        JIRA_URL = 'https://it-students-team-l04nlj9g.atlassian.net'
        JIRA_TOKEN = 'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJjb250ZXh0Ijp7ImJhc2VVcmwiOiJodHRwczovL2l0LXN0dWRlbnRzLXRlYW0tbDA0bmxqOWcuYXRsYXNzaWFuLm5ldCIsInVzZXIiOnsiYWNjb3VudElkIjoiNzEyMDIwOmRlMmNmZWRhLTIwYjgtNDZjNC04MWQyLThiMjg3YTZhMzFmOSIsInRva2VuSWQiOiJlZDRmODdhYS0yNTY0LTQ2MTctODk0OS05NTQwZDUyNWJiMjcifX0sImlzcyI6ImNvbS5rYW5vYWgudGVzdC1tYW5hZ2VyIiwic3ViIjoiMDM3NGE5NzgtYzY1Ni0zZTY0LTk0NTgtMGQwNWU4YTExOTRjIiwiZXhwIjoxNzY3Nzc2MTEwLCJpYXQiOjE3MzYyNDAxMTB9._VLeIDiSiy3c2dJiUfS7m-msFamG7G8uSUUWV5ueYds' // Remplacez par votre token API Jira
        TEST_CASE_KEY = 'PSLIB-T13' // Identifiant du cas de test Zephyr Scale
        CYCLE_KEY = 'PSLIB-R5' // Identifiant du cycle de test Zephyr Scale
    }

    stages {
        stage('Checkout') {
            steps {
                // Récupère le code source du dépôt Git
                checkout scm
            }
        }

        stage('Build with Maven') {
            steps {
                // Exécute la commande Maven pour construire le projet et exécuter les tests
                bat 'mvn clean test'
            }
        }

        stage('Report to Zephyr Scale') {
            steps {
                script {
                    // Appel API pour envoyer les résultats des tests à Zephyr Scale
                    sh """
                    curl -X POST \
                         -H "Authorization: Bearer ${JIRA_TOKEN}" \
                         -H "Content-Type: application/json" \
                         -d '{
                             "projectKey": "PROJET_SLIB",
                             "testCaseKey": "${TEST_CASE_KEY}",
                             "status": "Pass",
                             "executionTime": 120,
                             "comment": "Test exécuté via Jenkins build ${BUILD_NUMBER}.",
                             "testCycleKey": "${CYCLE_KEY}"
                         }' \
                         ${JIRA_URL}/rest/zephyr-scale/1.0/testresults
                    """
                }
            }
        }

        stage('Additional Build Stage') {
            steps {
                echo 'Building...' // Étape issue de script 1
            }
            post {
                always {
                    jiraSendBuildInfo site: 'https://it-students-team-l04nlj9g.atlassian.net' // Étape issue de script 1
                }
            }
        }

        stage('Deploy - Staging') {
            when {
                branch 'PSLIB-T13' // Condition issue de script 2
            }
            steps {
                echo 'Deploying to Staging from PSLIB-13...' // Étape issue de script 2
            }
            post {
                always {
                    jiraSendDeploymentInfo environmentId: 'us-stg-1', environmentName: 'us-stg-1', environmentType: 'staging' // Étape issue de script 2
                }
            }
        }

        stage('Deploy - Production') {
            when {
                branch 'PSLIB-T13' // Condition issue de script 2
            }
            steps {
                echo 'Deploying to Production from PSLIB-T13...' // Étape issue de script 2
            }
            post {
                always {
                    jiraSendDeploymentInfo environmentId: 'us-prod-1', environmentName: 'us-prod-1', environmentType: 'production' // Étape issue de script 2
                }
            }
        }
    }

    post {
        always {
            // Cette section est exécutée après la fin de la pipeline (peu importe le résultat)
            echo 'Pipeline terminée'
        }

        success {
            // Cette section est exécutée si la pipeline a réussi
            echo 'La pipeline a réussi !'
        }

        failure {
            // Cette section est exécutée si la pipeline a échoué
            echo 'La pipeline a échoué !'
        }
    }
}
