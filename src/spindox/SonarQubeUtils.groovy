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
        withCredentials([string(credentialsId: 'b47e8268-8ed2-458f-8078-5c3a9b8420b2', variable: 'SONAR_TOKEN')]) {
            
            steps.echo "=== Avvio scansione Sonar per repo: ${repoName} ==="
    
            steps.stage("Checkout") {
                steps.echo "DEBUG: URL = https://github.com/pepperizza/${repoName}.git"
                steps.git(
                    url: "https://github.com/pepperizza/${repoName}.git",
                    branch: "main"
                )
            }
    
            withSonarQubeEnv('sonarqube') {

                    bat """
                    "C:\\SonarScanner\\sonar-scanner-cli-7.3.0.5189-windows-x64\\bin\\sonar-scanner.bat" ^
                      -Dsonar.projectKey=${repoName} ^
                      -Dsonar.projectName="${repoName}" ^
                      -Dsonar.sources=. ^
                      -Dsonar.host.url=%SONAR_HOST_URL% ^
                      -Dsonar.token=%SONAR_TOKEN%
                    """
                }
    
            steps.echo "=== Scansione completata per ${repoName} ==="
        }
    }
}

