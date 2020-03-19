import java.util.ArrayList;
import java.util.Iterator;

public class UserVoucherMap extends ArrayMap<Integer, ArrayList<Voucher>> {

    public boolean addVoucher(Voucher v) {

        if (!(containsKey(v.campaign_ID))) {    // daca nu exista cheia deja
            ArrayList<Voucher> vouchers = new ArrayList<Voucher>();     // creez arraylist nou de vouchere
            vouchers.add(v);    // adaug voucherul
            return (put(v.campaign_ID, vouchers) == null);  // adaug intrarea in dictionar
        }
        ArrayList<Voucher> vouchers = get(v.campaign_ID);   // iau valoarea de la cheia
        if (containsKey(v.campaign_ID)) {   // daca exista cheia
            if (!vouchers.contains(v)) {    // daca nu exista voucherul in lista
                vouchers.add(v);
                return ((put(v.campaign_ID, vouchers)) == null);    // se adauga intrarea in dictionar
            }
        }
        return false;
    }
}
