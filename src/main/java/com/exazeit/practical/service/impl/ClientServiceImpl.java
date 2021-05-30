package com.exazeit.practical.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.exazeit.practical.dto.ClientDTO;
import com.exazeit.practical.dto.PaginationDTO;
import com.exazeit.practical.entity.Client;
import com.exazeit.practical.enumration.ErrorEnum;
import com.exazeit.practical.enumration.SearchOperation;
import com.exazeit.practical.mapper.ClientMapper;
import com.exazeit.practical.repository.ClientRepository;
import com.exazeit.practical.response.CommonListResponse;
import com.exazeit.practical.response.CommonResponse;
import com.exazeit.practical.service.ClientService;
import com.exazeit.practical.specification.ClientSpecification;
import com.exazeit.practical.util.Constants;
import com.exazeit.practical.util.SearchCriteria;

@Component
public class ClientServiceImpl implements ClientService {

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private UtilService utilService;

	/**
	 * Add/Update Client
	 */
	@Override
	public ResponseEntity<CommonResponse> addOrUpdateClient(@Valid ClientDTO clientDTO) {

		CommonResponse commonResponse = new CommonResponse();
		boolean isExist;
		if (Objects.isNull(clientDTO.getId())) {
			isExist = clientRepository.existsByMobileNumber(clientDTO.getMobileNumber());

			if (!isExist) {
				isExist = clientRepository.existsByIdNumber(clientDTO.getIdNumber());
			} else {
				commonResponse.setHttpStatus(HttpStatus.IM_USED);
				commonResponse
						.setErrorResponse(utilService.getErrorEnumResponse(ErrorEnum.MOBILE_NUMBER_ALREADY_IN_USE));
				return new ResponseEntity<>(commonResponse, commonResponse.getHttpStatus());
			}

			if (isExist) {
				commonResponse.setHttpStatus(HttpStatus.IM_USED);
				commonResponse.setErrorResponse(utilService.getErrorEnumResponse(ErrorEnum.IDNUMBER_ALREADY_IN_USE));
				return new ResponseEntity<>(commonResponse, commonResponse.getHttpStatus());
			}

			Client client = ClientMapper.INSTANCE.clientDTOToClient(clientDTO);
			clientRepository.save(client);

			commonResponse.setHttpStatus(HttpStatus.OK);
			commonResponse.setResponseMessage(Constants.CLIENT_DETAILS_SAVED_SUCCESSFULLY);
		} else {
			Optional<Client> opClient = clientRepository.findById(clientDTO.getId());
			if (opClient.isPresent()) {

				Client client = opClient.get();
				if (!client.getIdNumber().equals(clientDTO.getIdNumber())) {
					isExist = clientRepository.existsByIdNumberAndIdNot(clientDTO.getIdNumber(), clientDTO.getId());
					if (isExist) {
						commonResponse.setHttpStatus(HttpStatus.IM_USED);
						commonResponse
								.setErrorResponse(utilService.getErrorEnumResponse(ErrorEnum.IDNUMBER_ALREADY_IN_USE));
						return new ResponseEntity<>(commonResponse, commonResponse.getHttpStatus());
					}
				}
				isExist = clientRepository.existsByMobileNumberAndIdNot(clientDTO.getMobileNumber(), clientDTO.getId());
				if (isExist) {

					commonResponse.setHttpStatus(HttpStatus.IM_USED);
					commonResponse
							.setErrorResponse(utilService.getErrorEnumResponse(ErrorEnum.MOBILE_NUMBER_ALREADY_IN_USE));
					return new ResponseEntity<>(commonResponse, commonResponse.getHttpStatus());
				}
				client = ClientMapper.INSTANCE.updateClientFromClientDTO(clientDTO, client);
				clientRepository.save(client);

				commonResponse.setHttpStatus(HttpStatus.OK);
				commonResponse.setResponseMessage(Constants.CLIENT_DETAILS_UPDATED_SUCCESSFULLY);
			} else {
				commonResponse.setHttpStatus(HttpStatus.NO_CONTENT);
				commonResponse.setErrorResponse(utilService.getErrorEnumResponse(ErrorEnum.NO_RECORD_FOUND));
				return new ResponseEntity<>(commonResponse, commonResponse.getHttpStatus());
			}
		}
		return new ResponseEntity<>(commonResponse, commonResponse.getHttpStatus());
	}

	@Override
	public ResponseEntity<CommonResponse> getAllClients(PaginationDTO paginationDTO) {

		CommonResponse commonResponse = new CommonResponse();

		String search = paginationDTO.getKeyword();

		List<SearchCriteria> serviceListCriteria = new ArrayList<>();
		serviceListCriteria.add(new SearchCriteria("firstName", SearchOperation.EQUALS.toString(), search));
		serviceListCriteria.add(new SearchCriteria("lastName", SearchOperation.EQUALS.toString(), search));
		serviceListCriteria.add(new SearchCriteria("mobileNumber", SearchOperation.EQUALS.toString(), search));
		serviceListCriteria.add(new SearchCriteria("idNumber", SearchOperation.EQUALS.toString(), search));
		ClientSpecification spec = new ClientSpecification(serviceListCriteria);
		PageRequest pageRequest = UtilService.getPageRequestFromPaginationDTO(paginationDTO);
		Page<Client> clientPageList = clientRepository.findAll(spec, pageRequest);
		if (clientPageList.isEmpty()) {

			commonResponse.setHttpStatus(HttpStatus.NO_CONTENT);
			commonResponse.setErrorResponse(utilService.getErrorEnumResponse(ErrorEnum.NO_RECORD_FOUND));
		} else {

			List<ClientDTO> clientDTOList = ClientMapper.INSTANCE
					.clientListToClientDTOList(clientPageList.getContent());
			CommonListResponse<ClientDTO> commonListResponse = new CommonListResponse<>();
			commonListResponse.setResponseList(clientDTOList);
			commonResponse.setResponse(commonListResponse);
			commonResponse.setTotalCount(clientPageList.getTotalElements());
			commonResponse.setHttpStatus(HttpStatus.OK);
		}
		return new ResponseEntity<>(commonResponse, commonResponse.getHttpStatus());
	}

}
