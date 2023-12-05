package com.domain.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;

import com.domain.common.BaseModel;
import com.domain.enums.Status;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "employee_master")
public class Employee extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	private long id;

	@Column(name = "first_name", length = 25, nullable = true)
	private String firstName;

	@Column(name = "last_name", length = 25, nullable = true)
	private String lastName;

	@Column(name = "address", length = 500, nullable = true)
	private String address;

	@Column(name = "salary", length = 7, nullable = true)
	private BigDecimal salary;

	@Column(name = "status", length = 20, nullable = false)
	@Enumerated(EnumType.STRING)
	private Status status;

	@BatchSize(size = 50)
	@OrderBy("type asc")
	@OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Phone> phones;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "department_id", nullable = true)
	private Department departments;
}