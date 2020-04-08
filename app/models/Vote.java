package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
public class Vote extends Model {

    @ManyToOne
    public Person person;

    @ManyToOne
    public Review review;


    @Column(name = "created_at")
    public Date createdAt;

    public static final Finder<Long, Vote> find = new Finder<>(Vote.class);


}
