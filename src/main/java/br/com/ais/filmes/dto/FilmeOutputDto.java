package br.com.ais.filmes.dto;

import br.com.ais.filmes.model.Filme;

public class FilmeOutputDto {

    private Long id;
    private String nome;

    public static FilmeOutputDto converter(Filme f) {
        var filme = new FilmeOutputDto();
        filme.setId(f.getId());
        filme.setNome(f.getNome());
        return filme;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
