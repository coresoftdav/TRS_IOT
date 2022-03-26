package com.coresoft.electricalsolutions.Modal;

public class RoomModal {
    String room_name,room_img,device_id;
    int status;
    long online_time;

    public RoomModal() {
    }

    public RoomModal(String room_name, String room_img, String device_id, int status, long online_time) {
        this.room_name = room_name;
        this.room_img = room_img;
        this.device_id = device_id;
        this.status = status;
        this.online_time = online_time;
    }

    public String getRoom_name() {
        return room_name;
    }

    public void setRoom_name(String room_name) {
        this.room_name = room_name;
    }

    public String getRoom_img() {
        return room_img;
    }

    public void setRoom_img(String room_img) {
        this.room_img = room_img;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getOnline_time() {
        return online_time;
    }

    public void setOnline_time(long online_time) {
        this.online_time = online_time;
    }
}
