package controllers;

import io.ebean.ExpressionList;
import models.*;
import play.data.Form;
import play.data.FormFactory;
import play.libs.ws.WSBodyWritables;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import views.html.index;
import views.html.restaurantSingle;
import play.libs.ws.WSClient;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionStage;

import static play.libs.Json.toJson;

public class RestaurantController extends Controller {

    private final Form<Restaurant> form;

    @Inject
    public RestaurantController(FormFactory formFactory) {
        this.form = formFactory.form(Restaurant.class);

    }

    /**
     * Loads the View of a single restaurant.
     * @param id id of the restaurant
     * @param request
     * @return
     */
    public Result restaurantSingle(int id, Http.Request request) {
        String user;
        user = request.session().getOptional("connected").orElse("guest");


        List<Review> reviews = getReviews(id);
       List<ReviewDisplay> disReviews = new ArrayList<ReviewDisplay>();
        for(int i = 0; i < reviews.size(); i++){
            Review theReview = reviews.get(i);
            //review fetch
            ExpressionList<Review> reviewMatch = Review.find.query().where().eq("id", theReview.id);
            List<Review> toList = reviewMatch.findList();
            Review reviewObj = toList.get(0);
            //user fetch
            boolean voted;
            if(user != "guest"){
                ExpressionList<Person> userMatch = Person.find.query().where().eq("username", user);
                List<Person> toList2 = userMatch.findList();
                Person personObj = toList2.get(0);
                //boolean logic
                 voted = Vote.find.query().where().eq("review", reviewObj).eq("person", personObj).exists();
            }
            else {
                voted = true;
            }



            int vcount = Vote.find.query().where().eq("review", reviewObj).eq("person", theReview.person).findCount();
            int rating = (int)theReview.rating;
            ReviewDisplay theDisplayReview = new ReviewDisplay(i,theReview.id,theReview.person.username,theReview.text, voted, vcount);
            theDisplayReview.setRating(theReview.rating);
            disReviews.add(theDisplayReview);


        }

        return ok(restaurantSingle.render(id, disReviews, user));
    }

    @Inject WSClient ws;

    public CompletionStage<Result>  map(long id) {

        Restaurant restaurant = Restaurant.find.byId(id);

        String latLon = restaurant.location;

        String url = "https://maps.googleapis.com/maps/api/staticmap?center=" +
                latLon +
                "&zoom=15&size=300x300&maptype=roadmap" +
                "&markers=color:red%7Clabel:%7C" +
                latLon +
                "&key=AIzaSyDBgHKeKJ_1V0erRY_Hn3tOMTAJEx_xVHQ";
        System.out.println(url);

        return ws
                .url(url)
                .get()
                .thenApply(file -> ok(file.asByteArray()).as("image/jpeg"));



    }



    //get all resturants for the endpoint
    public Result getRestaurants(){

        return ok(toJson(getAll()));
    }

    //add restaurant
    public Result addRestaurant(Http.Request request){

        final Form<Restaurant> boundForm = form.bindFromRequest(request);

        Restaurant restaurant = boundForm.get();

        if(!restaurant.name.isEmpty()){
            restaurant.save();
        }

        return redirect(routes.HomeController.index());

    }

    //example methods of how to do common tasks

    public void add(Restaurant restaurant){
        restaurant.save();
    }

    public void remove(Restaurant restaurant){
        restaurant.delete();
    }

    public Restaurant get(Long id){
        Restaurant restaurant;
        return restaurant = Restaurant.find.byId(id);
    }

    public List<Restaurant> getAll(){
        List<Restaurant> restaurants = Restaurant.find.all();
        return restaurants;
    }

    /**
     * This method returns a list of reviews for a given restaurant
     * @param id is the id of the restaurant.
     * @return
     */
    public List<Review> getReviews(int id){
        Restaurant restaurant = Restaurant.find.byId((long)id);
        ExpressionList<Review> reviews = Review.find.query().where().eq("restaurant", restaurant);
        List<Review> toList = reviews.findList();
        System.out.println(toList);
        return toList;
    }

    /**
     * This method returns a burgerometer Rating for the restaurant.
     * @param id the id of the restaurant.
     * @return
     */
    public Result getBurgerometerRating(int id){
        double average = 0;
        double sum = 0;
        int count = 0;
        List<Review> reviews = getReviews(id);
        for(int i = 0; i < reviews.size(); i++ ){
            double temp = (double)reviews.get(i).rating;
            if(temp > 0){
                sum += temp;
                count++;
            }
        }
        average = sum/count;

        return ok(Json.toJson(average));
    }
}
