package br.com.ais.filmes.model;

import javax.persistence.*;

@Entity
@Table(name = "filme")
public class Filme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_filme")
    private Long id;

    @Column(name="nome")
    private String nome;

    public Filme() {}

    public Filme(String nome) {
        this.nome = nome;
    }

    public Filme(Long id,String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
