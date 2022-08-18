package com.bridgelabz.addressbook.repository;

import com.bridgelabz.addressbook.model.AddressBookModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IAddressBookRepository extends JpaRepository<AddressBookModel,Long> {

    Optional<AddressBookModel> findByEmailId(String emailId);

    List<AddressBookModel> findByCityStartsWithIgnoreCase(String city);
    @Query(value = "SELECT * FROM contact_details WHERE city =:city",nativeQuery = true)
    List<AddressBookModel> findByCityContaining(String city);
}