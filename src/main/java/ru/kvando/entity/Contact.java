package ru.kvando.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Contact extends AbsEntity {

    @Column(nullable = false, unique = true)
    private String name;

    private String lastName;

    private String email;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contact")
    private List<Numbers> phoneNumber;

    private String address;

}
