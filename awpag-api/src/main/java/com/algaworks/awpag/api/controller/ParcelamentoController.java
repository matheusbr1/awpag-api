package com.algaworks.awpag.api.controller;

import com.algaworks.awpag.api.model.ParcelamentoOutput;
import com.algaworks.awpag.domain.Exception.NegocioException;
import com.algaworks.awpag.domain.model.Parcelamento;
import com.algaworks.awpag.domain.repository.ParcelamentoRepository;
import com.algaworks.awpag.domain.service.ParcelamentoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/parcelamentos")
public class ParcelamentoController {
    private final ParcelamentoRepository parcelamentoRepository;
    private final ParcelamentoService parcelamentoService;

    @GetMapping
    public List<Parcelamento> listar () {
        return parcelamentoRepository.findAll();
    }

    @GetMapping("/{parcelamentoId}")
    public ResponseEntity<ParcelamentoOutput> buscar (@PathVariable Long parcelamentoId) {
        return parcelamentoRepository.findById(parcelamentoId)
                .map(parcelamento -> {
                    var parcelamentoOutput = new ParcelamentoOutput();
                    parcelamentoOutput.setId(parcelamento.getId());
                    parcelamentoOutput.setNomeCliente(parcelamento.getCliente().getNome());
                    parcelamentoOutput.setDescricao(parcelamento.getDescricao());
                    parcelamentoOutput.setValorTotal(parcelamento.getValorTotal());
                    parcelamentoOutput.setParcelas(parcelamento.getQuantidadeParcelas());
                    parcelamentoOutput.setDataCriacao(parcelamento.getDataCriacao());
                    return ResponseEntity.ok(parcelamentoOutput);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public  Parcelamento cadastrar (@Valid  @RequestBody Parcelamento parcelamento) {
        return parcelamentoService.cadastrar(parcelamento);
    }
}
