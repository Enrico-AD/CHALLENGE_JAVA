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
public class RastreadorDto implements Serializable {

    private Long id;

    @NotBlank(message = "O IMEI é obrigatório")
    private String imei;

    @NotBlank(message = "O modelo é obrigatório")
    private String modelo;

    @NotNull(message = "A latitude é obrigatória")
    private Double latitude;

    @NotNull(message = "A longitude é obrigatória")
    private Double longitude;

    private LocalDateTime dataAtualizacao;
    private LocalDateTime dataCriacao;
}
