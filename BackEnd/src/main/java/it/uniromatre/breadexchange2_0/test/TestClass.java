package it.uniromatre.breadexchange2_0.test;


import it.uniromatre.breadexchange2_0.common.BaseEntity;
import it.uniromatre.breadexchange2_0.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TestClass extends BaseEntity {


    private String name;
    private String description;
    private String test_url;                                                                                             // url dove vengono storati i file per la cover
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;




}
