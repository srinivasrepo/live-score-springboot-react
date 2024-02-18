pipeline {
    agent any
    tools{
        maven 'maven_3_9_0'
    }
    stages{
        stage('Build Maven'){
            steps{
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/srinivasrespo/live-score-springboot-react']])
                sh 'mvn clean install'
            }
        }
        stage('Build Docker Image'){
            steps{
                script{
                    sh 'docker build -t srinivasrespo/livescore:latest .'
                }
            }
        }
        stage('Push Image to DockerHub'){
            steps{
                script{
                    withCredentials([string(credentialsId: 'dockerhub-password', variable: 'dockerhub')]) {
                        sh 'docker login -u srinivasrespo -p ${dockerhub}'
                    }
                    sh 'docker push srinivasrespo/livescore:latest'
                }
            }
        }
    }
}
