package com.bridgelabz.addressbook.controller;

import com.bridgelabz.addressbook.dto.AddressBookDto;
import com.bridgelabz.addressbook.model.AddressBookModel;
import com.bridgelabz.addressbook.services.IAddressBookService;
import com.bridgelabz.addressbook.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

    @RestController
    @RequestMapping("/addressBook")
    public class AddressBookController {

        @Autowired
        IAddressBookService iAddressBookService;
        @GetMapping("/welcome")
        public String welcomeMessage(){
            return "Welcome to Address Book Spring application";
        }

        //create
        @PostMapping("createContact")
        public AddressBookModel createContact(@RequestBody AddressBookDto addressBookDTO){
            return iAddressBookService.addContacts(addressBookDTO);
        }

        //update
        @PutMapping("update{id}")
        public AddressBookModel updateContact(@RequestParam String token,@PathVariable Long id,@RequestBody AddressBookDto addressBookDTO){
            return iAddressBookService.editContact(token,id,addressBookDTO);
        }

        //retrieve in list
        @GetMapping("getContactList")
        public List<AddressBookModel> getContactList(String token){
            return iAddressBookService.getContact(token);
        }

        //delete contact
        @DeleteMapping("deleteContact")
        public AddressBookModel deleteContact(String token){
            return iAddressBookService.removeContact(token);
        }

        //login
        @PostMapping("loginContact")
        public Response login (@RequestParam String emailId , @RequestParam String password){
            return iAddressBookService.login(emailId,password);
        }

    }

