'use strict';
var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('#insertMessages');
//var connectingElement = document.querySelector('#connecting');
var lastSender="";
 
var stompClient = null;
var username = null;
  
 
function connect() {
    username = document.querySelector('#username').innerText.trim();
      
    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
 
    stompClient.connect({}, onConnected, onError );
}
 
// Connect to WebSocket Server.
connect();
function onConnected() {
    // Subscribe to the Public Topic
    stompClient.subscribe('/topic/'+chatTopic, onMessageReceived);
    // Tell your username to the server
    stompClient.send("/app/chat.addUser/"+chatTopic,
        {"chatTopic":chatTopic},
        JSON.stringify({sender: username, type: 'JOIN'})
    )
 
   // connectingElement.classList.add('hidden');
}
 
 
 function disconnect() {
 alert("in disconnect");
    stompClient.send("/app/chat.addUser/"+chatTopic,
        {"chatTopic":chatTopic},
        JSON.stringify({sender: username, type: 'LEAVE'})
    )
    stompClient.unsubscribe();
}
 
function onError(error) {
   // connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
   // connectingElement.style.color = 'red';
}
 
 
function sendMessage(event) {
    var messageContent = messageInput.value.trim();
    if(messageContent && stompClient) {
        var chatMessage = {
            sender: username,
            content: messageInput.value,
            type: 'CHAT'
        };
        stompClient.send("/app/chat.sendMessage/"+chatTopic, {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    }
    event.preventDefault();
}
 
 
 

 
function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);
 	
 	if (message.type === 'CHAT')
 	{
	 	if(message.sender === username)
	 	{
	 	//<span class="time_date"> 11:01 AM    |    June 9</span>
	        var messageElement = document.createElement('div');
		    messageElement.className = "outgoing_msg";
		    
		    var messageElement_child = document.createElement('div');
		    messageElement_child.className = "sent_msg";
		    var messageElement_content = document.createElement('p');
	   		messageElement_content.innerText = message.content;
	   		
	   		messageElement_child.appendChild(messageElement_content);
	   		
	   		messageElement.appendChild(messageElement_child);
	   		
	   		lastSender = message.sender;
	 	}
	 	else
	 	{
		 	var messageElement = document.createElement('div');
		    messageElement.className = "incoming_msg";
		    
		 	var messageElement_child = document.createElement('div');
		    messageElement_child.className = "received_msg";
		    
		    var messageElement_child_child = document.createElement('div');
		    messageElement_child_child.className = "received_withd_msg";
		    
		    var messageElement_content = document.createElement('p');
	   		messageElement_content.innerText = message.content;
	   		if(lastSender != message.sender)
	   		{
		   		var messageElement_content1 = document.createElement('strong');
		   		messageElement_content1.className ="primary-font";
		   		messageElement_content1.innerText = message.sender;
		   		lastSender = message.sender;
		   		messageElement_child_child.appendChild(messageElement_content1);
	   		}
	   		
	   		
		   	messageElement_child_child.appendChild(messageElement_content);
		   	
		   	messageElement_child.appendChild(messageElement_child_child);
		   	messageElement.appendChild(messageElement_child);
	   	}
	   	
	    
	   	
 	}
 	else if(message.type === 'JOIN')
 	{
 		var messageElement = document.createElement('div');
		messageElement.className = "outgoing_msg_copy sent_msg_copy";
		var messageElement_content = document.createElement('p');
	   	messageElement_content.innerText = message.sender + " Joined the Chat!!!";
		messageElement.appendChild(messageElement_content);
 	}
 	else
 	{
 	
 		var messageElement = document.createElement('div');
		messageElement.className = "outgoing_msg_copy sent_msg_copy";
		var messageElement_content = document.createElement('p');
	   	messageElement_content.innerText = message.sender + " Left the Chat!!!";
		messageElement.appendChild(messageElement_content);
 	}
    
    messageArea.appendChild(messageElement);
	messageArea.scrollTop = messageArea.scrollHeight;
}


  
messageForm.addEventListener('submit', sendMessage, true);