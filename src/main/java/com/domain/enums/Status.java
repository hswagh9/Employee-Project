package com.domain.enums;

import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Status {

	ACTIVATED("Activated"), DEACTIVATED("Deactivated"), DELETED("Deleted");

	private String status;

	private Status(String status) {
		this.status = status;
	}

	@JsonValue
	public String getStatus() {
		return status;
	}

	@JsonValue
	public static Status decode(String status) {
		return Stream.of(Status.values()).filter(x -> x.status.equalsIgnoreCase(status)).findFirst()
				.orElse(null);
	}

}