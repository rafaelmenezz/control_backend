package com.tcscontrol.control_backend.patrimony.impl.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.tcscontrol.control_backend.allocation.model.entity.Allocation;
import com.tcscontrol.control_backend.constructions.impl.mapper.ConstructionMapper;
import com.tcscontrol.control_backend.constructions.model.dto.ConstructionDTO;
import com.tcscontrol.control_backend.constructions.model.entity.Construction;
import com.tcscontrol.control_backend.department.impl.mapper.DepartmentMapper;
import com.tcscontrol.control_backend.department.model.dto.DepartmentDTO;
import com.tcscontrol.control_backend.department.model.entity.Department;
import com.tcscontrol.control_backend.enuns.Status;
import com.tcscontrol.control_backend.maintenance.impl.mapper.MaintenanceMapper;
import com.tcscontrol.control_backend.maintenance.model.dto.MaintenancePatrimonyDTO;
import com.tcscontrol.control_backend.maintenance.model.entity.Maintenance;
import com.tcscontrol.control_backend.patrimony.model.dto.PatrimonyDTO;
import com.tcscontrol.control_backend.patrimony.model.dto.PatrimonyResponse;
import com.tcscontrol.control_backend.patrimony.model.entity.Patrimony;
import com.tcscontrol.control_backend.pessoa.fornecedor.Fornecedor;
import com.tcscontrol.control_backend.pessoa.fornecedor.FornecedorNegocio;
import com.tcscontrol.control_backend.request_patrimony.model.entity.RequestPatrimony;
import com.tcscontrol.control_backend.requests.model.entity.Requests;
import com.tcscontrol.control_backend.utilitarios.UtilControl;
import com.tcscontrol.control_backend.utilitarios.UtilData;
import com.tcscontrol.control_backend.utilitarios.UtilObjeto;
import com.tcscontrol.control_backend.warranty.model.dto.WarrantyDTO;
import com.tcscontrol.control_backend.warranty.model.entity.Warranty;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class PatrimonyMapper {

    FornecedorNegocio fornecedorNegocio;
    DepartmentMapper departmentMapper;
    ConstructionMapper constructionMapper;
    MaintenanceMapper maintenanceMapper;

    public PatrimonyResponse toResponse(Patrimony patrimony) {
        if (patrimony == null) {
            return null;
        }

        RequestPatrimony rPatrimony = patrimony.getRequests()
                .stream()
                .filter(c -> c.getDtRetirada() != null && c.getDtDevolucao() == null)
                .findFirst()
                .orElse(new RequestPatrimony());

        Maintenance maintenance = patrimony.getMaintenances()
                .stream()
                .filter(c -> Status.ACTIVE.equals(c.getTpStatus()))
                .findFirst()
                .orElse(null);

        MaintenancePatrimonyDTO maintenancePatrimonyDTO = maintenanceMapper.toMaintenancePatrimonyDTO(maintenance);
        DepartmentDTO departmentDTO = null;

        Requests r = rPatrimony == null ? null : rPatrimony.getRequests();
        ConstructionDTO constructionDTO = r != null ? constructionMapper.toDto(r.getConstruction()) : null;

        List<WarrantyDTO> warrantys = patrimony.getWarrantys()
                .stream()
                .map(warranty -> new WarrantyDTO(
                        warranty.getId(),
                        warranty.getDsGarantia(),
                        UtilData.toString(warranty.getDtValidade(), UtilData.FORMATO_DDMMAA),
                        warranty.getTypewWarranty().getValue()))
                .collect(Collectors.toList());

        return new PatrimonyResponse(
                patrimony.getId(),
                patrimony.getNmPatrimonio(),
                patrimony.getNrSerie(),
                patrimony.getNmDescricao(),
                patrimony.getFornecedor().getNrCnpj(),
                patrimony.getFornecedor().getNmName(),
                patrimony.getNrNotaFiscal(),
                UtilData.toString(patrimony.getDtNotaFiscal(), UtilData.FORMATO_DDMMAA),
                UtilData.toString(patrimony.getDtAquisicao(), UtilData.FORMATO_DDMMAA),
                patrimony.getVlAquisicao(),
                patrimony.getFixo(),
                patrimony.getTpSituacao().getValue(),
                warrantys,
                departmentDTO != null ? departmentDTO : null,
                constructionDTO != null ? constructionDTO : null,
                maintenancePatrimonyDTO != null ? maintenancePatrimonyDTO : null);

    }

    public PatrimonyDTO toDto(Patrimony patrimony) {
        if (patrimony == null) {
            return null;
        }

        List<WarrantyDTO> warrantys = patrimony.getWarrantys()
                .stream()
                .map(warranty -> new WarrantyDTO(
                        warranty.getId(),
                        warranty.getDsGarantia(),
                        UtilData.toString(warranty.getDtValidade(), UtilData.FORMATO_DDMMAA),
                        warranty.getTypewWarranty().getValue()))
                .collect(Collectors.toList());

        List<RequestPatrimony> rps = patrimony.getRequests();
        List<Maintenance> maintenances = patrimony.getMaintenances();

        Department department = null;

        Construction construction = null;
        if (UtilObjeto.isNotEmpty(rps)) {
            for (RequestPatrimony requestPatrimony : rps) {
                if (UtilObjeto.isNotEmpty(requestPatrimony.getDtRetirada())
                        && UtilObjeto.isEmpty(requestPatrimony.getDtDevolucao())) {
                    construction = requestPatrimony.getRequests().getConstruction();
                }

            }
        }

        Maintenance maintenance = null;
        if (UtilObjeto.isNotEmpty(maintenances)) {
            maintenance = maintenances
                    .stream()
                    .filter(c -> Status.ACTIVE.equals(c.getTpStatus()) && UtilObjeto.isNotEmpty(c.getDtEntrada()))
                    .findFirst()
                    .orElse(null);
        }

        return new PatrimonyDTO(
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
                patrimony.getTpSituacao().getValue(),
                warrantys,
                UtilObjeto.isNotEmpty(department) ? departmentMapper.toDTO(department) : null,
                UtilObjeto.isNotEmpty(construction) ? constructionMapper.toDto(construction) : null,
                UtilObjeto.isNotEmpty(maintenance) ? maintenanceMapper.toMaintenancePatrimonyDTO(maintenance) : null);
    }

    public Patrimony toEntity(PatrimonyDTO patrimonyDTO) {

        if (patrimonyDTO == null) {
            return null;
        }
        Patrimony patrimony = new Patrimony();
        if (patrimonyDTO.id() != null) {
            patrimony.setId(patrimonyDTO.id());
        }

        Fornecedor fornecedor = fornecedorNegocio.pesquisaFornecedorCnpj(patrimonyDTO.nrCnpj());

        if (fornecedor == null) {
            fornecedor = new Fornecedor();
            fornecedor.setNmName(patrimonyDTO.nmFornecedor());
            fornecedor.setNrCnpj(patrimonyDTO.nrCnpj());
            fornecedor = fornecedorNegocio.cadastrarFornecedor(fornecedor);

        }

        patrimony.setNrSerie(patrimonyDTO.nrSerie());
        patrimony.setNmPatrimonio(patrimonyDTO.nmPatrimonio());
        patrimony.setNmDescricao(patrimonyDTO.nmDescricao());
        patrimony.setNrNotaFiscal(patrimonyDTO.nrNF());
        patrimony.setDtNotaFiscal(UtilData.toDate(patrimonyDTO.dtNF(), UtilData.FORMATO_DDMMAA));
        patrimony.setDtAquisicao(UtilData.toDate(patrimonyDTO.dtAquisicao(), UtilData.FORMATO_DDMMAA));
        patrimony.setVlAquisicao(patrimonyDTO.vlAquisicao());
        patrimony.setFixo(patrimonyDTO.fixo());
        patrimony.setTpSituacao(UtilControl.convertSituationTypeValue(patrimonyDTO.situacao()));
        patrimony.setFornecedor(fornecedor);
        List<Warranty> warrantys = patrimonyDTO.warranties()
                .stream()
                .map(warranty -> {
                    var garantia = new Warranty();
                    garantia.setId(warranty.id());
                    garantia.setDsGarantia(warranty.dsGarantia());
                    garantia.setDtValidade(UtilData.toDate(warranty.dtValidade(), UtilData.FORMATO_DDMMAA));
                    garantia.setTypewWarranty(UtilControl.convertTypeWarrantyValue(warranty.tipoGarantia()));
                    garantia.setPatrimony(patrimony);
                    return garantia;
                })
                .collect(Collectors.toList());
        patrimony.setWarrantys(warrantys);

        return patrimony;

    }

    public Patrimony toEntity(PatrimonyResponse patrimonyResponse) {

        if (patrimonyResponse == null) {
            return null;
        }
        Patrimony patrimony = new Patrimony();
        if (patrimonyResponse.id() != null) {
            patrimony.setId(patrimonyResponse.id());
        }
        Fornecedor fornecedor = fornecedorNegocio.pesquisaFornecedorCnpj(patrimonyResponse.nrCnpj());
        if (fornecedor == null) {
            fornecedor = new Fornecedor();
            fornecedor.setNmName(patrimonyResponse.nmFornecedor());
            fornecedor.setNrCnpj(patrimonyResponse.nrCnpj());
            fornecedor = fornecedorNegocio.cadastrarFornecedor(fornecedor);
        }

        patrimony.setNrSerie(patrimonyResponse.nrSerie());
        patrimony.setNmPatrimonio(patrimonyResponse.nmPatrimonio());
        patrimony.setNmDescricao(patrimonyResponse.nmDescricao());
        patrimony.setNrNotaFiscal(patrimonyResponse.nrNF());
        patrimony.setDtNotaFiscal(UtilData.toDate(patrimonyResponse.dtNF(), UtilData.FORMATO_DDMMAA));
        patrimony.setDtAquisicao(UtilData.toDate(patrimonyResponse.dtAquisicao(), UtilData.FORMATO_DDMMAA));
        patrimony.setVlAquisicao(patrimonyResponse.vlAquisicao());
        patrimony.setFixo(patrimonyResponse.fixo());
        patrimony.setFornecedor(fornecedor);
        List<Warranty> warrantys = patrimonyResponse.warranties()
                .stream()
                .map(warranty -> {
                    var garantia = new Warranty();
                    garantia.setId(warranty.id());
                    garantia.setDsGarantia(warranty.dsGarantia());
                    garantia.setDtValidade(UtilData.toDate(warranty.dtValidade(), UtilData.FORMATO_DDMMAA));
                    garantia.setTypewWarranty(UtilControl.convertTypeWarrantyValue(warranty.tipoGarantia()));
                    garantia.setPatrimony(patrimony);
                    return garantia;
                })
                .collect(Collectors.toList());
        patrimony.setWarrantys(warrantys);

        return patrimony;

    }

}
