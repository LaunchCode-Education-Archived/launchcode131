var choicesArray = ["package", "class", "method", "incoming parameter", "object", "method call", "outgoing parameter"];
var pos = 0;
var wrong = 0;

function load()
{
	shuffle(choicesArray);
	document.getElementById('content').innerHTML='Click on the <b>' + choicesArray[pos] + '</b>';
}

//Fisher Yates Shuffle 
//Function Source: http://stackoverflow.com/a/962890
function shuffle(array) { 
    var tmp, current, top = array.length;

    if(top) while(--top) {
        current = Math.floor(Math.random() * (top + 1));
        tmp = array[current];
        array[current] = array[top];
        array[top] = tmp;
    }

    return array;
}


function processClick(clicked)
{
	if (pos == choicesArray.length - 1)
	{
		document.getElementById('content').innerHTML='Correct!<br>You have completed the quiz with ' + wrong + ' incorrect attempt(s)<br><a href=\'\' style=\'color:blue;text-decoration:underline\'>Take the Quiz Again</a>';
	}
	else{
		if (clicked.id == choicesArray[pos])
		{
			pos += 1;
			var s = 'Correct!<br>Now click on the <b>' + choicesArray[pos] + '</b>';
			document.getElementById('content').innerHTML=s;
		}
		else
		{
			wrong += 1;
			document.getElementById('content').innerHTML='Incorrect.  You selected the <b><span style="color:red">' + clicked.id + '</span></b>.<br>Please try and click on the <b>' + choicesArray[pos] + '</b> again';
		}
	}
}