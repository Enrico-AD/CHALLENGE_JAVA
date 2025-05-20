package br.com.mottu.controller;

import br.com.mottu.dto.DocaDto;
import br.com.mottu.dto.DocaMotoDto;
import br.com.mottu.dto.MotoDto;
import br.com.mottu.service.DocaMotoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/doca-moto")
@RequiredArgsConstructor
public class DocaMotoController {
    @Autowired
    private DocaMotoService docaMotoService;

    @GetMapping
    public ResponseEntity<List<DocaMotoDto>> getAll() {
        return ResponseEntity.ok(docaMotoService.getAll());
    }

    @PostMapping
    public ResponseEntity<DocaMotoDto> create(@RequestBody @Valid DocaMotoDto dto) {
        return new ResponseEntity<>(docaMotoService.create(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DocaMotoDto> update(@PathVariable Long id, @RequestBody @Valid DocaMotoDto dto) {
        return ResponseEntity.ok(docaMotoService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        docaMotoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/docas/{id}/motos")
    public ResponseEntity<List<MotoDto>> getMotosByDoca(@PathVariable Long id) {
        return ResponseEntity.ok(docaMotoService.getMotosByDoca(id));
    }
}

