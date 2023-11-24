package com.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.domain.model.Phone;

@Repository
public interface EmployeeContactPhoneRepository	extends JpaRepository<Phone, Long>, JpaSpecificationExecutor<Phone> {

}