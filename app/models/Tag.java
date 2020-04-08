package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Tag extends Model {

    @ManyToOne
    public Review review;

    public String text;

    public static final Finder<Long, Tag> find = new Finder<>(Tag.class);

}
