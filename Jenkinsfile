pipeline {
    agent any  // Exécute sur n'importe quel agent disponible

    environment {
        MAVEN_HOME = 'C:\apache-maven-3.9.9-bin\apache-maven-3.9.9'  // Remplacez ce chemin par l'emplacement de Maven sur l'agent
        PATH = "${MAVEN_HOME}/bin:${env.PATH}"
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
                sh 'mvn clean test'  // Si tu veux inclure l'exécution des tests avec mvn clean test, remplace install par test
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
