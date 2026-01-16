package org.example.studentmanagementsystem.models;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.util.UUID;

@Entity
@Getter
@Table(name = "admin")
public class Admin {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue
    private UUID id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private String layout;

    public Admin(){}

    public Admin(String email, String password){
        this.email = email;
        this.password = password;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }
}
