package dev.saikat.userauthservice.models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@MappedSuperclass
public abstract class BaseModel {

    @Id //To tell JPA that this is the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) //For auto increment of the primary key(ID)
    private Long id;

    private Date createdAt;

    private Date lastUpdatedAt;

    private State state;  //ACTIVE or INACTIVE as Deleted or not Deleted

}
