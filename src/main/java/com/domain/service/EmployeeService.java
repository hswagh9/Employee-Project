package com.domain.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.domain.enums.Status;
import com.domain.web.vo.EmployeeVo;

@Service
public interface EmployeeService {

	public Page<EmployeeVo> search(String startDate, String endDate, Integer pageNo, Integer pageSize, String sortBy, String filterBy, String searchText);

	public List<EmployeeVo> fetchAll();

	public EmployeeVo fetchById(Long id);

	public EmployeeVo add(EmployeeVo employeeVo);

	public EmployeeVo update(EmployeeVo employeeVo);

	public void update(long id, Status status);

	public void delete(long id);

}
