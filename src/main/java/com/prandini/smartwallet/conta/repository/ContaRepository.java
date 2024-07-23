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
public interface ContaRepository extends JpaRepository<Conta, Long> {

    @Query("SELECT c " +
            "FROM Conta c " +
            "WHERE lower(c.nome) LIKE CONCAT('%', LOWER(:filter), '%') " +
            "OR lower(c.banco) LIKE CONCAT('%', LOWER(:filter),'%')")
    Optional<Conta> getContaByFilter(String filter);

    @Query("SELECT COUNT(c) > 0 " +
            "FROM Conta c " +
            "WHERE LOWER(c.nome) = LOWER(:nome) " +
            "AND LOWER(c.banco) = LOWER(:banco) ")
    boolean existsContaByNomeBanco(String nome, String banco);


    @Query("SELECT new com.prandini.smartwallet.common.model.AutcompleteDTO(c.id, CONCAT(c.banco, ' - ', c.nome) )" +
            "FROM Conta c " +
            "WHERE LOWER(c.nome) LIKE CONCAT('%', LOWER(:conta), '%') " +
            "OR LOWER(c.banco) LIKE CONCAT('%', LOWER(:conta), '%')")
    List<AutcompleteDTO> autcompleteContas(String conta);
}
