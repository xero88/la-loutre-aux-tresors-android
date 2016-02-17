package ch.xero88.qoqa.Utils;

/**
 * Created by Anthony on 13/02/2016.
 */
public class ValidationUtils {

    public static boolean isEmailValid(String email) {
        return email.contains("@");
    }

    public static boolean isPasswordValid(String password) {
        return password.length() > 4;
    }


}
