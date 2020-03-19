import java.time.LocalDateTime;
import java.util.Date;

public class LoyaltyVoucher extends Voucher {
    float discount;

    public LoyaltyVoucher(Integer voucher_ID, String code, VoucherStatusType voucherStatusType, LocalDateTime date, String email, Integer campaign_ID, float discount) {
        super(voucher_ID, code, voucherStatusType, date, email, campaign_ID);   // constructor
        this.discount = discount;
    }

    public float getValue() {
        return discount;
    }

    public void setValue(float value) {
        this.discount = value;
    }

    public String toString() {
        String res = "[";
        return res + voucher_ID + ";" + voucherStatusType + ";" + email + ";" + date + "]";
    }
}
