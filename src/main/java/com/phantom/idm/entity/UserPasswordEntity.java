package com.phantom.idm.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_password")
public class UserPasswordEntity {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    private UUID id;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UserDetailEntity userDetail;

    @Column(name = "password", nullable = false, length = 64)
    private String password;

    @Column(name = "created_by", nullable = false, length = 100)
    private String createdBy;

    @Column(name = "updated_by", length = 100)
    private String updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", nullable = false, updatable = false)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_date")
    private Date updatedDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "expired")
    private Date expired;

}
