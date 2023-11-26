package com.tcscontrol.control_backend.relatory.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;

import com.tcscontrol.control_backend.enuns.RelatoryFormatType;
import com.tcscontrol.control_backend.enuns.RelatoryType;
import com.tcscontrol.control_backend.exception.StorageException;
import com.tcscontrol.control_backend.exception.StorageFileNotFoundException;
import com.tcscontrol.control_backend.file.model.entity.StorageProperties;
import com.tcscontrol.control_backend.maintenance.MaintenanceRepository;
import com.tcscontrol.control_backend.maintenance.model.entity.Maintenance;
import com.tcscontrol.control_backend.patrimony.LossTheftRepository;
import com.tcscontrol.control_backend.patrimony.PatrimonyRepository;
import com.tcscontrol.control_backend.patrimony.model.entity.LossTheft;
import com.tcscontrol.control_backend.patrimony.model.entity.Patrimony;
import com.tcscontrol.control_backend.relatory.RelatorioNegocio;
import com.tcscontrol.control_backend.relatory.model.RelatorioRequestDTO;
import com.tcscontrol.control_backend.relatory.model.RelatoryResponseDTO;
import com.tcscontrol.control_backend.request_patrimony.model.entity.RequestPatrimony;
import com.tcscontrol.control_backend.requests.RequestsRepository;
import com.tcscontrol.control_backend.utilitarios.UtilData;
import com.tcscontrol.control_backend.utilitarios.UtilObjeto;
import com.tcscontrol.control_backend.utilitarios.UtilString;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRMapArrayDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;


@Component(value = "RelatorioNegocio")
public class RelatorioNegocioImpl implements RelatorioNegocio {

    private static final String JASPER_PATH = "src/main/resources/jasper/";

    private final Path relatoriosLocation;
    private final LossTheftRepository lossTheftRepository;
    private final RequestsRepository requestsRepository;
    private final PatrimonyRepository patrimonyRepository;
    private final MaintenanceRepository maintenanceRepository;

