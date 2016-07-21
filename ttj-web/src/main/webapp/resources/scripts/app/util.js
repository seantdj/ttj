Date.prototype.Format = function (fmt) {
  var o = {
    "M+": this.getMonth() + 1, //月份
    "d+": this.getDate(), //日
    "h+": this.getHours(), //小时
    "m+": this.getMinutes(), //分
    "s+": this.getSeconds(), //秒
    "q+": Math.floor((this.getMonth() + 3) / 3), //季度
    "S": this.getMilliseconds() //毫秒
  };
  if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
  for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
  return fmt;
};

function ajaxComm(url, param, async, dataType){
  var dfd = $.Deferred();
  var header = 'X-XSRF-TOKEN';//$("meta[name='_csrf_header']").attr("content");
  var token = Cookie.getCookie('XSRF-TOKEN');//$("meta[name='_csrf']").attr("content");
  $.ajax({
    type: "POST",
    url: url,
    async: async==false?false:true,
    data: param,
    //contentType: "application/json; charset=utf-8",
    dataType: dataType?dataType:"json",
    beforeSend: function (xhr) {
      xhr.setRequestHeader(header, token);
    },
    success: function (data) {
      dfd.resolveWith(data);
    },
    error: function (e) {
      var ret = {
        success: false,
        message: e
      };
      dfd.rejectWith(ret);
    }
  });
  return dfd.promise();
}

var Cookie = {
  setCookie:function(name,value,days)
  {
    var exp = new Date();
    exp.setTime(exp.getTime() + days*24*60*60*1000);
    document.cookie = name + "="+ value + ";expires=" + exp.toGMTString();
  },
  getCookie:function(name)
  {
    var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
    if(arr=document.cookie.match(reg))
      return unescape(arr[2]);
    else
      return null;
  },
  delCookie:function(name)
  {
    var exp = new Date();
    exp.setTime(exp.getTime() - 1);
    var cval=getCookie(name);
    if(cval!=null)
      document.cookie= name + "="+cval+";expires="+exp.toGMTString();
  }
};