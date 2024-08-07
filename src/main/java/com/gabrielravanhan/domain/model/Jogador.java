package com.gabrielravanhan.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "jogadores")
public class Jogador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private LocalDate dataNascimento;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "posicoes_jogadores",
            joinColumns = @JoinColumn(name = "jogador_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "posicao_id", nullable = false)
    )
    private List<Posicao> posicoes;
}
