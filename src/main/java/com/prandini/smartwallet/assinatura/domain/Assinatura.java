package com.prandini.smartwallet.assinatura.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


/**
 * @author kaiooliveira
 * created 04/09/2024
 */

@Data
@Entity
@Builder
@NoArgsConstructor @AllArgsConstructor
@Table(name = "ASSINATURA")
public class Assinatura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dtInicio;

    private LocalDate dtFim;

    private boolean ativa;
}
