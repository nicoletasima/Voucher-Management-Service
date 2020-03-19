import java.time.LocalDateTime;
import java.util.*;

enum CampaignStatusType {   // statusul campaniei
    NEW, STARTED, EXPIRED, CANCELLED
}

public class Campaign {
    Integer campaign_ID;    // id campanie
    String name;            // nume campanie
    String description;     // descriere campanie
    LocalDateTime start;             // data de start
    LocalDateTime end;               // data de finalizare
    int total_available_vouchers;   // numarul de vouchere care pot fi distribuite
    int current_available_vouchers; // numarul curent de vouchere disponibile
    CampaignStatusType campaign_status_type;
    CampaignVoucherMap campaignVoucherMap = new CampaignVoucherMap();    // dictionar de vouchere distribuite
    ArrayList<User> observers = new ArrayList<User>();       // utilizatorii care au primit cel putin un voucher corespunzator campaniei
    String strategy_type;      // tipul strategiei
    public Integer generated_vouchers = 0; // numarul de ordine al voucherului
    LocalDateTime app_date;

    public Campaign(Integer campaign_ID, String name, String description, LocalDateTime start, LocalDateTime end,   // constructor
                    int total_available_vouchers, String strategy_type, LocalDateTime app_date) {
        this.campaign_ID = campaign_ID;
        this.name = name;
        this.description = description;
        this.start = start;
        this.end = end;
        this.total_available_vouchers = total_available_vouchers;
        this.current_available_vouchers = total_available_vouchers;
        this.strategy_type = strategy_type;
        this.app_date = app_date;
        if (app_date.isBefore(start)) {
            campaign_status_type = CampaignStatusType.NEW;
        }
        if (app_date.isAfter(start) && app_date.isBefore(end)) {
            campaign_status_type = CampaignStatusType.STARTED;
        }
        if (app_date.isAfter(end)) {
            campaign_status_type = CampaignStatusType.EXPIRED;
        }
    }

    public ArrayList<Voucher> getVouchers() {
        ArrayList<Voucher> all_vouchers = new ArrayList<Voucher>();

        for (Map.Entry<String, ArrayList<Voucher>> entry: campaignVoucherMap.entrySet()) {  // iterez prin map
            for (Iterator it = entry.getValue().iterator(); it.hasNext();) {    //iterez prin multimea de valori
                Voucher obj = (Voucher) it.next();
                all_vouchers.add(obj);  // adaug valoarea in all_vouchers
            }
        }
        return all_vouchers;
    }

    public Voucher getVoucher(String code) {
        for (Map.Entry<String, ArrayList<Voucher>> entry: campaignVoucherMap.entrySet()) {  // iterez prin map
            for (Iterator it =  entry.getValue().iterator(); it.hasNext();) {
                Voucher obj = (Voucher) it.next();
                if (obj.code.equals(code)) {        // verific daca gasesc voucherul cu codul respectiv
                     return obj;
                }
            }
        }
        return null;
    }

    public Voucher generateVoucher(String email, String voucherType, float value) {
        generated_vouchers = generated_vouchers + 1;
        Voucher v = null;
        if (voucherType.equals("GiftVoucher")) {
            v = new GiftVoucher(generated_vouchers, Integer.toString((generated_vouchers)), VoucherStatusType.UNUSED, app_date, email, campaign_ID, value);
        }
         else {
            if (voucherType.equals("LoyaltyVoucher")) {
                v = new LoyaltyVoucher(generated_vouchers, Integer.toString(generated_vouchers), VoucherStatusType.UNUSED, app_date, email, campaign_ID, value);
            }
        }
         return v;
    }

    public void redeemVoucher(String code, LocalDateTime date) {
        Voucher v = getVoucher(code);
        if (date.isBefore(end) && date.isAfter(start) && v.voucherStatusType == VoucherStatusType.UNUSED && campaign_status_type == CampaignStatusType.STARTED) {
            v.voucherStatusType = VoucherStatusType.USED;
            v.date = date;
        }
    }

    public ArrayList getObservers() {
        return observers;
    }

    public void addObserver(User user){
        observers.add(user);
    }

    public void removeObserver(User user) {
        observers.remove(user);
    }

    public void notifyAllObservers(Notification notification) {

        for (User observer : observers) {
            ArrayList<Voucher> vouchers = campaignVoucherMap.get(observer.user_email);
            ArrayList<String> codes = new ArrayList<String>();
            for (Iterator it = vouchers.iterator(); it.hasNext();) {
                Voucher v = (Voucher) it.next();
                codes.add(v.code);
            }
            notification.codes = codes;
            observer.update(notification);
        }

    }

    public String toString() {
        String res = "[";
        return res + campaign_ID + ";" + name + ";" + description + ";" + start + ";" + end + campaign_status_type + "]";
    }


}
