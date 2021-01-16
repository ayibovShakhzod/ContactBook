package ru.kvando.payload;

import lombok.Data;
import ru.kvando.entity.Numbers;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class ReqContact {
    private UUID id;

    private String name;

    private String lastName;

    private String email;

    private List<ReqNumbers> phoneNumber;

    private String address;

}
