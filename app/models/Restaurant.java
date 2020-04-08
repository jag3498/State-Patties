package models;


import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Restaurant extends Model {

    @Id
    @GeneratedValue
    public int id;

    public String name;
    public String location;



    public static final Finder<Long, Restaurant> find = new Finder<>(Restaurant.class);

}
