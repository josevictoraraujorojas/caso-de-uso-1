package org.example.casodeuso1.service;

import org.example.casodeuso1.dto.AparelhoCreateDTO;
import org.example.casodeuso1.dto.AparelhoResponseDTO;
import org.example.casodeuso1.model.Aparelho;
import org.example.casodeuso1.repository.AparelhoRepository;
import org.example.casodeuso1.util.DataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AparelhoService {
    private final AparelhoRepository aparelhoRepository;

    @Autowired
    public AparelhoService(AparelhoRepository aparelhoRepository) {
        this.aparelhoRepository = aparelhoRepository;
    }

    public AparelhoResponseDTO salvar(AparelhoCreateDTO aparelhoCreateDTO) {
        if (aparelhoCreateDTO == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Dados do Aparelho inválidos"
            );
        }

        Aparelho aparelho = DataMapper.parseObject(aparelhoCreateDTO, Aparelho.class);
        return DataMapper.parseObject(aparelhoRepository.save(aparelho),AparelhoResponseDTO.class);
    }

    public AparelhoResponseDTO editar(Long aparelhoId,AparelhoCreateDTO aparelhoCreateDTO) {
        Aparelho aparelho = aparelhoRepository.findById(aparelhoId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Aparelho não encontrado"
                ));
        aparelho.setNome(aparelhoCreateDTO.getNome());
        aparelho.setPotenciaNominal(aparelhoCreateDTO.getPotenciaNominal());
        return DataMapper.parseObject(aparelhoRepository.save(aparelho),AparelhoResponseDTO.class);
    }

    public AparelhoResponseDTO buscarPorId(Long aparelhoId) {
        Aparelho aparelho = aparelhoRepository.findById(aparelhoId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Aparelho não encontrado"
                ));

        return DataMapper.parseObject(aparelho,AparelhoResponseDTO.class);
    }

    public List<AparelhoResponseDTO> listar() {
        List<Aparelho> aparelhos = aparelhoRepository.findAll();

        if (aparelhos.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Nenhum aparelho encontrado");
        }

        return DataMapper.parseListObjects(aparelhos,AparelhoResponseDTO.class);
    }

    public void excluir(Long aparelhoId) {
        Aparelho aparelho = aparelhoRepository.findById(aparelhoId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Aparelho não encontrado"
                ));

        aparelhoRepository.delete(aparelho);
    }
}