    public RelatorioNegocioImpl(StorageProperties properties,
            LossTheftRepository lossTheftRepository, RequestsRepository requestsRepository,
            PatrimonyRepository patrimonyRepository, MaintenanceRepository maintenanceRepository) {
        this.relatoriosLocation = Paths.get(properties.getRelatorios());
        this.lossTheftRepository = lossTheftRepository;
        this.requestsRepository = requestsRepository;
        this.patrimonyRepository = patrimonyRepository;
        this.maintenanceRepository = maintenanceRepository;
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(relatoriosLocation);
        } catch (IOException e) {
            throw new StorageException("Não foi possivel iniciar o repositório", e);
        }
    }

    @Override
    public RelatoryResponseDTO store(RelatorioRequestDTO relatorioRequestDTO) throws IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'store'");
    }

    @Override
    public Resource loadAsResource(RelatorioRequestDTO relatorioRequestDTO) {
        if (RelatoryType.BENS_BAIXADOS.getValue().equals(relatorioRequestDTO.nmRelatory()))
            return obterPdfBensBaixados(relatorioRequestDTO);

        if (RelatoryType.DEVOLUCOES_VENCIDAS.getValue().equals(relatorioRequestDTO.nmRelatory()))
            return obterPdfDevolucoesVencidas(relatorioRequestDTO);

        if (RelatoryType.PATRIMONIOS_DISPONIVEIS.getValue().equals(relatorioRequestDTO.nmRelatory()))
            return obterPdfPatrimonioDisponiveis(relatorioRequestDTO);

        if (RelatoryType.GASTOS_MANUTENCAO.getValue().equals(relatorioRequestDTO.nmRelatory()))
            return obterPdfGastosManutencao(relatorioRequestDTO);

        if (RelatoryType.GERAR_QRCODE.getValue().equals(relatorioRequestDTO.nmRelatory()))
            return obterPdfGerarQrCode(relatorioRequestDTO);

        return null;
    }

    private Resource obterPdfBensBaixados(RelatorioRequestDTO relatorioRequestDTO) {

        List<LossTheft> baixas = new ArrayList<>();

        if (UtilObjeto.isEmpty(relatorioRequestDTO.dtStart()) || UtilObjeto.isEmpty(relatorioRequestDTO.dtEnd())) {
            baixas = lossTheftRepository.obterRelatorioBaixa();
        } else {
            baixas = lossTheftRepository.obterRelatorioBaixa(
                    UtilData.toDate(relatorioRequestDTO.dtStart(), UtilData.FORMATO_DDMMAA),
                    UtilData.toDate(relatorioRequestDTO.dtEnd(), UtilData.FORMATO_DDMMAA));
        }

        List<Map<String, Object>> dados = obterDadosRelatorioBensBaixados(baixas);
        Map<String, Object> relatorio = new HashMap<>();
        relatorio.put("IMAGEM_DIRETORIO", JASPER_PATH);
        relatorio.put("DIRETORIO", relatoriosLocation);
        relatorio.put("DADOS", dados);
        relatorio.put("COMPILE", "lista_bens_baixados.jrxml");
        relatorio.put("NOME", "bens_baixados_");
        String nome = UtilString.EMPTY;
        try {
           if (RelatoryFormatType.PDF.getValue().equals(relatorioRequestDTO.type())) {
                nome = exportPDF(relatorio, dados);
            }

            if (RelatoryFormatType.EXCEL.getValue().equals(relatorioRequestDTO.type())) {
                nome = exportExcel(relatorio, dados);
            }
            Path file = load(nome);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException(
                        "Could not read file: " + nome);
            }

        } catch (Exception e) {
            throw new IllegalArgumentException("Não foi prossível criar o arquivo");
        }
    }

    private Resource obterPdfDevolucoesVencidas(RelatorioRequestDTO relatorioRequestDTO) {

        List<RequestPatrimony> dadosRelatorio = new ArrayList<>();
        Date dataAtual = java.sql.Date.valueOf(LocalDate.now());
        dadosRelatorio = requestsRepository.getDevolucoesVencidas(dataAtual);

        List<Map<String, Object>> dados = obterDadosRelatorioDevolucoesVencidas(dadosRelatorio);
        Map<String, Object> relatorio = new HashMap<>();
        relatorio.put("IMAGEM_DIRETORIO", JASPER_PATH);
        relatorio.put("DIRETORIO", relatoriosLocation);
        relatorio.put("DADOS", dados);
        relatorio.put("COMPILE", "lista_devolucoes_vencidas.jrxml");
        relatorio.put("NOME", "devolucoes_vencidas_");
        String nome = UtilString.EMPTY;
        try {
            if (RelatoryFormatType.PDF.getValue().equals(relatorioRequestDTO.type())) {
                nome = exportPDF(relatorio, dados);
            }

            if (RelatoryFormatType.EXCEL.getValue().equals(relatorioRequestDTO.type())) {
                nome = exportExcel(relatorio, dados);
            }
            Path file = load(nome);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException(
                        "Could not read file: " + nome);
            }

        } catch (Exception e) {
            throw new IllegalArgumentException("Não foi prossível criar o arquivo");
        }
    }

    private Resource obterPdfPatrimonioDisponiveis(RelatorioRequestDTO relatorioRequestDTO) {

        List<Patrimony> dadosRelatorio = new ArrayList<>();

        if (UtilObjeto.isEmpty(relatorioRequestDTO.fixo())) {
            dadosRelatorio = patrimonyRepository.getPatrimoniosDisponivel();
        } else {
            dadosRelatorio = patrimonyRepository.getPatrimoniosDisponivel(relatorioRequestDTO.fixo());
        }

        List<Map<String, Object>> dados = obterDadosRelatorioPatrimoniosDisponiveis(dadosRelatorio);
        Map<String, Object> relatorio = new HashMap<>();
        relatorio.put("IMAGEM_DIRETORIO", JASPER_PATH);
        relatorio.put("DIRETORIO", relatoriosLocation);
        relatorio.put("DADOS", dados);
        relatorio.put("COMPILE", "lista_disponiveis.jrxml");
        relatorio.put("NOME", "patrimonio_disponiveis_");
        String nome = UtilString.EMPTY;
        try {
            if (RelatoryFormatType.PDF.getValue().equals(relatorioRequestDTO.type())) {
                nome = exportPDF(relatorio, dados);
            }

            if (RelatoryFormatType.EXCEL.getValue().equals(relatorioRequestDTO.type())) {
                nome = exportExcel(relatorio, dados);
            }
            Path file = load(nome);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException(
                        "Could not read file: " + nome);
            }

        } catch (Exception e) {
            throw new IllegalArgumentException("Não foi prossível criar o arquivo");
        }
    }

    private Resource obterPdfGastosManutencao(RelatorioRequestDTO relatorioRequestDTO) {

        Date inicio = UtilObjeto.isNotEmpty(relatorioRequestDTO.dtStart())
                ? UtilData.toDate(relatorioRequestDTO.dtStart(), UtilData.FORMATO_DDMMAA)
                : null;
        Date fim = UtilObjeto.isNotEmpty(relatorioRequestDTO.dtEnd())
                ? UtilData.toDate(relatorioRequestDTO.dtEnd(), UtilData.FORMATO_DDMMAA)
                : null;
        Double vlMin = UtilObjeto.isNotEmpty(relatorioRequestDTO.vlMin()) ? relatorioRequestDTO.vlMin() : null;
        Double vlMax = UtilObjeto.isNotEmpty(relatorioRequestDTO.vlMax()) ? relatorioRequestDTO.vlMax() : null;

        List<Maintenance> dadosRelatorio = obterPesquisaManutencao(inicio, fim, vlMin, vlMax);

        List<Map<String, Object>> dados = obterDadosRelatorioGastoManutencao(dadosRelatorio);
        Map<String, Object> relatorio = new HashMap<>();
        relatorio.put("IMAGEM_DIRETORIO", JASPER_PATH);
        relatorio.put("DIRETORIO", relatoriosLocation);
        relatorio.put("DADOS", dados);
        relatorio.put("COMPILE", "lista_gastos_manutencao.jrxml");
        relatorio.put("NOME", "gastos_manutencao_");
        String nome = UtilString.EMPTY;
        try {
            if (RelatoryFormatType.PDF.getValue().equals(relatorioRequestDTO.type())) {
                nome = exportPDF(relatorio, dados);
            }

            if (RelatoryFormatType.EXCEL.getValue().equals(relatorioRequestDTO.type())) {
                nome = exportExcel(relatorio, dados);
            }

            Path file = load(nome);

            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException(
                        "Could not read file: " + nome);
            }

        } catch (Exception e) {
            throw new IllegalArgumentException("Não foi prossível criar o arquivo" + e);
        }
    }

    private List<Maintenance> obterPesquisaManutencao(Date inicio, Date fim, Double vlMin, Double vlMax) {
        if (UtilObjeto.isEmpty(inicio) || UtilObjeto.isEmpty(fim)) {
            if (UtilObjeto.isEmpty(vlMin) || UtilObjeto.isEmpty(vlMax)) {
                return maintenanceRepository.getGastosManutencao();
            } else {
                return maintenanceRepository.getGastosManutencao(vlMin, vlMax);
            }
        } else {
            if (UtilObjeto.isEmpty(vlMin) || UtilObjeto.isEmpty(vlMax)) {
                return maintenanceRepository.getGastosManutencao(inicio, fim);
            }
        }
        return maintenanceRepository.getGastosManutencao(inicio, fim, vlMin, vlMax);

    }

    private Resource obterPdfGerarQrCode(RelatorioRequestDTO relatorioRequestDTO) {

        String nomeP = UtilObjeto.isNotEmpty(relatorioRequestDTO.nmPatrimony()) ? relatorioRequestDTO.nmPatrimony()
                : UtilString.EMPTY;
        String nrSerie = UtilObjeto.isNotEmpty(relatorioRequestDTO.nrSerie()) ? relatorioRequestDTO.nrSerie()
                : UtilString.EMPTY;

        List<Patrimony> dadosRelatorio = patrimonyRepository.getPatrimonies(nomeP, nrSerie);

        List<Map<String, Object>> dados = obterDadosRelatorioGeracaoPatrimonio(dadosRelatorio);
        Map<String, Object> relatorio = new HashMap<>();
        relatorio.put("IMAGEM_DIRETORIO", JASPER_PATH);
        relatorio.put("DIRETORIO", relatoriosLocation);
        relatorio.put("DADOS", dados);
        relatorio.put("COMPILE", "lista_geracao_qrcode.jrxml");
        relatorio.put("NOME", "qr_code_patrimonios_");
        String nome = UtilString.EMPTY;
        try {
            nome = exportPDF(relatorio, dados);
            Path file = load(nome);

            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException(
                        "Could not read file: " + nome);
            }

        } catch (Exception e) {
            throw new IllegalArgumentException("Não foi prossível criar o arquivo");
        }
    }

    private Path load(String fileName) {
        return relatoriosLocation.resolve(fileName);
    }

    private List<Map<String, Object>> obterDadosRelatorioBensBaixados(List<LossTheft> dados) {
        List<Map<String, Object>> retorno = new ArrayList<>();

        for (LossTheft item : dados) {
            Map<String, Object> map = new HashMap<>();

            map.put("patrimonio_id", item.getPatrimony().getId());
            map.put("nr_serie", item.getPatrimony().getNrSerie());
            map.put("nm_patrimonio", item.getPatrimony().getNmPatrimonio());
            map.put("dt_aquisicao", UtilData.toString(item.getPatrimony().getDtAquisicao(), UtilData.FORMATO_DDMMAA));
            map.put("dt_perda_roubo", UtilData.toString(item.getDtLost(), UtilData.FORMATO_DDMMAA));
            map.put("nm_obervacao", item.getNmObservation());
            map.put("vl_aquisicao", item.getPatrimony().getVlAquisicao());

            retorno.add(map);
        }

        return retorno;

    }

    private List<Map<String, Object>> obterDadosRelatorioDevolucoesVencidas(List<RequestPatrimony> dados) {
        List<Map<String, Object>> retorno = new ArrayList<>();

        for (RequestPatrimony item : dados) {
            Map<String, Object> map = new HashMap<>();

            map.put("id_patrimonio", item.getPatrimony().getId());
            map.put("nr_serie", item.getPatrimony().getNrSerie());
            map.put("nm_patrimonio", item.getPatrimony().getNmPatrimonio());
            map.put("nm_obra", item.getRequests().getConstruction().getNmObra());
            map.put("dt_retirada", UtilData.toString(item.getDtRetirada(), UtilData.FORMATO_DDMMAA));
            map.put("dt_previsao_devolucao", UtilData.toString(item.getDtPrevisaoDevolucao(), UtilData.FORMATO_DDMMAA));
            map.put("nm_name", item.getRequests().getConstruction().getUser().getNmName());
            map.put("nm_logradouro", item.getRequests().getConstruction().getNmLogradouro());
            map.put("nm_bairro", item.getRequests().getConstruction().getNmBairro());
            map.put("nm_cidade", item.getRequests().getConstruction().getNmCidade());
            map.put("nm_uf", item.getRequests().getConstruction().getNmUf());
            map.put("vl_aquisicao", item.getPatrimony().getVlAquisicao());

            retorno.add(map);
        }

        return retorno;

    }

    private List<Map<String, Object>> obterDadosRelatorioPatrimoniosDisponiveis(List<Patrimony> dados) {
        List<Map<String, Object>> retorno = new ArrayList<>();

        for (Patrimony item : dados) {
            Map<String, Object> map = new HashMap<>();

            map.put("id_patrimonio", item.getId());
            map.put("nr_serie", item.getNrSerie());
            map.put("nm_patrimonio", item.getNmPatrimonio());
            map.put("ds_patrimonio", item.getNmDescricao());
            map.put("dt_aquisicao", UtilData.toString(item.getDtAquisicao(), UtilData.FORMATO_DDMMAA));
            map.put("vl_aquisicao", item.getVlAquisicao());
            map.put("nm_name", item.getFornecedor().getNmName());

            retorno.add(map);
        }
        return retorno;
    }

    private List<Map<String, Object>> obterDadosRelatorioGastoManutencao(List<Maintenance> dados) {
        List<Map<String, Object>> retorno = new ArrayList<>();

        for (Maintenance item : dados) {
            Map<String, Object> map = new HashMap<>();

            map.put("id_patrimonio", item.getPatrimony().getId());
            map.put("nr_serie", item.getPatrimony().getNrSerie());
            map.put("nm_patrimonio", item.getPatrimony().getNmPatrimonio());
            map.put("dt_agendamento", UtilData.toString(item.getDtAgendamento(), UtilData.FORMATO_DDMMAA));
            map.put("dt_entrada", UtilData.toString(item.getDtEntrada(), UtilData.FORMATO_DDMMAA));
            map.put("dt_fim", UtilData.toString(item.getDtFim(), UtilData.FORMATO_DDMMAA));
            map.put("tp_manutencao", item.getTpManutencao().getValue());
            map.put("vl_manutencao", item.getVlManutencao());
            ;
            map.put("nm_name", item.getFornecedor().getNmName());
            retorno.add(map);
        }
        return retorno;

    }

    private List<Map<String, Object>> obterDadosRelatorioGeracaoPatrimonio(List<Patrimony> dados) {
        List<Map<String, Object>> retorno = new ArrayList<>();

        for (Patrimony item : dados) {
            Map<String, Object> map = new HashMap<>();

            map.put("id_patrimonio", item.getId());
            map.put("nr_serie", item.getNrSerie());
            map.put("nm_patrimonio", item.getNmPatrimonio());

            retorno.add(map);
        }

        return retorno;

    }

    public String exportPDF(Map<String, Object> dados, List<Map<String, Object>> data) throws Exception {
        JRDataSource dataSource = new JRMapArrayDataSource(data.toArray());
        String jrxml = (String) dados.get("COMPILE");
        String nome = (String) dados.get("NOME")
                + UtilData.toString(new Date(), UtilData.FORMATO_DATAHORA_SEM_CARACTERE) + ".pdf";
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(JASPER_PATH + jrxml);
            JasperPrint print = JasperFillManager.fillReport(jasperReport, dados, dataSource);
            String pdfOutputPath = relatoriosLocation.toAbsolutePath().toString().concat("/").concat(nome);
            File outputDir = new File(relatoriosLocation.toAbsolutePath().toString());
            System.out.println(relatoriosLocation.toAbsolutePath().toString());
            outputDir.mkdirs();

            JasperExportManager.exportReportToPdfFile(print, pdfOutputPath);
        } catch (JRException e) {
            e.printStackTrace();
        }
        return nome;
    }

    public String exportExcel(Map<String, Object> dados, List<Map<String, Object>> data) throws Exception {
        JRDataSource dataSource = new JRMapArrayDataSource(data.toArray());
        String jrxml = (String) dados.get("COMPILE");
        String nome = (String) dados.get("NOME")
                + UtilData.toString(new Date(), UtilData.FORMATO_DATAHORA_SEM_CARACTERE) + ".xls";
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(JASPER_PATH + jrxml);
            JasperPrint print = JasperFillManager.fillReport(jasperReport, dados, dataSource);

            JRXlsExporter exporter = new JRXlsExporter();

            exporter.setExporterInput(new SimpleExporterInput(print));

            String xlsOutputPath = relatoriosLocation.toAbsolutePath().toString().concat("/").concat(nome);
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(xlsOutputPath));

            SimpleXlsReportConfiguration configuration = new SimpleXlsReportConfiguration();
            configuration.setDetectCellType(true);
            configuration.setCollapseRowSpan(false);
            exporter.setConfiguration(configuration);

            exporter.exportReport();

        } catch (JRException e) {
            e.printStackTrace();
        }
        return nome;
    }

}
