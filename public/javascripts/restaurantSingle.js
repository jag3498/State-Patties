$(document).ready(function(){

    $.get("/restaurants", function(data, status){
        $(data).each(function(index, restaurant) {

            if(restaurant.id == restId){

                $("#title").text(restaurant.name);

                $(restaurant.reviews).each(function(index, review) {
                    $("#list").append("<li class=\"list-group-item\">\n" +
                        '<a href="restaurantSingle\\' +  + restaurant.id + '">' +
                        "<div class=\"d-flex w-100 justify-content-between\">\n" +
                        "<h5 class=\"mb-1\">" + restaurant.name +
                        "</h5>\n" +
                        "</div>\n" +
                        "</a>\n" +
                        "</li>");

                });

            }



        });
    });


});