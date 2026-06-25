/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quickchatpart3;

/**
 *
 * @author maboy
 */
public class Login {

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String cellPhoneNumber;

    public Login(String firstName, String lastName,
            String username, String password,
            String cellPhoneNumber) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.cellPhoneNumber = cellPhoneNumber;
    }
//This is meant to validates the username requirements
    public boolean checkUserName() {

        return username.contains("_")
                && username.length() <= 5;
    }
//Check complexity of the password
    public boolean checkPasswordComplexity() {

        boolean hasCapital = false;
        boolean hasNumber = false;
        boolean hasSpecial = false;

        for (int i = 0; i < password.length(); i++) {

            char currentCharacter = password.charAt(i);

            if (Character.isUpperCase(currentCharacter)) {
                hasCapital = true;
            }

            if (Character.isDigit(currentCharacter)) {
                hasNumber = true;
            }

            if (!Character.isLetterOrDigit(currentCharacter)) {
                hasSpecial = true;
            }

        }

        return password.length() >= 8
                && hasCapital
                && hasNumber
                && hasSpecial;

    }
//checks cellphone number requirements
    public boolean checkCellPhoneNumber() {

        return cellPhoneNumber.startsWith("+27")
                && cellPhoneNumber.length() == 12;

    }

    public String registerUser() {

        if (!checkUserName()) {

            return "Username is not correctly formatted; please ensure your username contains an underscore and is no more than five characters long.";

        }

        if (!checkPasswordComplexity()) {

            return "Password is not correctly formatted; please ensure password contains eight characters, a capital letter, a number and a special character.";

        }

        if (!checkCellPhoneNumber()) {

            return "Cell phone number incorrectly formatted or does not contain international code.";

        }

        return "User registered successfully.";

    }

    public boolean loginUser(String enteredUsername,
            String enteredPassword) {

        return enteredUsername.equals(username)
                && enteredPassword.equals(password);

    }

    public String returnLoginStatus(boolean loginStatus) {

        if (loginStatus) {

            return "Welcome "
                    + firstName
                    + " "
                    + lastName
                    + ", it is great to see you again.";

        }

        return "Username or password incorrect, please try again.";

    }

}
