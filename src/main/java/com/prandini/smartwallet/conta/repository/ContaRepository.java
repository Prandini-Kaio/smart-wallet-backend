package com.prandini.smartwallet.conta.repository;


import com.prandini.smartwallet.common.model.AutcompleteDTO;
import com.prandini.smartwallet.conta.domain.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/*
 * @author prandini
 * created 4/5/24
 */
@Repository
public interface ContaRepository extends JpaRepository<Conta, Long>, ContaRepositoryCustom {

    @Query(" SELECT COUNT(c) > 0 " +
            " FROM Conta c " +
            " WHERE LOWER(c.banco) LIKE CONCAT('%', LOWER(:banco), '%') " +
            " AND LOWER(c.nome) LIKE CONCAT('%', LOWER(:nome), '%') ")
    boolean existsContaByNomeBanco(String nome, String banco);


    @Query("SELECT new com.prandini.smartwallet.common.model.AutcompleteDTO(c.id, CONCAT(c.banco, ' - ', c.nome) )" +
            "FROM Conta c " +
            "WHERE LOWER(c.banco) LIKE CONCAT('%', LOWER(:banco), '%') " +
            " OR LOWER(c.nome) LIKE CONCAT('%', LOWER(:nome), '%') ")
    List<AutcompleteDTO> autcompleteContas(String nome, String banco);
}
