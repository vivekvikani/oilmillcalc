package com.oil.vivek.oilmillcalc.notification;

/**
 * Created by vivek.vikani on 2/9/17.
 */

public class notification_model {
    private String message;
    private String title;
    private int image;
    private String time;
    private int index;
    private boolean newNotification;

    public notification_model(){
    }

    public notification_model(String message, String title, int image, String time, int index, boolean newNotification){
        this.message = message;
        this.title = title;
        this.image = image;
        this.time = time;
        this.index = index;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isNewNotification() {
        return newNotification;
    }

    public void setNewNotification(boolean newNotification) {
        this.newNotification = newNotification;
    }
}
