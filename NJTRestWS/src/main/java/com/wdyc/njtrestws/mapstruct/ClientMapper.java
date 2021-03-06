/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wdyc.njtrestws.mapstruct;

import com.wdyc.njtrestws.domen.ClientEntity;
import com.wdyc.njtrestws.dto.ClientDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 *
 * @author Dusan
 */
@Mapper
public interface ClientMapper {

    @Mapping(target = "carList", ignore = true)
    ClientDTO clientEntityToDto(ClientEntity client);
    
    @Mapping(target = "carList", ignore = true)
    ClientEntity clientDtoToEntity(ClientDTO client);
}
