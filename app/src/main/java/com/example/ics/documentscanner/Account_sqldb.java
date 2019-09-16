package com.example.ics.documentscanner;

import android.graphics.Bitmap;

import com.google.android.gms.vision.face.Landmark;

public class Account_sqldb {
    private int id;
    private String Name;
    private String Email;
    private  int Mobile;
    private  String Locaton;
    private  String Landmark;
    private  String PAN;
    private  String GST;
    private String Customer_id;
    private String Image_address;
    private String Customer_code;
    public Account_sqldb( int id, String Name, String Email , int Mobile,String Location , String Landmark ,  String PAN , String GST,String Customer_id , String Image_address,String Customer_code) {
        this.Name = Name;
        this.Email = Email;
        this.Mobile = Mobile;
        this.id = id;
        this.Locaton = Location;
        this.Landmark = Landmark;
        this.PAN = PAN;
        this.GST = GST;
        this.Customer_id = Customer_id;
        this.Image_address = Image_address;
        this.Customer_code = Customer_code;
    }
    public Account_sqldb( String Name, String Email , int Mobile,String Location , String Landmark ,  String PAN , String GST,String Customer_id) {
        this.Name = Name;
        this.Email = Email;
        this.Mobile = Mobile;
        this.id = id;
        this.Locaton = Location;
        this.Landmark = Landmark;
        this.PAN = PAN;
        this.GST = GST;
        this.Customer_id = Customer_id;
//        this.Image_address = Image_address;
//        this.Customer_code = Customer_code;
    }

    public String getCustomer_code() {
        return Customer_code;
    }

    public void setCustomer_code(String customer_code) {
        Customer_code = customer_code;
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

    public String getPAN() {
        return PAN;
    }

    public String setPAN(String PAN) {
        this.PAN = PAN;
        return PAN;
    }

    public String getGST() {
        return GST;
    }

    public void setGST(String GST) {
        this.GST = GST;
    }

    public String getCustomer_id() {
        return Customer_id;
    }

    public void setCustomer_id(String customer_id) {
        Customer_id = customer_id;
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
