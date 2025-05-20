package br.com.mottu.repository;

import br.com.mottu.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
    List<Funcionario> findByEstabelecimentoId(Long estabelecimentoId);
}
