package com.domain.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.domain.common.BaseModel;
import com.domain.enums.PhoneType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "phone_master")
public class Phone extends BaseModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(name = "type", nullable = true, length = 50)
	private PhoneType type;

	@Column(name = "value", nullable = false, length = 30)
	private String value;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "employee_id", nullable = false) 
	private Employee employee;

}
