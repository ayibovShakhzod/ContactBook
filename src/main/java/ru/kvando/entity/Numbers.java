package ru.kvando.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Numbers extends AbsEntity{
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Contact contact;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, unique = true)
    private String number;


}
