package com.algaworks.awpag.domain.service;

import com.algaworks.awpag.domain.Exception.NegocioException;
import com.algaworks.awpag.domain.model.Cliente;
import com.algaworks.awpag.domain.repository.ClienteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CadastroClienteService {
    private final ClienteRepository clienteRepository;

    @Transactional
    public Cliente salvar (Cliente cliente) {
        boolean emailEmUso = clienteRepository.findByEmail(cliente.getEmail())
                .filter(c -> !c.equals(cliente))
                .isPresent();

        if (emailEmUso) {
            throw new NegocioException("Já existe um cliente cadastrado com esse e-mail!");
        }

        return clienteRepository.save(cliente);
    }

    public Cliente buscar (Long clienteId) {
        return  clienteRepository.findById(clienteId)
                .orElseThrow(() -> new NegocioException("Cliente não encontrado"));
    }

    @Transactional
    public void excluir (Long clienteId) {
        clienteRepository.deleteById(clienteId);
    }
}
