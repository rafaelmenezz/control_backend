package com.tcscontrol.control_backend.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.tcscontrol.control_backend.jasper_reports.JasperService;
import com.tcscontrol.control_backend.jasper_reports.repository.CidadeRepository;
import com.tcscontrol.control_backend.jasper_reports.repository.IdPatrimonioRepository;
import com.tcscontrol.control_backend.jasper_reports.repository.NomeObraRepository;
import com.tcscontrol.control_backend.jasper_reports.repository.NomePatrimonioRepository;
import com.tcscontrol.control_backend.jasper_reports.repository.SituacaoPatrimonioRepository;
import com.tcscontrol.control_backend.jasper_reports.repository.TipoPatrimonioRepository;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.persistence.Id;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@RestController
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping("/api/jasperReport")
@AllArgsConstructor
public class JasperController {

    @Autowired
    private JasperService jasperService;
    @Autowired
    private CidadeRepository cidadeRepository;
    @Autowired
    private IdPatrimonioRepository idPatrimonioRepository;
    @Autowired
    private NomeObraRepository nomeObraRepository;
    @Autowired
    private NomePatrimonioRepository nomePatrimonioRepository;
    @Autowired
    private SituacaoPatrimonioRepository situacaoPatrimonioRepository;
    @Autowired
    private TipoPatrimonioRepository tipoPatrimonioRepository;
    
    // Esse é para relatório sem parametros
    @GetMapping("/relatorio/pdf/{code}")
    public void exibirRelatorioInventario(@PathVariable("code") String code,
                                HttpServletResponse response) throws IOException, Exception {
        byte[] bytes = jasperService.exportPDF(code);
        response.setContentType(MediaType.APPLICATION_PDF_VALUE);
        response.setHeader("Content-disposition", "attachment; filename=relatorio-" + code + ".pdf");
        response.getOutputStream().write(bytes);
    }
    
    // Esse é para relatório sem parametros
    @GetMapping("/relatorio/pdf/{code}")
    public void exibirRelatorioVencidos(@PathVariable("code") String code,
                                        HttpServletResponse response) throws IOException, Exception {
        byte[] bytes = jasperService.exportPDF(code);
        response.setContentType(MediaType.APPLICATION_PDF_VALUE);
        response.setHeader("Content-disposition", "attachment; filename=relatorio-" + code + ".pdf");
        response.getOutputStream().write(bytes);
    }
    
    // Esse é para relatório com parametros
    @GetMapping("/relatorio/pdf/{code}")
    public void exibirRelatorioBaixados(@PathVariable("code") String code, 
                                @RequestParam(name="dataInicial", required = false) String dataInicial,
                                @RequestParam(name="dataFinal", required = false) String dataFinal,
                                HttpServletResponse response) throws IOException, Exception {
        jasperService.addParams(dataInicial, dataInicial.isEmpty() ? null : dataInicial);
        jasperService.addParams(dataFinal, dataFinal.isEmpty() ? null : dataFinal);
        byte[] bytes = jasperService.exportPDF(code);
        response.setContentType(MediaType.APPLICATION_PDF_VALUE);
        response.setHeader("Content-disposition", "attachment; filename=relatorio-" + code + ".pdf");
        response.getOutputStream().write(bytes);
    }

    // Esse é para relatório com parametros
    @GetMapping("/relatorio/pdf/{code}")
    public void exibirRelatorioDisponiveis(@PathVariable("code") String code, 
                                @RequestParam(name="tipoPatrimonio", required = false) String tipoPatrimonio,
                                HttpServletResponse response) throws IOException, Exception {
        jasperService.addParams(tipoPatrimonio, tipoPatrimonio.isEmpty() ? null : tipoPatrimonio);
        byte[] bytes = jasperService.exportPDF(code);
        response.setContentType(MediaType.APPLICATION_PDF_VALUE);
        response.setHeader("Content-disposition", "attachment; filename=relatorio-" + code + ".pdf");
        response.getOutputStream().write(bytes);
    }
        
