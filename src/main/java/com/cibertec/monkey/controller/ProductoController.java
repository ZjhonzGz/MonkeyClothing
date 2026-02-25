package com.cibertec.monkey.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cibertec.monkey.entity.Producto;
import com.cibertec.monkey.service.ProductoService;
import com.cibertec.monkey.repository.CategoriaRepository;
import com.cibertec.monkey.repository.ProductoRepository;
import com.cibertec.monkey.entity.Categoria;

import jakarta.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@RequestMapping("/productos")
public class ProductoController {

    private final ProductoService productoService;
    private final CategoriaRepository categoriaRepository;
    private final ProductoRepository productoRepository;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("productos", productoService.listar());
        return "producto/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("producto", new Producto());
        model.addAttribute("categorias", categoriaRepository.findAll());
        return "producto/form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Producto producto,
                          @RequestParam("idcate") String idcate,
                          RedirectAttributes redirectAttrs) {

        if (producto.getPrecioCompra().compareTo(producto.getPrecioVenta()) > 0) {
            redirectAttrs.addFlashAttribute("error", "El precio de compra no puede ser mayor al precio de venta.");
            return producto.getIdproducto() != null
                    ? "redirect:/productos/editar/" + producto.getIdproducto()
                    : "redirect:/productos/nuevo";
        }
        
        if (producto.getIdproducto() == null && productoRepository.existsByCodigo(producto.getCodigo())) {
            redirectAttrs.addFlashAttribute("error", "Ya existe un producto con el código '" + producto.getCodigo() + "'.");
            return "redirect:/productos/nuevo";
        }
        
        if (producto.getIdproducto() != null) {
            productoRepository.findByCodigo(producto.getCodigo()).ifPresent(existente -> {
                if (!existente.getIdproducto().equals(producto.getIdproducto())) {
                    redirectAttrs.addFlashAttribute("error", "Ya existe un producto con el código '" + producto.getCodigo() + "'.");
                }
            });
            if (redirectAttrs.getFlashAttributes().containsKey("error")) {
                return "redirect:/productos/editar/" + producto.getIdproducto();
            }
        }

        Categoria categoria = categoriaRepository.findById(idcate).orElse(null);
        producto.setCategoria(categoria);
        productoService.guardar(producto);
        return "redirect:/productos";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {

        Producto producto = productoService.buscarPorId(id);

        model.addAttribute("producto", producto);
        model.addAttribute("categorias", categoriaRepository.findAll());
        model.addAttribute("categoriaSeleccionada",
                producto.getCategoria() != null ? producto.getCategoria().getIdcate() : "");

        return "producto/form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id) {
        productoService.eliminar(id);
        return "redirect:/productos";
    }
    
    @GetMapping("/listar")
    public String listar(Model model, HttpSession session) {

        if (session.getAttribute("usuarioLogeado") == null) {
            return "redirect:/";
        }

        model.addAttribute("productos", productoService.listar());
        return "producto/lista";
    }
}