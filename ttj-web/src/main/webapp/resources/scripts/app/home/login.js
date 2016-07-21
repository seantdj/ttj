$(function () {
  $('button').on('click', function () {
    var param = {
      login_code:$('input:first').val(),
      password:$('input:last').val()
    };
    ajaxComm("/doLogin",param)
      .done(function () {
        if(this.success)
          location.href = '/home/index';
        else
          console.error(this.message);
      });
  })
})