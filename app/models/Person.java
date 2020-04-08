package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.*;
import java.util.List;


@Entity
public class Person extends Model {


    @Id
    @GeneratedValue
    public int id;

    public String username;
    public String password;

    @OneToMany(mappedBy="person")
    public List<Vote> votes;


    public static final Finder<Long, Person> find = new Finder<>(Person.class);


}
