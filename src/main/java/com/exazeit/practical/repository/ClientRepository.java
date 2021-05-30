package com.exazeit.practical.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.exazeit.practical.entity.Client;

public interface ClientRepository
		extends PagingAndSortingRepository<Client, Integer>, JpaSpecificationExecutor<Client> {

	boolean existsByMobileNumber(String mobileNumber);

	boolean existsByIdNumber(String idNumber);

	boolean existsByMobileNumberAndIdNot(String mobileNumber, Integer id);

	boolean existsByIdNumberAndIdNot(String idNumber, Integer id);

}
