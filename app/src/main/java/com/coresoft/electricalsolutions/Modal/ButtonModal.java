package com.coresoft.electricalsolutions.Modal;

public class ButtonModal {
    String button_name,button_pic,button_id;
    int button_status;
    long active_time;

    public ButtonModal() {
    }

    public ButtonModal(String button_name, String button_pic, String button_id, int button_status, long active_time) {
        this.button_name = button_name;
        this.button_pic = button_pic;
        this.button_id = button_id;
        this.button_status = button_status;
        this.active_time = active_time;
    }

    public String getButton_name() {
        return button_name;
    }

    public void setButton_name(String button_name) {
        this.button_name = button_name;
    }

    public String getButton_pic() {
        return button_pic;
    }

    public void setButton_pic(String button_pic) {
        this.button_pic = button_pic;
    }

    public String getButton_id() {
        return button_id;
    }

    public void setButton_id(String button_id) {
        this.button_id = button_id;
    }

    public int getButton_status() {
        return button_status;
    }

    public void setButton_status(int button_status) {
        this.button_status = button_status;
    }

    public long getActive_time() {
        return active_time;
    }

    public void setActive_time(long active_time) {
        this.active_time = active_time;
    }
}
