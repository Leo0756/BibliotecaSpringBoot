package org.ieszaidinvergeles.dam.bibliotecaspringboot.models.repositories;

import org.ieszaidinvergeles.dam.bibliotecaspringboot.models.entities.EntityCategoria;
import org.springframework.data.repository.CrudRepository;

public interface IRepositoryCategoria extends CrudRepository<EntityCategoria, Integer> {
}
