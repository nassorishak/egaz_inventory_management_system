package com.egaz.inventory.management.system.API;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/products")
public class ReportApi {

    @GetMapping("/report/excel")
    public void downloadExcel(HttpServletResponse response) throws IOException {
        // ... generate Excel
    }

    @GetMapping("/report/pdf")
    public void downloadPdf(HttpServletResponse response) throws IOException {
        // ... generate PDF
    }
}