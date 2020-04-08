package controllers;

import io.ebean.*;
import models.Person;
import models.Restaurant;
import play.data.*;
import play.mvc.*;
import javax.inject.Inject;
import play.data.FormFactory;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import views.html.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static play.libs.Json.toJson;

public class LoginController extends Controller{

    private final Form<Person> form;

    @Inject
    public LoginController(FormFactory formFactory) {
        this.form = formFactory.form(Person.class);

    }

    /**
     * Checks if user exists and password matches.
     * @param name
     * @return
     */
    private boolean userCheck(String name, String password){
        //List<Person> userMatchestest = Person.find.all();
        ExpressionList<Person> userMatches = Person.find.query().where().eq("username", name).eq("password", password);
        List<Person> toList = userMatches.findList();
        Person user;
        if(!(toList.size() == 0)){

            return true;
        }
        return false;
    }
    //check if passwords match
    //get/set current person


    //check if currentPerson is null

    /**
     * Attempts to login with current information.
     * If successful, it will
     * @param request
     * @return
     */
    public Result loginAction(Http.Request request){
        final Form<Person> boundForm = form.bindFromRequest(request);
        Person person = boundForm.get();
        boolean check = false;

        if(!(person.username == null || person.password == null)){
            check = userCheck(person.username, person.password);
        }

        if(check){
            return redirect(routes.HomeController.index()).addingToSession(request, "connected", person.username);
        }else{
            System.out.println("Error, the username and password combination does not exist.");
        }
        return ok(login.render("The Username and Password combination does not exist."));
    }

    public Result logout() {
        return redirect(routes.HomeController.login()).withNewSession();
    }


        //https://www.playframework.com/documentation/2.7.x/JavaSessionFlash
        //this is a document on how to use sessions

    public Result whoami(Http.Request request){
        String response;
        return request.session().getOptional("connected").map(user -> ok("Welcome " + user))
                .orElseGet(() -> unauthorized("You are not connected"));
    }

    public Result whoamiJson(Http.Request request){
        String response = "guest";
        System.out.println("1");
        response = request.session().getOptional("connected").orElse("guest");
        System.out.println("user response " + response);
        return ok(toJson(response));
    }


    //for future auth actions in scala https://www.playframework.com/documentation/2.6.x/api/scala/index.html#play.api.mvc.Security$$AuthenticatedBuilder$
}
