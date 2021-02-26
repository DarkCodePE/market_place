package com.example.multisecurityspring.domain.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

//Heredar propiedades en JPA
@MappedSuperclass
//Para no grabar manualmente la fecha de creacion del usuario
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable {
    @CreatedDate
    private LocalDateTime createdDate;
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;
}
