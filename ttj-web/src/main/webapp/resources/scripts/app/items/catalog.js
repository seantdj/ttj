/**
 * Created by sean on 2016/7/19.
 */
$(function(){

    $('.nav-sidebar > li').click(function () {
        if (!$(this).hasClass("active")) {
            $('.nav-sidebar > li.active > div.active').removeClass('active');
            $('.nav-sidebar > li.active > ul').slideUp("slow");
            $('.nav-sidebar > li.active').removeClass('active');
            $(this).addClass("active");
            $('.nav-sidebar > li.active > ul').slideDown("slow");
            $('.nav-sidebar > li.active > div').addClass('active');
        }
    });
    $("#sidebar-toggle").click(function (e) {
        $("#wrap").toggleClass("toggled");
    });
});