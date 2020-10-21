package com.uk.ac.tees.w92214775.fixitexpress.helpers;

public class homeModel {
    private String S_ID,S_PICURL;

    public homeModel(String s_ID, String s_PICURL) {
        S_ID = s_ID;
        S_PICURL = s_PICURL;
    }

    public homeModel() {
    }

    public String getS_ID() {
        return S_ID;
    }

    public void setS_ID(String s_ID) {
        S_ID = s_ID;
    }

    public String getS_PICURL() {
        return S_PICURL;
    }

    public void setS_PICURL(String s_PICURL) {
        S_PICURL = s_PICURL;
    }
}
