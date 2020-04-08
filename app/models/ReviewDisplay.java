package models;

public class ReviewDisplay {

    private int index;
    private int reviewID;
    private String username;
    private String text;
    private boolean voted;
    private int votes;
    private long rating;

    public ReviewDisplay(int myindex, int myreviewID, String myusername, String mytext, boolean myvoted, int myvotes, long myrating){
        index = myindex; reviewID = myreviewID; username = myusername; text = mytext; setVoted(myvoted); setVotes(myvotes);
    }

    public ReviewDisplay(int myindex, int myreviewID, String myusername, String mytext, boolean myvoted, int myvotes){
        index = myindex; reviewID = myreviewID; username = myusername; text = mytext; setVoted(myvoted);
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getReviewID() {
        return reviewID;
    }

    public void setReviewID(int reviewID) {
        this.reviewID = reviewID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isVoted() {
        return voted;
    }

    public void setVoted(boolean voted) {
        this.voted = voted;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public long getRating() {
        return rating;
    }

    public void setRating(long rating) {
        this.rating = rating;
    }
}
