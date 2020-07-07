'use strict';

var divJoin = document.getElementById('divJoin');
var divChat = document.getElementById('divChat');
var formUser = document.getElementById('formUser');
var formChat = document.getElementById('formChat');
var messageInput = document.getElementById('message');
var messageArea = document.getElementById('messageArea');
var leaveArea = document.getElementById('messageArea');
var connectingElement = document.querySelector('.connecting');

var stompClient = null;
var username = null;

var colors = ['#2196F3', '#32c787', '#00BCD4', '#ff5652', '#ffc107', '#ff85af', '#FF9800', '#39bbb0'];

//Event when first trying to connect to public group chat
function connect(event) {
    username = document.getElementById('name').value.trim();

    if(username) {
        divJoin.classList.add('d-none');
        divChat.classList.remove('d-none');

        var socket = new SockJS('/user');
        stompClient = Stomp.over(socket);

        stompClient.connect({}, onConnected, onError);
    }
    event.preventDefault();
}

//Event on successful connection
function onConnected() {

    stompClient.subscribe('/topic/group', onMessageReceived);
    stompClient.send("/chat/join", {}, JSON.stringify({sender: username, type: 'JOIN'}));
    connectingElement.classList.add('d-none');
}

function onError(error) {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}

//New message sending event
function send(event) {
    var messageContent = messageInput.value.trim();

    if(messageContent && stompClient) {
        var chatMessage = {
            sender: username,
            content: messageInput.value,
            type: 'CHAT'
        };

        stompClient.send("/chat/send", {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    }
    event.preventDefault();
}

//Event on receiving new message
function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);

    var messageElement = document.createElement('div');
    messageElement.classList.add('card');
    messageElement.classList.add('m-2');

    var chatItemElement = document.createElement('div');
    chatItemElement.classList.add("card-body");

    if(message.type === 'JOIN') {
        message.content = message.sender + ' joined!';

    } else if (message.type === 'LEAVE') {
        message.content = message.sender + ' left!';

    } else {

        chatItemElement.style['background-color'] = getBackgroundColor(message.sender);

        var usernameElement = document.createElement('strong');
        var usernameText = document.createTextNode(message.sender);
        usernameElement.appendChild(usernameText);
        chatItemElement.appendChild(usernameElement);
        chatItemElement.appendChild(document.createElement('br'));
    }

    var textElement = document.createElement('span');
    var messageText = document.createTextNode(message.content);
    textElement.appendChild(messageText);
    chatItemElement.appendChild(textElement);
    messageElement.appendChild(chatItemElement);

    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
}

//Generate unique background color using the sender username
function getBackgroundColor(messageSender) {
    var hashCode = 0;
    for (var i = 0; i < messageSender.length; i++) {
        hashCode = 31 * hashCode + messageSender.charCodeAt(i);
    }
    var index = Math.abs(hashCode % colors.length);
    return colors[index];
}

//initiate event listeners
formUser.addEventListener('submit', connect, true)
formChat.addEventListener('submit', send, true)
