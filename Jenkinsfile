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
    if(env.BRANCH_NAME == 'master'){
        stage('Push Image to Dockerhub') {
          steps {
            withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: 'dockerhub', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD']]) {
              sh 'mvn docker:push -Ddocker.password=${PASSWORD} -Ddocker.username=${USERNAME}'
             }
          }
        }
    }
    stage('Cleanup') {
      steps {
        cleanWs(cleanWhenAborted: true, cleanWhenFailure: true, cleanWhenNotBuilt: true, cleanWhenSuccess: true, cleanWhenUnstable: true, cleanupMatrixParent: true, deleteDirs: true)
      }
    }
  }
}