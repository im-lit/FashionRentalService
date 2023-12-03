package com.example.fashionrentalservice.model.dto.product;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_feedbackimg")
public class FeedBackImgDTO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "feedbackimg_id", columnDefinition = "INT")
	private int feedBackImgID;
	
	private String imgUrl;
		
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "feedback_id")
	@JsonIgnoreProperties(value = { "applications", "hibernateLazyInitializer" })
	private FeedBackDTO feedBackDTO;
	
}
