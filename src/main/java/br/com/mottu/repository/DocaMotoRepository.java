package br.com.mottu.repository;

import br.com.mottu.enums.StatusVinculoEnum;
import br.com.mottu.model.DocaMoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DocaMotoRepository extends JpaRepository<DocaMoto, Long> {
    List<DocaMoto> findByDocaId(Long docaId);
    List<DocaMoto> findByMotoId(Long motoId);
    List<DocaMoto> findByDocaIdAndStatus(Long docaId, StatusVinculoEnum statusVinculoEnum);
    Long countByDocaIdAndStatus(Long docaId, StatusVinculoEnum statusVinculoEnum);
    @Modifying
    @Query("UPDATE DocaMoto dm SET dm.status = br.com.mottu.enums.StatusVinculoEnum.INATIVO WHERE dm.moto.id = :motoId")
    void updateStatusVinculoToInativoByMotoId(@Param("motoId") Long motoId);

}
