package br.com.mottu.service;

import br.com.mottu.dto.RastreadorDto;
import br.com.mottu.exceptions.ObjectNotFoundException;
import br.com.mottu.model.Rastreador;
import br.com.mottu.repository.RastreadorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RastreadorService {

    private final RastreadorRepository rastreadorRepository;

    public List<RastreadorDto> getAll() {
        return rastreadorRepository.findAll().stream().map(this::mapToDTO).toList();
    }

    public Optional<RastreadorDto> getById(Long id) {
        return rastreadorRepository.findById(id).map(this::mapToDTO);
    }

    public RastreadorDto create(RastreadorDto dto) {
        return mapToDTO(rastreadorRepository.save(mapToEntity(dto)));
    }

    public RastreadorDto update(Long id, RastreadorDto dto) {
        if (!rastreadorRepository.existsById(id)) {
            throw new ObjectNotFoundException("Rastreador não encontrado com id: " + id);
        }
        Rastreador entity = mapToEntity(dto);
        entity.setId(id);
        return mapToDTO(rastreadorRepository.save(entity));
    }

    public void delete(Long id) {
        rastreadorRepository.deleteById(id);
    }

    public RastreadorDto mapToDTO(Rastreador r) {
        RastreadorDto dto = new RastreadorDto();
        dto.setId(r.getId());
        dto.setImei(r.getImei());
        dto.setModelo(r.getModelo());
        dto.setLatitude(r.getLatitude());
        dto.setLongitude(r.getLongitude());
        dto.setDataCriacao(r.getDataCriacao());
        dto.setDataAtualizacao(r.getDataAtualizacao());
        return dto;
    }

    private Rastreador mapToEntity(RastreadorDto dto) {
        Rastreador r = new Rastreador();
        r.setImei(dto.getImei());
        r.setModelo(dto.getModelo());
        r.setLatitude(dto.getLatitude());
        r.setLongitude(dto.getLongitude());
        r.setDataCriacao(dto.getDataCriacao());
        r.setDataAtualizacao(dto.getDataAtualizacao());
        return r;
    }
}
