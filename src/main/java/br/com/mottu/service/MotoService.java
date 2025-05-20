package br.com.mottu.service;

import br.com.mottu.dto.MotoDto;
import br.com.mottu.dto.RastreadorDto;
import br.com.mottu.enums.SituacaoMotoEnum;
import br.com.mottu.enums.StatusVinculoEnum;
import br.com.mottu.exceptions.ObjectNotFoundException;
import br.com.mottu.model.DocaMoto;
import br.com.mottu.model.Moto;
import br.com.mottu.model.Rastreador;
import br.com.mottu.repository.DocaMotoRepository;
import br.com.mottu.repository.EstabelecimentoRepository;
import br.com.mottu.repository.MotoRepository;
import br.com.mottu.repository.RastreadorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MotoService {
    @Autowired
    private MotoRepository motoRepository;
    @Autowired
    private RastreadorRepository rastreadorRepository;
    @Autowired
    private EstabelecimentoRepository estabelecimentoRepository;
    @Autowired
    private RastreadorService rastredorService;
    @Autowired
    private DocaMotoRepository docaMotoRepository;
    public List<MotoDto> getAll() {
        return motoRepository.findAll().stream().map(this::mapToDTO).toList();
    }

    public Optional<MotoDto> getById(Long id) {
        return motoRepository.findById(id).map(this::mapToDTO);
    }

    public MotoDto create(MotoDto dto) {
        Moto moto = mapToEntity(dto);
        System.out.println(moto);
        return mapToDTO(motoRepository.save(moto));
    }

    public MotoDto update(Long id, MotoDto dto) {
        if (!motoRepository.existsById(id)) {
            throw new ObjectNotFoundException("Moto não encontrada com id: " + id);
        }
        Moto moto = mapToEntity(dto);
        moto.setId(id);
        return mapToDTO(motoRepository.save(moto));
    }

    public void delete(Long id) {
        motoRepository.deleteById(id);
    }

    // Associa rastreador a uma moto já existente
    public MotoDto associarRastreador(Long motoId, Long rastreadorId) {
        Moto moto = motoRepository.findById(motoId)
                .orElseThrow(() -> new ObjectNotFoundException("Moto não encontrada com id: " + motoId));

        Rastreador rastreador = rastreadorRepository.findById(rastreadorId)
                .orElseThrow(() -> new ObjectNotFoundException("Rastreador não encontrado com id: " + rastreadorId));

        moto.setRastreador(rastreador);
        return mapToDTO(motoRepository.save(moto));
    }

    // Remove rastreador de uma moto
    public MotoDto removerRastreador(Long motoId) {
        Moto moto = motoRepository.findById(motoId)
                .orElseThrow(() -> new ObjectNotFoundException("Moto não encontrada com id: " + motoId));

        moto.setRastreador(null);
        return mapToDTO(motoRepository.save(moto));
    }
    public List<MotoDto> getByDoca(Long docaId) {
        List<DocaMoto> docaMotos = docaMotoRepository.findByDocaIdAndStatus(docaId, StatusVinculoEnum.ATIVO);
        return docaMotos.stream()
                .map(DocaMoto::getMoto)
                .distinct()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private MotoDto mapToDTO(Moto entity) {
        MotoDto dto = new MotoDto();
        dto.setId(entity.getId());
        dto.setModelo(entity.getModelo());

        dto.setAno(entity.getAno());
        dto.setPlaca(entity.getPlaca());

        dto.setStatus(entity.getStatus());
        dto.setEstabelecimentoId(entity.getEstabelecimento().getId());
        dto.setDataCriacao(entity.getDataCriacao());
        dto.setDataAtualizacao(entity.getDataAtualizacao());
        if (entity.getRastreador() != null) {
            dto.setRastreador(rastredorService.mapToDTO(entity.getRastreador()));
        }

        return dto;
    }

    private Moto mapToEntity(MotoDto dto) {
        Moto entity = new Moto();
        entity.setModelo(dto.getModelo());
        entity.setAno(dto.getAno());
        entity.setPlaca(dto.getPlaca());
        entity.setStatus(dto.getStatus());
        entity.setQuilometragem(dto.getQuilometragem());
        entity.setDataCriacao(dto.getDataCriacao());
        entity.setDataAtualizacao(dto.getDataAtualizacao());
        entity.setEstabelecimento(estabelecimentoRepository.findById(dto.getEstabelecimentoId())
                .orElseThrow(() -> new ObjectNotFoundException("Estabelecimento não encontrado com id: " + dto.getEstabelecimentoId())));

        if (dto.getRastreador() != null && dto.getRastreador().getId() != null) {
            entity.setRastreador(
                    rastreadorRepository.findById(dto.getRastreador().getId())
                            .orElseThrow(() -> new ObjectNotFoundException("Rastreador não encontrado com id: " + dto.getRastreador().getId()))
            );
        }

        return entity;
    }

    public List<MotoDto> getByEstabelecimento(Long id) {
        List<Moto> motos = motoRepository.findByEstabelecimentoId(id);
        return motos.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<MotoDto> filtrarMotos(SituacaoMotoEnum situacao, Long estabelecimentoId, Long docaId) {
        List<Moto> motos = motoRepository.findByFiltros(situacao, estabelecimentoId, docaId);
        return motos.stream()
                .map(this::mapToDTO)
                .toList();
    }


}
