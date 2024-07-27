package users;

public abstract class User {
    private static int nextUserId=1;
    protected String firstName;
    protected String lastName;
    protected long phoneNumber;
    protected String email;
    protected String userName;
    protected String password;
    protected int userId;

    public User(String firstName,String lastName,long phoneNumber,String email, String userName,String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.userId = nextUserId++;
    }
    public int getUserId(){
        return userId;
    }

    public String getFirstName(){
        return firstName;
    }

    public void setFirstName(String firstName){

        this.firstName = firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public void setLastName(String Last_Name){

        this.lastName = lastName;
    }

    public long getPhoneNumber(){

        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber){

        this.phoneNumber = phoneNumber;
    }

    public String getEmail(){

        return email;
    }

    public void setEmail(String email){

        this.email = email;
    }

    public String getUserName(){
        return userName;
    }

    public void setUserName (String userName ){

        this.userName = userName;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){

        this.password = password;
    }

    public int getNextUserId(){
        return nextUserId;
    }

    public void displayUserInfo(){
        System.out.println("");
        System.out.println("User ID : " + userId);
        System.out.println("First Name : " + firstName);
        System.out.println("Last Name : " + lastName);
        System.out.println("Phone Number : "+ phoneNumber);
        System.out.println("Email: " + email);
        System.out.println("User Name : " + userName);
        System.out.println("Password : " + password);
    }

    public abstract void welcomeHome();
}