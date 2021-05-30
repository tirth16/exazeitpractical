package com.exazeit.practical.dto;

import javax.validation.constraints.NotNull;

import com.exazeit.practical.util.Constants;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PaginationDTO {

	@NotNull(message = Constants.PAGINATION_KEYWORD_SHOULD_NOT_BE_NULL)
	@JsonProperty("keyword")
	private String keyword;
	@NotNull(message = Constants.PAGINATION_LIMIT_SHOULD_NOT_BE_NULL)
	@JsonProperty("page_limit")
	private int pageLimit;
	@NotNull(message = Constants.PAGINATION_START_SHOULD_NOT_BE_NULL)
	@JsonProperty("page_start")
	private int pageStart;
	@NotNull(message = Constants.PAGINATION_SORT_BY_SHOULD_NOT_BE_NULL)
	@JsonProperty("sort_by")
	private String sortBy;
	@NotNull(message = Constants.PAGINATION_SORT_ORDER_SHOULD_NOT_BE_NULL)
	@JsonProperty("sort_order")
	private Boolean sortOrder;

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public int getPageLimit() {
		return pageLimit;
	}

	public void setPageLimit(int pageLimit) {
		this.pageLimit = pageLimit;
	}

	public int getPageStart() {
		return pageStart;
	}

	public void setPageStart(int pageStart) {
		this.pageStart = pageStart;
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public Boolean getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Boolean sortOrder) {
		this.sortOrder = sortOrder;
	}

}
