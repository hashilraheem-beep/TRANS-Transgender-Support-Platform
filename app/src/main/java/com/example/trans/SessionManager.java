package com.example.trans;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "Login";
    private static final String IS_LOGIN = "IsLoggedIn";
    public  SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createLoginSession(String id, String name, String age, String email, String phone, String password){

        editor.putBoolean(IS_LOGIN, true);
        editor.putString("id",id);
        editor.putString("name",name);
        editor.putString("age",age);
        editor.putString("email", email);
        editor.putString("phone", phone);
        editor.putString("password", password);



        editor.commit();
    }
//    public void updateprofile(String fullname,String address,String place,
//                              String district,String state,String phone,String company,String email,
//                              String password){
//        //editor.putBoolean(IS_LOGIN, true);
//        //editor.putString("id",id);
//        editor.putString("fullname",fullname);
//        editor.putString( "address",address );
//        editor.putString("place",place);
//        editor.putString("district", district);
//        editor.putString("state", state);
//        editor.putString("phone", phone);
//        editor.putString("company", company);
//        editor.putString("email", email);
//        editor.putString("password", password);
//
//
//
//
//
//        editor.commit();
//    }
    public void updateimage(String image){
        //editor.putBoolean(IS_LOGIN, true);
        //editor.putString("id",id);
        editor.putString("profile",image);


        editor.commit();
    }
    public void updatevideo(String video){
        //editor.putBoolean(IS_LOGIN, true);
        //editor.putString("id",id);
        editor.putString("video",video);


        editor.commit();
    }
    public boolean checkLogin(){
        if(this.isLoggedIn()) {
            return true;
        } else {
            return false;
        }

    }
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        user.put("id", pref.getString("id", null));
        user.put("name", pref.getString("name", null));
        user.put("age", pref.getString("age", null));
        user.put("email", pref.getString("email", null));
        user.put("phone", pref.getString("phone", null));
        user.put("password", pref.getString("password", null));




        return user;
    }

    public void logoutUser(){
        editor.clear();
        editor.commit();
    }
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}

