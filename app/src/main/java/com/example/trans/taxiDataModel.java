package com.example.trans;

public class taxiDataModel {
    String Id,Vname,Vehicletype,Vno,Dname,Phone,Stand,Price,image;

    public taxiDataModel(String id, String vname, String vehicletype, String vno, String dname, String phone, String stand, String price, String image) {
        Id = id;
        Vname = vname;
        Vehicletype = vehicletype;
        Vno = vno;
        Dname = dname;
        Phone = phone;
        Stand = stand;
        Price = price;
        this.image = image;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getVname() {
        return Vname;
    }

    public void setVname(String vname) {
        Vname = vname;
    }

    public String getVehicletype() {
        return Vehicletype;
    }

    public void setVehicletype(String vehicletype) {
        Vehicletype = vehicletype;
    }

    public String getVno() {
        return Vno;
    }

    public void setVno(String vno) {
        Vno = vno;
    }

    public String getDname() {
        return Dname;
    }

    public void setDname(String dname) {
        Dname = dname;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getStand() {
        return Stand;
    }

    public void setStand(String stand) {
        Stand = stand;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
