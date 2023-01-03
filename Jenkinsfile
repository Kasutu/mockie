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
        checkout([$class: 'GitSCM', branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[url: "https://github.com/splitscale/mockie.git"]]])
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
         sh "docker build -t splitscale/mockie:latest ."
      }
    }
	  
    stage('deploy') {
       steps {
         sh "docker run splitscale/mockie:latest"
      }
    }
	  
  }

}
