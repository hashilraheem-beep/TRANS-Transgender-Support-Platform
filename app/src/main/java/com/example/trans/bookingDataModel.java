package com.example.trans;

public class bookingDataModel {
    String id,user_id,Name,Phone;

    public bookingDataModel(String id, String user_id, String name, String phone) {
        this.id = id;
        this.user_id = user_id;
        Name = name;
        Phone = phone;
    }

    public String getId() {
        return id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getName() {
        return Name;
    }

    public String getPhone() {
        return Phone;
    }
}

