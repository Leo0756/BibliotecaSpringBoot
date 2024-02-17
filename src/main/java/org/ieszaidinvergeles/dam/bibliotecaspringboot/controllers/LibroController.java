package org.ieszaidinvergeles.dam.bibliotecaspringboot.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.ieszaidinvergeles.dam.bibliotecaspringboot.controllers.helper.HistoricoHelper;
import org.ieszaidinvergeles.dam.bibliotecaspringboot.models.entities.EntityLibro;
import org.ieszaidinvergeles.dam.bibliotecaspringboot.models.repositories.IRepositoryHistorico;
import org.ieszaidinvergeles.dam.bibliotecaspringboot.models.repositories.IRepositoryLibro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;
import java.util.Optional;

/**
 * Controlador REST que gestiona las operaciones relacionadas con los libros.
 */
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/biblioteca/libros")
public class LibroController {
    @Autowired //inyección de dependencia: por tanto, será inicializado
    IRepositoryLibro librosRepository; //creando Spring automáticamente su instancia

    @Autowired
    IRepositoryHistorico historicoRepository;



    /**
     * Método de selección de todos los libros.
     *
     * @return Devuelve todos los libros existentes.
     */
    @GetMapping //endpoint para buscar todos
    // Tipo de solicitud HTTP --> GET
    public List<EntityLibro> buscarLibros() {
        // ... (código para buscar todos los libros)
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ipCliente = request.getRemoteAddr();

        HistoricoHelper.guardarSentencia(ipCliente, historicoRepository, "SELECT * FROM libro;");
        return (List<EntityLibro>) librosRepository.findAll();
    }

    /**
     * Metodo para buscar un libro por su ID, donde {id} es un parámetro de ruta.
     *
     * @param id parametro con el ID del libro
     * @return devuelve el libro o error dependiendo de si existe o no.
     */
    @GetMapping("/{id}") //endpoint para buscar un libro por id
    public ResponseEntity<EntityLibro> buscarLibroPorId(@PathVariable(value = "id") int id) {
        // ... (código para buscar un libro por su identificador)
        Optional<EntityLibro> libro = librosRepository.findById(id);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ipCliente = request.getRemoteAddr();
        HistoricoHelper.guardarSentencia(ipCliente, historicoRepository, "SELECT * FROM libro WHERE id = " + id);

        if (libro.isPresent()) return ResponseEntity.ok().body(libro.get()); // HTTP 200 OK
        else return ResponseEntity.notFound().build(); // HTTP 404
    }

    /**
     * Metodo para buscar un libro por su nombre, donde {nombre} es un parámetro de ruta.
     *
     * @param nombre parametro con el nombre del libro.
     * @return devuelve el libro o error dependiendo de si existe o no.
     */
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<EntityLibro> buscarLibroPorNombre(@PathVariable(value = "nombre") String nombre) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ipCliente = request.getRemoteAddr();
        List<EntityLibro> lista = buscarLibros(); // Obtiene todos los libros.
        EntityLibro libro = null;
        for (int i = 0; i < lista.size(); i++) { // Recorre todos los libros
            if (lista.get(i).getNombre().equals(nombre)) { // Si el nombre del libro coincide con el parámetro de ruta...
                HistoricoHelper.guardarSentencia("ip", historicoRepository, "SELECT * FROM libro WHERE nombre = " + nombre);
                libro = lista.get(i); // ...guarda esa categoría.
            }
        }

        if (libro != null) { // Si se encontró un libro...
            return ResponseEntity.ok().body(libro); // ...devuelve el libro.

        } else { // Si no se encontró un libro...
            return ResponseEntity.notFound().build(); // ...devuelve un error 404.
        }
    }

    /**
     * Metodo para insertar un libro.
     *
     * @param libro parametro que contiene el objeto EntityLibro a insertar
     * @return inserta el libro.
     */
    @PostMapping //endpoint para añadir un nuevo libro
    public EntityLibro guardarLibro(@Validated @RequestBody EntityLibro libro) {
        // ... (código para guardar un nuevo libro)
        libro.setId(0);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ipCliente = request.getRemoteAddr();
        HistoricoHelper.guardarSentencia(ipCliente, historicoRepository, "INSERT INTO libro (nombre, autor, editorial, categoria) " + "VALUES (" + libro.getNombre() + "," + libro.getAutor() + "," + libro.getEditorial() + "," + libro.getCategoria() + ")");
        return librosRepository.save(libro); // da error si ya existe
    }

    /**
     * Metodo para borrar un libro, donde {id} es un parámetro de ruta.
     *
     * @param id parametro con el libro a eliminar
     * @return devuelve el libro o error dependiendo de si existe o no.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> borrarLibro(@PathVariable(value = "id") int id) {
        // ... (código para borrar un libro por su id)
        Optional<EntityLibro> libro = librosRepository.findById(id);
        if (libro.isPresent()) {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String ipCliente = request.getRemoteAddr();
            HistoricoHelper.guardarSentencia(ipCliente, historicoRepository, "DELETE FROM libro WHERE id=" + id);
            librosRepository.deleteById(id);
            return ResponseEntity.ok().body("Borrado");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Metodo para actualizar un libro existente, donde {id} es un parámetro de ruta.
     *
     * @param nuevoLibro objeto EntityLibro que contiene los nuevos datos del libro
     * @param id         parametro con el libro a actualizar
     * @return devuelve el libro o error dependiendo de si existe o no.
     */
    // Tipo de solicitud HTTP --> PUT
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarLibro(@Validated @RequestBody EntityLibro nuevoLibro, @PathVariable(value = "id") int id) {
        Optional<EntityLibro> libro = librosRepository.findById(id);

        if (libro.isPresent()) {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String ipCliente = request.getRemoteAddr();
            HistoricoHelper.guardarSentencia(ipCliente, historicoRepository, "UPDATE libro SET " + "nombre =" + nuevoLibro.getNombre() + "," + "autor =" + nuevoLibro.getAutor() + "," + "editorial =" + nuevoLibro.getEditorial() + "," + "categoria =" + nuevoLibro.getCategoria() + "WHERE id=" + id);
            libro.get().setNombre(nuevoLibro.getNombre());
            libro.get().setAutor(nuevoLibro.getAutor());
            libro.get().setEditorial(nuevoLibro.getEditorial());
            libro.get().setCategoria(nuevoLibro.getCategoria());
            librosRepository.save(libro.get());
            return ResponseEntity.ok().body("Actualizado");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
