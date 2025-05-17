package br.com.mottu.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FuncionarioDto implements Serializable {

    private Long id;

    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "E-mail inválido")
    private String email;

    @NotBlank(message = "O telefone é obrigatório")
    private String telefone;

    @NotNull(message = "A data de nascimento é obrigatória")
    private LocalDate nascimento;

    @NotBlank(message = "O CPF é obrigatório")
    @Size(min = 11, max = 11, message = "O CPF deve ter 11 dígitos")
    private String cpf;

    @NotBlank(message = "A função é obrigatória")
    private String funcao;

    @NotNull(message = "O estabelecimentoId é obrigatório")
    private Long estabelecimentoId;

    private LocalDateTime dataCriacao;

    private LocalDateTime dataAtualizacao;
}
