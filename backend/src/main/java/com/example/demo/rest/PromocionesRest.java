package com.example.demo.rest;

import com.example.demo.model.Promocion;
import com.example.demo.service.PromocionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController()
@RequiredArgsConstructor
public class PromocionesRest {
    private final PromocionService service;

    @GetMapping("/api/promociones")
    @RolesAllowed({"admin", "user"})
    public List<Promocion> getAll() {
        return service.obtenerTodos();
    }

    @PostMapping("/api/promociones")
    @RolesAllowed({"admin"})
    public ResponseEntity create(@RequestBody Promocion promocion) throws Exception {
        List errores = new ArrayList<String>();
        if (promocion.getTitulo() != null) {
            if (promocion.getTitulo().length() < 5) {
                errores.add("El titulo tiene que ser mayor que 5");
            }
            if (promocion.getTitulo().length() > 45) {
                errores.add("El titulo tiene que ser menor que 45");
            }
        } else {
            errores.add("Debe ingresar el titulo");
        }

        if (promocion.getDescripcion() != null) {
            if (promocion.getDescripcion().length() > 255) {
                errores.add("La descripción tiene que ser menor que 255");
            }
        } else {
            errores.add("Debe ingresar la descripcion");
        }

        if (promocion.getPrecioOriginal() != null) {
            if (!promocion.getPrecioOriginal().toString().matches("^[0-9]+(\\.[0-9]{1,2})?$")) {
                errores.add("El precio original debe tener hasta dos decimales");
            }
        } else {
            errores.add("Debe ingresar el precio original");
        }

        if (promocion.getPrecioPromocion() != null) {
            if (!promocion.getPrecioPromocion().toString().matches("^[0-9]+(\\.[0-9]{1,2})?$")) {
                errores.add("El precio promocion debe tener hasta dos decimales");
            }
        } else {
            errores.add("Debe ingresar el precio promocion");
        }

        if (promocion.getPorcentajeDescuento() != null) {
            if (promocion.getPorcentajeDescuento() > 100 || promocion.getPorcentajeDescuento() < 0) {
                errores.add("El porcentaje descuento debe ser un entero entre 0 y 100");
            }
        } else {
            errores.add("Debe ingresar el porcentaje descuento");
        }

        if (promocion.getDetalle() != null) {
            if (promocion.getDetalle().length() > 255) {
                errores.add("El detalle tiene que ser menor que 2555");
            }
        } else {
            errores.add("Debe ingresar el detalle");
        }

        if (promocion.getFecha() != null) {
            if (promocion.getFecha().getTime() < new Date().getTime()) {
                errores.add("La fecha tiene que ser mayor a hoy");
            }
        } else {
            errores.add("Debe ingresar la fecha");
        }

        if (promocion.getCantidadDias() != null) {
            if (promocion.getCantidadDias() > 999 || promocion.getCantidadDias() < 0) {
                errores.add("La cantidad de dias debe ser un entero de 3 digitos");
            }
        } else {
            errores.add("Debe ingresar la cantidad de dias");
        }
        if (errores.size() > 0) {
            return ResponseEntity.badRequest().body(String.join(", ", errores));
        } else {
            service.crear(promocion);
            return ResponseEntity.ok().build();
        }
    }
}
