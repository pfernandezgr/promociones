package com.example.demo.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
public class Promocion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String titulo;
    String descripcion;
    Double precioOriginal;
    Double precioPromocion;
    Integer porcentajeDescuento;
    String image;
    String detalle;
    Date fecha;
    Integer cantidadDias;
}
