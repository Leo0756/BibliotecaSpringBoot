package org.ieszaidinvergeles.dam.bibliotecaspringboot.controllers.helper;

import org.ieszaidinvergeles.dam.bibliotecaspringboot.models.entities.EntityHistorico;
import org.ieszaidinvergeles.dam.bibliotecaspringboot.models.repositories.IRepositoryHistorico;

import java.time.LocalDateTime;

/**
 * Helper que gestiona las operaciones relacionadas con el histórico.
 */
public class HistoricoHelper {

    /**
     * Método para guardar la información que se envía al servidor a través de los controladores.
     *
     * @param IP                  Dirección IP del usuario remitente.
     * @param historicoRepository Interfaz de tipo IRepositoryHistorico para guardar una sentencia en Historico.
     * @param sentencia           Sentencia SQL.
     */
    public static void guardarSentencia(String IP, IRepositoryHistorico historicoRepository, String sentencia) {
        EntityHistorico historico = new EntityHistorico();
        historico.setUser(IP);
        historico.setFecha(LocalDateTime.now());
        historico.setInfo(sentencia);
        historicoRepository.save(historico);
    }

}
