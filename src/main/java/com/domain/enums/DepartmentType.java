package com.domain.enums;

import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum DepartmentType {
	SALES("Sales"), 
	ACCOUNT("Account"), 
	FINANCE("Finance"), 
	OTHER("Other");

	private String name;

	DepartmentType(String name) {
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return name;
	}

	@JsonCreator
	public static DepartmentType decode(final String code) {
		return Stream.of(DepartmentType.values()).filter(targetEnum -> targetEnum.name.equals(code)).findFirst()
				.orElse(null);
	}
}