package br.com.gestao_colaboradores_api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TesteDbController {
    @Autowired
    private TesteDbRepository testeDbRepository;

    @GetMapping("/api/testedb")
    public List<String> listarTesteDb() {
        return testeDbRepository.findAllTesteValues();
    }
}
