package com.example.demo.service;

import com.example.demo.model.Promocion;
import com.example.demo.repository.PromocionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PromocionService {

    public final PromocionRepository repository;

    public void crear(Promocion promocion) {
        repository.save(promocion);
        log.info("Promocion creada: " + promocion);
    }

    public List<Promocion> obtenerTodos() {
        log.info("Obtenidas todas las promociones");
        return repository.findAll();
    }
}
