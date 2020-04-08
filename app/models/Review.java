package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Review extends Model {

    @Id
    @GeneratedValue
    public int id;

    public String text;
    public long rating; //This should be between 1 and 5

    @ManyToOne
    public Person person;

    @ManyToOne
    public Restaurant restaurant;

    @OneToMany(mappedBy="review")
    public List<Tag> tags;


    public static final Finder<Long, Review> find = new Finder<>(Review.class);


}
