package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import io.ebean.*;
import models.Person;
import models.Restaurant;
import models.Review;
import models.Tag;
import play.data.*;
import play.libs.Json;
import play.mvc.*;
import javax.inject.Inject;
import play.data.FormFactory;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import views.html.*;

import java.lang.reflect.Array;
import java.util.*;

import static play.libs.Json.toJson;

public class SearchController extends Controller{

    private final Form<Tag> form;

    @Inject
    public SearchController(FormFactory formFactory) {
        this.form = formFactory.form(Tag.class);

    }


    public Result getTags(){

        return ok(toJson(getAll()));
    }


    public Result getTagByID(int reviewID){


        ExpressionList<Tag> tags = Tag.find.query().where().eq("review_id", reviewID);
        List<Tag> tagList = tags.findList();

        ArrayList<String> tagsAgain = new ArrayList<String>();

        for (Tag tag : tagList) {
            tagsAgain.add(tag.text);
        }

        System.out.println("got for id" + reviewID);

        return ok(Json.toJson(tagsAgain));


    }

    public Result search(){
        return ok(search.render(""));
    }

    public Result getSearch(String search){

        ExpressionList<Tag> tagList = Tag.find.query().where().eq("text", search);

        List<Tag> toList = tagList.findList();


        Iterator<Tag> tagsIT = toList.iterator();

        ArrayList<Restaurant> rests = new ArrayList<Restaurant>();

        while(tagsIT.hasNext()) {
            Restaurant restCurrent = tagsIT.next().review.restaurant;

            if(rests.contains(restCurrent)){

            }
            else{
                rests.add(restCurrent);
            }




            System.out.println(restCurrent.id);
        }

        return ok(Json.toJson(rests));
    }

    public List<Tag> getAll(){
        List<Tag> tags;
        tags = Tag.find.all();

        return tags;
    }

    public List<Tag> searchByTag(String searchTag){



        ExpressionList<Tag> tagResult = Tag.find.query().where().eq("text", searchTag);
        List<Tag> resultList = tagResult.findList();

        return resultList;
    }

}
