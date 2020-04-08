package models;

import java.util.List;

public class ReviewPartial {

    public String text;

    public long rating; //This should be between 1 and 5

    //will be passed as a string
    public int restaurant;

    //tagging
    public String tagCheap = "null";
    public String tagExpensive = "null";
    public String tagJuicy = "null";
    public String tagDry = "null";
    public String tagFast = "null";
    public String tagSlow = "null";

}
