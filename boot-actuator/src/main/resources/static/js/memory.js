var obj=urlToJSON();
//图表更新时间间歇
var time=5000;

layui.use(['element','jquery'], function(){
    var element = layui.element,$=layui.jquery;
    var stompClient = null;
    function connect() {
        var socket = new SockJS('/socket');                      // 建立连接对象（还未发起连接）
        stompClient = Stomp.over(socket);                        // 获取 STOMP 子协议的客户端对象
        stompClient.connect({}, function(frame) {
            stompClient.subscribe('/topic/jvmMemory', function(data) {
                var jvm=JSON.parse(data.body);

                memory(jvm.jvmInfo);
                nonHeapinfo(jvm.nonHeapinfo);
                heapinfoClass(jvm);


                setTimeout(function () {
                    stompClient.send("/app/jvmMemory", {}, obj.key);
                }, time);
            });
            stompClient.send("/app/jvmMemory", {}, obj.key);

        }, function errorCallBack(error) {                          // 连接失败时（服务器响应 ERROR 帧）的回调方法

        });
    }

    $(function() {
        $("#actname").text(obj.name+"监控面板")
        // 监听信息
        connect();

    });
});


function memory(data){
    var myChart = echarts.init(document.getElementById('memory'),'shine');
// 指定图表的配置项和数据
    var option = {
        title : {
            subtext: '单位/MB',
            x:'center'
        },
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            left: 'left',
            data: ['JVM最大内存','JVM已用内存']
        },
        series : [
            {
                name: 'JVM内存监控',
                type: 'pie',
                radius : '55%',
                center: ['50%', '60%'],
                data:data,
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };
// 使用刚指定的配置项和数据显示图表。
 myChart.setOption(option);
}


//非堆内存
function nonHeapinfo(data) {
    var myChart = echarts.init(document.getElementById('nonHeapinfo'));
    var option = {
        title : {
            subtext: '单位/MB',
            x:'center'
        },
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b}: {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            x: 'left',
            data:['最大非堆内存','已使用非堆内存','元空间',"类指针压缩空间","代码缓存区"]
        },
        series: [
            {
                name:'nonHeap',
                type:'pie',
                radius: ['50%', '70%'],
                avoidLabelOverlap: false,
                label: {
                    normal: {
                        show: false,
                        position: 'center'
                    },
                    emphasis: {
                        show: true,
                        textStyle: {
                            fontSize: '12',
                            fontWeight: 'bold'
                        }
                    }
                },
                labelLine: {normal: {show: false}
                },
                data:[
                    {value:data.nonHeapMax, name:'最大非堆内存'},
                    {value:data.nonHeapUsed, name:'已使用非堆内存'},
                    {value:data.metaspace, name:'元空间'},
                    {value:data.compressedClassSpaceUsed, name:'类指针压缩空间'},
                    {value:data.codeCacheUsed, name:'代码缓存区'}
                ]
            }
        ]
    };

    myChart.setOption(option);
}


//堆内存
function heapinfoClass(data) {
    var myChart = echarts.init(document.getElementById('heapinfoClass'));
    var option = {
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b}: {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            x: 'left',
            data:['最大堆内存','已用堆内存','OldGe已用内存','Eden已用','Survivor已用','加载Class','卸载Class']
        },
        series: [
            {
                name:'class加载(次)',
                type:'pie',
                selectedMode: 'single',
                radius: [0, '30%'],

                label: {
                    normal: {
                        position: 'inner'
                    }
                },
                labelLine: {
                    normal: {
                        show: false
                    }
                },
                data:[
                    {value:data.classinfo.classLoaded, name:'加载class', selected:true},
                    {value:data.classinfo.unclassLoaded, name:'卸载class'},
                ]
            },
            {
                name:'heap堆区(单位/MB)',
                type:'pie',
                radius: ['40%', '55%'],
                label: {
                    normal: {
                        formatter: '{a|{a}}{abg|}\n{hr|}\n  {b|{b}：}{c}  {per|{d}%}  ',
                        backgroundColor: '#eee',
                        borderColor: '#aaa',
                        borderWidth: 1,
                        borderRadius: 4,
                        rich: {
                            a: {
                                color: '#999',
                                lineHeight: 22,
                                align: 'center'
                            },
                            hr: {
                                borderColor: '#aaa',
                                width: '100%',
                                borderWidth: 0.5,
                                height: 0
                            },
                            b: {
                                fontSize: 13,
                                lineHeight: 30
                            },
                            per: {
                                color: '#eee',
                                backgroundColor: '#334455',
                                padding: [2, 4],
                                borderRadius: 2
                            }
                        }
                    }
                },
                data:[
                    {value:data.heapinfo.heapMax, name:'最大堆内存'},
                    {value:data.heapinfo.heapUsed, name:'已用堆内存'},
                    {value:data.heapinfo.oldGeUsed, name:'OldGe已用内存'},
                    {value:data.heapinfo.endeSpaceUsed, name:'Eden已用'},
                    {value:data.heapinfo.survivorSpaceUsed, name:'Survivor已用'}
                ]
            }
        ]
    };
// 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
}