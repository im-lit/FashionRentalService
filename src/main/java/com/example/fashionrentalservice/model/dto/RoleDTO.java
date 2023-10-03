package com.example.fashionrentalservice.model.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_role")
public class RoleDTO {
    @Id
    @Column(name = "role_id", columnDefinition = "INT")
    private String roleID;

    private String roleName;
}

