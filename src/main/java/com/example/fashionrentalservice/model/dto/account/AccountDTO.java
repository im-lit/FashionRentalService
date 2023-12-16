package com.example.fashionrentalservice.model.dto.account;




import javax.persistence.*;

import com.example.fashionrentalservice.model.dto.chat.MessageDTO;
import com.example.fashionrentalservice.model.dto.chat.RoomDTO;
import com.example.fashionrentalservice.model.dto.notification.Notification;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_account" )
public class AccountDTO {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id", unique = true)
	private int accountID;
	
	
	private String password;
	
	private String email;
	
	@Enumerated(EnumType.STRING)
	private AccountStatus status;
	
	private int reportedCount;
	
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "roleID", columnDefinition = "INT")
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
	private RoleDTO roleDTO;
	
	@JsonIgnore
	@OneToOne(mappedBy = "accountDTO",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
	private CustomerDTO customerDTO;
	
	@JsonIgnore
	@OneToOne(mappedBy = "accountDTO", cascade = CascadeType.ALL)
	@JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
	private ProductOwnerDTO productOwnerDTO;
	
	@JsonIgnore
	@OneToOne(mappedBy = "accountDTO", cascade = CascadeType.ALL)
	@JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
	private StaffDTO staffDTO;
	
	@JsonIgnore
	@OneToOne(mappedBy = "accountDTO", cascade = CascadeType.ALL)
	@JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
	private WalletDTO walletDTO;

	@JsonIgnore
	@ManyToMany
	private Set<RoomDTO> rooms;

	@JsonIgnore
	@OneToMany(mappedBy = "account")
	private Set<MessageDTO> messages;

	@JsonIgnore
	@OneToMany(mappedBy = "account")
	private Set<Notification> notifications;
	
	public enum AccountStatus {
		BLOCKED, VERIFIED, NOT_VERIFIED
	}
	
}
