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
            steps.git url: "https://github.com/spindox/${repoName}.git", branch: "main"
        }

        steps.stage("Sonar Scanner") {
          
          steps.bat """
              sonar-scanner.bat ^
              -Dsonar.projectKey=${repoName} ^
              -Dsonar.sources=. ^
              -Dsonar.host.url=https://sonar.spindox.it ^
              -Dsonar.login=%SONAR_TOKEN%
          """
        }

        steps.echo "=== Scansione completata per ${repoName} ==="
    }
}

