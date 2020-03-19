import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

public class CampaignVoucherMap extends ArrayMap<String, ArrayList<Voucher>> {
    public boolean addVoucher(Voucher v) {

        if (!(containsKey(v.email))) {      // daca nu contine cheia
            ArrayList<Voucher> vouchers = new ArrayList<Voucher>(); // creez lista noua
            vouchers.add(v);    // adaug voucherul
            return (put(v.email, vouchers) == null);    // adaug intrarea in dictionar
        }
        ArrayList<Voucher> vouchers = get(v.email); // iau valoarea de la cheia respectiva
        if (containsKey(v.email) && (!vouchers.contains(v))) {  // daca exista cheia in dictionar si nu exista voucherul in lista de vouchere
                vouchers.add(v);    // adaug noul voucher
                return ((put(v.email, vouchers)) == null);  // adaug in dictionar
        }
        return false;
    }
}
