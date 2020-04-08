package controllers;

import models.Person;
import models.Restaurant;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.List;

import static play.libs.Json.toJson;

public class PersonController extends Controller {

    private final Form<Person> form;

    @Inject
    public PersonController(FormFactory formFactory) {
        this.form = formFactory.form(Person.class);

    }

    //get all persons for the endpoint
    public Result getPersons(){

        return ok(toJson(getAll()));
    }

    //add restaurant
    public Result addPerson(Http.Request request){

        final Form<Person> boundForm = form.bindFromRequest(request);

        Person person = boundForm.get();

        if(!person.username.isEmpty()){
            person.save();
        }

        return redirect(routes.HomeController.index());

    }

    //example methods of how to do common tasks

    public void add(Person person){
        person.save();
    }

    public void remove(Person person){
        person.delete();
    }

    public Person get(Long id){
        Person person;
        return person = Person.find.byId(id);

    }

    public List<Person> getAll(){
        List<Person> persons = Person.find.all();
        return persons;
    }
}
