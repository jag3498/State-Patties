$(document).ready(function(){

    $.get("/restaurants", function(data, status){
        $(data).each(function(index, restaurant) {

            $("#persons").append("<li>" + restaurant.name + "</li>");

        });
    });

});