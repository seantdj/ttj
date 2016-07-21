/**
 * Created by sean on 2016/7/19.
 */
$(function () {
    console.log($("#view-menu"))
    $("#view-menu").click(function(e){$("#wrap").toggleClass("toggled")})
    $("#sidebar-close").click(function(e){$("#wrap").removeClass("toggled")})
});