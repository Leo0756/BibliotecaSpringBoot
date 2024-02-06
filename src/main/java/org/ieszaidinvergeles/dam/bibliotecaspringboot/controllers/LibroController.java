package org.ieszaidinvergeles.dam.bibliotecaspringboot.controllers;

import org.ieszaidinvergeles.dam.bibliotecaspringboot.models.entities.EntityLibro;
import org.ieszaidinvergeles.dam.bibliotecaspringboot.models.repositories.IRepositoryLibro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/biblioteca/libros")
public class LibroController {
    @Autowired //inyección de dependencia: por tanto, será inicializado
    IRepositoryLibro librosRepository; //creando Spring automáticamente su instancia

    @GetMapping //endpoint para buscar todos
    // Tipo de solicitud HTTP --> GET
    public List<EntityLibro> buscarLibros() {
        // ... (código para buscar todos los libros)
        return (List<EntityLibro>) librosRepository.findAll();
    }

    @GetMapping("/{id}") //endpoint para buscar un libro por id
    public ResponseEntity<EntityLibro> buscarLibroPorId(@PathVariable(value = "id") int id) {
        // ... (código para buscar un libro por su identificador)
        Optional<EntityLibro> libro = librosRepository.findById(id);
        if (libro.isPresent())
            return ResponseEntity.ok().body(libro.get()); // HTTP 200 OK
        else return ResponseEntity.notFound().build(); // HTTP 404
    }

    /*@GetMapping("/{nom_libro}") //endpoint para buscar un libro por nombre
    public ResponseEntity<EntityLibro> buscarLibroPorNombre(@PathVariable(value = "nom_libro") String nombre) {
        // ... (código para buscar un departamento por su nombre)
        Optional<EntityLibro> libro = Optional.ofNullable(librosRepository.findByNomLibro(nombre));
        if (libro.isPresent())
            return ResponseEntity.ok().body(libro.get()); // HTTP 200 OK
        else return ResponseEntity.notFound().build(); // HTTP 404
    }
*/
    @PostMapping //endpoint para añadir un nuevo libro
    public EntityLibro guardarLibro(@Validated @RequestBody EntityLibro libro) {
        // ... (código para guardar un nuevo libro)
        return librosRepository.save(libro); // da error si ya existe
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> borrarLibro (@PathVariable(value = "id") int id) {
        // ... (código para borrar un libro por su id)
        Optional<EntityLibro> libro = librosRepository.findById(id);
        if(libro.isPresent()) {
            librosRepository.deleteById(id);
            return ResponseEntity.ok().body("Borrado");
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    // Tipo de solicitud HTTP --> PUT
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarLibro(@RequestBody EntityLibro nuevoLibro,
                                                    @PathVariable(value = "id") int id) {
        Optional<EntityLibro> libro = librosRepository.findById(id);
        if (libro.isPresent()) {
            libro.get().setNombre(nuevoLibro.getNombre());
            libro.get().setAutor(nuevoLibro.getAutor());
            libro.get().setEditorial(nuevoLibro.getEditorial());
            libro.get().setCategoria(nuevoLibro.getCategoria());
            librosRepository.save(libro.get());
            return ResponseEntity.ok().body("Actualizado");
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }
}
