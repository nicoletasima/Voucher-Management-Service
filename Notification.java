import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
enum NotificationType {  // tipul notificarii
    EDIT, CANCEL
}
public class Notification {
    NotificationType notification_type; //tipul notificarii
    LocalDateTime date;                          // data la care a fost trimisa notificarea
    Integer campaign_ID;                // id campanie
    List<String> codes = new ArrayList<String>(); // lista codurilor

    public Notification(NotificationType notification_type, LocalDateTime date, Integer campaign_ID) {  // constructor
        this.notification_type = notification_type;
        this.date = date;
        this.campaign_ID = campaign_ID;
    }

    public NotificationType getNotification_type() {
        return notification_type;
    }

    public LocalDateTime getLocalDateTime() {
        return date;
    }

    public void setLocalDateTime(LocalDateTime date) {
        this.date = date;
    }

    public void setNotification_type(NotificationType notification_type) {
        this.notification_type = notification_type;
    }

    public Integer getCampaign_ID() {
        return campaign_ID;
    }

    public void setCampaign_ID(Integer campaign_ID) {
        this.campaign_ID = campaign_ID;
    }

    public List<String> getCodes() {
        return codes;
    }

    public void setCodes(List<String> codes) {
        this.codes = codes;
    }

    public String toString() {
        String res = "[";
        return res + campaign_ID + ";" + date + ";" + notification_type + "]";
    }
}
