package com.bridgelabz.addressbook.dto;

import lombok.Data;

import javax.validation.constraints.Pattern;


@Data
public class AddressBookDto {
    private Long contactId;
    @Pattern(regexp = "[A-Z][a-z]{2,}",message = "First letter should be capital")
    private String firstName;
    @Pattern(regexp = "[A-Z][a-z]{2,}",message = "First letter should be capital")
    private String lastName;
    private String address;
    private String city;
    private String state;
    private Long zipCode;
    private Long phoneNumber;
    @Pattern(regexp = "[\\w+-]+(\\.[\\w+-]+)*@[\\w]+(\\.[\\w]+)?(?=(\\.[A-Za-z_]{2,3}$|\\.[a-zA-Z]{2,3}$)).*",message = "email regex")
    private String emailId;
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[\\W])(?=.*[a-z]).{8,}$",message = "password regex")
    private String password;
    
}