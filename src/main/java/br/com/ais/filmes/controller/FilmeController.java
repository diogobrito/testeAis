package br.com.ais.filmes.controller;

import br.com.ais.filmes.dto.FilmeInputDto;
import br.com.ais.filmes.dto.FilmeOutputDto;
import br.com.ais.filmes.model.Filme;
import br.com.ais.filmes.repository.FilmeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/filme")
public class FilmeController {

    private final FilmeRepository filmeRepository;

    public FilmeController(FilmeRepository filmeRepository) {
        this.filmeRepository = filmeRepository;
    }

    @GetMapping
    public List<FilmeOutputDto> findAll() {
        var filmes = filmeRepository.findAll();
        return filmes
                .stream()
                .map(FilmeOutputDto::converter)
                .collect(Collectors.toList());

    }

    @GetMapping("/{id}")
    public ResponseEntity<FilmeOutputDto> findById(@PathVariable("id") Long id){
        return filmeRepository.findById(id)
                .map(filme -> ResponseEntity.ok().body(FilmeOutputDto.converter(filme)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody @Valid FilmeInputDto input) {
        Filme filme = input.converter();
        filmeRepository.save(filme);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FilmeOutputDto> update(@PathVariable("id") Long id, @RequestBody FilmeInputDto filmeInputDto) throws Exception {
        return filmeRepository.findById(id)
                .map(filme -> {
                    filme.setNome(filmeInputDto.getNome());
                    Filme filmeSave = filmeRepository.save(filme);
                    return ResponseEntity.ok().body(FilmeOutputDto.converter(filmeSave));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        return filmeRepository.findById(id)
                .map(filme -> {
                    filmeRepository.delete(filme);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

}
