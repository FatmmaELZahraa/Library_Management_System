package items;
import users.*;

import java.io.Serializable;

public class Review implements Serializable {
    private User user;
    private int numOfStar;
    private  String userComment ;
    private Book book;

    public  Review(int numOfStar ,User user ,String userComment,Book book ){
        this.user=user;
        this.numOfStar=numOfStar;
        this.userComment=userComment;
        this.book=book;

    }
    public void setNumOfStar(int numOfStar){
        this.numOfStar=numOfStar;
    }
    public int getNumOfStar(){
        return numOfStar;
    }
    public User getUser() {
        return user;
    }

    public void print() {
        System.out.print(". From :"+user.getUserName() +"  comment:"+userComment );
        System.out.print("  The rate is:");
        for (int i = 0; i < numOfStar; i++) {
            System.out .print("⭐");
        }
        System.out.println();
    }
    public void setUserReview(String userReview){
        this.userComment=userReview;
    }
    public String getUserReview(){
        return userComment;
    }
    public String getUserName(){
        return user.getUserName();
    }
    public String getTitle() {
        return book.getTitle();
    }
}

