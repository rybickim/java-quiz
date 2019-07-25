package com.rybickim.javaquiz.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@MappedSuperclass
public abstract class BaseClass<IdType extends Serializable> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private IdType id;
}
