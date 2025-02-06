package com.api.gymapi.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import com.api.gymapi.enums.Role;
import lombok.Data;


@Entity
@Data
@Table(name = "employees")
public class Employee extends User{
    @Enumerated(EnumType.STRING)
    private Role role;
}
