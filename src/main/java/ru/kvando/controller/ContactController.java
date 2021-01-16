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

/**
 * @author Shakhzod Ayibjonov
 * Contact Api controller
 */

@RestController
@RequestMapping("/api/contact")
public class ContactController {

    @Autowired
    ContactServiceImpl contactService;

    @Autowired
    ContactRepository contactRepository;


    /**
     * Add Contact and Update Contact. path='/api/contact'
     * @param reqContact
     * @return HttpEntity in body {@link ApiResponse}
     */
    @PostMapping
    public HttpEntity<?> addContact(@RequestBody ReqContact reqContact) {
        ApiResponse response = contactService.addContact(reqContact);
        return ResponseEntity.status(response.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(response);
    }



    /**
     * Get Contact by id. path='/api/contact/{id}'
     * @param id
     * @return HttpEntity with {@link Contact}
     */
    @GetMapping("/{id}")
    public HttpEntity<?> getProduct(@PathVariable UUID id) {
        Contact contact = contactRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("getContact"));
        return ResponseEntity.ok(contactService.getContact(contact));
    }



    /**
     * Get Pageable Contacts. path='/api/contact'
     * @param page
     * @param size
     * @return HttpEntity with list {@link Contact}
     */
    @GetMapping
    public HttpEntity<?> getContacts(
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE) int page,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_SIZE) int size
    ) {
        return ResponseEntity.ok(contactService.getContacts(page, size));
    }



    /**
     * Delete Contact by id. path='/api/contact/{id}'
     * @param id
     * @return HttpEntity in body {@link ApiResponse}
     */
    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteContact(@PathVariable UUID id) {
        ApiResponse response = contactService.deleteContact(id);
        return ResponseEntity.status(response.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.CONFLICT)
                .body(response);
    }




    /**
     *  Get Contacts by search query nameOrNumber. path='/api/contact/search?nameOrNumber=searchText'
     * @param nameOrNumber
     * @return HttpEntity with list {@link Contact}
     */
    @GetMapping("/search")
    public HttpEntity<?> getContactSearch(@RequestParam(value = "nameOrNumber") String nameOrNumber) {
        return ResponseEntity.ok(new ApiResponse("Contact", true, contactService.getContactSearch(nameOrNumber)));
    }



    /**
     *  Update Contact by id. path='/api/contact/{id}'
     * @param reqContact
     * @return HttpEntity in body {@link ApiResponse}
     */
    @PutMapping("/{id}")
    public HttpEntity<?> updateContact(@PathVariable UUID id, @RequestBody ReqContact reqContact) {
        ApiResponse response = contactService.updateContact(id, reqContact);
        return ResponseEntity.status(response.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(response);
    }
}
