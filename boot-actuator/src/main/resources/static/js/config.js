var baseURl="http://localhost:8083";


//将url变成json数据
function urlToJSON() {
    //url=？xx=yy&ff=jj格式值
    //将url变成json数据
    var k_v = decodeURI(window.location.search).substring(1).split("&");
    var data = {};
    for (var i = 0; i < k_v.length; i++) {
        var arr = k_v[i].split("=");
        data[arr[0]] = arr[1];
    }
    return data;
};

