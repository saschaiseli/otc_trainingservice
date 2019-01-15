@Library('github.com/fabric8io/fabric8-pipeline-library@master') _

pipeline {
  agent {
    docker {
      image 'maven:3.5-jdk-8'
    }
  }
  stages {
    stage('Install Garmin') {
      steps {
        sh 'mvn clean'
      }
    }
    stage('JUnit Tests') {
      steps {
        sh 'mvn test -Drun.profiles=test'
        archive "target/**/*"
        junit 'target/surefire-reports/*.xml'
      }W
    }
    stage('Package') {
      steps {
        sh 'mvn package'
      }
    }
    stage('Build & Push TrainingService 2 Docker Hub') {
      steps {
        sh 'mvn docker:build docker:push'
      }
    }
    stage('Start Service and Database') {
      steps {
        sh 'mvn docker:run'
      }
    }
    stage('Stop Service and Database') {
      steps {
        sh 'mvn docker:stop'
      }
    }
    stage('Cleanup') {
      steps {
        cleanWs(cleanWhenAborted: true, cleanWhenFailure: true, cleanWhenNotBuilt: true, cleanWhenSuccess: true, cleanWhenUnstable: true, cleanupMatrixParent: true, deleteDirs: true)
      }
    }
  }
}