1
2
node {
	properties(
		[parameters(
			[choice(choices: 
			[
				'0.1', 
				'0.2', 
				'0.3', 
				'0.4', 
				'0.5'], 
	description: 'Which version of the app should I deploy? ', 
	name: 'Version')])])
	stage("Stage1"){
		timestamps {
			ws {
                checkout([$class: 'GitSCM', branches: [[name: '${Version}']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[url: 'https://github.com/farrukh90/artemis.git']]])	
    	}
	}
}
	stage("Get Credentials"){
		timestamps {
			ws{
				sh '''
					aws ecr get-login-password --region eu-west-2 | docker login --username AWS --password-stdin 620911902775.dkr.ecr.eu-west-2.amazonaws.com/artemis
					'''
		    }
	    }
    }
    stage("Build Docker Image"){
		timestamps {
			ws {
				sh '''
					docker build -t artemis:${Version} .
					'''
		    }
	    }
    }
    stage("Tag Image"){
		timestamps {
			ws {
				sh '''
                    docker tag artemis:${Version} 620911902775.dkr.ecr.eu-west-2.amazonaws.com/artemis:${Version}                   
                '''
				}
			}
		}
    stage("Push Image"){
	    timestamps {
			ws {
				sh '''
					docker push 620911902775.dkr.ecr.eu-west-2.amazonaws.com/artemis:${Version}
					'''
				}
			}
		}
    stage("Send slack notifications"){
		timestamps {
			ws {
					echo "Slack"
					//slackSend color: '#BADA55', message: 'Hello, World!'
				}
			}
		}
	}
