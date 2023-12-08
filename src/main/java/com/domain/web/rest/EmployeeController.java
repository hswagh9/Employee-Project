package com.domain.web.rest;

import static com.domain.constants.Constants.EMPLOYEE_CONTROLLER;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.domain.common.constant.GlobalConstants;
import com.domain.enums.Status;
import com.domain.response.entity.RestResponse;
import com.domain.response.entity.RestStatus;
import com.domain.service.EmployeeService;
import com.domain.web.vo.EmployeeVo;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping(EMPLOYEE_CONTROLLER)
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@Operation(summary = "View a list of available Employees")
	@GetMapping("/search")
	public ResponseEntity<RestResponse<Page<EmployeeVo>>> search(
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = "" + Integer.MAX_VALUE, required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = "id:desc", required = false) String sortBy,
			@RequestParam(value = "filterBy", required = false) String filterBy,
			@RequestParam(value = "searchText", required = false) String searchText) {
		final RestStatus<?> restStatus = new RestStatus<>(HttpStatus.OK, GlobalConstants.FETCH_RECORDS);
		final Page<EmployeeVo> allEmployees = employeeService.search(startDate, endDate, pageNo, pageSize, sortBy,
				filterBy, searchText);
		final RestResponse<Page<EmployeeVo>> response = new RestResponse<>(allEmployees, restStatus);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Operation(summary = "View a list of available Employees")
	@GetMapping
	public ResponseEntity<RestResponse<List<EmployeeVo>>> fetchAll() {
		RestStatus<?> restStatus = new RestStatus<>(HttpStatus.OK, GlobalConstants.FETCH_RECORDS);
		List<EmployeeVo> list = employeeService.fetchAll();
		RestResponse<List<EmployeeVo>> response = new RestResponse<>(list, restStatus);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Operation(summary = "View Employee By Id")
	@GetMapping("/{id}")
	public ResponseEntity<RestResponse<EmployeeVo>> fetchById(@PathVariable(name = "id") long id) {
		RestStatus<?> restStatus = new RestStatus<>(HttpStatus.OK, GlobalConstants.FETCH_RECORDS);
		RestResponse<EmployeeVo> response = new RestResponse<>(employeeService.fetchById(id), restStatus);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@Operation(summary = "Add new Employee")
	@PostMapping
	public ResponseEntity<RestResponse<EmployeeVo>> add(@Valid @RequestBody EmployeeVo employeeVo) {
		RestStatus<?> restStatus = new RestStatus<>(HttpStatus.CREATED, GlobalConstants.ADD_RECORD);
		RestResponse<EmployeeVo> response = new RestResponse<>(employeeService.add(employeeVo), restStatus);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@Operation(summary = "Update Employee By Id")
	@PutMapping("/{id}")
	public ResponseEntity<RestResponse<EmployeeVo>> update(@PathVariable(name = "id") long id,
			@Valid @RequestBody EmployeeVo employeeVo) {
		employeeVo.setId(id);
		RestStatus<?> restStatus = new RestStatus<>(HttpStatus.OK, GlobalConstants.UPDATE_RECORD);
		RestResponse<EmployeeVo> response = new RestResponse<>(employeeService.update(employeeVo), restStatus);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * Method : Update Employee status URL
	 * http://localhost:8080/v0/employee/2952?status=ACTIVATED
	 * 
	 * @param id
	 * @param status
	 * @return
	 */
	@Operation(summary = "Update Employee Status")
	@PatchMapping("/{id}")
	public ResponseEntity<RestResponse<Void>> updateStatus(@PathVariable(name = "id") long id,
			@RequestParam(name = "status") Status status) {
		RestStatus<?> restStatus = new RestStatus<>(HttpStatus.OK, GlobalConstants.UPDATE_RECORD);
		employeeService.update(id, status);
		RestResponse<Void> response = new RestResponse<>(null, restStatus);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Operation(summary = "Delete Employee")
	@DeleteMapping("/{id}")
	public ResponseEntity<RestResponse<Void>> delete(@PathVariable(name = "id") long id) {
		RestStatus<?> restStatus = new RestStatus<>(HttpStatus.NO_CONTENT, GlobalConstants.DELETE_MSG);
		employeeService.delete(id);
		RestResponse<Void> response = new RestResponse<>(null, restStatus);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}