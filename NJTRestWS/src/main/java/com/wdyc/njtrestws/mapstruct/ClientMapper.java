/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wdyc.njtrestws.mapstruct;

import com.wdyc.njtrestws.domen.ClientEntity;
import com.wdyc.njtrestws.dto.ClientDTO;
import org.mapstruct.Mapper;

/**
 *
 * @author Dusan
 */
@Mapper
public interface ClientMapper {

    ClientDTO clientEntityToDto(ClientEntity client);
    
    ClientEntity clientDtoToEntity(ClientDTO client);
}
