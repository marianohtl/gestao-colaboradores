package br.com.gestao_colaboradores_api;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TesteDbRepository extends JpaRepository<TesteDb, Long> {

    @Query(value = "SELECT teste FROM testedb", nativeQuery = true)
    List<String> findAllTesteValues();
}