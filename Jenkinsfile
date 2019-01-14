pipeline {
  agent {
    docker {
      image 'maven:3.5-jdk-8'
    }
    
  }
  stages {
    stage('Test') {
      steps {
        sh 'mvn install:install-file test -Drun.profiles=test'
        archive "target/**/*"
        junit 'target/surefire-reports/*.xml'
      }
    }
    stage('Package') {
      steps {
        sh 'mvn package'
      }
    }
    stage('Cleanup') {
      steps {
        cleanWs(cleanWhenAborted: true, cleanWhenFailure: true, cleanWhenNotBuilt: true, cleanWhenSuccess: true, cleanWhenUnstable: true, cleanupMatrixParent: true, deleteDirs: true)
      }
    }
  }
}