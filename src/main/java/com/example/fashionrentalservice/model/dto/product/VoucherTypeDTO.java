package com.example.fashionrentalservice.model.dto.product;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "tbl_vouchertype")
public class VoucherTypeDTO {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vouchertype_id", columnDefinition = "INT")
	private int voucherTypeID;
	
	@Column(name = "vouchertype_name", columnDefinition = "nvarchar(255)", nullable = true)
	private String voucherTypeName;
}
