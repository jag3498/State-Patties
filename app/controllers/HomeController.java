package controllers;

import io.ebean.Model;
import models.Person;
import models.Restaurant;
import play.data.*;
import play.mvc.*;
import javax.inject.Inject;
import play.data.FormFactory;

import views.html.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static play.libs.Json.toJson;


/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */





public class HomeController extends Controller {

   /* priarticlevate final Form<User> form;

    @Inject
    public HomeController(FormFactory formFactory) {
        this.form = formFactory.form(Person.class);

    }
*/
    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */



    public Result index() {
        return ok(index.render("Hello World"));
    }

    public Result signUp() {
        return ok(person.render(""));
    }

    public Result login(){
        return ok(login.render(""));
    }

    public Result restaurantAdmin(Http.Request request) {
        String user = request.session().getOptional("connected").orElse("guest");
        System.out.println("user "+user+ "  " + user.toLowerCase());
        if(user.toLowerCase().equals("admin")){
            System.out.println(user+" is viewing restaurant Admin Page");
            return ok(restaurantAdmin.render(""));
        }
        return redirect(routes.HomeController.restaurantPage());
    }

    /*
    public Result review(){
        return ok(review.render(""));
    }
    */

    public Result search(){
        return ok(search.render(""));
    }

    public Result restaurantPage() {
        return ok(restaurant.render(""));
    }

    @Inject FormFactory formFactory;
    public Result addPerson(Http.Request request){

        Form<Person> personForm = formFactory.form(Person.class).bindFromRequest(request);
        Person person = personForm.get();

        //Person person = formFactory.form(Person.class).bindFromRequest().get();
        person.save();
        return redirect(routes.HomeController.index());

    }

    public Result getPersons(){
        //List<Person> persons = new Model.Finder(String.class, Person.class).all();

        List<Person> persons = Person.find.all();

        return ok(toJson(persons));
    }

    public Result addRestaurant(Http.Request request){
        Form<Restaurant> restaurantForm = formFactory.form(Restaurant.class).bindFromRequest(request);
        Restaurant restaurant = restaurantForm.get();

        restaurant.save();
        
        return redirect(routes.HomeController.index());
    }
    
    public Result getRestaurants(){
        List<Restaurant> restaurants = Restaurant.find.all();
        
        return ok(toJson(restaurants));
    }
}
