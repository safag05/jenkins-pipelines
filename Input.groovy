node {
	timestamps{
	stage("Stage1"){
		echo "hello"
	}
	}
	stage("Stage2"){
		echo "hello"
	}
	stage("Stage3"){
		echo "hello"
	}
	stage("Ask for Input"){
		input 'Should I proceed?'
	}
	stage("Stage4"){
		echo "hello world"	
	}
	stage("stage5"){
		input 'Continue?'
	}
	stage("stage6"){
		echo 'Nice Work!'
	}
	stage("stage7"){
		echo 'great job!!'
	}
}

