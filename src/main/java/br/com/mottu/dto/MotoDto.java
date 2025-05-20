package br.com.mottu.dto;

import br.com.mottu.enums.StatusMotoEnum;
import jakarta.validation.Valid;
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
public class MotoDto implements Serializable {

    private Long id;

    @NotBlank(message = "A placa é obrigatória")
    private String placa;

    @NotBlank(message = "O modelo é obrigatório")
    private String modelo;

    @NotNull(message = "O ano é obrigatório")
    private Integer ano;

    @NotNull(message = "O status é obrigatório")
    private StatusMotoEnum status;

    @NotNull(message = "A quilometragem é obrigatória")
    private Integer quilometragem;

    @NotNull(message = "O estabelecimentoId é obrigatório")
    private Long estabelecimentoId;


    private RastreadorDto rastreador;

    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;

}

