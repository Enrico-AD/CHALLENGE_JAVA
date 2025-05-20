package br.com.mottu.service;

import br.com.mottu.dto.RelatorioMotosEstabelecimentoDto;
import br.com.mottu.dto.RelatorioOcupacaoDocaDto;
import br.com.mottu.enums.StatusMotoEnum;
import br.com.mottu.enums.StatusVinculoEnum;
import br.com.mottu.exceptions.ObjectNotFoundException;
import br.com.mottu.model.Doca;
import br.com.mottu.model.Estabelecimento;
import br.com.mottu.repository.DocaMotoRepository;
import br.com.mottu.repository.DocaRepository;
import br.com.mottu.repository.EstabelecimentoRepository;
import br.com.mottu.repository.MotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RelatorioService {
    @Autowired
    private EstabelecimentoRepository estabelecimentoRepository;
    @Autowired
    private MotoRepository motoRepository;
    @Autowired
    private DocaRepository docaRepository;
    @Autowired
    private DocaMotoRepository docaMotoRepository;

    public List<RelatorioMotosEstabelecimentoDto> getRelatorioMotosPorEstabelecimento() {
        List<Estabelecimento> estabelecimentos = estabelecimentoRepository.findAll();

        return estabelecimentos.stream().map(est -> {
            Long totalMotos = motoRepository.countByEstabelecimentoId(est.getId());
            Long motosAtivas = motoRepository.countByEstabelecimentoIdAndStatus(est.getId(), StatusMotoEnum.ATIVO);
            Long motosManutencao = motoRepository.countByEstabelecimentoIdAndStatus(est.getId(), StatusMotoEnum.MANUTENCAO);

            return new RelatorioMotosEstabelecimentoDto(
                    est.getCnpj(), // ou est.getNome(), dependendo da nomenclatura
                    totalMotos,
                    motosAtivas,
                    motosManutencao
            );
        }).collect(Collectors.toList());
    }
    public List<RelatorioOcupacaoDocaDto> getOcupacaoDocasPorEstabelecimento(Long estabelecimentoId) {
        List<Doca> docas = docaRepository.findByEstabelecimentoId(estabelecimentoId);

        return docas.stream().map(doc -> {
            Long motosAtivas = docaMotoRepository.countByDocaIdAndStatus(doc.getId(), StatusVinculoEnum.ATIVO);
            Integer capacidade = doc.getCapacidadeDeMotos();
            Integer vagas = capacidade - motosAtivas.intValue();

            return new RelatorioOcupacaoDocaDto(
                    doc.getId(),
                    doc.getDescricao(),
                    capacidade,
                    motosAtivas,
                    Math.max(vagas, 0)
            );
        }).collect(Collectors.toList());
    }
    public RelatorioOcupacaoDocaDto getOcupacaoPorDoca(Long docaId) {
        Doca doca = docaRepository.findById(docaId)
                .orElseThrow(() -> new ObjectNotFoundException("Doca não encontrada com id " + docaId));

        Long motosAtivas = docaMotoRepository.countByDocaIdAndStatus(docaId, StatusVinculoEnum.ATIVO);
        Integer capacidade = doca.getCapacidadeDeMotos();
        Integer vagas = capacidade - motosAtivas.intValue();

        return new RelatorioOcupacaoDocaDto(
                doca.getId(),
                doca.getDescricao(),
                capacidade,
                motosAtivas,
                Math.max(vagas, 0)
        );
    }

}
