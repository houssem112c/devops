pipeline {
    agent any

    environment {
        DOCKER_IMAGE_NAME = 'houssem69/event'
        DOCKER_IMAGE_VERSION = 'latest'
        DOCKER_REGISTRY = 'docker.io'
        DOCKERHUB_CREDENTIALS = credentials('dockerhub')
        NEXUS_CREDENTIALS = credentials('nexus-credentials') // Nexus credentials
    }

    stages {
        stage('Git') {
            steps {
                echo 'Pulling from GitHub'
                git branch: 'main', url: 'https://github.com/houssem112c/devops.git'
            }
        }

        stage('Build Maven') {
            steps {
                sh 'mvn -Dmaven.test.failure.ignore=true clean package'
            }
        }

        stage('Unit Test') {
            steps {
                sh 'mvn -Dmaven.test.failure.ignore=true test'
            }
        }

        stage('Run Sonar') {
            steps {
                withCredentials([string(credentialsId: 'sonarqube', variable: 'SONAR_TOKEN')]) {
                    sh 'mvn sonar:sonar -Dsonar.host.url=http://192.168.50.10:9000 -Dsonar.login=$SONAR_TOKEN'
                }
            }
        }

        stage('Maven Deploy') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'nexus-credentials', usernameVariable: 'NEXUS_USERNAME', passwordVariable: 'NEXUS_PASSWORD')]) {
                        sh '''
                            mvn deploy -Dusername=$NEXUS_USERNAME -Dpassword=$NEXUS_PASSWORD
                        '''
                    }
                }
            }
        }

        stage('Login to Docker Registry') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'dockerhub', usernameVariable: 'DOCKERHUB_CREDENTIALS_USR', passwordVariable: 'DOCKERHUB_CREDENTIALS_PSW')]) {
                        sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'
                    }
                }
            }
        }

        stage('Build & Push Docker Image') {
            steps {
                script {
                    dir("${WORKSPACE}") {
                        sh "docker build -t ${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_VERSION} ."
                        sh "docker push ${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_VERSION}"
                    }
                }
            }
        }

        stage('JUNIT TEST with JaCoCo') {
            steps {
                sh 'mvn test jacoco:report'
                echo 'Test stage done'
            }
        }

        stage('Check JaCoCo Report') {
            steps {
                sh 'ls -alh target/site/jacoco/'  // Check if the JaCoCo report is generated
                echo 'Checked JaCoCo report generation'
            }
        }

        stage('Collect JaCoCo Coverage') {
            steps {
                jacoco(execPattern: 'target/jacoco.exec')  // Collect the JaCoCo coverage
                echo 'JaCoCo Coverage collected successfully'
            }
        }

        stage('Docker Compose') {
            steps {
                sh "docker-compose up -d"
                sh 'sleep 60' // Adjust the sleep time based on your application's startup time
            }
        }
    }

    post {
        failure {
            echo 'Pipeline encountered an error.'
        }
    }
}
