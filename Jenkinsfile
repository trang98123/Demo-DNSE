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

        stage('check file') {
          steps {
            sh 'mvn -version'
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

    stage('clean') {
      steps {
        bat 'mvn clean -DBROWSER_NAME=CHROME -DTEST_ENVIRONMENT=DEV'
      }
    }

    stage('test chrome') {
      parallel {
        stage('test chrome') {
          steps {
            bat 'mvn test -DBROWSER_NAME=CHROME -DTEST_ENVIRONMENT=DEV'
          }
        }

        stage('test firefox') {
          steps {
            bat 'mvn test -DBROWSER_NAME=FIREFOX -DTEST_ENVIRONMENT=DEV'
          }
        }

      }
    }

  }
}