package com.meddetectai.main;


public class MySQL {
    private static String url = "jdbc:mysql://uotyb.h.filess.io:3307/medDetectIA_writtenpie";
    private static String user = "medDetectIA_writtenpie";
    private static String password = "c04f420403bf2ac311d0524a768e627fd3f52069";

    public static String getUrl() {
        return url;
    }
    public static String setUrl() {
        return MySQL.url;
    }

    public static String getUser() {
        return user;
    }
    public static void setUser(String user) {
        MySQL.user = user;
    }


    public static String getPassword() {
        return password;
    }
    public static void setPassword(String password) {
        MySQL.password = password;
    }

}