package org.ieszaidinvergeles.dam.bibliotecaspringboot.controllers;

import jakarta.validation.Valid;
import org.ieszaidinvergeles.dam.bibliotecaspringboot.models.entities.EntityCategoria;
import org.ieszaidinvergeles.dam.bibliotecaspringboot.models.entities.EntityLibro;
import org.ieszaidinvergeles.dam.bibliotecaspringboot.models.entities.EntityPrestamos;
import org.ieszaidinvergeles.dam.bibliotecaspringboot.models.repositories.IRepositoryLibro;
import org.ieszaidinvergeles.dam.bibliotecaspringboot.models.repositories.IRepositoryPrestamos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controlador REST que gestiona las operaciones relacionadas con los prestamos.
 */
@RestController
@RequestMapping("/biblioteca/prestamos")
public class PrestamosController {
    @Autowired //inyección de dependencia: por tanto, será inicializado
    IRepositoryPrestamos prestamosRepository; //creando Spring automáticamente su instancia

    /**
     * Metodo de seleccion de todos los prestamos.
     * @return Devuelve todos los prestamos existentes.
     */
    @GetMapping //endpoint para buscar todos
    // Tipo de solicitud HTTP --> GET
    public List<EntityPrestamos> buscarPrestamos() {
        // ... (código para buscar todos los libros)
        return (List<EntityPrestamos>) prestamosRepository.findAll();
    }

    /**
     * Metodo para buscar un prestamo por su ID, donde {id} es un parámetro de ruta.
     * @param id parametro con el ID del prestamo
     * @return devuelve el prestamo o error dependiendo de si existe o no.
     */
    @GetMapping("/{id}") //endpoint para buscar un libro por id
    public ResponseEntity<EntityPrestamos> buscarLibroPorId(@PathVariable(value = "id") int id) {
        // ... (código para buscar un libro por su identificador)
        Optional<EntityPrestamos> prestamo = prestamosRepository.findById(id);
        if (prestamo.isPresent())
            return ResponseEntity.ok().body(prestamo.get()); // HTTP 200 OK
        else return ResponseEntity.notFound().build(); // HTTP 404
    }

    /**
     * Metodo para insertar un prestamo.
     * @param prestamo parametro que contiene el objeto EntityPrestamo a insertar
     * @return inserta el prestamo.
     */
    @PostMapping
    public EntityPrestamos guardarPrestamo(@Valid @RequestBody EntityPrestamos prestamo) {
        prestamo.setIdPrestamo(0);
        return prestamosRepository.save(prestamo); // Guarda el prestamo.
    }
}
