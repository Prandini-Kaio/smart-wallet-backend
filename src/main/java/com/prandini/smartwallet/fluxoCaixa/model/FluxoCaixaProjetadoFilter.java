package com.prandini.smartwallet.fluxoCaixa.model;

import com.prandini.smartwallet.conta.model.ContaFilter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

/**
 * @author kaiooliveira
 * created 04/01/2025
 */

@Data
@NoArgsConstructor @AllArgsConstructor
public class FluxoCaixaProjetadoFilter {

    private List<Long> contaIds;

    private LocalDate dtInicio;

    private Month mes;

}
