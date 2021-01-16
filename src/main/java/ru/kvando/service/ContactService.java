package ru.kvando.service;

import ru.kvando.entity.Contact;
import ru.kvando.payload.ApiResponse;
import ru.kvando.payload.ReqContact;
import ru.kvando.payload.ResContact;
import ru.kvando.payload.ResPageable;

import java.util.List;
import java.util.UUID;

/**
 * @author Shakhzod Ayibjonov
 */

public interface ContactService {

    ApiResponse addContact(ReqContact reqContact);

    ResPageable getContacts(int page, int size);

    ResContact getContact(Contact contact);

    ApiResponse deleteContact(UUID id);

    List<ResContact> getContactSearch(String nameOrNumber);

    ApiResponse updateContact(UUID id, ReqContact reqContact);
}
