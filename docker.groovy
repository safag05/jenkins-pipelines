node {
	properties(
		[parameters(
			[choice(choices: 
				[
				'v0.1', 
				'v0.2', 
				'v0.3', 
				'v0.4', 
				'v0.5'], 
	description: 'Which version of the app should I deploy? ', 
		name: 'Version'), 
			choice(choices: 
			[
			'dev.safa-g.com', 
			'qa.safa-g.com', 
			'stage.safa-g.com', 
			'prod.safa-g.com'], 
	description: 'Please provide an environment to build the application', 
		name: 'Environment')])])
	timestamps {
	stage("Stage2"){
		timestamps {
			git 'https://github.com/farrukh90/packer.git'
	}
}
	stage("Stage2"){
		timestamps {
			ws {
				echo "hello"
		}
	}
}
	stage("Stage3"){
		timestamps {
			ws {
				echo "hello"
		}
	}
}
	stage("Stage4"){
		timestamps {
			ws {
				echo "hello"
		}
	}
}
	stage("Stage5"){
		timestamps {
			ws {
				withDockerContainer('centos') {
					echo "Hello"
                    sleep 20
				}
			}
		}
	}
	}		
}
