package com.example.ics.documentscanner;

public class Incometax_sqldb {
    private int id;
    private String Name;
    private String Email;
    private  int Mobile;
    private  String Locaton;
    private  String Landmark;
    private  int PAN;
    private  String GST;
    private String Aadhar_no;
    private String Image_address;
    public Incometax_sqldb( int id, String Name, String Email , int Mobile,  int PAN , String GST,String Aadhar_no , String Image_address) {
        this.Name = Name;
        this.Email = Email;
        this.Mobile = Mobile;
        this.id = id;
        this.PAN = PAN;
        this.GST = GST;
        this.Aadhar_no = Aadhar_no;
        this.Image_address = Image_address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public int getMobile() {
        return Mobile;
    }

    public void setMobile(int mobile) {
        Mobile = mobile;
    }

    public String getLocaton() {
        return Locaton;
    }

    public void setLocaton(String locaton) {
        Locaton = locaton;
    }

    public String getLandmark() {
        return Landmark;
    }

    public void setLandmark(String landmark) {
        Landmark = landmark;
    }

    public int getPAN() {
        return PAN;
    }

    public void setPAN(int PAN) {
        this.PAN = PAN;
    }

    public String getGST() {
        return GST;
    }

    public void setGST(String GST) {
        this.GST = GST;
    }

    public String getAadhar_no() {
        return Aadhar_no;
    }

    public void setAadhar_no(String aadhar_no) {
        Aadhar_no = aadhar_no;
    }

    public String getImage_address() {
        return Image_address;
    }

    public void setImage_address(String image_address) {
        Image_address = image_address;
    }
//    public String getPrice() {
//        return price;
//    }
//
//    public void setPrice(String price) {
//        this.price = price;
//    }
//
//    public String getImage() {
//        return Image_address;
//    }
//
//    public void setImage(String image_address) {
//        this.Image_address = image_address;
//    }
}
