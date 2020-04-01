node {
    properties([
        // Below Line sets "Discard Builds more than 5"
        buildDiscarder(logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '', numToKeepStr: '5')), 
        
        // Below Line triggers this job every minute
        pipelineTriggers([pollSCM('* * * * *')])
        ])

stage("Pull Repo"){ 
git   'https://github.com/farrukh90/cool_website.git' 
} 
stage("Stage2"){ 
echo "hello" 
} 
stage("Stage3"){ 
echo "hello" 
} 
stage("Stage4"){ 
echo "hello" 
} 
stage("Stage5"){ 
echo "hello" 
} 
} 
