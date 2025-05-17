package br.com.mottu.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocaDto implements Serializable {

    private Long id;

    @NotBlank(message = "A descrição é obrigatória")
    private String descricao;

    @NotNull(message = "O comprimento é obrigatório")
    private Double comprimento;

    @NotNull(message = "A largura é obrigatória")
    private Double largura;

   /* @NotBlank(message = "A posição é obrigatória")
    private String posicao;
*/
    @NotNull(message = "A capacidade de motos é obrigatória")
    private Integer capacidadeDeMotos;

    @NotNull(message = "O estabelecimentoId é obrigatório")
    private Long estabelecimentoId;

    private LocalDateTime dataCriacao;

    private LocalDateTime dataAtualizacao;

}
