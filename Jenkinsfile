def runServer() {
  sh 'docker run --name mockie -p 32615:32615 -d splitscale/mockie:latest'
}

pipeline {
    agent any

    tools {
        maven 'Maven'
    }

    stages {
        stage('initialize') {
      steps {
        sh 'mvn -v'
        sh 'java -version'
        sh 'git --version'
        sh 'docker -v'
      }
        }

        stage('pull') {
      steps {
        checkout([$class: 'GitSCM', branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/splitscale/mockie.git']]])
      }
        }

        stage('install') {
      steps {
        script {
          sh 'mvn clean install'
        }
      }
        }

        stage('build docker image') {
      steps {
        sh 'docker build -t splitscale/mockie:latest .'
      }
        }

        stage('deploy') {
      steps {
        script {
          try {
            runServer()
                    } catch (Exception e) {
            sh 'docker stop mockie'
            sh 'docker rm mockie'
            runServer()
          }
        }
      }
        }
    }

  post {
    success {
      discordSend description: '', footer: '', image: '', link: 'env.BUILD_URL', result: 'SUCCESS', scmWebUrl: '', thumbnail: '', title: 'env.JOB_NAME', webhookURL: "${DISCORD_WEBHOOK_URL}"
    }

    failure {
      discordSend description: '', footer: '', image: '', link: 'env.BUILD_URL', result: 'FAILURE', scmWebUrl: '', thumbnail: '', title: 'env.JOB_NAME', webhookURL: "${DISCORD_WEBHOOK_URL}"
    }
  }
}
