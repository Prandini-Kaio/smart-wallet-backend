package com.prandini.smartwallet.transacao.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/*
 * @author prandini
 * created 5/4/24
 */
public class TransacaoRepositoryCustomImpl implements TransacaoRepositoryCustom{

    @PersistenceContext
    private EntityManager entityManager;

}
