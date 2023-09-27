package com.example.G4_DailyReport.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "role")
@Table(name = "role")
@Data
@NoArgsConstructor
public class Role implements Serializable{
    @Serial
    private static final long serialVersionUID = -5204391003825277886L;

    @Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @ManyToMany(mappedBy = "roles")
    private List<User> users = new ArrayList<>();

    public Role(String name) {
        this.name = name;
    }
}