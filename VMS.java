import java.util.ArrayList;

/*modeleaza colectia de date VMS*/
public class VMS {
    private static VMS instance = null; //instanta

    public  ArrayList<Campaign> campaigns = new ArrayList<>();  // multimea de campanii
    public ArrayList<User> users = new ArrayList<>();           // utilizatorii care pot primi vouchere

    private VMS() {} //constructor 1

    public static VMS getInstance() { // constructor 2
        if (instance == null) {
            instance = new VMS();
        }
        return instance;
    }

    public ArrayList<Campaign> getCampaigns() {
        return campaigns;
    }   // returneaza campaniile

    public Campaign getCampaign(Integer id) {
        for (Campaign obj : campaigns) {    // iterez prin colectia de campanii
            if (obj.campaign_ID == id) {    // verific egalitatea cu id-ul dat ca parametru
                return obj;
            }
        }
        return null;
    }

    public User getUser(String email) {
        for (User obj : users) {    // iterez prin colectia de useri
            if (obj.user_email.equals(email)) { // verific egalitatea cu email-ul dat ca parametru
                return obj;
            }
        }
        return null;
    }

    public User getUser(Integer id) {
        for (User obj : users) {
            if (obj.user_ID == id) {    // verific egalitatea cu id-ul userului
                return obj;
            }
        }
        return null;
    }

    public void addCampaign(Campaign campaign) {
        campaigns.add(campaign);    // adaug campanie in colectia de campanii din vms
    }

    public void updateCampaign(Integer id, Campaign campaign) {
        Campaign c = null;
        for (Campaign obj : campaigns) {
            if (obj.campaign_ID == id) {
                if (obj.campaign_status_type == CampaignStatusType.NEW) {   // modific pentru campania NEW
                    obj.description = campaign.description;
                    obj.start = campaign.start;
                    obj.end = campaign.end;
                    obj.total_available_vouchers = campaign.total_available_vouchers;
                    Notification n = new Notification(NotificationType.EDIT, campaign.app_date, campaign.campaign_ID);
                    obj.notifyAllObservers(n);
                    break;
                }
                if (obj.campaign_status_type == CampaignStatusType.STARTED) {   // modific pentru camapania STARTED
                    obj.total_available_vouchers = campaign.total_available_vouchers;
                    obj.end = campaign.end;
                    Notification n = new Notification(NotificationType.EDIT, campaign.app_date, campaign.campaign_ID);
                    obj.notifyAllObservers(n);
                    break;
                }
            }
        }
    }

    public void cancelCampaign(Integer id) {

        for (Campaign obj : campaigns) {
            if (obj.campaign_ID == id) {
                if ((obj.campaign_status_type == CampaignStatusType.NEW) ||
                        (obj.campaign_status_type == CampaignStatusType.STARTED)) {
                    obj.campaign_status_type = CampaignStatusType.CANCELLED;
                    Notification n = new Notification(NotificationType.CANCEL, obj.app_date, obj.campaign_ID);
                    obj.notifyAllObservers(n);
                    break;
                }
            }
        }
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void addUser(User user) {
        users.add(user);
    }
}
