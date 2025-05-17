package br.com.mottu.controller;

import br.com.mottu.dto.EstabelecimentoDto;
import br.com.mottu.service.EstabelecimentoService;
import br.com.mottu.service.FuncionarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/estabelecimentos")
@RequiredArgsConstructor
public class EstabelecimentoController {
    @Autowired
    private EstabelecimentoService estabelecimentoService;

    @GetMapping
    public ResponseEntity<List<EstabelecimentoDto>> getAllEstabelecimento() {
        return ResponseEntity.ok(estabelecimentoService.getAllEstabelecimento());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstabelecimentoDto> getById(@PathVariable Long id) {
        return estabelecimentoService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EstabelecimentoDto> create(@RequestBody @Valid EstabelecimentoDto dto) {
        EstabelecimentoDto created = estabelecimentoService.create(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstabelecimentoDto> update(@PathVariable Long id, @RequestBody @Valid EstabelecimentoDto dto) {
        EstabelecimentoDto updated = estabelecimentoService.update(id, dto);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        estabelecimentoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
