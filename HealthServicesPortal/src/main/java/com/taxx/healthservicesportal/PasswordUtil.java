package com.taxx.healthservicesportal;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

    //convert an actual password into a hash
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    //check whether the entered password matches the stored hash
    public static boolean checkPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}
