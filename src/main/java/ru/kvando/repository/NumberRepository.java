package ru.kvando.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.kvando.entity.Numbers;

import java.util.List;
import java.util.UUID;

public interface NumberRepository extends JpaRepository<Numbers, UUID> {
    List<Numbers> findAllByContactId(UUID contact_id);

    boolean existsByNumberEquals(String number);
}
