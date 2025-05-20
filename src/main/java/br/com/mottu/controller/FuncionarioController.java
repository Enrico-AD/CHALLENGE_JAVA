package br.com.mottu.controller;
import br.com.mottu.dto.FuncionarioDto;
import br.com.mottu.service.FuncionarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/funcionarios")
@RequiredArgsConstructor
public class FuncionarioController {
    @Autowired
    private FuncionarioService funcionarioService;

    @GetMapping
    public List<FuncionarioDto> getAllFuncionario() {
        return funcionarioService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FuncionarioDto> getById(@PathVariable Long id) {
        return funcionarioService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<FuncionarioDto> create(@RequestBody @Valid FuncionarioDto dto) {
        return new ResponseEntity<>(funcionarioService.create(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FuncionarioDto> update(@PathVariable Long id, @RequestBody @Valid FuncionarioDto dto) {
        FuncionarioDto updated = funcionarioService.update(id, dto);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        funcionarioService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/estabelecimentos/{id}/funcionarios")
    public ResponseEntity<List<FuncionarioDto>> getByEstabelecimento(@PathVariable Long id) {
        return ResponseEntity.ok(funcionarioService.getByEstabelecimento(id));
    }
}
