var obj=urlToJSON();
//更新时间间歇
var time=5000;
layui.use(['element','jquery'], function(){
    var element = layui.element,$=layui.jquery;

    var stompClient = null;
    function connect() {
        var socket = new SockJS('/socket');                      // 建立连接对象（还未发起连接）
        stompClient = Stomp.over(socket);                        // 获取 STOMP 子协议的客户端对象
        stompClient.connect({}, function(frame) {
            stompClient.subscribe('/topic/processInfo', function(data) {
                var info=(JSON.parse(data.body).info);
                $("#uptime").text(info.uptime);
                $("#fileOpen").text(info.fileOpen);
                $("#loadAver").text(info.loadAverage);
                setTimeout(function () {
                    stompClient.send("/app/processInfo", {}, obj.key);
                }, time);
            });
            stompClient.send("/app/processInfo", {}, obj.key);

        }, function errorCallBack(error) {                          // 连接失败时（服务器响应 ERROR 帧）的回调方法

        });
    }

    $(function() {
        $("#actname").text(obj.name+"监控面板")
        // 监听信息
        connect();

        $.ajax({
            type: "POST",
            url: baseURl + "/getCpuAndfilesMax",
            dataType: "json",
            data: {"url":obj.key},
            success: function (data) {
                if(data.code==0){
                    $("#cpuCount").text(data.cpuCount);
                    $("#filesMax").text(data.filesMax);
                }
            }
        });

    });
});