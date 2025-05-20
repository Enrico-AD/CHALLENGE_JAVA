package br.com.mottu.dto;

import br.com.mottu.enums.SituacaoMotoEnum;
import br.com.mottu.enums.StatusMotoEnum;
import br.com.mottu.enums.StatusVinculoEnum;
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
public class DocaMotoDto implements Serializable {

    private Long id;

    @NotNull(message = "O docaId é obrigatório")
    private Long docaId;

    @NotNull(message = "O motoId é obrigatório")
    private Long motoId;

    @NotNull(message = "O status é obrigatório")
    private StatusVinculoEnum status;

    @NotNull(message = "A situação é obrigatória")
    private SituacaoMotoEnum situacao;

    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
}

