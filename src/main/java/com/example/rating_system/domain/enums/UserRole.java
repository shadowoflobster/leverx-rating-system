package com.example.rating_system.domain.enums;

public enum UserRole {
    ADMINISTRATOR("Administrator"),
    SELLER("Seller"),
    USER("User");

    private final String dbValue;

    UserRole(String dbValue) {
        this.dbValue = dbValue;
    }

    public String getDbValue() {
        return dbValue;
    }

    public static UserRole fromDbValue(String dbValue){
        for(UserRole role : values()){
            if(role.getDbValue().equalsIgnoreCase(dbValue)){
                return role;
            }
        } throw new IllegalArgumentException("Unknown role:" + dbValue);
    }
}
