package com.phantom.idm.utils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchCriteria {

	private String key;
	private Object value;
	private SearchOperation operation;

	public SearchCriteria(String key, Object value, SearchOperation operation) {
		this.key = key;
		this.value = value;
		this.operation = operation;
	}

}
