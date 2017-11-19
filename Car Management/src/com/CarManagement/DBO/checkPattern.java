package com.CarManagement.DBO;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class checkPattern {
    public boolean isValidEmail(String email){
        boolean isValid = false;
        String patternEmail = "\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b";
        Pattern pattern = Pattern.compile(patternEmail);
        Matcher matcher = pattern.matcher(email);
        if (matcher.find()){
            isValid = true;
        }
        else isValid = false;

        return isValid;
    }
    public boolean isValidCarDNum(String cardNum){
        boolean isValid = false;
        String patternCardNum = "^[0-9]{16}";
        Pattern pattern = Pattern.compile(patternCardNum);
        Matcher matcher = pattern.matcher(cardNum);
        if (matcher.find()){
            isValid = true;
        }
        else isValid = false;
        return isValid;
    }
    public boolean isValidCVV(String cvv){
        boolean isValid = false;
        String patterncvv = "^[0-9]{3}";
        Pattern pattern = Pattern.compile(patterncvv);
        Matcher matcher = pattern.matcher(cvv);
        if (matcher.find()){
            isValid = true;
        }
        else isValid = false;
        return isValid;
    }
    public boolean isValidDate(String date){
        boolean isValid = false;
        String patternDate = "(0[1-9]|1[012])[/](19|20|21|22|23|24)";
        Pattern pattern = Pattern.compile(patternDate);
        Matcher matcher = pattern.matcher(String.valueOf(date));
        if (matcher.find()){
            isValid = true;
        }
        else isValid = false;
        return isValid;
    }


    public boolean isAdmin(String username){
        boolean isAdmin = false;
        String patternAdmin = "^[A,a]+:[A-Za-z]{1,10}"; //write again
        Pattern pattern = Pattern.compile(patternAdmin);
        Matcher matcher = pattern.matcher(username);
        if (matcher.find()){
            isAdmin = true;
        }
        else isAdmin = false;

        return isAdmin;
    }
    public boolean isValidPhone(String number){
        boolean isValid = false;
        String patternPhone = "^[0-9\\-]*$";
        Pattern pattern = Pattern.compile(patternPhone);
        Matcher matcher = pattern.matcher(number);
        if (matcher.find()){
            isValid = true;
        }
        else isValid = false;

        return isValid;
    }
}
