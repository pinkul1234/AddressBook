package com.bridgelabz.addressbook.services;

import com.bridgelabz.addressbook.dto.AddressBookDto;
import com.bridgelabz.addressbook.model.AddressBookModel;
import com.bridgelabz.addressbook.util.Response;


import java.util.List;

public interface IAddressBookService  {

    AddressBookModel addContacts(AddressBookDto addressBookDTO);

    AddressBookModel editContact(String token,Long id, AddressBookDto addressBookDTO);

    List<AddressBookModel> getContact(String token);

    AddressBookModel removeContact(String token);


    List<AddressBookModel> findWithCity(String city);

    Response login(String emailId, String password);
}