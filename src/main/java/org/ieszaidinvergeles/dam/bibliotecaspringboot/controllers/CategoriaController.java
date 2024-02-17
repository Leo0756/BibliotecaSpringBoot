package org.ieszaidinvergeles.dam.bibliotecaspringboot.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.ieszaidinvergeles.dam.bibliotecaspringboot.controllers.helper.HistoricoHelper;
import org.ieszaidinvergeles.dam.bibliotecaspringboot.models.entities.EntityCategoria;
import org.ieszaidinvergeles.dam.bibliotecaspringboot.models.entities.EntityLibro;
import org.ieszaidinvergeles.dam.bibliotecaspringboot.models.repositories.IRepositoryCategoria;
import org.ieszaidinvergeles.dam.bibliotecaspringboot.models.repositories.IRepositoryHistorico;
import org.ieszaidinvergeles.dam.bibliotecaspringboot.models.repositories.IRepositoryLibro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Controlador REST que gestiona las operaciones relacionadas con las categorias.
 */
@RestController
@RequestMapping("/biblioteca/categorias")
@CrossOrigin(origins = "http://localhost:3000")

public class CategoriaController {

    @Autowired // Implementación de la interfaz IRepositoryCategoria.
    IRepositoryCategoria repositoryCategoria;

    @Autowired // Implementación de la interfaz IRepositoryLibro.
    IRepositoryLibro repositoryLibro;

    @Autowired
    IRepositoryHistorico historicoRepository;

    /**
     * Metodo de seleccion de todas las categorias.
     *
     * @return Devuelve todas las categorias existentes.
     */
    @GetMapping
    public List<EntityCategoria> buscarCategorias() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ipCliente = request.getRemoteAddr();
        HistoricoHelper.guardarSentencia(ipCliente, historicoRepository, "SELECT * FROM categoria;");
        return (List<EntityCategoria>) repositoryCategoria.findAll(); // Devuelve todas las categorías.
    }

    /**
     * Metodo para buscar una categoria por su ID, donde {id} es un parámetro de ruta.
     *
     * @param id parametro con el ID de la categoria
     * @return devuelve la categoria o error dependiendo de si existe o no.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EntityCategoria> buscarCategoriaPorId(@PathVariable(value = "id") int id) {
        Optional<EntityCategoria> categoria = repositoryCategoria.findById(id); // Busca la categoría por su ID.
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ipCliente = request.getRemoteAddr();
        HistoricoHelper.guardarSentencia(ipCliente, historicoRepository, "SELECT * FROM categoria WHERE id = " + id);

        if (categoria.isPresent()) { // Si la categoría existe...
            return ResponseEntity.ok().body(categoria.get()); // ...devuelve la categoría.

        } else { // Si la categoría no existe...
            return ResponseEntity.notFound().build(); // ...devuelve un error 404.
        }
    }

    /**
     * Metodo para buscar una categoria por su nombre, donde {nombre} es un parámetro de ruta.
     *
     * @param nombre parametro con el nombre de la categoria.
     * @return devuelve la categoria o error dependiendo de si existe o no.
     */
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<EntityCategoria> buscarCategoriaPorNombre(@PathVariable(value = "nombre") String nombre) {
        List<EntityCategoria> lista = buscarCategorias(); // Obtiene todas las categorías.
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ipCliente = request.getRemoteAddr();
        EntityCategoria categoria = null;
        for (int i = 0; i < lista.size(); i++) { // Recorre todas las categorias
            if (lista.get(i).getCategoria().equals(nombre)) { // Si el nombre de la categoría coincide con el parámetro de ruta...
                categoria = lista.get(i); // ...guarda esa categoría.
                HistoricoHelper.guardarSentencia(ipCliente, historicoRepository, "SELECT * FROM categoria WHERE categoria =" + categoria.getCategoria());
            }
        }
        if (categoria != null) { // Si se encontró una categoría...
            return ResponseEntity.ok().body(categoria); // ...devuelve la categoría.

        } else { // Si no se encontró una categoría...
            return ResponseEntity.notFound().build(); // ...devuelve un error 404.
        }
    }

    /**
     * Metodo para insertar una categoria.
     *
     * @param categoria parametro que contiene el objeto EntityCategoria a insertar
     * @return inserta la categoria.
     */
    @PostMapping
    public EntityCategoria guardarCategoria(@Valid @RequestBody EntityCategoria categoria,HttpServletRequest request) {
        categoria.setId(0);

        String ipCliente = request.getRemoteAddr();

        HistoricoHelper.guardarSentencia(ipCliente, historicoRepository, "INSERT INTO categoria (categoria) " + "VALUES (" + categoria.getCategoria() + ")");

        return repositoryCategoria.save(categoria); // Guarda la categoria.
    }

    /**
     * Metodo para actualizar una categoria existente, donde {id} es un parámetro de ruta.
     *
     * @param newCategoria objeto EntityCategoria que contiene los nuevos datos de la categoria
     * @param id           parametro con la categoria a actualizar
     * @return devuelve la categoria o error dependiendo de si existe o no.
     */
    @PutMapping("{id}")
    public ResponseEntity<?> actualizarCategoria(@RequestBody @Valid EntityCategoria newCategoria, @PathVariable(value = "id") int id) {
        Optional<EntityCategoria> categoria = repositoryCategoria.findById(id); // Busca una categoría por su ID.
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ipCliente = request.getRemoteAddr();
        if (categoria.isPresent()) { // Si la categoría existe...

            HistoricoHelper.guardarSentencia(ipCliente, historicoRepository, "UPDATE categoria SET categoria =" + categoria.get().getCategoria() + "WHERE id=" + id);

            categoria.get().setCategoria(newCategoria.getCategoria()); // ...actualiza su nombre.
            categoria.get().setListaLibros(newCategoria.getListaLibros()); // ...actualiza su lista de libros.
            repositoryCategoria.save(categoria.get()); // ...guarda la categoría actualizada.
            return ResponseEntity.ok().body("Actualizado"); // ...devuelve un mensaje de éxito.

        } else { // Si la categoría no existe...
            return ResponseEntity.notFound().build(); // ...devuelve un error 404.
        }
    }

    /**
     * Metodo para borrar una categoria, donde {id} es un parámetro de ruta.
     *
     * @param id parametro con la categoria a eliminar
     * @return devuelve la categoria o error dependiendo de si existe o no.
     */
    @DeleteMapping("{id}")
    public ResponseEntity<?> borrarCategoria(@PathVariable(value = "id") int id) {
        Optional<EntityCategoria> categoria = repositoryCategoria.findById(id); // Busca una categoría por su ID.
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ipCliente = request.getRemoteAddr();
        if (categoria.isPresent()) { // Si la categoría existe...
            Collection<EntityLibro> libros = categoria.get().getListaLibros(); // ...obtiene sus libros.

            for (EntityLibro libro : libros) { // Bucle para recorrer todos los libros.
                repositoryLibro.deleteById(libro.getId()); // Borra cada libro del bucle.
            }

            HistoricoHelper.guardarSentencia(ipCliente, historicoRepository, "DELETE FROM categoria WHERE id=" + id);
            repositoryCategoria.deleteById(id); // Borra la categoría.
            return ResponseEntity.ok().body("Borrado"); // Devuelve un mensaje de éxito.

        } else { // Si la categoría no existe...
            return ResponseEntity.notFound().build(); // ...devuelve un error 404.
        }
    }
}

