package com.prandini.smartwallet.transacao.repository;

import com.prandini.smartwallet.transacao.domain.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long>, TransacaoRepositoryCustom {

}
