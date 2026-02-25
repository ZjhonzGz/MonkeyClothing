package com.cibertec.monkey.service.impl;

import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

@Service
public class ReporteServiceImpl {

    @Autowired
    private DataSource dataSource;

    public byte[] generarReporte(String jrxml) throws Exception {
        try (Connection connection = dataSource.getConnection()) {
            InputStream jrxmlStream =
                new ClassPathResource("reportes/" + jrxml)
                .getInputStream();
            JasperReport jasperReport =
                JasperCompileManager.compileReport(jrxmlStream);
            Map<String, Object> params = new HashMap<>();
            JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperReport, params, connection);
            return JasperExportManager.exportReportToPdf(jasperPrint);
        }
    }
}