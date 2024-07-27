package com.gabrielravanhan.controller;

import com.gabrielravanhan.controller.dto.TemporadaJogadorDto;
import com.gabrielravanhan.domain.model.TemporadaJogador;
import com.gabrielravanhan.service.TemporadaJogadorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RequestMapping("/temporadas-jogadores")
@RestController
public class TemporadaJogadorController {

    @Autowired
    private TemporadaJogadorService temporadaJogadorService;

    @GetMapping
    @Operation(
            summary = "Buscar todas as temporadas dos jogadores",
            description = "Retorna uma lista com todas as temporadas dos jogadores"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso")
    })
    public ResponseEntity<List<TemporadaJogadorDto>> bucarTodos() {
        List<TemporadaJogadorDto> temporadasJogadoresDto = this.temporadaJogadorService.buscarTodos().stream().map(TemporadaJogadorDto::new).toList();
        return ResponseEntity.ok(temporadasJogadoresDto);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar temporada de um jogador pelo ID",
            description = "Retorna uma temporada de um jogador específico, de acordo com o ID informado"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso"),
            @ApiResponse(responseCode = "404", description = "Temporada do jogador não encontrada")
    })
    public ResponseEntity<TemporadaJogadorDto> buscarPeloId(@PathVariable Long id) {
        TemporadaJogador temporadaJogador = this.temporadaJogadorService.buscarPeloId(id);
        return ResponseEntity.ok(new TemporadaJogadorDto(temporadaJogador));
    }

    @PostMapping
    @Operation(
            summary = "Criar informações da temporada de um jogador",
            description = "Insere informações da temporada de um jogador no banco de dados"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Temporada do jogador criada"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "422", description = "Dados inválidos")
    })
    public ResponseEntity<TemporadaJogadorDto> criar(@RequestBody TemporadaJogadorDto temporadaJogadorDto) {
        TemporadaJogador temporadaJogador = temporadaJogadorService.criar(temporadaJogadorDto.converterParaModel());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(temporadaJogador.getId())
                .toUri();
        return ResponseEntity.created(location).body(new TemporadaJogadorDto(temporadaJogador));
    }

    @PutMapping
    @Operation(
            summary = "Atualizar informações da temporada de um jogador",
            description = "Atualiza informações da temporada de um jogador"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Temporada do jogador não atualizada"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Temporada do jogador não encontrada"),
            @ApiResponse(responseCode = "422", description = "Dados inválidos")
    })
    public ResponseEntity<TemporadaJogadorDto> atualizar(@RequestParam Long id, @RequestBody TemporadaJogadorDto temporadaJogadorDto) {
        TemporadaJogador dbTemporadaJogador = temporadaJogadorService.atualizar(id, temporadaJogadorDto.converterParaModel());
        return ResponseEntity.ok(new TemporadaJogadorDto(dbTemporadaJogador));
    }

    @DeleteMapping
    @Operation(
            summary = "Deletar um jogador",
            description = "Deleta um jogador do banco de dados"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Temporada do jogador deletada"),
            @ApiResponse(responseCode = "404", description = "Temporada do jogador não encontrada")
    })
    public ResponseEntity<Void> deletar(@RequestParam Long id) {
        temporadaJogadorService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
