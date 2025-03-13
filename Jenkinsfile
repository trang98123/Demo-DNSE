pipeline {
  agent any
  stages {
    stage('log tool version') {
      parallel {
        stage('Log Tool version') {
          steps {
            sh '''mvn -version
git --version
java --version'''
          }
        }

        stage('Check For POM') {
          steps {
            fileExists 'pom.xml'
          }
        }

      }
    }

    stage('Build with Maven') {
      steps {
        sh 'mvn compile test package'
      }
    }

    stage('Post Build Steps') {
      steps {
        writeFile(file: 'status.txt', text: 'Hey it worked!!')
      }
    }

  }
}