package com.example.bipl.biplpos.boc;

/**
 * Created by fahad on 2/16/2017.
 */

public class MainActivityBOC {
    private String user;
    private String pass;

    public MainActivityBOC(String user, String pass) {
        this.user = user;
        this.pass = pass;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public boolean logIn(){
        boolean status=false;
        status = user.equals("admin") && pass.equals("123");
        return status;
    }
}
