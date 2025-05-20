package br.com.mottu.repository;

import br.com.mottu.model.Rastreador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RastreadorRepository extends JpaRepository<Rastreador, Long> {
    Optional<Rastreador> findByImei(String imei);
}
