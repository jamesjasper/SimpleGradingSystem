/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pcc.sgs.helper;

import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author James Jasper D. Villamor
 */
public class PasswordManager {
    
    // Hash a password using Bcrypt
    public String hashPassword(String plainTextPassword) {
        // Number of log rounds determines the complexity of the hash
        int logRounds = 12;
        String hashedPassword = BCrypt.hashpw(plainTextPassword, BCrypt.gensalt(logRounds));
        return hashedPassword;
    }

    // Check if a given plain text password matches the hashed password
    public boolean checkPassword(String plainTextPassword, String hashedPassword) {
        return BCrypt.checkpw(plainTextPassword, hashedPassword);
    }
    
}
