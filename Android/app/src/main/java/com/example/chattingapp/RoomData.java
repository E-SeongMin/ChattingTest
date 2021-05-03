package com.example.chattingapp;

public class RoomData {
    private String userName;
    private String roomNumber;

    public RoomData(String userName, String roomNumber) {
        this.userName = userName;
        this.roomNumber = roomNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }
}
