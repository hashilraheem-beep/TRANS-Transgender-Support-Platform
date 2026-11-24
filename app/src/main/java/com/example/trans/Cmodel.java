package com.example.trans;

public class Cmodel {
    String id,name,email,hospital,phone,type,password;

    public Cmodel(String id, String name, String email, String hospital, String phone, String type, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.hospital= hospital;
        this.phone = phone;
        this.type = type;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getHospital() {
        return hospital;
    }

    public String getPhone() {
        return phone;
    }

    public String getType() {
        return type;
    }

    public String getPassword() {
        return password;
    }
}
