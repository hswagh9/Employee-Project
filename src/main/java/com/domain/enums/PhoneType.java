package com.domain.enums;
import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum PhoneType {
	PERSONAL("Personal"), OFFICE("Office"), NEIGHBOUR("Neighbour");

	private String name;

	PhoneType(String name) {
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return name;
	}

	@JsonCreator
	public static PhoneType decode(final String code) {
		return Stream.of(PhoneType.values()).filter(targetEnum -> targetEnum.name.equals(code)).findFirst()
				.orElse(null);
	}
}