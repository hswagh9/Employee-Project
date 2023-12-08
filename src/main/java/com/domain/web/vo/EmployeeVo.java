package com.domain.web.vo;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.domain.enums.Status;

import lombok.Data;

@Data
public class EmployeeVo {

	private long id = 0L;

	@NotBlank(message = "First name is required")
	private String firstName;

	@NotBlank(message = "Last name is required")
	private String lastName;

	@NotBlank(message = "Address is required")
	private String address;

	private BigDecimal salary;

	@NotNull(message = "Status is required")
	private Status status;

	private List<PhoneVo> phones;
	
	private DepartmentVo departments; 

}
