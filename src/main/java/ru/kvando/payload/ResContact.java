package ru.kvando.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

/**
 * @author Shakhzod Ayibjonov
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResContact {

    private UUID id;

    private String name;

    private String lastName;

    private String email;

    private List<ResNumbers> phoneNumber;

    private String address;

}
