let source = null;
let sender = null;
const host = window.location.hostname;

function login(uname) {
    sender = uname;
    setMessageInnerHTML("登录成功");
    if (!!window.EventSource) {
        source = new EventSource('http://' + host +':8080/sse/connect?uname=' + uname);

        source.addEventListener('message', function (e) {
            setMessageInnerHTML(e.data);
        });

        source.addEventListener('error', function (e) {
            if (e.readyState === EventSource.CLOSED) {
                setMessageInnerHTML("连接关闭");
            } else {
                console.log(e);
            }
        }, false);

    } else {
        setMessageInnerHTML("你的浏览器不支持SSE");
    }

    window.onbeforeunload = function () {
        closeSse();
    };
}

function closeSse() {
    source.close();
    const httpRequest = new XMLHttpRequest();
    httpRequest.open('GET', 'http://' + host +':8080/sse/close?uname=' + sender, true);
    httpRequest.send();
    setMessageInnerHTML("登出成功");
}

function connectWithUser(receiver) {
    $.post(`http://' + host +':8080/user/connect?sender=${sender}&receiver=${receiver}`, ()=>{
        setMessageInnerHTML(`开始与${receiver}的聊天`)
    });
}

function listUsers() {
    $.get('http://' + host +':8080/user/list', (data)=>{
        setMessageInnerHTML("当前在线用户：" + data)
    });
}

function sendMessage(content) {
    $.ajax('http://' + host +':8080/message/send', {
        type: 'post',
        data: JSON.stringify({sender: sender, content: content}),
        contentType: 'application/json;charset=UTF-8'
    });
}

// 将消息显示在网页上
function setMessageInnerHTML(innerHTML) {
    $('#message')[0].innerHTML += innerHTML + '<br/>';
}