package com.endava.demo.assembler;

import java.util.List;

public interface Assembler<DTO, ENTITY> {

    DTO toDto(ENTITY entity);

//    List toDtos(List<ENTITY> entities);

    ENTITY toEntity(DTO dto);

//    List toEntities(List<DTO> dtos);
}
