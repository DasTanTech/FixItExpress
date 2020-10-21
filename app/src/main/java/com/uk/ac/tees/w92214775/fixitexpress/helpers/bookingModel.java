package com.uk.ac.tees.w92214775.fixitexpress.helpers;

public class bookingModel {
    private String bBID,bName,bDate;

    public bookingModel(String bBID ,String bName, String bDate) {
        this.bBID = bBID;
        this.bName = bName;
        this.bDate = bDate;
    }

    public bookingModel() {
    }

    public String getbBID() {
        return bBID;
    }

    public void setbBID(String bBID) {
        this.bBID = bBID;
    }

    public String getbName() {
        return bName;
    }

    public void setbName(String bName) {
        this.bName = bName;
    }

    public String getbDate() {
        return bDate;
    }

    public void setbDate(String bDate) {
        this.bDate = bDate;
    }
}
