package br.com.mottu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelatorioOcupacaoDocaDto {
    private Long docaId;
    private String descricao;
    private Integer capacidadeDeMotos;
    private Long motosAtivas;
    private Integer vagasDisponiveis;
}

