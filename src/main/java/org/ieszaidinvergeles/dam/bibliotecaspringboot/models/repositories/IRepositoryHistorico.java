package org.ieszaidinvergeles.dam.bibliotecaspringboot.models.repositories;

import org.ieszaidinvergeles.dam.bibliotecaspringboot.models.entities.EntityHistorico;
import org.springframework.data.repository.CrudRepository;

public interface IRepositoryHistorico extends CrudRepository<EntityHistorico, Integer> {
}
