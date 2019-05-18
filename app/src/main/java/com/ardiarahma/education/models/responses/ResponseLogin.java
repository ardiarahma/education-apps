package com.ardiarahma.education.models.responses;

import com.ardiarahma.educationapplication.models.Token;
import com.ardiarahma.educationapplication.models.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Windows 10 on 2/9/2019.
 */

public class ResponseLogin {

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("user")
    @Expose
    private User user;

    @SerializedName("token")
    @Expose
    private Token token;

    public ResponseLogin(String status, User user, Token token) {
        this.status = status;
        this.user = user;
        this.token = token;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }
}
