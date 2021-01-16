package ru.kvando.payload;

import lombok.Data;

import java.util.List;
import java.util.UUID;

/**
 * @author Shakhzod Ayibjonov
 */

@Data
public class ReqContact {
    private UUID id;

    private String name;

    private String lastName;

    private String email;

    private List<ReqNumbers> phoneNumber;

    private String address;

}
