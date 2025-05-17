package br.com.mottu.repository;

import br.com.mottu.model.Doca;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocaRepository extends JpaRepository<Doca, Long> {
    List<Doca> findByEstabelecimentoId(Long estabelecimentoId);
}
