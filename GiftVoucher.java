import java.time.LocalDateTime;
import java.util.Date;

public class GiftVoucher extends Voucher {
    float sum;

    public GiftVoucher(Integer voucher_ID, String code, VoucherStatusType voucherStatusType, LocalDateTime date, String email, Integer campaign_ID, float sum) {
        super(voucher_ID, code, voucherStatusType, date, email, campaign_ID);   // constructor
        this.sum = sum;
    }

    public float getValue() {
        return sum;
    }

    public void setValue(float sum) {
        this.sum = sum;
    }

    public String toString() {
        String res = "[";
        return res + voucher_ID + ";" + voucherStatusType + ";" + email + ";" + date + "]";
    }
}
