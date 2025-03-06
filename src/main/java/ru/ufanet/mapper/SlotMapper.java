package ru.ufanet.mapper;

import org.mapstruct.Mapper;
import ru.ufanet.dto.SlotResponse;
import ru.ufanet.models.SlotEntity;

@Mapper(componentModel = "spring")
public interface SlotMapper {


    SlotResponse toSlotResponse(SlotEntity slotEntity);
}
