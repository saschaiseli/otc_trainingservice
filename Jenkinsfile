pipeline {
  agent any
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
      }
    }
    stage('Integration Tests') {
      steps {
        sh 'mvn verify -DskipTests=true'
      }
    }
    stage('Package') {
      steps {
        sh 'mvn install'
      }
    }
    stage('Docker Build') {
      steps {
        withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: 'dockerhub', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD']]) {
            sh 'mvn docker:build -Ddocker.password=${PASSWORD} -Ddocker.username=${USERNAME}'
        }
      }
    }
    stage('Start Service and Database') {
      steps {
        sh 'mvn docker:start'
      }
    }
    stage('Stop Service and Database') {
      steps {
        sh 'mvn docker:stop'
      }
    }
    stage('Push Image to Dockerhub (Develop)') {
      when{
         branch 'develop'
      }
      steps {
        withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: 'dockerhub', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD']]) {
          sh "docker build -t iselisa/trainingservice:env.BRANCH_NAME-${currentBuild.number}"
          sh "docker login  --username ${USERNAME} --password ${PASSWORD}"
          sh "docker push iselisa/trainingservice:env.BRANCH_NAME-${currentBuild.number}"
         }
      }
    }
    stage('Push Image to Dockerhub') {
      when{
         branch 'master'
      }
      steps {
        withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: 'dockerhub', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD']]) {
          sh "docker build -t iselisa/trainingservice:${currentBuild.number}"
          sh "docker login  --username ${USERNAME} --password ${PASSWORD}"
          sh "docker push iselisa/trainingservice:${currentBuild.number}"
         }
      }
    }
    stage('Start new Image') {
      when{
         branch 'master'
      }
      steps {
        sh "sh cleanrestart.ch ${currentBuild.number}"
      }
    }
    stage('Cleanup') {
      steps {
        cleanWs(cleanWhenAborted: true, cleanWhenFailure: true, cleanWhenNotBuilt: true, cleanWhenSuccess: true, cleanWhenUnstable: true, cleanupMatrixParent: true, deleteDirs: true)
      }
    }
  }
}