package com.algaworks.awpag.domain.service;

import com.algaworks.awpag.domain.Exception.NegocioException;
import com.algaworks.awpag.domain.model.Cliente;
import com.algaworks.awpag.domain.model.Parcelamento;
import com.algaworks.awpag.domain.repository.ClienteRepository;
import com.algaworks.awpag.domain.repository.ParcelamentoRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ParcelamentoService {
    private final ParcelamentoRepository parcelamentoRepository;
    private final CadastroClienteService cadastroClienteService;


    @Transactional
    public Parcelamento cadastrar (Parcelamento novoParcelamento) {
        if(novoParcelamento.getId() != null) {
            throw new NegocioException(("Parcelamento a ser criado não deve possuir um código"));
        }

        Cliente cliente = cadastroClienteService.buscar(novoParcelamento.getCliente().getId());

        novoParcelamento.setCliente(cliente);
        novoParcelamento.setDataCriacao(OffsetDateTime.now());

        return parcelamentoRepository.save(novoParcelamento);
    }
}