    // Esse é para relatório com parametros
    @GetMapping("/relatorio/pdf/{code}")
    public void exibirRelatorioGastosManutencao(@PathVariable("code") String code, 
                                @RequestParam(name="dataInicial", required = false) String dataInicial,
                                @RequestParam(name="dataFinal", required = false) String dataFinal,
                                @RequestParam(name="valorInicial", required = false) String valorInicial,
                                @RequestParam(name="valorFinal", required = false) String valorFinal,
                                HttpServletResponse response) throws IOException, Exception {
        jasperService.addParams(dataInicial, dataInicial.isEmpty() ? null : dataInicial);
        jasperService.addParams(dataFinal, dataFinal.isEmpty() ? null : dataFinal);
        jasperService.addParams(valorInicial, valorInicial.isEmpty() ? null : valorInicial);
        jasperService.addParams(valorFinal, valorFinal.isEmpty() ? null : valorFinal);
        byte[] bytes = jasperService.exportPDF(code);
        response.setContentType(MediaType.APPLICATION_PDF_VALUE);
        response.setHeader("Content-disposition", "attachment; filename=relatorio-" + code + ".pdf");
        response.getOutputStream().write(bytes);
    }
    
    // Esse é para relatório com parametros
    @GetMapping("/relatorio/pdf/{code}")
    public void exibirRelatorioQRCode(@PathVariable("code") String code, 
                                    @RequestParam(name="nomePatrimonio", required = false) String nomePatrimonio,
                                    @RequestParam(name="numeroPatrimonio", required = false) String numeroPatrimonio,
                                    HttpServletResponse response) throws IOException, Exception {
        jasperService.addParams("nomePatrimonio", nomePatrimonio.isEmpty() ? null : nomePatrimonio);
        jasperService.addParams("numeroPatrimonio", numeroPatrimonio.isEmpty() ? null : numeroPatrimonio);
        byte[] bytes = jasperService.exportPDF(code);
        response.setContentType(MediaType.APPLICATION_PDF_VALUE);
        response.setHeader("Content-disposition", "attachment; filename=relatorio-" + code + ".pdf");
        response.getOutputStream().write(bytes);
    }
    
    // Esse é para relatório com parametros
    @GetMapping("/relatorio/pdf/{code}")
    public void exibirRelatorioGeralPatirmonios(@PathVariable("code") String code, 
                                @RequestParam(name="dataInicial", required = false) String dataInicial,
                                @RequestParam(name="dataFinal", required = false) String dataFinal,
                                @RequestParam(name="situacaoPatrimonio", required = false) String situacaoPatrimonio,
                                HttpServletResponse response) throws IOException, Exception {
        jasperService.addParams(dataInicial, dataInicial.isEmpty() ? null : dataInicial);
        jasperService.addParams(dataFinal, dataFinal.isEmpty() ? null : dataFinal);
        jasperService.addParams(situacaoPatrimonio, situacaoPatrimonio.isEmpty() ? null : situacaoPatrimonio);
        byte[] bytes = jasperService.exportPDF(code);
        response.setContentType(MediaType.APPLICATION_PDF_VALUE);
        response.setHeader("Content-disposition", "attachment; filename=relatorio-" + code + ".pdf");
        response.getOutputStream().write(bytes);
    }
    
    // Esse é para relatório com parametros
    @GetMapping("/relatorio/pdf/{code}")
    public void exibirRelatorioManutencoesAgendadas(@PathVariable("code") String code, 
                                @RequestParam(name="dataInicial", required = false) String dataInicial,
                                @RequestParam(name="dataFinal", required = false) String dataFinal,
                                HttpServletResponse response) throws IOException, Exception {
        jasperService.addParams(dataInicial, dataInicial.isEmpty() ? null : dataInicial);
        jasperService.addParams(dataFinal, dataFinal.isEmpty() ? null : dataFinal);
        byte[] bytes = jasperService.exportPDF(code);
        response.setContentType(MediaType.APPLICATION_PDF_VALUE);
        response.setHeader("Content-disposition", "attachment; filename=relatorio-" + code + ".pdf");
        response.getOutputStream().write(bytes);
    }
    
