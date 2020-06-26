node {
	timestamps{
	stage("Stage1"){
		echo "hello"
	}
	}
	stage("Stage2"){
		echo "hello"
	}
	timestamps{
	stage("Stage3"){
		echo "hello"
	}
	}
	timestamps{
	stage("Ask for Input"){
		input 'Should I proceed?'
	}
	}
	timestamps{
	stage("Stage4"){
		echo "hello world"	
	}
	}
	timestamps{
	stage("stage5"){
		input 'Continue?'
	}
        }
	stage("stage6"){
		echo 'Nice Work!'
	}
	stage("stage7"){
		input 'see the final result??'
		echo 'great job!!'
	}
	stage("stage8"){
		input 'continue?'
	}
}

