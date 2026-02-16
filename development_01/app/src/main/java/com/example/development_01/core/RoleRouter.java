package com.example.development_01.core;

public class RoleRouter {
    public String determineRole(String email){
        if(email.equals("abdul.123@dal.ca")){
            return "Employer";
        } else if (email.equals("abd.123@dal.ca")) {
            return "Employee";
        } else if (email.trim().isEmpty()){
            return "Empty Email";
        }


        return "Invalid Email";
    }
}
