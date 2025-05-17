package br.com.mottu.controller;
import br.com.mottu.dto.RastreadorDto;
import br.com.mottu.service.RastreadorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/rastreador")
@RequiredArgsConstructor
public class RastreadorController {
    @Autowired
    private RastreadorService rastreadorService;

    @GetMapping
    public List<RastreadorDto> getAll() {
        return rastreadorService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RastreadorDto> getById(@PathVariable Long id) {
        return rastreadorService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<RastreadorDto> create(@RequestBody @Valid RastreadorDto dto) {
        return new ResponseEntity<>(rastreadorService.create(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RastreadorDto> update(@PathVariable Long id, @RequestBody @Valid RastreadorDto dto) {
        return ResponseEntity.ok(rastreadorService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        rastreadorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

