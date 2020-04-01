node {
	properties([
		// Below line sets "Discard Builds more than 5"
		buildDiscarder(logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '', numToKeepStr: '5')), 
		
		// Below line triggers this job every minute
		pipelineTriggers([pollSCM('* * * * *')]),
		parameters([choice(choices: [
			'dev1.safa-g.com', 
			'qa1.safa-g.com', 
			'stage1.safa-g.com', 
			'prod1.safa-g.com'], 
			description: 'Please choose an environment', 
			name: 'ENVIR')]), 
		])
	stage("Pull Repo"){
		git   'https://github.com/farrukh90/cool_website.git'
	}
        //Installs web server on different environment
	stage("Install Prerequisites"){
		sh """
		ssh centos@jenkins_worker1.${ENVIR}.com                 sudo yum install httpd -y
		"""
	}
        //Copies over developers files to different environment
	stage("Copy artifacts"){
		sh """
		scp -r *  centos@jenkins_worker1.${ENVIR}.com:/tmp
		ssh centos@jenkins_worker1.${ENVIR}.com                 sudo cp -r /tmp/index.html /var/www/html/
		ssh centos@jenkins_worker1.${ENVIR}.com                 sudo cp -r /tmp/style.css /var/www/html/
		ssh centos@jenkins_worker1.${ENVIR}.com				   sudo chown centos:centos /var/www/html/
		ssh centos@jenkins_worker1.${ENVIR}.com				   sudo chmod 777 /var/www/html/*
		"""
	}
        //Restarts web server
	stage("Restart web server"){
		sh "ssh centos@jenkins_worker1.${ENVIR}.com                 sudo systemctl restart httpd"
	}
        //Sends a message to slack
	stage("Slack"){
		slackSend color: '#BADA55', message: 'Hello, World!'
	}
}