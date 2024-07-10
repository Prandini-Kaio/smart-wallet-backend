package com.prandini.smartwallet.conta.repository;


import com.prandini.smartwallet.conta.domain.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/*
 * @author prandini
 * created 4/5/24
 */
@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {

    @Query("SELECT c FROM Conta c WHERE lower(c.nome) LIKE CONCAT('%', LOWER(:filter), '%') OR lower(c.banco) LIKE CONCAT('%', LOWER(:filter),'%')")
    Conta getContaByFilter(String filter);

    @Query("SELECT COUNT(c) > 0 " +
            "FROM Conta c " +
            "WHERE LOWER(c.nome) = :nome " +
            "AND LOWER(c.banco) = :banco ")
    boolean existsContaByNomeBanco(String nome, String banco);
}
