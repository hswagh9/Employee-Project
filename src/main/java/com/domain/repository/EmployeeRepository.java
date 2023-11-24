package com.domain.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.domain.enums.Status;
import com.domain.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {

	@Query(value = "Update Employee set status=:status where id =:id")
	@Modifying
	int updateStatus(long id, Status status);
}
