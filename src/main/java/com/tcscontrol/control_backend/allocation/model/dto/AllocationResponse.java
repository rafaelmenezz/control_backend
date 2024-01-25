package com.tcscontrol.control_backend.allocation.model.dto;

import java.util.List;

import com.tcscontrol.control_backend.allocation_patrimony.model.dto.AllocationPatrimonyDTO;
import com.tcscontrol.control_backend.department.model.dto.DepartmentDTO;

public record AllocationResponse(
    Long id,
    DepartmentDTO department,
    List<AllocationPatrimonyDTO>  patrimonies
) {}
