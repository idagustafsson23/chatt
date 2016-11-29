var wsocket;
var serviceLocation = "ws://localhost:8080/chatt/chat/";

var $nickName;
var $message;
var $chatWindow;

function onMessageReceived(evt) {
	var msg = JSON.parse(evt.data); // native API
	var $messageLine = $('<tr><td class="received">' + msg.received
			+ '</td><td class="user label label-info">' + msg.sender
			+ '</td><td class="message badge">' + msg.message + '</td></tr>');
	var splitUsers = msg.users.split(/[^0-9a-zA-Z]/);
	var stringUsers = "";
	for (var i = 0; i < splitUsers.length; i++) {
		if(splitUsers[i] !== ""){
			stringUsers = stringUsers + splitUsers[i] + "<br/ >";
		}
		
	}
	document.getElementById("users").innerHTML = stringUsers;
	$chatWindow.append($messageLine);
}

function sendMessage() {
	var msg = '{"message":"' + $message.val() + '", "sender":"'
			+ $nickName.val() + '", "received":""}';
	wsocket.send(msg);
	$message.val('').focus();
}

$(document).ready(function() {
	$nickName = $('#userName');
	$message = $('#message');
	$chatWindow = $('#response');

	// från connectToChatServer
	wsocket = new WebSocket(serviceLocation);
	wsocket.onmessage = onMessageReceived;

});