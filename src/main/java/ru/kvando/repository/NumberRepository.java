package ru.kvando.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kvando.entity.Numbers;

import java.util.List;
import java.util.UUID;


/**
 * @author Shakhzod Ayibjonov
 */

public interface NumberRepository extends JpaRepository<Numbers, UUID> {
    List<Numbers> findAllByContactId(UUID contact_id);

    boolean existsByNumberEquals(String number);
}
