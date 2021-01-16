package ru.kvando.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import ru.kvando.entity.Contact;
import ru.kvando.entity.Numbers;
import ru.kvando.payload.*;
import ru.kvando.repository.ContactRepository;
import ru.kvando.repository.NumberRepository;
import ru.kvando.utils.AppConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    ContactRepository contactRepository;

    @Autowired
    NumberRepository numberRepository;

    @Override
    public ApiResponse addContact(ReqContact request) {
        try {
            Contact contact = request.getId() == null ? new Contact() : contactRepository.findById(request.getId()).orElseThrow(() -> new ResourceNotFoundException("getContact"));
            contact.setName(request.getName());
            contact.setLastName(request.getLastName());
            contact.setEmail(request.getEmail());
            contact.setAddress(request.getAddress());
            if (request.getId() != null) {
                List<Numbers> numbers = numberRepository.findAllByContactId(request.getId());
                for (Numbers num : numbers) {
                    numberRepository.deleteById(num.getId());
                }
            }
            Contact savedContact = contactRepository.save(contact);
            List<Numbers> numbersList = new ArrayList<>();
            for (ReqNumbers reqNumbers : request.getPhoneNumber()) {
                Numbers numbers = new Numbers();
                numbers.setContact(savedContact);
                numbers.setTitle(reqNumbers.getTitle());
                numbers.setNumber(reqNumbers.getNumber());
                numbersList.add(numbers);
            }

            numberRepository.saveAll(numbersList);

            return new ApiResponse(request.getId() == null ? "Contact Saved!" : "Contact Edited!", true);
        } catch (Exception e) {
            return new ApiResponse("ERROR: " + e.getMessage(), false);
        }
    }

    @Override
    public ResPageable getContacts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Contact> contactPage = contactRepository.findAll(pageable);
        return new ResPageable(
                contactPage.getTotalPages(),
                contactPage.getTotalElements(),
                page,
                contactPage.getContent().stream().map(this::getContact).collect(Collectors.toList()));
    }

    @Override
    public ResContact getContact(Contact contact) {
        ResContact resContact = new ResContact();
        resContact.setId(contact.getId());
        resContact.setName(contact.getName());
        resContact.setLastName(contact.getLastName());
        resContact.setEmail(contact.getEmail());
        resContact.setPhoneNumber(contact.getPhoneNumber().stream().map(this::getNumber).collect(Collectors.toList()));
        resContact.setAddress(contact.getAddress());
        return resContact;
    }

    private ResNumbers getNumber(Numbers numbers) {
        return new ResNumbers(numbers.getTitle(), numbers.getNumber());
    }

    @Override
    public ApiResponse deleteContact(UUID id) {
        try {
            Contact contact = contactRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("getContact"));
            List<Numbers> numbers = numberRepository.findAllByContactId(contact.getId());
            for (Numbers num : numbers) {
                numberRepository.deleteById(num.getId());
            }
            contactRepository.deleteById(id);
            return new ApiResponse("Contact deleted", true);
        } catch (Exception e) {
            return new ApiResponse("Error Contact delete", false);
        }
    }

    @Override
    public List<ResContact> getContactSearch(String nameOrNumber) {
        List<Contact> contacts = contactRepository.getContactSearch(nameOrNumber);
        return contacts.stream().map(this::getContact).collect(Collectors.toList());
    }
}
