package com.exazeit.practical.mapper;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import com.exazeit.practical.dto.ClientDTO;
import com.exazeit.practical.entity.Client;

@Mapper
public interface ClientMapper {

	ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);

	ClientDTO clientToClientDTO(Client client);

	Client clientDTOToClient(ClientDTO clientDTO);

	List<ClientDTO> clientListToClientDTOList(List<Client> clientList);

	List<Client> clientDTOListToClientList(List<ClientDTO> clientDTOList);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	Client updateClientFromClientDTO(ClientDTO clientDTO, @MappingTarget Client client);
}
