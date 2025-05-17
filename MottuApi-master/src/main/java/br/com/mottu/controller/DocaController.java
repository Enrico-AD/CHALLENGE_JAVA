package br.com.mottu.controller;

import br.com.mottu.dto.DocaDto;
import br.com.mottu.service.DocaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/docas")
@RequiredArgsConstructor
public class DocaController {
    @Autowired
    private DocaService docaService;

    @GetMapping
    public ResponseEntity<List<DocaDto>> getAll() {

        return ResponseEntity.ok(docaService.getAll());

    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<DocaDto>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(docaService.getById(id));
    }

    @PostMapping
    public ResponseEntity<DocaDto> create(@RequestBody @Valid DocaDto dto) {
        return new ResponseEntity<>(docaService.create(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DocaDto> update(@PathVariable Long id, @RequestBody @Valid DocaDto dto) {
        DocaDto updated = docaService.update(id, dto);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        docaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/estabelecimentos/{id}/docas")
    public ResponseEntity<List<DocaDto>> getByEstabelecimento(@PathVariable Long id) {
        return ResponseEntity.ok(docaService.getByEstabelecimento(id));
    }

    @GetMapping("/disponiveis")
    public ResponseEntity<List<DocaDto>> getDisponiveis(@RequestParam Long estabelecimentoId) {
        return ResponseEntity.ok(docaService.getDisponiveis(estabelecimentoId));
    }
}
