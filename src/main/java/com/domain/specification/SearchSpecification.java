package com.domain.specification;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.Predicate;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import com.domain.common.constant.GlobalConstants;
import com.domain.common.utility.Utility;
import com.domain.exception.ServiceException;
import com.domain.model.Employee;

public class SearchSpecification {

	/**
	 * To get the required jpa specification
	 * 
	 * @param filterBy
	 * @param searchText
	 * @return
	 */
	public static Specification<Employee> searchEmployee(String startDate, String endDate,String filterBy, String searchText) {
		return (root, query, cb) -> {

			String containsLikePattern = Utility.getContainsLikePattern(searchText);
			List<Predicate> predicates = new ArrayList<>();
			if (StringUtils.isNotBlank(filterBy)) {
				Map<String, String> filterByMap = Utility.prepareFilterByMap(filterBy);
				for (Map.Entry<String, String> filterByEntry : filterByMap.entrySet()) {
					predicates.add(cb.equal(root.<String>get(filterByEntry.getKey()).as(String.class),
							filterByEntry.getValue()));
				}
			}
			predicates.add(cb.or(cb.like(cb.lower(root.<String>get("firstName")), containsLikePattern),
					cb.like(cb.lower(root.<String>get("address")), containsLikePattern),
					cb.like(cb.lower(root.<String>get("salary").as(String.class)), containsLikePattern),
					cb.like(cb.lower(root.<String>get("status").as(String.class)), containsLikePattern)));
			
			if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
				if(Boolean.TRUE.equals(Utility.validateDate(startDate, GlobalConstants.SIMPLE_DATE_FORMAT, endDate, GlobalConstants.SIMPLE_DATE_FORMAT))){
					Specification<Employee> dateRangeSpecification = Utility.createDateRangeSpecification(startDate, endDate, root.get("logTimeStamp"));
					if (dateRangeSpecification != null) {
						predicates.add(dateRangeSpecification.toPredicate(root, query, cb));
					}
				} else {
					throw new ServiceException(GlobalConstants.INVALID_DATE);
				}
			}
			return cb.and(predicates.toArray(new Predicate[0]));
		};
	}
}