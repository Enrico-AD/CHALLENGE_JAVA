package br.com.mottu.dto;

import br.com.mottu.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioDto implements Serializable {

    private static final long serialVersionUID = 1534244992322023678L;

    private Long id;
    private String nome;
    private String username;
    private String password;
    private String email;
    private String roles;
    private String cpf;



   /* public UsuarioDto(Usuario u) {
        this.id = u.getId();
        this.nome = u.getNome();
        this.username = u.getUsername();
        this.password = u.getPassword();
        this.email = u.getEmail();
        this.cpf = u.getCpf();
        //this.status = u.getStatus();
    }*/

}