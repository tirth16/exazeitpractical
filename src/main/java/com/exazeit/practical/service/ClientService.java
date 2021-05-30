package com.exazeit.practical.service;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.exazeit.practical.dto.ClientDTO;
import com.exazeit.practical.dto.PaginationDTO;
import com.exazeit.practical.response.CommonResponse;

@Service
public interface ClientService {

	ResponseEntity<CommonResponse> addOrUpdateClient(@Valid ClientDTO clientDTO);

	ResponseEntity<CommonResponse> getAllClients(PaginationDTO paginationDTO);

}
