package com.codetest.bookingsystem.mapper;

import com.codetest.bookingsystem.dto.ScheduleClassDTO;
import com.codetest.bookingsystem.model.ScheduleClass;


public class SchedulerClassMapper {

    public static ScheduleClassDTO toDTO(ScheduleClass entity) {
        ScheduleClassDTO scheduledto = null;
        if (entity != null) {
            scheduledto = new ScheduleClassDTO();
            scheduledto.setId(entity.getId());
            scheduledto.setName(entity.getName());
            scheduledto.setMaxSlots(entity.getMaxSlots());
            scheduledto.setNoOfUsedSlots(entity.getNoOfUsedSlots());
            scheduledto.setNoOfCredits(entity.getNoOfCredits());
            scheduledto.setClassDate(entity.getClassDate());
            scheduledto.setStartTime(entity.getStartTime());
            scheduledto.setEndTime(entity.getEndTime());
            scheduledto.setLecturerName(entity.getLecturerName());
            scheduledto.setAddress(entity.getAddress());
            scheduledto.setPhNo(entity.getPhNo());
            scheduledto.setDescription(entity.getDescription());
            if (entity.getCountry() != null) {
                scheduledto.setCountryDTO(CountryMapper.mapToDTO(entity.getCountry()));
            }
        }
        return scheduledto;
    }

    public static ScheduleClass toModel(ScheduleClassDTO dto) {
        ScheduleClass entity = null;
        if (dto != null) {
            entity = new ScheduleClass();
            entity.setId(dto.getId());
            entity.setName(dto.getName());
            entity.setMaxSlots(dto.getMaxSlots());
            entity.setNoOfUsedSlots(dto.getNoOfUsedSlots());
            entity.setNoOfCredits(dto.getNoOfCredits());
            entity.setClassDate(dto.getClassDate());
            entity.setStartTime(dto.getStartTime());
            entity.setEndTime(dto.getEndTime());
            entity.setLecturerName(dto.getLecturerName());
            entity.setAddress(dto.getAddress());
            entity.setPhNo(dto.getPhNo());
            entity.setDescription(dto.getDescription());

            if (dto.getCountryDTO() != null) {
                entity.setCountry(CountryMapper.mapToModel(dto.getCountryDTO()));
            }
        }
        return entity;
    }
}
