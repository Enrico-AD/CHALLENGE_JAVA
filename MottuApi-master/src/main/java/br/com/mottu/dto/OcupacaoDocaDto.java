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
public class OcupacaoDocaDto implements Serializable {

    private Long docaId;
    private String descricao;
    private int capacidadeDeMotos;
    private int motosAtivas;
    private int vagasDisponiveis;
}

