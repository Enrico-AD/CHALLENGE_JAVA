package br.com.mottu.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RelatorioMotosEstabelecimentoDto implements Serializable {

    private String estabelecimento;
    private Long totalMotos;
    private Long motosAtivas;
    private Long motosManutencao;
}

