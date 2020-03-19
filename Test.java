import org.omg.PortableInterceptor.LOCATION_FORWARD;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

public class Test {
    public static void main(String args[]) throws IOException {
        VMS vms = VMS.getInstance();
        BufferedReader in;
        BufferedWriter out = new BufferedWriter(new FileWriter("C:\\Users\\Nicoleta\\Desktop\\VMStests\\output.txt"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        try {
            in = new BufferedReader(new FileReader("C:\\Users\\Nicoleta\\Desktop\\VMStests\\test00\\input\\campaigns.txt"));
            String line = in.readLine();    // nr de campanii
            String line1 = in.readLine();   //data aplicatiei
            LocalDateTime app_datetime = LocalDateTime.parse(line1, formatter);
            String line2 = in.readLine();   //prima campanie

            while (line2 != null) {
                StringTokenizer st = new StringTokenizer(line2, ";");
                String camp_id = st.nextToken();
                String name = st.nextToken();
                String description = st.nextToken();
                String start_date = st.nextToken();
                String end_date = st.nextToken();
                String budget = st.nextToken();
                String strategy = st.nextToken();
                Integer int_camp_id = Integer.parseInt(camp_id);
                LocalDateTime start_dateTime = LocalDateTime.parse(start_date, formatter);
                LocalDateTime end_dateTime = LocalDateTime.parse(end_date, formatter);
                int int_budget = Integer.parseInt(budget);

                Campaign campaign = new Campaign(int_camp_id, name, description, start_dateTime, end_dateTime, int_budget, strategy, app_datetime); // instantiez campanie noua
                vms.addCampaign(campaign);  // adaug in colectia de campanii din vms, campania nou creata
                line2 = in.readLine();
            }

            in = new BufferedReader((new FileReader("C:\\Users\\Nicoleta\\Desktop\\VMStests\\test00\\input\\users.txt")));
            line = in.readLine();   //nr de useri
            line1 = in.readLine();  // primul user

            while (line1 != null) {
                StringTokenizer st = new StringTokenizer(line1, ";");
                String id = st.nextToken();
                String name = st.nextToken();
                String password = st.nextToken();
                String email = st.nextToken();
                String type = st.nextToken();
                Integer int_id = Integer.parseInt(id);
                UserType userType;

                if (type.equals("ADMIN"))
                    userType = UserType.ADMIN;
                else
                    userType = UserType.GUEST;
                User user = new User(int_id, name, password, email, userType);
                vms.addUser(user);
                line1 = in.readLine();
            }

            in = new BufferedReader((new FileReader("C:\\Users\\Nicoleta\\Desktop\\VMStests\\test00\\input\\events.txt")));
            line = in.readLine();   // data aplicatiei
            LocalDateTime events_app_datetime = LocalDateTime.parse(line, formatter);
            line = in.readLine();  // numarul de evenimente
            int nr_events = Integer.parseInt(line);
            line = in.readLine(); // primul eveniment

            while (nr_events > 0) {
                StringTokenizer st = new StringTokenizer(line, ";");
                int userId = (Integer.parseInt(st.nextToken()));
                String type_event = st.nextToken();

                if (type_event.equals("addCampaign")) {
                    String campaignId = st.nextToken();
                    String campaignName = st.nextToken();
                    String campaignDescription = st.nextToken();
                    String startDate = st.nextToken();
                    String endDate = st.nextToken();
                    String budget = st.nextToken();
                    String strategy = st.nextToken();

                    Campaign c = new Campaign(Integer.parseInt(campaignId), campaignName, campaignDescription, LocalDateTime.parse(startDate, formatter),
                            LocalDateTime.parse(endDate, formatter), Integer.parseInt(budget), strategy, app_datetime);
                    for (int i = 0; i < vms.users.size(); i++) {
                        if (vms.users.get(i).userType == UserType.ADMIN) {  // verific conditia ca userul sa fie admin
                            vms.addCampaign(c); // adaug campania creata
                            break;
                        }
                    }
                }

                if (type_event.equals("editCampaign")) {
                    String campaignId = st.nextToken();
                    String campaignName = st.nextToken();
                    String campaignDescription = st.nextToken();
                    String startDate = st.nextToken();
                    String endDate = st.nextToken();
                    String budget = st.nextToken();

                    Campaign c = new Campaign(Integer.parseInt(campaignId), campaignName, campaignDescription, LocalDateTime.parse(startDate, formatter),
                            LocalDateTime.parse(endDate, formatter), Integer.parseInt(budget), null, app_datetime);
                    for (int i = 0; i < vms.users.size(); i++) {
                        if (vms.users.get(i).user_ID == userId && vms.users.get(i).userType == UserType.ADMIN) {
                            vms.updateCampaign(Integer.parseInt(campaignId), c);
                            break;
                        }
                    }
                }

                if (type_event.equals("cancelCampaign")) {
                    String campaignId = st.nextToken();
                    int int_campaignId = Integer.parseInt(campaignId);

                    for (int i = 0; i < vms.users.size(); i++) {
                        if (vms.users.get(i).user_ID == userId && vms.users.get(i).userType == UserType.ADMIN) {
                            vms.cancelCampaign(int_campaignId);
                            break;
                        }
                    }
                }

                if (type_event.equals("generateVoucher")) {
                    String campaignId = st.nextToken();
                    String email = st.nextToken();
                    String voucherType = st.nextToken();
                    String value = st.nextToken();
                    if (vms.getUser(userId).userType == UserType.ADMIN) {
                        Voucher v = vms.getCampaign(Integer.parseInt(campaignId)).generateVoucher(email, voucherType, Float.parseFloat(value));
                        if (vms.getCampaign(Integer.parseInt(campaignId)).current_available_vouchers > 0) {
                            vms.getCampaign(Integer.parseInt(campaignId)).campaignVoucherMap.addVoucher(v); // adaug voucherul in dictionarul campaniei
                            vms.getCampaign(Integer.parseInt(campaignId)).current_available_vouchers--;
                            vms.getUser(email).userVoucherMap.addVoucher(v); // adaug voucherul in dictionarul userului

                            ArrayList<User> obs = vms.users;
                            for (User user : obs) {
                                if (user.user_email.equals(email) && (!(vms.getCampaign(Integer.parseInt(campaignId)).observers.contains(user)))) {
                                    vms.getCampaign(Integer.parseInt(campaignId)).addObserver(user);    // actualizez lista de observatori
                                }
                            }
                        }
                    }

                }

                if (type_event.equals("redeemVoucher")) {   // marcheaza voucherul ca fiind utilizat
                    String campaignId = st.nextToken();
                    String voucherId = st.nextToken();
                    String localDate = st.nextToken();
                    User u = vms.getUser(userId);

                    if (u.userType == UserType.ADMIN) {
                        for (int i = 0; i < vms.campaigns.size(); i++) {
                            if (vms.campaigns.get(i).campaign_ID == Integer.parseInt(campaignId) && vms.campaigns.get(i).campaign_status_type != CampaignStatusType.EXPIRED
                            || vms.campaigns.get(i).campaign_status_type != CampaignStatusType.CANCELLED) {
                                ArrayList<Voucher> all = vms.campaigns.get(i).getVouchers();
                                for (Iterator it = all.iterator(); it.hasNext(); ) {
                                    Voucher v = (Voucher) it.next();
                                    if (v.voucher_ID == Integer.parseInt(voucherId)) {
                                        vms.campaigns.get(i).redeemVoucher(v.code, LocalDateTime.parse(localDate, formatter));
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }

                if (type_event.equals("getVouchers")) {
                        if (vms.getUser(userId).userType == UserType.GUEST) {
                            ArrayList<Voucher> arr = new ArrayList<Voucher>();
                            for (Map.Entry<Integer, ArrayList<Voucher>> entry: vms.getUser(userId).userVoucherMap.entrySet()) {
                                for (Iterator it = entry.getValue().iterator(); it.hasNext();) {
                                    Voucher obj = (Voucher) it.next();
                                    arr.add(obj);
                                }
                            }
                            out.write(String.valueOf(arr.toString()));
                            out.newLine();
                            }

                    }
                if (type_event.equals("getObservers")) {
                    String camp_id = st.nextToken();
                    int int_camp_id = Integer.parseInt(camp_id);

                    if (vms.getUser(userId).userType == UserType.ADMIN) {
                            for (int j = 0; j < vms.campaigns.size(); j++) {
                                if (vms.campaigns.get(j).campaign_ID == int_camp_id) {

                                    out.write(String.valueOf(vms.campaigns.get(j).observers));
                                    out.newLine();
                                    break;
                                    }

                                }
                            }
                        }

                if (type_event.equals("getNotifications")) {
                    for (int i = 0; i < vms.users.size(); i++) {
                        if (vms.users.get(i).user_ID == userId && vms.users.get(i).userType == UserType.GUEST) {
                            for (int j = 0; j < vms.users.get(i).notifications.size(); j ++) {
                                Notification n = vms.users.get(i).notifications.get(j);
                                String rez = "[" + n.campaign_ID + ";[" + userId + "];" + n.date + ";" + n.notification_type + "]";
                                out.write(rez);
                            }
                            out.newLine();
                            break;
                        }
                    }
                }
                if (type_event.equals("getVoucher")) {
                    // bonus

                }
                    nr_events--;
                    line = in.readLine();
                }
                out.close();
        }
        catch(FileNotFoundException e) {
                System.out.println("File not found");
        } catch(IOException e) {
                e.printStackTrace();
        }
    }
}