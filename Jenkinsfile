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
      }
    }

    stage('build') {
      steps {
        checkout([$class: 'GitSCM', branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[url: "https://github.com/splitscale/mockie.git"]]])
      }
    }

    stage('deploy') {
 
      steps {
        script {
	         sh 'mvn clean install'
        }
      }
    }
  }

  post {
    success {
      discordSend description: "${
				env.GIT_BRANCH} branch build PASSED ✅", 
				footer: "${env.BUILD_TAG}", image: '', 
				link: "https://kasutu-jenkins-dashboard.loca.lt/job/${env.JOB_NAME}", 
				result: 'SUCCESS', scmWebUrl: 'https://github.com/Kasutu/core-api', 
				showChangeset: true, thumbnail: 'https://imagepng.org/wp-content/uploads/2019/12/check-icone-2.png', 
				title: "${env.JOB_NAME}", 
				webhookURL: 'https://discord.com/api/webhooks/1043508882963697715/t6CcT0athX48p5XgioMamiQ4msmZKOwfdX18vJfdRIASRq6bRifgs66rxoVO8IGl4SxT'
    }

    failure {
      discordSend description: "${env.GIT_BRANCH} branch build FAILED ❌", 
				footer: "${env.BUILD_TAG}", image: '', 
				link: "https://kasutu-jenkins-dashboard.loca.lt/job/${env.JOB_NAME}", 
				result: 'FAILURE', scmWebUrl: 'https://github.com/Kasutu/core-api', 
				showChangeset: true, 
				thumbnail: 'https://external-content.duckduckgo.com/iu/?u=http%3A%2F%2Fvignette3.wikia.nocookie.net%2Frating-system%2Fimages%2F3%2F3f%2F450px-White_X_in_red_background.svg.png%2Frevision%2Flatest%3Fcb%3D20130326132551&f=1&nofb=1&ipt=1da84387ac09cf6684f8b3d4c2c9fe25fd43792eaabaeb38e817e84b7af0df7d&ipo=images', 
				title: "${env.JOB_NAME}", 
				webhookURL: 'https://discord.com/api/webhooks/1043508882963697715/t6CcT0athX48p5XgioMamiQ4msmZKOwfdX18vJfdRIASRq6bRifgs66rxoVO8IGl4SxT'
    }
  }
}
