package com.cibertec.monkey.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.cibertec.monkey.service.DashboardService;

@Controller
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("ventasHoy",         dashboardService.contarVentasHoy());
        model.addAttribute("ingresosMes",        dashboardService.totalIngresosMes());
        model.addAttribute("gananciaMes",        dashboardService.totalGananciaMes());
        model.addAttribute("stockBajoCount",     dashboardService.contarProductosStockBajo(20));
        model.addAttribute("productosStockBajo", dashboardService.listarProductosStockBajo(20));
        model.addAttribute("ultimasVentas",      dashboardService.ultimasVentas());
        return "dashboard";
    }
}