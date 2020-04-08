package controllers;

import io.ebean.Ebean;
import io.ebean.ExpressionList;
import io.ebean.Query;
import models.*;


import play.data.Form;
import play.data.FormFactory;
import play.data.DynamicForm;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.twirl.api.Content;
import views.html.restaurant;
import views.html.restaurantSingle;
import views.html.review;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static play.libs.Json.toJson;
import com.fasterxml.jackson.databind.JsonNode;

public class ReviewController extends Controller {

    private final Form<Review> form;
    private final Form<Vote> vForm;
    private final Form<ReviewPartial> rpForm;


    @Inject
    public ReviewController(FormFactory formFactory) {
        this.form = formFactory.form(Review.class);
        this.vForm = formFactory.form(Vote.class);
        this.rpForm = formFactory.form(ReviewPartial.class);

    }

    //get all persons for the endpoint
    public Result getReview(){

        return ok(toJson(getAll()));
    }

    /*
    //the old add restaurant
    public Result addReview(Http.Request request){

        final Form<Review> boundForm = form.bindFromRequest(request);

        Review review = boundForm.get();

        if(!review.text.isEmpty()){
            review.save();
        }

        return redirect(routes.HomeController.index());

    }
    */

    //add restaurant
    public Result addReview(Http.Request request){

        final Form<ReviewPartial> boundForm = rpForm.bindFromRequest(request);

        ReviewPartial review = boundForm.get();
        Review newReview = new Review();


        //reviewTag.review = newReview;


        String user = request.session().getOptional("connected").orElse("guest");
        if(!user.equals("guest")){
            newReview.person = findPerson(user);
            //int resID = Integer.parseInt(review.restaurant);
            int resID = review.restaurant;
            newReview.restaurant = findRestaurant(resID);
            newReview.text = review.text;
            newReview.rating = review.rating;

            if(!review.text.isEmpty()){
                newReview.save();

            }

            //Tagging
            ArrayList<String> tagText = new ArrayList<>();;


            if(!review.tagCheap.equals("null")){
                tagText.add("Cheap");

            }

            if(!review.tagExpensive.equals("null")){
                tagText.add("Expensive");

            }


            if(!review.tagJuicy.equals("null")){
                tagText.add("Juicy");

            }

            if(!review.tagDry.equals("null")){
                tagText.add("Dry");

            }


            if(!review.tagFast.equals("null")){
                tagText.add("Fast");

            }

            if(!review.tagSlow.equals("null")){
                tagText.add("Slow");

            }


            //Iterate through list of tags and save each tag
            if(tagText.size() > 0) {
                for (String s : tagText) {
                    Tag reviewTag = new Tag();
                    reviewTag.text = s;
                    reviewTag.review = newReview;
                    reviewTag.save();
                }
            }



        }

        return redirect(routes.HomeController.restaurantPage());

    }

    //example methods of how to do common tasks

    public void add(Review review){
        review.save();
    }

    public void remove(Review review){
        review.delete();
    }

    public Review get(Long id){
        Review review;
        return review = Review.find.byId(id);

    }

    public List<Review> getAll(){
        List<Review> reviews = Review.find.all();
        return reviews;
    }



    /*------------------------------------------Voting Methods --------------------------------------------------*/
    /**
     * This method adds a vote object to the database.
     * @param request
     * @return
     */
    public Result voteReview(Http.Request request){
        System.out.println("entered vote Review");
        Date submitDate = new Date();
        Form<Vote> myForm = vForm.bindFromRequest(request);
        //get user person obj
        String user = request.session().getOptional("connected").orElse("guest");
        if(user.equals("guest"))
            //return ok(review.render(""));
            return ok(restaurant.render(""));
        Person person = findPerson(user);
        //get review obj
        int reviewID = Integer.parseInt(regexFormReviewID(myForm.toString()));
        Review reviewObj = findReview(reviewID);
        Vote myVote = new Vote();
        myVote.createdAt = submitDate;
        myVote.person = person;
        myVote.review = reviewObj;
        myVote.save();
       // return redirect("/restaurantSingle/"+reviewID);

        return ok(restaurant.render(""));
    }

    /*
    *regex the string value, and then split it
    *It returns the value of the review ID as a String.
    */
    public String regexFormReviewID(String str){
        Pattern p = Pattern.compile("reviewID\\=(?:\\d+)");
        Matcher m = p.matcher(str);
        String reviewID = "";
        while(m.find()){
            reviewID = m.group(0);
        }

        String array[] = reviewID.split("\\=");
        return array[1];

    }

    /**
     * This method fines the person ojb, given the username.
     * @param user
     * @return
     */
    public Person findPerson(String user){
        ExpressionList<Person> personMatch = Person.find.query().where().eq("username", user);
        List<Person> toList = personMatch.findList();
        Person person = toList.get(0);
        return person;

    }

    /**
     * This method returns a review obj, given the ID
     * @param reviewID
     * @return
     */
    public Review findReview(int reviewID){
        ExpressionList<Review> reviewMatch = Review.find.query().where().eq("id", reviewID);
        List<Review> toList2 = reviewMatch.findList();
        Review reviewObj = toList2.get(0);
        return reviewObj;
    }

    /**
     * This method returns a Restaurant obj, given the ID
     * @param resID
     * @return
     */
    public Restaurant findRestaurant(int resID){
        ExpressionList<Restaurant> resMatch = Restaurant.find.query().where().eq("id", resID);
        List<Restaurant> toList2 = resMatch.findList();
        Restaurant resObj = toList2.get(0);
        return resObj;
    }

    /**
     * This method controls the function of removing votes from reviews and from the database.
     * @param request
     * @return
     */
    public Result removeVoteReview(Http.Request request){
        Form<Vote> myForm = vForm.bindFromRequest(request);
        //get user person obj
        String user = request.session().getOptional("connected").orElse("guest");
        if(user.equals("guest")) return ok(restaurant.render(""));
        Person person = findPerson(user);
        //get review obj
        int reviewID = Integer.parseInt(regexFormReviewID(myForm.toString()));
        Review reviewObj = findReview(reviewID);
        //Delete the value
        Vote.find.query().where().eq("review", reviewObj).eq("person", person).delete();
        return ok(restaurant.render(""));
    }

    /**
     * This method returns all the votes as a json obj
     * @return
     */
    public Result getAllVotes(){
        return ok(toJson(getVotes()));
    }

    /**
     * This method returns votes as a List of votes
     * @return
     */
    public List<Vote> getVotes(){
        List<Vote> votes = Vote.find.all();
        return votes;
    }

    /**
     * This method fetches the count for the number of votes a review has.
     * @param id
     * @return
     */
    public Result reviewVotes(int id){
        Review reviewObj = findReview(id);
        int icount;
        icount = Vote.find.query().where().eq("review", reviewObj).findCount();
        return ok(Json.toJson(icount));
    }




}
