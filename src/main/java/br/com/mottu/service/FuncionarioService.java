package br.com.mottu.service;

import br.com.mottu.dto.EstabelecimentoDto;
import br.com.mottu.dto.FuncionarioDto;
import br.com.mottu.exceptions.ObjectNotFoundException;
import br.com.mottu.model.Estabelecimento;
import br.com.mottu.model.Funcionario;
import br.com.mottu.repository.EstabelecimentoRepository;
import br.com.mottu.repository.FuncionarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@RequiredArgsConstructor
@Service
public class FuncionarioService {
    @Autowired
    private FuncionarioRepository funcionarioRepository;
    @Autowired
    private EstabelecimentoRepository estabelecimentoRepository;

    public List<FuncionarioDto> getAll() {
        return funcionarioRepository.findAll()
                .stream().map(this::mapToDTO).toList();
    }

    public Optional<FuncionarioDto> getById(Long id) {
        return funcionarioRepository.findById(id)
                .map(this::mapToDTO);
    }

    public List<FuncionarioDto> getByEstabelecimento(Long estabelecimentoId) {
        return funcionarioRepository.findByEstabelecimentoId(estabelecimentoId)
                .stream().map(this::mapToDTO).toList();
    }

    public FuncionarioDto create(FuncionarioDto dto) {
        Funcionario entity = mapToEntity(dto);
        return mapToDTO(funcionarioRepository.save(entity));
    }

    public FuncionarioDto update(Long id, FuncionarioDto dto) {
        if (!funcionarioRepository.existsById(id)) {
            throw new ObjectNotFoundException("Funcionário não encontrado com id: " + id);
        }
        Funcionario entity = mapToEntity(dto);
        entity.setId(id);
        return mapToDTO(funcionarioRepository.save(entity));
    }

    public void delete(Long id) {
        funcionarioRepository.deleteById(id);
    }

    private FuncionarioDto mapToDTO(Funcionario entity) {
        FuncionarioDto dto = new FuncionarioDto();
        dto.setId(entity.getId());
        dto.setNome(entity.getNome());
        dto.setEmail(entity.getEmail());
        dto.setTelefone(entity.getTelefone());
        dto.setNascimento(entity.getNascimento());
        dto.setCpf(entity.getCpf());
        dto.setDataCriacao(entity.getDataCriacao());
        dto.setDataAtualizacao(entity.getDataAtualizacao());
       // dto.setFuncao(entity.getFuncao());
        dto.setEstabelecimentoId(entity.getEstabelecimento().getId());
        return dto;
    }

    private Funcionario mapToEntity(FuncionarioDto dto) {
        Funcionario entity = new Funcionario();
        entity.setNome(dto.getNome());
        entity.setEmail(dto.getEmail());
        entity.setTelefone(dto.getTelefone());
        entity.setNascimento(dto.getNascimento());
        entity.setCpf(dto.getCpf());
        entity.setDataCriacao(dto.getDataCriacao());
        entity.setDataAtualizacao(dto.getDataAtualizacao());
     //   entity.setFuncao(dto.getFuncao());
        entity.setEstabelecimento(estabelecimentoRepository.findById(dto.getEstabelecimentoId())
                .orElseThrow(() -> new ObjectNotFoundException("Estabelecimento não encontrado com id: " + dto.getEstabelecimentoId())));
        return entity;
    }
}
