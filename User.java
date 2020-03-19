import java.util.ArrayList;

enum UserType {
    ADMIN, GUEST
}

public class User {
    Integer user_ID;    // id user
    String username;    // nume user
    String user_email;  // email user
    String password;    // parola
    UserType userType;  // tipul userului
    public UserVoucherMap userVoucherMap = new UserVoucherMap(); // dictionar de vouchere primite
    ArrayList<Notification> notifications = new ArrayList<Notification>();   // colectie de notificari

    public User(Integer user_ID, String username, String password, String user_email, UserType userType) {  // constructor
        this.user_ID = user_ID;
        this.username = username;
        this.user_email = user_email;
        this.password = password;
        this.userType = userType;
    }

    public void update(Notification notification) {
        notifications.add(notification);
    }

    public UserVoucherMap getUserVoucherMap() {
        return userVoucherMap;
    }

    public String getPassword() {
        return password;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUser_ID(Integer user_ID) {
        this.user_ID = user_ID;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUsername() {
        return username;
    }

    public ArrayList<Notification> getNotifications() {
        return notifications;
    }

    public void setUserVoucherMap(UserVoucherMap userVoucherMap) {
        this.userVoucherMap = userVoucherMap;
    }

    public Integer getUser_ID() {
        return user_ID;
    }

    public void setNotifications(ArrayList<Notification> notifications) {
        this.notifications = notifications;
    }

    public String toString() {
        String rez = "[";
        return rez + this.user_ID + ";" + this.username + ";" + this.user_email + ";" + this.userType + "]";
    }
}
