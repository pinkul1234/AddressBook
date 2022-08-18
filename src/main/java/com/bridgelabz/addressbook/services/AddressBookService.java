package com.bridgelabz.addressbook.services;

import com.bridgelabz.addressbook.dto.AddressBookDto;
import com.bridgelabz.addressbook.exception.ContactNotFoundException;
import com.bridgelabz.addressbook.model.AddressBookModel;
import com.bridgelabz.addressbook.repository.IAddressBookRepository;
import com.bridgelabz.addressbook.util.Response;
import com.bridgelabz.addressbook.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class AddressBookService implements IAddressBookService {
    @Autowired
    IAddressBookRepository iAddressBookRepository;

    @Autowired
    TokenUtil tokenUtil;
    @Autowired
    MailService mailService;

    @Override
    public AddressBookModel addContacts(AddressBookDto addressBookDTO) {
        AddressBookModel addressBookModel = new AddressBookModel(addressBookDTO);
        iAddressBookRepository.save(addressBookModel);
        String body="Contact is added successfully with contactId :-"+addressBookModel.getContactId();
        String subject="Contact Registration Successful";
        mailService.send(addressBookModel.getEmailId(),subject,body);
        return addressBookModel;
    }

    @Override
    public AddressBookModel editContact(String token ,Long id, AddressBookDto addressBookDTO) {
        Long contactId = tokenUtil.decodeToken(token);
        Optional<AddressBookModel> isContactPresent = iAddressBookRepository.findById(contactId);
        if (isContactPresent.isPresent()){
            isContactPresent.get().setFirstName(addressBookDTO.getFirstName());
            isContactPresent.get().setLastName(addressBookDTO.getLastName());
            isContactPresent.get().setCity(addressBookDTO.getCity());
            isContactPresent.get().setAddress(addressBookDTO.getAddress());
            isContactPresent.get().setState(addressBookDTO.getState());
            isContactPresent.get().setZipCode(addressBookDTO.getZipCode());
            isContactPresent.get().setPhoneNumber(addressBookDTO.getPhoneNumber());
            isContactPresent.get().setEmailId(addressBookDTO.getEmailId());
            isContactPresent.get().setPassword(addressBookDTO.getPassword());
            iAddressBookRepository.save(isContactPresent.get());
            String body="Contact is updated successfully with contactId :-"+isContactPresent.get().getContactId();
            String subject="Contact update Successful";
            mailService.send(isContactPresent.get().getEmailId(),subject,body);
            return isContactPresent.get();
        }
        throw new ContactNotFoundException(400,"Contact Not Found");
    }


    @Override
    public List<AddressBookModel> getContact(String token) {
        Long contactId = tokenUtil.decodeToken(token);
        Optional<AddressBookModel> isContactPresent = iAddressBookRepository.findById(contactId);
        if (isContactPresent.isPresent()) {
            List<AddressBookModel> getList = iAddressBookRepository.findAll();
            if (getList.size() > 0) {
                return getList;
            } else {
                throw new ContactNotFoundException(400, "List Is Empty ");
            }
        }
        throw new ContactNotFoundException(400,"Token is not Correct");
    }
    @Override
    public AddressBookModel removeContact(String token){
        Long contactId = tokenUtil.decodeToken(token);
        Optional<AddressBookModel> isContactPresent = iAddressBookRepository.findById(contactId);
        if (isContactPresent.isPresent()){
            iAddressBookRepository.delete(isContactPresent.get());
            String body="Contact is delete successfully with contactId :-"+isContactPresent.get().getContactId();
            String subject="Contact Deleted Successful";
            mailService.send(isContactPresent.get().getEmailId(),subject,body);
            return isContactPresent.get();
        }else {
            throw new ContactNotFoundException(400,"Contact Not Found");
        }
    }

    @Override
    public Response login(String emailId, String password) {
        Optional<AddressBookModel> isContactPresent = iAddressBookRepository.findByEmailId(emailId);
        if (isContactPresent.isPresent()){
            if (isContactPresent.get().getPassword().equals(password)){
                String token = tokenUtil.createToken(isContactPresent.get().getContactId());
                return new Response(200,"Login Successfull",token);
            }else {
                throw new ContactNotFoundException(400,"Contact Not Found");
            }
        }
        throw new ContactNotFoundException(400,"no data found");
    }

    @Override
    public List<AddressBookModel> findWithCity(String city) {
        List<AddressBookModel> getCites = iAddressBookRepository.findByCityContaining(city);
        if (getCites.size()>0){
            return getCites;
        }
        throw new ContactNotFoundException(400,"No Cities Found");
    }

}