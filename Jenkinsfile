pipeline {
  agent {
    docker {
      image 'maven:3.5-jdk-8'
    }
    
  }
  stages {
    stage('Test') {
      steps {
        sh 'mvn test -Drun.profiles=test'
        archive "target/**/*"
        junit 'target/surefire-reports/*.xml'
      }
    }
    stage('Integration Tests') {
      steps {
        sh 'mvn verify -Drun.profiles=test'
        archive "target/**/*"
        junit 'target/surefire-reports/*.xml'
      }
    }
    stage('Start the server') {
      steps {
      script{
          sh """
                    docker build -t iselisa/trainingservice .
                    docker rm -f trainingservice
                    docker run -it --rm -p 8282:8080 -p 9999:9990 -e DB_USERNAME=$DB_USER_DEV -e DB_PASSWORD=$DB_PASS_DEV -e DB_HOST=$DB_HOST_DEV -e DB_PORT=$DB_PORT_DEV -e DB_DATABASE=$DB_DATABASE_DEV iselisa/trainingservice
                    
                """
      }
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