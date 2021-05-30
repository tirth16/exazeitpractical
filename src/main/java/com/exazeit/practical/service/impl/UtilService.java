package com.exazeit.practical.service.impl;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;

import com.exazeit.practical.dto.PaginationDTO;
import com.exazeit.practical.enumration.ErrorEnum;
import com.exazeit.practical.exception.ErrorResponse;

@Component
public class UtilService {

	/**
	 * Get Error Response
	 *
	 * @param errorEnum
	 * @return
	 */
	public ErrorResponse getErrorEnumResponse(ErrorEnum errorEnum) {

		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setErrorCode(errorEnum.getErrorCode());
		errorResponse.setErrorMessage(errorEnum.getErrorMessage());

		return errorResponse;
	}

	public static PageRequest getPageRequestFromPaginationDTO(PaginationDTO paginationDTO) {
		if (paginationDTO.getPageLimit() != 0) {
			return PageRequest.of((paginationDTO.getPageStart() / paginationDTO.getPageLimit()),
					paginationDTO.getPageLimit(), paginationDTO.getSortOrder() ? Direction.ASC : Direction.DESC,
					paginationDTO.getSortBy());
		} else {
			return PageRequest.of(0, Integer.MAX_VALUE, paginationDTO.getSortOrder() ? Direction.ASC : Direction.DESC,
					paginationDTO.getSortBy());
		}
	}
}
