package com.domain.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.domain.constants.Utility;
import com.domain.enums.Status;
import com.domain.exception.ServiceException;
import com.domain.model.Employee;
import com.domain.model.Phone;
import com.domain.repository.EmployeeRepository;
import com.domain.service.EmployeeService;
import com.domain.specification.SearchSpecification;
import com.domain.web.mapper.EmployeeMapper;
import com.domain.web.vo.EmployeeVo;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository repo;

	@Autowired
	private EmployeeMapper employeeMapper;

	@Override
	public Page<EmployeeVo> search(String startDate, String endDate, Integer pageNo, Integer pageSize, String sortBy,
			String filterBy, String searchText) {
		final Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Utility.sortByValues(sortBy)));
		final Specification<Employee> specification = SearchSpecification.searchEmployee(startDate, endDate, filterBy,
				searchText);
		return repo.findAll(specification, pageable).map(employeeMapper::toVo);
	}

	@Override
	public List<EmployeeVo> fetchAll() {
		return employeeMapper.toVos(repo.findAll());
	}

	@Override
	public EmployeeVo fetchById(Long id) {
		return employeeMapper.toVo(findById(id));
	}

	private Employee findById(Long id) {
		return repo.findById(id).orElseThrow(() -> new ServiceException("Empoyee ID not present"));
	}

	@Override
	@Transactional
	public EmployeeVo add(EmployeeVo employeeVo) {
	    Employee employee = employeeMapper.toEntity(employeeVo);
	    List<Phone> phones = employeeVo.getPhones().stream()
	            .map(phoneVo -> {
	                Phone phone = new Phone();
	                BeanUtils.copyProperties(phoneVo, phone);
	                phone.setEmployee(employee);
	                return phone;
	            })
	            .collect(Collectors.toList());
	    employee.setPhones(phones.isEmpty() ? null : phones);
	    Employee savedEmployee = repo.save(employee);
	    return employeeMapper.toVo(savedEmployee);
	}

	@Override
	@Transactional
	public EmployeeVo update(EmployeeVo employeeVo) {
		Objects.requireNonNull(employeeMapper.toEntity(employeeVo));
		Optional<Employee> optionalEmployee = repo.findById(employeeVo.getId());
		if (optionalEmployee.isPresent()) {
			Employee employee = employeeMapper.toEntity(employeeVo);
			return employeeMapper.toVo(repo.save(employee));
		} else {
			throw new ServiceException("Employee with ID " + employeeVo.getId() + " not found");
		}
	}

	@Override
	@Transactional
	public void update(long id, Status status) {
		repo.updateStatus(id, status);

	}

	@Override
	public void delete(long id) {
		findById(id);
		repo.deleteById(id);

	}

}