
const socket = new WebSocket("ws://localhost:8080/WebNxtb/logWebSocket");


  socket.onmessage = function (event) {
      const logMessage = event.data;
      const textarea = document.getElementById("update");

      textarea.innerHTML += logMessage;

      textarea.scrollTop = textarea.scrollHeight;
  };