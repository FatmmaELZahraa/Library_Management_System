package items;

import users.Borrower;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public  class Notification implements Serializable {
    private static int nextNotificationId=1;
    private int notificationId;
    private Borrower borrower;
    private String message;
    private Date notificationDate;

    public Notification(String message,Borrower borrower) {
        this.notificationId=nextNotificationId++;
        this.message = message;
        this.borrower=borrower;
        this.notificationDate = new Date();
    }

    public int getNotificationId() {
        return notificationId;
    }
    public String getMessage() {
        return message;
    }
    public Date getNotificationDate() {
        return notificationDate;
    }
    public void displayNotification(){
        System.out.println("To: " +borrower.getUserName());
        System.out.println("From: Library");
        System.out.println(message);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        System.out.print("Date: ");
        System.out.println(sdf.format(notificationDate));
    }
}
