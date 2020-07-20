package br.com.ais.filmes.controller;

import br.com.ais.filmes.dto.FilmeInputDto;
import br.com.ais.filmes.model.Filme;
import br.com.ais.filmes.repository.FilmeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;;

@WebMvcTest(FilmeController.class)
public class FilmeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FilmeRepository filmeRepository;

    @Test
    public void deveEncontrarUmFilmeComSucesso() throws Exception {

        when(filmeRepository.findById(1L)).thenReturn(Optional.of(new Filme("007")));

        this.mockMvc.perform(get("/filme/1")).andDo(print()).andExpect(status().isOk());

    }

    @Test
    public void deveRetornar404FilmeNaoEncontrado() throws Exception {

        when(filmeRepository.findById(1L)).thenReturn(Optional.of(new Filme("007")));

        this.mockMvc.perform(get("/filme/2")).andDo(print()).andExpect(status().isNotFound());

    }

    @Test
    public void deveInserirUmNovoFilme() throws Exception {

        var filme = new FilmeInputDto();
        filme.setNome("João e Maria");

        when(filmeRepository.save(filme.converter())).thenReturn(filme.converter());

        this.mockMvc.perform(post("/filme")
                .content(asJsonString(filme))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

    }

    @Test
    public void deveAtualizarUmFilme() throws Exception {

        var entity = new Filme(1L,"007 Tomorrow never Die");

        when(filmeRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(filmeRepository.save(entity)).thenReturn(entity);

        var filme = new FilmeInputDto();
        filme.setNome("Transformers");

        this.mockMvc.perform(put("/filme/1")
                .content(asJsonString(filme))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Transformers"));


    }

    @Test
    public void naoDeveAtualizarUmFilme() throws Exception {

        var entity = new Filme(1L,"007 Tomorrow never Die");

        when(filmeRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(filmeRepository.save(entity)).thenReturn(entity);

        var filme = new FilmeInputDto();
        filme.setNome("Transformers");

        this.mockMvc.perform(put("/filme/2")
                .content(asJsonString(filme))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());


    }

    @Test
    public void deveExcluirUmFilme() throws Exception {

        var entity = new Filme(1L,"007 Tomorrow never Die");
        when(filmeRepository.findById(1L)).thenReturn(Optional.of(entity));

        this.mockMvc.perform(delete("/filme/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void naoDeveExcluirUmFilme() throws Exception {

        var entity = new Filme(2L,"007 Tomorrow never Die");
        when(filmeRepository.findById(2L)).thenReturn(Optional.of(entity));

        this.mockMvc.perform(delete("/filme/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
