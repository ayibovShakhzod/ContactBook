package ru.kvando.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.kvando.entity.Contact;
import ru.kvando.payload.ResContact;

import java.util.List;
import java.util.UUID;

public interface ContactRepository extends JpaRepository<Contact, UUID> {
    @Query(value = "SELECT * FROM contact WHERE name like CONCAT('%', :name, '%') or last_name like CONCAT('%', :name, '%') or id IN (SELECT contact_id FROM numbers where number like CONCAT('%', :name, '%'))", nativeQuery = true)
    List<Contact> getContactSearch(@Param("name") String name);
}