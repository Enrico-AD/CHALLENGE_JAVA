package br.com.mottu.service;

import br.com.mottu.dto.EstabelecimentoDto;
import br.com.mottu.model.Estabelecimento;
import br.com.mottu.repository.EstabelecimentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class EstabelecimentoService {

    @Autowired
    private EstabelecimentoRepository estabelecimentoRepository;

    public List<EstabelecimentoDto> getAllEstabelecimento() {
        return estabelecimentoRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public Optional<EstabelecimentoDto> getById(Long id) {
        return estabelecimentoRepository.findById(id).map(this::mapToDto);
    }

    public EstabelecimentoDto create(EstabelecimentoDto dto) {
        Estabelecimento entity = mapToEntity(dto);
        return mapToDto(estabelecimentoRepository.save(entity));
    }

    public EstabelecimentoDto update(Long id, EstabelecimentoDto dto) {
        if (estabelecimentoRepository.existsById(id)) {
            Estabelecimento entity = mapToEntity(dto);
            entity.setId(id);
            return mapToDto(estabelecimentoRepository.save(entity));
        }
        return null;
    }

    public void delete(Long id) {
        estabelecimentoRepository.deleteById(id);
    }

    private EstabelecimentoDto mapToDto(Estabelecimento entity) {
        EstabelecimentoDto dto = new EstabelecimentoDto();
        dto.setId(entity.getId());
        dto.setCnpj(entity.getCnpj());
        dto.setCidade(entity.getCidade());
        dto.setEstado(entity.getEstado());
        dto.setPais(entity.getPais());
        dto.setDataCriacao(entity.getDataCriacao());
        dto.setDataAtualizacao(entity.getDataAtualizacao());
        if (entity.getFilial() != null)
            dto.setFilialId(entity.getFilial().getId());
        return dto;
    }

    private Estabelecimento mapToEntity(EstabelecimentoDto dto) {
        Estabelecimento entity = new Estabelecimento();
        entity.setCnpj(dto.getCnpj());
        entity.setCidade(dto.getCidade());
        entity.setEstado(dto.getEstado());
        entity.setPais(dto.getPais());
        entity.setDataCriacao(dto.getDataCriacao());
        entity.setDataAtualizacao(dto.getDataAtualizacao());
        if (dto.getFilialId() != null) {
            Estabelecimento filial = new Estabelecimento();
            filial.setId(dto.getFilialId());
            entity.setFilial(filial);
        }
        return entity;
    }
}
