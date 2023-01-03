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
      discordSend description: 'Build PASSED ✅', enableArtifactsList: true, footer: 'ci.splitscale.systems', image: '', link: 'env.BUILD_URL', result: 'SUCCESS', scmWebUrl: 'https://github.com/splitscale/mockie', thumbnail: 'https://vignette.wikia.nocookie.net/disney/images/6/6b/Incredibles_2_-_Concept_Art_-_Mr._Incredible.jpg/revision/latest?cb=20180802115635', title: 'env.JOB_NAME', webhookURL: "${DISCORD_WEBHOOK_URL}"
     }

    failure {
      discordSend description: 'Build FAILED ❌', enableArtifactsList: true, footer: 'ci.splitscale.systems', image: '', link: 'env.BUILD_URL', result: 'FAILURE', scmWebUrl: 'https://github.com/splitscale/mockie', thumbnail: 'https://preview.redd.it/7yjyx0pq1s9a1.png?width=393&format=png&auto=webp&s=451ed3d625145389b1c0554e660382ed21e05f42', title: 'env.JOB_NAME', webhookURL: "${DISCORD_WEBHOOK_URL}"
     }
  }
}
