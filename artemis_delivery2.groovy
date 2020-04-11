node {
	properties(
		[buildDiscarder(logRotator(artifactDaysToKeepStr: '', 
		artifactNumToKeepStr: '', 
		daysToKeepStr: '', 
		numToKeepStr: '5')), 
		disableConcurrentBuilds(),
		parameters(
			[choice(choices: 
				[
				'0.1', 
				'0.2', 
				'0.3', 
				'0.4', 
				'0.5',
				'0.6',
				'0.7',
				'0.8',
				'0.9',
				'10',
			], 

		description: 'Which version of the app should I deploy? ', 
		name: 'Version')])])
		stage("Stage1"){
			timestamps {
				ws {
                    checkout([$class: 'GitSCM', branches: [[name: '${Version}']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[url: 'https://github.com/farrukh90/artemis.git']]])			}
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
		stage("Authenticate"){
			timestamps {
				ws {
					sh '''
						ssh centos@dev1.safa-g.com $(aws ecr get-login --no-include-email --region eu-west-2)
						'''
				}
			}
		}
		stage("Clean Up"){
			timestamps {
				ws {
					try {
						sh '''
							#!/bin/bash
							IMAGES=$(ssh centos@dev1.safa-g.com docker ps -aq) 
							for i in \$IMAGES; do
								ssh centos@dev1.safa-g.com docker stop \$i
								ssh centos@dev1.safa-g.com docker rm \$i
							done 
							'''
					} catch(e) {
						println("Script failed with error: ${e}")
						}
					}
				}
			}
		
	stage("Run Container"){
		timestamps {
			ws {
				sh '''
					ssh centos@dev1.safa-g.com docker run -dti -p 5001:5000 620911902775.dkr.ecr.eu-west-2.amazonaws.com/artemis:${Version}
					'''
            }
        }
    }
}