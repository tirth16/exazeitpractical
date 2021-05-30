package com.exazeit.practical.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exazeit.practical.dto.ClientDTO;
import com.exazeit.practical.dto.PaginationDTO;
import com.exazeit.practical.response.CommonResponse;
import com.exazeit.practical.service.ClientService;

@RestController
@RequestMapping("client")
public class ClientController {

	@Autowired
	private ClientService clientService;

	/**
	 * Add/Update Client
	 *
	 * @param clientDTO
	 * @return
	 */
	@PostMapping("addOrUpdateClient")
	public ResponseEntity<CommonResponse> addOrUpdateClient(@Valid @RequestBody ClientDTO clientDTO) {

		return clientService.addOrUpdateClient(clientDTO);
	}

	@PostMapping("getAllClients")
	public ResponseEntity<CommonResponse> getAllClients(@RequestBody PaginationDTO paginationDTO) {

		return clientService.getAllClients(paginationDTO);
	}
}
