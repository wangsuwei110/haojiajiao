package com.education.hjj.bz.util;

public class Token {

	private Token() {}
    private  String token;
    private  String accessToken;
    private  long expiryTime;
    private static Token instance  = new Token();
    public static Token getInstance() {
        return instance;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public long getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(long expiryTime) {
        this.expiryTime = expiryTime;
    }

    public  String getToken() {
        return token;
    }
    public  void setToken(String token) {
        this.token = token;
    }

}
