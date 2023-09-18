package com.tcscontrol.control_backend.allocation.impl.mapper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.tcscontrol.control_backend.allocation.model.dto.AllocationDTO;
import com.tcscontrol.control_backend.allocation.model.entity.Allocation;
import com.tcscontrol.control_backend.department.impl.mapper.DepartmentMapper;
import com.tcscontrol.control_backend.department.model.entity.Department;
import com.tcscontrol.control_backend.patrimony.impl.mapper.PatrimonyMapper;
import com.tcscontrol.control_backend.patrimony.model.dto.PatrimonyDTO;
import com.tcscontrol.control_backend.patrimony.model.entity.Patrimony;
import com.tcscontrol.control_backend.utilitarios.UtilData;
import com.tcscontrol.control_backend.warranty.model.dto.WarrantyDTO;
import com.tcscontrol.control_backend.warranty.model.entity.Warranty;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class AllocationMapper {
      
      private DepartmentMapper departmentMapper;
      private PatrimonyMapper patrimonyMapper;

      public AllocationDTO toDto(Allocation allocation){
            if (allocation == null) {
                  return null;
            }

            List<PatrimonyDTO> patrimonyDTO = allocation.getPatrimonios()
                  .stream()
                  .map(
                  patrimony-> new PatrimonyDTO(
                  patrimony.getId(),
                patrimony.getNrSerie(),
                patrimony.getNmPatrimonio(),
                patrimony.getNmDescricao(),
                patrimony.getFornecedor().getNrCnpj(),
                patrimony.getFornecedor().getNmName(),
                patrimony.getNrNotaFiscal(),
                UtilData.toString(patrimony.getDtNotaFiscal(), UtilData.FORMATO_DDMMAA),
                UtilData.toString(patrimony.getDtAquisicao(), UtilData.FORMATO_DDMMAA),
                patrimony.getVlAquisicao(),
                patrimony.getFixo(),
                listWarrantyDTOs(patrimony.getWarrantys()),
                departmentMapper.toDTO(allocation.getDepartamento()))).collect(Collectors.toList());

            return new AllocationDTO(
                  allocation.getId(),
                  UtilData.toString(allocation.getDtAlocacao(), UtilData.FORMATO_DDMMAA),
                  allocation.getNmObservacao(),
                  patrimonyDTO,
                  departmentMapper.toDTO(allocation.getDepartamento()));
      }

      public Allocation toEntity(AllocationDTO allocationDTO){
            if (allocationDTO == null) {
                  return null;
            }
            Allocation allocation = new Allocation();
            if (allocationDTO.id() != null) {
                  return null;
            }
            Set<Patrimony> patrimonys =  new HashSet<>(allocationDTO.patrimonies()
                  .stream()
                  .map(patrimonyMapper::toEntity).collect(Collectors.toList()));

            Department department = departmentMapper.toEntity(allocationDTO.departament()); 

            allocation.setDtAlocacao(UtilData.toDate(allocationDTO.dtAlocacao(), UtilData.FORMATO_DDMMAA));
            allocation.setNmObservacao(allocationDTO.observation());
            allocation.setPatrimonios(patrimonys);
            allocation.setDepartamento(department);

            return allocation;

      }

      private List<WarrantyDTO> listWarrantyDTOs(List<Warranty> list){
            return list.stream()
            .map(warranty -> new WarrantyDTO(
                    warranty.getId(),
                    warranty.getDsGarantia(),
                    UtilData.toString(warranty.getDtValidade(), UtilData.FORMATO_DDMMAA),
                    warranty.getTypewWarranty().getValue()))
            .collect(Collectors.toList());
      }

}
