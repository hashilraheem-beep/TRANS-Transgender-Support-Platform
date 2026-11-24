package com.example.trans;



public class bookingmodel {
    String id,u_id,dr_name, DName;


    public bookingmodel(String id, String u_id, String dr_name, String DName) {
        this.id = id;
        this.u_id = u_id;
        this.dr_name = dr_name;
        this.DName = DName;

    }

    public String getId() {
        return id;
    }

    public String getU_id() {
        return u_id;
    }

    public String getDr_name() {
        return dr_name;
    }

    public String getDName() {
        return DName;
    }

}
