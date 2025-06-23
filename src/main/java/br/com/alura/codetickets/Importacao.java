package br.com.alura.codetickets;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Importacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cpf;
    private String cliente;
    private LocalDate nascimento;
    private String evento;
    private LocalDate data;
    private String tipoIgresso;
    private Double valor;
    private Double taxaAdministrativa;
    private LocalDateTime horaImportacao;
}
