package br.com.mottu.repository;

import br.com.mottu.enums.SituacaoMotoEnum;
import br.com.mottu.enums.StatusMotoEnum;
import br.com.mottu.model.Moto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MotoRepository extends JpaRepository<Moto, Long> {
    List<Moto> findByEstabelecimentoId(Long estabelecimentoId);
    List<Moto> findByStatus(String status);
    Long countByEstabelecimentoId(Long estabelecimentoId);

    Long countByEstabelecimentoIdAndStatus(Long estabelecimentoId, StatusMotoEnum status);

    @Query("""
    SELECT m FROM Moto m
    LEFT JOIN DocaMoto dm ON dm.moto.id = m.id
        AND (dm.status = ATIVO)
        AND (:situacao IS NULL OR dm.situacao = :situacao)
        AND (:docaId IS NULL OR dm.doca.id = :docaId)
    WHERE (:situacao IS NULL AND :docaId IS NULL OR dm.id IS NOT NULL)
      AND (:estabelecimentoId IS NULL OR m.estabelecimento.id = :estabelecimentoId)
""")
    List<Moto> findByFiltros(
            @Param("situacao") SituacaoMotoEnum situacao,
            @Param("estabelecimentoId") Long estabelecimentoId,
            @Param("docaId") Long docaId
    );

}
