package com.android.jamal.laporandanamasjid.login;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by jamal on 25/11/17.
 */

public class Session {

    private Context context;

    public Session(Context context) {
        this.context = context;
    }

    public void userLogin (String username, String password, String nama, String jk,
                           String alamat, String telp, String role){

        SharedPreferences sharedPreferences = this.context.getSharedPreferences("login", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username",username);
        editor.putString("password",password);
        editor.putString("nama",nama);
        editor.putString("jk",jk);
        editor.putString("alamat",alamat);
        editor.putString("telp",telp);
        editor.putString("role",role);
        editor.commit();
    }

    public boolean cekLogin(){
        SharedPreferences preferences = this.context.getSharedPreferences("login",Context.MODE_PRIVATE);
        if(preferences.getString("username",null) != null){

            return  true;
        }else return false;
    }

    public String getRole(){
        SharedPreferences preferences = this.context.getSharedPreferences("login",Context.MODE_PRIVATE);
        return preferences.getString("role",null);
    }
}
