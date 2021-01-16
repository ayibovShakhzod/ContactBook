package ru.kvando.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kvando.entity.Contact;
import ru.kvando.payload.ApiResponse;
import ru.kvando.payload.ReqContact;
import ru.kvando.repository.ContactRepository;
import ru.kvando.service.ContactServiceImpl;
import ru.kvando.utils.AppConstants;

import java.util.UUID;

@RestController
@RequestMapping("/api/contact")
public class ContactController {

    @Autowired
    ContactServiceImpl contactService;

    @Autowired
    ContactRepository contactRepository;

    @PostMapping
    public HttpEntity<?> addContact(@RequestBody ReqContact reqContact) {
        ApiResponse response = contactService.addContact(reqContact);
        return ResponseEntity.status(response.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(response);
    }

    @GetMapping("/{id{")
    public HttpEntity<?> getProduct(@PathVariable UUID id) {
        Contact contact = contactRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("getContact"));
        return ResponseEntity.ok(contactService.getContact(contact));
    }

    @GetMapping
    public HttpEntity<?> getContacts(
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE) int page,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_SIZE) int size
    ) {
        return ResponseEntity.ok(contactService.getContacts(page, size));
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteContact(@PathVariable UUID id) {
        ApiResponse response = contactService.deleteContact(id);
        return ResponseEntity.status(response.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.CONFLICT)
                .body(response);
    }

    @GetMapping("/search")
    public HttpEntity<?> getContactSearch(@RequestParam(value = "nameOrNumber") String nameOrNumber) {
        return ResponseEntity.ok(new ApiResponse("Contact ", true, contactService.getContactSearch(nameOrNumber)));
    }
}
