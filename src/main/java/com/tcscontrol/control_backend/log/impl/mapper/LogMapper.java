package com.tcscontrol.control_backend.log.impl.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.tcscontrol.control_backend.log.model.dto.LogDTO;
import com.tcscontrol.control_backend.log.model.entity.Log;

@Mapper(componentModel = "spring")
public interface LogMapper {

    @Mapping(target = "id_patrimonio", source = "patrimony.id")
    @Mapping(target = "id_usuario", source = "user.id")
    LogDTO logToLogDTO(Log log);

    @Mapping(target = "patrimony.id", source = "id_patrimonio")
    @Mapping(target = "user.id", source = "id_usuario")
    Log logDTOToLog(LogDTO logDTO);
}

