package com.exazeit.practical.response;

import java.util.List;

public class CommonListResponse<T> {

	private List<T> responseList;

	public List<T> getResponseList() {
		return responseList;
	}

	public void setResponseList(List<T> responseList) {
		this.responseList = responseList;
	}

}