    // Esse é para relatório com parametros
    @GetMapping("/relatorio/pdf/{code}")
    public void exibirRelatorioPatrimoniosNasObras(@PathVariable("code") String code, 
                                @RequestParam(name="dataInicial", required = false) String dataInicial,
                                @RequestParam(name="dataFinal", required = false) String dataFinal,
                                @RequestParam(name="cidade", required = false) String cidade,
                                HttpServletResponse response) throws IOException, Exception {
        jasperService.addParams(dataInicial, dataInicial.isEmpty() ? null : dataInicial);
        jasperService.addParams(dataFinal, dataFinal.isEmpty() ? null : dataFinal);
        jasperService.addParams(cidade, cidade.isEmpty() ? null : cidade);
        byte[] bytes = jasperService.exportPDF(code);
        response.setContentType(MediaType.APPLICATION_PDF_VALUE);
        response.setHeader("Content-disposition", "attachment; filename=relatorio-" + code + ".pdf");
        response.getOutputStream().write(bytes);
    }
    
    // Esse é para relatório com parametros
    @GetMapping("/relatorio/pdf/{code}")
    public void exibirRelatorioPatrimoniosNosDepartamentos(@PathVariable("code") String code, 
    @RequestParam(name="dataInicial", required = false) String dataInicial,
    @RequestParam(name="dataFinal", required = false) String dataFinal,
    HttpServletResponse response) throws IOException, Exception {
        jasperService.addParams(dataInicial, dataInicial.isEmpty() ? null : dataInicial);
        jasperService.addParams(dataFinal, dataFinal.isEmpty() ? null : dataFinal);
        byte[] bytes = jasperService.exportPDF(code);
        response.setContentType(MediaType.APPLICATION_PDF_VALUE);
        response.setHeader("Content-disposition", "attachment; filename=relatorio-" + code + ".pdf");
        response.getOutputStream().write(bytes);
    }
    
    // Esse é para relatório com parametros
    @GetMapping("/relatorio/pdf/{code}")
    public void exibirRelatorioPatrimoniosPorObra(@PathVariable("code") String code, 
                                @RequestParam(name="dataInicial", required = false) String dataInicial,
                                @RequestParam(name="dataFinal", required = false) String dataFinal,
                                @RequestParam(name="nomeObra", required = false) String nomeObra,
                                HttpServletResponse response) throws IOException, Exception {
        jasperService.addParams(dataInicial, dataInicial.isEmpty() ? null : dataInicial);
        jasperService.addParams(dataFinal, dataFinal.isEmpty() ? null : dataFinal);
        jasperService.addParams(nomeObra, nomeObra.isEmpty() ? null : nomeObra);
        byte[] bytes = jasperService.exportPDF(code);
        response.setContentType(MediaType.APPLICATION_PDF_VALUE);
        response.setHeader("Content-disposition", "attachment; filename=relatorio-" + code + ".pdf");
        response.getOutputStream().write(bytes);
    }
    
    // Esse é para relatório com parametros
    @GetMapping("/relatorio/pdf/{code}")
    public void exibirRelatorioRequisicoesPendentes(@PathVariable("code") String code, 
                                @RequestParam(name="dataInicial", required = false) String dataInicial,
                                @RequestParam(name="dataFinal", required = false) String dataFinal,
                                HttpServletResponse response) throws IOException, Exception {
        jasperService.addParams(dataInicial, dataInicial.isEmpty() ? null : dataInicial);
        jasperService.addParams(dataFinal, dataFinal.isEmpty() ? null : dataFinal);
        byte[] bytes = jasperService.exportPDF(code);
        response.setContentType(MediaType.APPLICATION_PDF_VALUE);
        response.setHeader("Content-disposition", "attachment; filename=relatorio-" + code + ".pdf");
        response.getOutputStream().write(bytes);
    }
    
    
    @ModelAttribute("cidades")
    public List<String> getCidades(){
        return cidadeRepository.findCidades();
    }

    @ModelAttribute("idPatrimonio")
    public List<String> getIdPatrimonio(){
        return idPatrimonioRepository.findIdPatrimonio();
    }

    @ModelAttribute("nomeObra")
    public List<String> getNomeObra(){
        return nomeObraRepository.findNomeObra();
    }

    @ModelAttribute("nomePatrimonio")
    public List<String> getNomePatrimonio(){
        return nomePatrimonioRepository.findNomePatrimonio();
    }

    @ModelAttribute("situacaoPatrimonio")
    public List<String> getSituacaoPatrimonio(){
        return situacaoPatrimonioRepository.findSituacaoPatrimonio();
    }

    @ModelAttribute("tipoPatrimonio")
    public List<String> getTipoPatrimonio(){
        return tipoPatrimonioRepository.findTipoPatrimonio();
    }


}
