package br.com.mottu.service;

import br.com.mottu.dto.DocaMotoDto;
import br.com.mottu.dto.MotoDto;
import br.com.mottu.dto.RastreadorDto;
import br.com.mottu.enums.StatusVinculoEnum;
import br.com.mottu.exceptions.ObjectNotFoundException;
import br.com.mottu.model.DocaMoto;
import br.com.mottu.model.Moto;
import br.com.mottu.model.Rastreador;
import br.com.mottu.repository.DocaMotoRepository;
import br.com.mottu.repository.DocaRepository;
import br.com.mottu.repository.MotoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DocaMotoService {
    @Autowired
    private DocaMotoRepository docaMotoRepository;
    @Autowired
    private DocaRepository docaRepository;
    @Autowired
    private MotoRepository motoRepository;

    public List<DocaMotoDto> getAll() {
        return docaMotoRepository.findAll().stream().map(this::mapToDTO).toList();
    }

    public Optional<DocaMotoDto> getById(Long id) {
        return docaMotoRepository.findById(id).map(this::mapToDTO);
    }

    @Transactional
    public DocaMotoDto create(DocaMotoDto dto) {
        // Inativar todos os vínculos anteriores da moto
        docaMotoRepository.updateStatusVinculoToInativoByMotoId(dto.getMotoId());

        // Criar novo vínculo
        DocaMoto novaAssociacao = mapToEntity(dto);
        DocaMoto salva = docaMotoRepository.save(novaAssociacao);

        return mapToDTO(salva);
    }


    public DocaMotoDto update(Long id, DocaMotoDto dto) {
        if (!docaMotoRepository.existsById(id)) {
            throw new ObjectNotFoundException("Associação Doca-Moto não encontrada com id: " + id);
        }
        DocaMoto entity = mapToEntity(dto);
        entity.setId(id);
        return mapToDTO(docaMotoRepository.save(entity));
    }

    public void delete(Long id) {
        docaMotoRepository.deleteById(id);
    }
    public List<MotoDto> getMotosByDoca(Long docaId) {
        List<DocaMoto> docaMotos = docaMotoRepository.findByDocaIdAndStatus(docaId, StatusVinculoEnum.ATIVO);

        return docaMotos.stream()
                .map(DocaMoto::getMoto)
                .distinct()
                .map(this::mapToDTOCompleto)
                .collect(Collectors.toList());
    }
    private DocaMotoDto mapToDTO(DocaMoto entity) {
        DocaMotoDto dto = new DocaMotoDto();
        dto.setId(entity.getId());
        dto.setDocaId(entity.getDoca().getId());
        dto.setMotoId(entity.getMoto().getId());
        dto.setStatus(entity.getStatus());
        dto.setSituacao(entity.getSituacao());
        dto.setDataCriacao(entity.getDataCriacao());
        dto.setDataAtualizacao(entity.getDataAtualizacao());
        return dto;
    }
    private MotoDto mapToDTOCompleto(Moto moto) {
        MotoDto dto = new MotoDto();
        dto.setId(moto.getId());
        dto.setPlaca(moto.getPlaca());
        dto.setModelo(moto.getModelo());
        dto.setAno(moto.getAno());
        dto.setStatus(moto.getStatus());
        dto.setQuilometragem(moto.getQuilometragem());
        dto.setEstabelecimentoId(moto.getEstabelecimento().getId());
        dto.setDataCriacao(moto.getDataCriacao());
        dto.setDataAtualizacao(moto.getDataAtualizacao());
        if (moto.getRastreador() != null) {
            Rastreador rastreador = moto.getRastreador();
            RastreadorDto rastreadorDto = new RastreadorDto();
            rastreadorDto.setId(rastreador.getId());
            rastreadorDto.setImei(rastreador.getImei());
            rastreadorDto.setModelo(rastreador.getModelo());
            rastreadorDto.setLatitude(rastreador.getLatitude());
            rastreadorDto.setLongitude(rastreador.getLongitude());
            dto.setRastreador(rastreadorDto);
        }

        return dto;
    }

    private DocaMoto mapToEntity(DocaMotoDto dto) {
        DocaMoto entity = new DocaMoto();
        entity.setDoca(docaRepository.findById(dto.getDocaId())
                .orElseThrow(() -> new ObjectNotFoundException("Doca não encontrada")));
        entity.setMoto(motoRepository.findById(dto.getMotoId())
                .orElseThrow(() -> new ObjectNotFoundException("Moto não encontrada")));
        entity.setStatus(dto.getStatus());
        entity.setSituacao(dto.getSituacao());
        entity.setDataAtualizacao(dto.getDataAtualizacao());
        entity.setDataCriacao(dto.getDataCriacao());
        return entity;
    }
}
