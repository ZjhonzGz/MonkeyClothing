package com.cibertec.monkey.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cibertec.monkey.entity.AsesorVenta;
import com.cibertec.monkey.service.AsesorVentaService;

@Controller
public class AsesorVentaController {

    @Autowired
    private AsesorVentaService asesorVentaService;

    @GetMapping("/asesor/new")
    public String createForm(Model model) {
        model.addAttribute("asesor", new AsesorVenta());
        return "usuario/create_asesor";
    }

    @PostMapping("/asesor")
    public String save(@RequestParam("nombres") String nombres,
                       @RequestParam("email") String email,
                       @RequestParam("telefono") String telefono,
                       @RequestParam(value = "id", required = false) Integer id,
                       RedirectAttributes redirectAttrs) {

        if (nombres.isBlank()) {
            redirectAttrs.addFlashAttribute("error", "El nombre es obligatorio.");
            return id != null ? "redirect:/asesor/edit/" + id : "redirect:/asesor/new";
        }

        if (!telefono.isBlank() && !telefono.matches("\\d+")) {
            redirectAttrs.addFlashAttribute("error", "El teléfono solo debe contener números.");
            return id != null ? "redirect:/asesor/edit/" + id : "redirect:/asesor/new";
        }

        AsesorVenta asesor = (id != null) ? asesorVentaService.buscarById(id) : new AsesorVenta();
        asesor.setNombres(nombres);
        asesor.setEmail(email);
        asesor.setTelefono(telefono);
        asesorVentaService.guardar(asesor);
        return "redirect:/usuario";
    }

    @GetMapping("/asesor/edit/{id}")
    public String editForm(@PathVariable Integer id, Model model) {
        model.addAttribute("asesor", asesorVentaService.buscarById(id));
        return "usuario/create_asesor";
    }

    @GetMapping("/asesor/delete/{id}")
    public String delete(@PathVariable Integer id) {
        asesorVentaService.eliminar(id);
        return "redirect:/usuario";
    }
}