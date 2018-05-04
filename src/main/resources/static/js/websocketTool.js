var websocket = null;
if('WebSocket' in window) {
    websocket = new WebSocket('ws://wm.limit-tech.com/wxorder/webSocket');
}else {
    alert('该浏览器不支持websocket!');
}

websocket.onopen = function (event) {
    console.log('建立连接');
}

websocket.onclose = function (event) {
    console.log('连接关闭');
}

websocket.onmessage = function (event) {
    console.log('收到消息:' + event.data)
    //弹窗提醒, 播放音乐
    $('#myModal').modal('show');

    document.getElementById('notice').play();
}

websocket.onerror = function () {
    alert('websocket通信发生错误！');
}

window.onbeforeunload = function () {
    websocket.close();
}