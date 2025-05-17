package br.com.mottu.controller;

import br.com.mottu.dto.OcupacaoDocaDto;
import br.com.mottu.dto.RelatorioMotosEstabelecimentoDto;
import br.com.mottu.dto.RelatorioOcupacaoDocaDto;
import br.com.mottu.service.RelatorioService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/relatorio")
@RequiredArgsConstructor
public class RelatorioController {
    @Autowired
    private RelatorioService relatorioService;

    @GetMapping("/motos-por-estabelecimento")
    public ResponseEntity<List<RelatorioMotosEstabelecimentoDto>> getRelatorioMotos() {
        return ResponseEntity.ok(relatorioService.getRelatorioMotosPorEstabelecimento());
    }


    @GetMapping("/estabelecimentos/{id}/docas/ocupacao")
    public ResponseEntity<List<RelatorioOcupacaoDocaDto>> getOcupacaoDocas(@PathVariable Long id) {
        return ResponseEntity.ok(relatorioService.getOcupacaoDocasPorEstabelecimento(id));
    }
    @GetMapping("/docas/{id}/ocupacao")
    public ResponseEntity<RelatorioOcupacaoDocaDto> getOcupacaoPorDoca(@PathVariable Long id) {
        return ResponseEntity.ok(relatorioService.getOcupacaoPorDoca(id));
    }

}
