package br.com.mottu.controller;
import br.com.mottu.dto.MotoDto;
import br.com.mottu.enums.SituacaoMotoEnum;
import br.com.mottu.service.MotoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/motos")
@RequiredArgsConstructor
public class MotoController {
    @Autowired
    private MotoService motoService;

    @GetMapping
    public ResponseEntity<List<MotoDto>> getAll() {
        return ResponseEntity.ok(motoService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MotoDto> getById(@PathVariable Long id) {
        return motoService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MotoDto> create(@RequestBody @Valid MotoDto dto) {
        System.out.println("criar moto" + dto);
        return new ResponseEntity<>(motoService.create(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MotoDto> update(@PathVariable Long id, @RequestBody @Valid MotoDto dto) {
        MotoDto updated = motoService.update(id, dto);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        motoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/estabelecimentos/{id}/motos")
    public ResponseEntity<List<MotoDto>> getByEstabelecimento(@PathVariable Long id) {
        return ResponseEntity.ok(motoService.getByEstabelecimento(id));
    }

    @GetMapping("/docas/{id}/motos")
    public ResponseEntity<List<MotoDto>> getByDoca(@PathVariable Long id) {
        return ResponseEntity.ok(motoService.getByDoca(id));
    }
// GET /motos/filtrar?situacao=DISPONIVEL&estabelecimentoId=1&docaId=2
@GetMapping("/filtrar")
public ResponseEntity<List<MotoDto>> filtrarMotos(
        @RequestParam(required = false) SituacaoMotoEnum situacao,
        @RequestParam(required = false) Long estabelecimentoId,
        @RequestParam(required = false) Long docaId
) {
    return ResponseEntity.ok(motoService.filtrarMotos(situacao, estabelecimentoId, docaId));
}

}
