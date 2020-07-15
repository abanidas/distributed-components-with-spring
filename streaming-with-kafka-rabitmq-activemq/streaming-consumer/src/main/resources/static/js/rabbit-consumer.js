var stompClient = null;
function connect(){
    var socket = new SockJS('/consumer/consume');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, onConnected, onError);
}

function onError(event){
    alert(event);
}

function onConnected() {
    stompClient.subscribe('/topic.socket.rabbit', onMessageReceived);
}

function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);
    var tableBody = document.getElementById("tableBody");
    var newRow = tableBody.insertRow();
    var cell1 = newRow.insertCell(0);
    cell1.appendChild(document.createTextNode(message.message));
    var cell2 = newRow.insertCell(1);
    cell2.appendChild(document.createTextNode(message.time));
}

window.onload = connect;