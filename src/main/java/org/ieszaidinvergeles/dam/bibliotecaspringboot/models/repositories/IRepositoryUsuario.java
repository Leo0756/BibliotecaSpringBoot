package org.ieszaidinvergeles.dam.bibliotecaspringboot.models.repositories;

import org.ieszaidinvergeles.dam.bibliotecaspringboot.models.entities.EntityUsuario;
import org.springframework.data.repository.CrudRepository;

public interface IRepositoryUsuario extends CrudRepository<EntityUsuario, Integer> {
}
