var wsocket;
var serviceLocation = "wss://localhost:8181/chatt/chat/";

var $nickName;
var $message;
var $decodeKey;
var $chatWindow;

function onMessageReceived(evt) {
	var msg = JSON.parse(evt.data); // native API
	var splitMsg = addNewlines(msg.message);
	var $messageLine = $('<br><div class="message">' + msg.received
			+ '<br>' + msg.sender + ' said: ' + splitMsg + '</div>');
	console.log(msg.users);
	var splitUsers = msg.users.split(/[^0-9a-zA-Z- ]/);
	var stringUsers = "";
	for (var i = 0; i < splitUsers.length; i++) {
		if(splitUsers[i] !== ""){
			stringUsers = stringUsers + splitUsers[i] + "<br/ >";
		}		
	}
	document.getElementById("users").innerHTML = stringUsers;
	$chatWindow.append($messageLine);
	
	$('#response').animate({scrollTop: $('#response').prop("scrollHeight")}, 500);
}

function addNewlines(str) {
	  var result = '';
	  while (str.length > 0) {
	    result += str.substring(0, 100) + '\n';
	    str = str.substring(100);
	  }
	  return result;
	}

function sendMessage() {
	var msg = '{"message":"' + $message.val() + '", "sender":"'
			+ $nickName.val() + '", "received":"", "decodeKey":"' + $decodeKey.val() + '"}';
	wsocket.send(msg);
	$message.val('').focus();
}

$(document).ready(function() {
	$nickName = $('#userName');
	$message = $('#message');
	$decodeKey = $('#decodeKey');
	$chatWindow = $('#response');

	// från connectToChatServer
	wsocket = new WebSocket(serviceLocation);
	wsocket.onmessage = onMessageReceived;

});