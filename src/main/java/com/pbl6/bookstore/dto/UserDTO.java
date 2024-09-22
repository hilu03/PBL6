package com.pbl6.bookstore.dto;


public class UserDTO {
    private int id;

    private String fullName;

    private String phoneNumber;

    private String role;

    private String email;

    private String username;

    public UserDTO() {

    }

    public UserDTO(String fullName, String phoneNumber, String role, String email, String username) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.email = email;
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
