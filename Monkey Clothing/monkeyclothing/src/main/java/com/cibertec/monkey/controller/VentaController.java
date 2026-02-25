package com.cibertec.monkey.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.cibertec.monkey.entity.*;
import com.cibertec.monkey.service.*;

@Controller
public class VentaController {

    @Autowired private ProductoService productoService;
    @Autowired private VentaService ventaService;
    @Autowired private DetalleVentaService detalleVentaService;
    @Autowired private ClienteService clienteService;
    @Autowired private AsesorVentaService asesorVentaService;

    @SuppressWarnings("unchecked")
    private ArrayList<ProductoParaVender> obtenerCarrito(HttpServletRequest request) {
        ArrayList<ProductoParaVender> carrito =
            (ArrayList<ProductoParaVender>) request.getSession().getAttribute("carrito");
        return carrito != null ? carrito : new ArrayList<>();
    }

    private void guardarCarrito(ArrayList<ProductoParaVender> carrito, HttpServletRequest request) {
        request.getSession().setAttribute("carrito", carrito);
    }

    private void limpiarCarrito(HttpServletRequest request) {
        guardarCarrito(new ArrayList<>(), request);
    }

    @GetMapping("/venta")
    public String listVentas(Model model) {
        model.addAttribute("ventas", ventaService.listarTodosVentas());
        model.addAttribute("clienteList", clienteService.listarTodosCliente());
        model.addAttribute("asesorVentaList", asesorVentaService.listarTodos());
        return "venta/index";
    }

    @GetMapping("/venta/new")
    public String interfazVender(Model model, HttpServletRequest request) {
        ArrayList<ProductoParaVender> carrito = obtenerCarrito(request);
        float total = 0;
        for (ProductoParaVender p : carrito) total += p.getTotal();

        model.addAttribute("venta", new Venta());
        model.addAttribute("clienteList", clienteService.listarTodosCliente());
        model.addAttribute("asesorVentaList", asesorVentaService.listarTodos());
        model.addAttribute("producto", new Producto());
        model.addAttribute("total", total);
        model.addAttribute("productoList", productoService.listar());
        return "venta/create";
    }

    @PostMapping("/venta/agregar")
    public String agregarAlCarrito(@ModelAttribute Producto producto,
                                   @RequestParam(defaultValue = "1") int cantidad,
                                   HttpServletRequest request,
                                   RedirectAttributes redirectAttrs) {
        ArrayList<ProductoParaVender> carrito = obtenerCarrito(request);
        Producto encontrado = productoService.buscarProductoByCodigo(producto.getCodigo());

        if (encontrado == null) {
            redirectAttrs.addFlashAttribute("mensaje", "Producto con código " + producto.getCodigo() + " no existe")
                         .addFlashAttribute("clase", "warning");
            return "redirect:/venta/new";
        }
        if (encontrado.sinExistencia()) {
            redirectAttrs.addFlashAttribute("mensaje", "El producto está agotado")
                         .addFlashAttribute("clase", "warning");
            return "redirect:/venta/new";
        }

        boolean yaEnCarrito = false;
        for (ProductoParaVender item : carrito) {
            if (item.getCodigo().equals(encontrado.getCodigo())) {
                item.aumentarCantidad(cantidad);
                yaEnCarrito = true;
                break;
            }
        }
        if (!yaEnCarrito) {
            carrito.add(new ProductoParaVender(
                encontrado.getIdproducto(),
                encontrado.getCodigo(),
                encontrado.getDescripcion(),
                encontrado.getPrecioCompra(),
                encontrado.getPrecioVenta(),
                encontrado.getStock(), cantidad));
        }
        guardarCarrito(carrito, request);
        return "redirect:/venta/new";
    }

    @PostMapping("/venta/quitar/{indice}")
    public String quitarDelCarrito(@PathVariable int indice, HttpServletRequest request) {
        ArrayList<ProductoParaVender> carrito = obtenerCarrito(request);
        if (!carrito.isEmpty() && carrito.get(indice) != null) {
            carrito.remove(indice);
            guardarCarrito(carrito, request);
        }
        return "redirect:/venta/new";
    }

    @PostMapping("/venta/terminar")
    public String terminarVenta(HttpServletRequest request,
                                RedirectAttributes redirectAttrs,
                                @ModelAttribute("venta") Venta miVenta) {
        ArrayList<ProductoParaVender> carrito = obtenerCarrito(request);
        if (carrito.isEmpty()) return "redirect:/venta/new";

        ventaService.registrarVentaCompleta(miVenta, carrito);
        limpiarCarrito(request);

        redirectAttrs.addFlashAttribute("mensaje", "Venta realizada correctamente")
                     .addFlashAttribute("clase", "success");
        return "redirect:/venta/new";
    }

    @GetMapping("/venta/limpiar")
    public String cancelarVenta(HttpServletRequest request, RedirectAttributes redirectAttrs) {
        limpiarCarrito(request);
        redirectAttrs.addFlashAttribute("mensaje", "Venta cancelada")
                     .addFlashAttribute("clase", "info");
        return "redirect:/venta/new";
    }

    @GetMapping("/venta/verDetalle/{id}")
    public String verDetalle(@PathVariable Long id, Model model) {
        Venta venta = ventaService.buscarVentaById(id);
        List<DetalleVenta> dv = detalleVentaService.buscarDetalleVentaByNroVenta(id);
        model.addAttribute("ventas", ventaService.listarTodosVentas());
        model.addAttribute("ventaSeleccionada", venta);
        model.addAttribute("listDetalleVenta", dv);
        return "venta/index";
    }
    
    @PostMapping("/venta/eliminar/{id}")
    public String eliminarVenta(@PathVariable Long id, RedirectAttributes redirectAttrs) {
        ventaService.eliminarVenta(id);
        redirectAttrs.addFlashAttribute("mensaje", "Venta eliminada correctamente")
                     .addFlashAttribute("clase", "success");
        return "redirect:/venta";
    }
}