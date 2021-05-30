/**
 *
 */
package com.exazeit.practical.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.exazeit.practical.dto.ClientDTO;
import com.exazeit.practical.dto.PaginationDTO;
import com.exazeit.practical.entity.Client;
import com.exazeit.practical.enumration.SearchOperation;
import com.exazeit.practical.repository.ClientRepository;
import com.exazeit.practical.response.CommonResponse;
import com.exazeit.practical.specification.ClientSpecification;
import com.exazeit.practical.util.SearchCriteria;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {

	@InjectMocks
	private ClientServiceImpl clientService;

	@Mock
	private ClientRepository clientRepository;

	@Mock
	private UtilService utilService;

	private ClientDTO clientDTO;

	@BeforeEach
	public void declareClientDTO() {

		clientDTO = new ClientDTO();
		clientDTO.setFirstName("Test First Name");
		clientDTO.setLastName("Test Last Name");
		clientDTO.setIdNumber("840911 4567 658");
		clientDTO.setMobileNumber("1234567890");
	}

	@Test
	void testCaseForAddClientToTestMobileNumber() {

		// Given
		Mockito.when(clientRepository.existsByMobileNumber(ArgumentMatchers.anyString())).thenReturn(true);

		// When
		ResponseEntity<CommonResponse> commonResponse = clientService.addOrUpdateClient(clientDTO);

		// Then
		assertEquals(HttpStatus.IM_USED, commonResponse.getStatusCode());
	}

	@Test
	void testCaseForAddClientToTestIdNumber() {

		// Given
		Mockito.when(clientRepository.existsByMobileNumber(ArgumentMatchers.anyString())).thenReturn(false);
		Mockito.when(clientRepository.existsByIdNumber(ArgumentMatchers.anyString())).thenReturn(true);

		// When
		ResponseEntity<CommonResponse> commonResponse = clientService.addOrUpdateClient(clientDTO);

		// Then
		assertEquals(HttpStatus.IM_USED, commonResponse.getStatusCode());
	}

	@Test
	void testCaseForAddClient() {

		// Given
		Mockito.when(clientRepository.existsByMobileNumber(ArgumentMatchers.anyString())).thenReturn(false);

		Mockito.when(clientRepository.existsByIdNumber(ArgumentMatchers.anyString())).thenReturn(false);

		// When
		ResponseEntity<CommonResponse> commonResponse = clientService.addOrUpdateClient(clientDTO);

		// Then
		assertTrue(commonResponse.getStatusCode().equals(HttpStatus.OK));
	}

	@Test
	void testCaseForUpdateClientWhenClientNotFound() {

		// Given
		this.clientDTO.setId(1);
		Mockito.when(clientRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.empty());

		// When
		ResponseEntity<CommonResponse> commonResponse = clientService.addOrUpdateClient(clientDTO);

		// Then
		assertThat(commonResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

	}

	@Test
	void testCaseForUpdateClientWhenIdNumberIsSame() {

		// Given
		this.clientDTO.setId(1);
		Client client = new Client();
		client.setId(1);
		client.setFirstName("Test First Name");
		client.setLastName("Test Last Name");
		client.setIdNumber("840911 4567 655");
		client.setMobileNumber("1234567890");
		Mockito.when(clientRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(client));

		Mockito.when(clientRepository.existsByIdNumberAndIdNot(ArgumentMatchers.anyString(), ArgumentMatchers.anyInt()))
				.thenReturn(true);

		// When
		ResponseEntity<CommonResponse> commonResponse = clientService.addOrUpdateClient(clientDTO);

		// Then
		assertEquals(HttpStatus.IM_USED, commonResponse.getStatusCode());
	}

	@Test
	void testCaseForUpdateClientWhenMobileNumberIsSame() {

		// Given
		this.clientDTO.setId(1);
		Client client = new Client();
		client.setId(1);
		client.setFirstName("Test First Name");
		client.setLastName("Test Last Name");
		client.setIdNumber("840911 4567 655");
		client.setMobileNumber("1234567890");
		Mockito.when(clientRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(client));

		Mockito.when(clientRepository.existsByIdNumberAndIdNot(ArgumentMatchers.anyString(), ArgumentMatchers.anyInt()))
				.thenReturn(false);

		Mockito.when(
				clientRepository.existsByMobileNumberAndIdNot(ArgumentMatchers.anyString(), ArgumentMatchers.anyInt()))
				.thenReturn(true);

		// When
		ResponseEntity<CommonResponse> commonResponse = clientService.addOrUpdateClient(clientDTO);

		// Then
		assertEquals(HttpStatus.IM_USED, commonResponse.getStatusCode());
	}

	@Test
	void testCaseForUpdateClient() {

		// Given
		this.clientDTO.setId(1);
		Client client = new Client();
		client.setId(1);
		client.setFirstName("Test First Name");
		client.setLastName("Test Last Name");
		client.setIdNumber("840911 4567 658");
		client.setMobileNumber("1234567890");

		Mockito.when(clientRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(client));

		Mockito.when(
				clientRepository.existsByMobileNumberAndIdNot(ArgumentMatchers.anyString(), ArgumentMatchers.anyInt()))
				.thenReturn(false);

		// When
		ResponseEntity<CommonResponse> commonResponse = clientService.addOrUpdateClient(clientDTO);

		// Then
		assertSame(HttpStatus.OK, commonResponse.getStatusCode());
	}

	@Test
	void testCaseForGetAllClientsWhenListEmpty() {

		// Given
		PaginationDTO paginationDTO = new PaginationDTO();
		paginationDTO.setKeyword("");
		paginationDTO.setPageLimit(10);
		paginationDTO.setPageStart(1);
		paginationDTO.setSortBy("id");
		paginationDTO.setSortOrder(true);

		String search = paginationDTO.getKeyword();
		List<SearchCriteria> serviceListCriteria = new ArrayList<>();
		serviceListCriteria.add(new SearchCriteria("firstName", SearchOperation.EQUALS.toString(), search));
		serviceListCriteria.add(new SearchCriteria("lastName", SearchOperation.EQUALS.toString(), search));
		serviceListCriteria.add(new SearchCriteria("mobileNumber", SearchOperation.EQUALS.toString(), search));
		serviceListCriteria.add(new SearchCriteria("idNumber", SearchOperation.EQUALS.toString(), search));
		ClientSpecification spec = new ClientSpecification(serviceListCriteria);
		Specification.where(spec);
		Mockito.when(clientRepository.findAll(ArgumentMatchers.any(Specification.class),
				ArgumentMatchers.any(Pageable.class))).thenReturn(Page.empty());

		// When
		ResponseEntity<CommonResponse> commonResponse = clientService.getAllClients(paginationDTO);

		// Then
		assertEquals(HttpStatus.NO_CONTENT, commonResponse.getStatusCode());
	}

	@Test
	void testCaseForGetAllClients() {

		// Given
		PaginationDTO paginationDTO = new PaginationDTO();
		paginationDTO.setKeyword("");
		paginationDTO.setPageLimit(10);
		paginationDTO.setPageStart(1);
		paginationDTO.setSortBy("id");
		paginationDTO.setSortOrder(true);

		String search = paginationDTO.getKeyword();
		List<SearchCriteria> serviceListCriteria = new ArrayList<>();
		serviceListCriteria.add(new SearchCriteria("firstName", SearchOperation.EQUALS.toString(), search));
		serviceListCriteria.add(new SearchCriteria("lastName", SearchOperation.EQUALS.toString(), search));
		serviceListCriteria.add(new SearchCriteria("mobileNumber", SearchOperation.EQUALS.toString(), search));
		serviceListCriteria.add(new SearchCriteria("idNumber", SearchOperation.EQUALS.toString(), search));
		ClientSpecification spec = new ClientSpecification(serviceListCriteria);
		Specification.where(spec);
		List<Client> clientList = new ArrayList<>();
		Client client = new Client();
		client.setFirstName("Test First Name");
		client.setLastName("Test Last Name");
		client.setIdNumber("840911 4567 658");
		client.setId(1);
		client.setMobileNumber("1234567890");
		clientList.add(client);
		Page<Client> pageOfClient = new PageImpl<>(clientList);
		Mockito.when(clientRepository.findAll(ArgumentMatchers.any(Specification.class),
				ArgumentMatchers.any(Pageable.class))).thenReturn(pageOfClient);

		// When
		ResponseEntity<CommonResponse> commonResponse = clientService.getAllClients(paginationDTO);

		// Then
		assertEquals(HttpStatus.OK, commonResponse.getStatusCode());
	}
}
