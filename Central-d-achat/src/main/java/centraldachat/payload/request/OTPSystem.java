package centraldachat.payload.request;


public class OTPSystem {

  private String mobilenumber;
  private String otp;
  private long expiretime;

  public String getMobilenumber() {
    return mobilenumber;
  }

  public void setMobilenumber(String mobilenumber) {
    this.mobilenumber = mobilenumber;
  }

  public String getOtp() {
    return otp;
  }

  public void setOtp(String otp) {
    this.otp = otp;
  }

  public long getExpiretime() {
    return expiretime;
  }

  public void setExpiretime(long expiretime) {
    this.expiretime = expiretime;
  }

}
