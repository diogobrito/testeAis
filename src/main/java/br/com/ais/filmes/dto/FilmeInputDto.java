package br.com.ais.filmes.dto;

import br.com.ais.filmes.model.Filme;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class FilmeInputDto {

    @NotBlank
    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Filme converter() {
        return new Filme(this.nome);
    }
}
