import java.time.LocalDateTime;
import java.util.Date;
enum VoucherStatusType { //satusul voucherului
    USED, UNUSED
}
public abstract class Voucher {  //clasa abstracta
    Integer voucher_ID;         //id-ul voucherului
    String code;               //codul voucherului
    VoucherStatusType voucherStatusType; //statusul
    LocalDateTime date;                  // data utilizarii
    String email;               // email-ul catre care a fost distribuit voucherul
    Integer campaign_ID;        // id-ul campaniei in cadrul careia a fost generat

    public Voucher(Integer voucher_ID, String code, VoucherStatusType voucherStatusType, LocalDateTime date, String email, Integer campaign_ID) {   // constructor
        this.voucher_ID = voucher_ID;
        this.code = code;
        this.voucherStatusType = voucherStatusType;
        this.date = null;
        this.email = email;
        this.campaign_ID = campaign_ID;
    }

    public Integer getCampaign_ID() {
        return campaign_ID;
    }

    public void setCampaign_ID(Integer campaign_ID) {
        this.campaign_ID = campaign_ID;
    }

    public Integer getVoucher_ID() {
        return voucher_ID;
    }

    public LocalDateTime getLocalDateTime() {
        return date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLocalDateTime(LocalDateTime date) {
        this.date = date;
    }

    public void setVoucher_ID(Integer voucher_ID) {
        this.voucher_ID = voucher_ID;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public VoucherStatusType getVoucherStatusType() {
        return voucherStatusType;
    }

    public void setVoucherStatusType(VoucherStatusType voucherStatusType) {
        this.voucherStatusType = voucherStatusType;
    }
    public String toString() {
        String res = "[";
        return res + voucher_ID + ";" + voucherStatusType + ";" + email + ";" + date + "]";
    }
}
