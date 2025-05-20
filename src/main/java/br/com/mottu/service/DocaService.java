package br.com.mottu.service;

import br.com.mottu.dto.DocaDto;
import br.com.mottu.enums.StatusVinculoEnum;
import br.com.mottu.exceptions.ObjectNotFoundException;
import br.com.mottu.model.Doca;
import br.com.mottu.repository.DocaMotoRepository;
import br.com.mottu.repository.DocaRepository;
import br.com.mottu.repository.EstabelecimentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DocaService {
    @Autowired
    private DocaRepository docaRepository;
    @Autowired
    private EstabelecimentoRepository estabelecimentoRepository;
    @Autowired
    private DocaMotoRepository docaMotoRepository;


    public List<DocaDto> getAll() {
        return docaRepository.findAll().stream().map(this::mapToDTO).toList();
    }

    public Optional<DocaDto> getById(Long id) {
        return docaRepository.findById(id).map(this::mapToDTO);
    }

    public List<DocaDto> getByEstabelecimento(Long id) {
        return docaRepository.findByEstabelecimentoId(id).stream().map(this::mapToDTO).toList();
    }

    public DocaDto create(DocaDto dto) {
        Doca doca = mapToEntity(dto);
        return mapToDTO(docaRepository.save(doca));
    }

    public DocaDto update(Long id, DocaDto dto) {
        if (!docaRepository.existsById(id)) {
            throw new ObjectNotFoundException("Doca não encontrada com id: " + id);
        }
        Doca doca = mapToEntity(dto);
        doca.setId(id);
        return mapToDTO(docaRepository.save(doca));
    }

    public void delete(Long id) {
        docaRepository.deleteById(id);
    }

    private DocaDto mapToDTO(Doca entity) {
        DocaDto dto = new DocaDto();
        dto.setId(entity.getId());
        dto.setDescricao(entity.getDescricao());
        dto.setComprimento(entity.getComprimento());
        dto.setLargura(entity.getLargura());
        dto.setDataCriacao(entity.getDataCriacao());
        dto.setDataAtualizacao(entity.getDataAtualizacao());
      //  dto.setPosicao(entity.getPosicao());
        dto.setCapacidadeDeMotos(entity.getCapacidadeDeMotos());
        dto.setEstabelecimentoId(entity.getEstabelecimento().getId());
        return dto;
    }

    private Doca mapToEntity(DocaDto dto) {
        Doca entity = new Doca();
        entity.setDescricao(dto.getDescricao());
        entity.setComprimento(dto.getComprimento());
        entity.setLargura(dto.getLargura());
        entity.setDataCriacao(dto.getDataCriacao());
        entity.setDataAtualizacao(dto.getDataAtualizacao());
       // entity.setPosicao(dto.getPosicao());
        entity.setCapacidadeDeMotos(dto.getCapacidadeDeMotos());
        entity.setEstabelecimento(estabelecimentoRepository.findById(dto.getEstabelecimentoId())
                .orElseThrow(() -> new ObjectNotFoundException("Estabelecimento não encontrado com id: " + dto.getEstabelecimentoId())));
        return entity;
    }

    public List<DocaDto> getDisponiveis(Long estabelecimentoId) {
        List<Doca> docas = docaRepository.findByEstabelecimentoId(estabelecimentoId);

        return docas.stream()
                .filter(doca -> {
                    Long motosAtivas = docaMotoRepository.countByDocaIdAndStatus(doca.getId(), StatusVinculoEnum.ATIVO);
                    return motosAtivas < doca.getCapacidadeDeMotos();
                })
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
}
