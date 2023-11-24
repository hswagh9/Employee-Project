package com.domain.web.mapper;
import java.util.List;

import org.mapstruct.Mapper;

import com.domain.model.Employee;
import com.domain.web.vo.EmployeeVo;

@Mapper
public interface EmployeeMapper {

	public EmployeeVo toVo(Employee employee);

	public Employee toEntity(EmployeeVo vo);

	public List<EmployeeVo> toVos(List<Employee> list);

	public List<Employee> toEntities(List<EmployeeVo> list);
}
