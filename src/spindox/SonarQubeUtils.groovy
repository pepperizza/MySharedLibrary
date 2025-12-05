package spindox

class SonarQubeUtils implements Serializable {

    def steps

    SonarQubeUtils(steps) {
        this.steps = steps
    }

    /**
     * Recupera repo GitHub e lancia SonarScanner
     * @param repoName = nome del repository GitHub
     */
    def scanProject(String repoName) {

        steps.echo "=== Avvio scansione Sonar per repo: ${repoName} ==="

        steps.stage("Checkout") {
            steps.echo "DEBUG: URL = https://github.com/pepperizza/${repoName}.git"
            steps.git(
                url: "https://github.com/pepperizza/${repoName}.git",
                branch: "main"
            )
        }

        steps.stage("Sonar Scanner") {
          
          steps.bat """
              sonar-scanner.bat ^
              -Dsonar.projectKey=${repoName} ^
              -Dsonar.sources=. ^
              -Dsonar.host.url=%SONAR_HOST_URL% ^
              -Dsonar.login=%SONAR_TOKEN%
          """
        }

        steps.echo "=== Scansione completata per ${repoName} ==="
    }
}

