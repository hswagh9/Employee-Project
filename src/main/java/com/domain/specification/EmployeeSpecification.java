package com.domain.specification;

import org.springframework.data.jpa.domain.Specification;

import com.domain.model.Employee;

public class EmployeeSpecification {

	public static Specification<Employee> searchEmployee(String filterBy, String searchText) {

		Specification<Employee> specification = null;
		if (filterBy != null) {
			if (filterBy.equals("firstName")) {
				specification = getEmpByName(filterBy, searchText);
			} else if (filterBy.equals("address")) {
				specification = getEmpByAddress(filterBy, searchText);
			} else {
				specification = getEmpByStatus(filterBy, searchText);
			}
			return specification;
		} else {
			return null;
		}
	}

	private static Specification<Employee> getEmpByStatus(String filterBy, String searchText) {
		return (root, query, criterialBuilder) -> {
			return criterialBuilder.like((root.get(filterBy)), "%" + searchText + "%");
		};

	}

	private static Specification<Employee> getEmpByAddress(String filterBy, String searchText) {
		return (root, query, criterialBuilder) -> {
			return criterialBuilder.like((root.get(filterBy)), "%" + searchText + "%");
		};
	}

	private static Specification<Employee> getEmpByName(String filterBy, String searchText) {
		return (root, query, criterialBuilder) -> {
			return criterialBuilder.like((root.get(filterBy)), "%" + searchText + "%");
		};
	}
}
