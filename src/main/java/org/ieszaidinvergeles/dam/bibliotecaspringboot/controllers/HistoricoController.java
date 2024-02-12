package org.ieszaidinvergeles.dam.bibliotecaspringboot.controllers;

import jakarta.validation.Valid;
import org.ieszaidinvergeles.dam.bibliotecaspringboot.models.entities.EntityHistorico;
import org.ieszaidinvergeles.dam.bibliotecaspringboot.models.repositories.IRepositoryHistorico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/biblioteca/historicos")
public class HistoricoController {
    @Autowired
    private IRepositoryHistorico historicoRepository;

    /**
     * Selecciona y devuelve todos los historicos de la tabla Historico.
     *
     * @return Un objeto de tipo List<EntityHistorico>.
     */
    @GetMapping("/select/all")
    public List<EntityHistorico> buscarHistoricos() {
        return (List<EntityHistorico>) historicoRepository.findAll();
    }

    /**
     * Selecciona y devuelve todos un historio a través de un identificador.
     *
     * @param id El identificador del historico a seleccionar.
     * @return Un objeto de tipo ResponseEntity<EntityHistorico>.
     */
    @GetMapping("/select/{id}")
    public ResponseEntity<EntityHistorico> buscarHistoricoPorId(@PathVariable(value = "id") int id) {
        Optional<EntityHistorico> historico = historicoRepository.findById(id);
        if (historico.isPresent()) {
            return ResponseEntity.ok().body(historico.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Inserta un historico en la tabla Historico a través de un objeto EntityHistorico.
     *
     * @param historico Un objeto de tipo EntityHistorico que es introducido a través de un formato JSON.
     * @return Un objeto de tipo EntityHistorico.
     */
    @PostMapping("/insert/")
    public EntityHistorico insertarHistorico(@Valid @RequestBody EntityHistorico historico) {
        historico.setIdHistorico(0);
        return historicoRepository.save(historico);
    }

    /**
     * Borra un historico de la tabla Historico a través de un identificador.
     *
     * @param id El identificador del historico a borrar.
     * @return Un objeto de tipo ResponseEntity<?>.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> borrarHistorico(@PathVariable(value = "id") int id) {
        Optional<EntityHistorico> historico = historicoRepository.findById(id);
        if (historico.isPresent()) {
            historicoRepository.deleteById(id);
            return ResponseEntity.ok().body("Borrado");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Actualiza un historico a través de un objeto de tipo EntityHistorico y un identificador.
     *
     * @param nuevoHistorico El objeto de tipo EntityHistorico con los datos a actualizar.
     * @param id             El identificador del historico a modificar.
     * @return Un objeto de tipo ResponseEntity<?>.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<?> actualizarHistorico(@Valid @RequestBody EntityHistorico nuevoHistorico, @PathVariable(value = "id") int id) {
        Optional<EntityHistorico> historico = historicoRepository.findById(id);
        if (historico.isPresent()) {
            historico.get().setFecha(nuevoHistorico.getFecha());
            historico.get().setInfo(nuevoHistorico.getInfo());
            historico.get().setUser(nuevoHistorico.getUser());
            historicoRepository.save(historico.get());
            return ResponseEntity.ok().body("Actualizado");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
