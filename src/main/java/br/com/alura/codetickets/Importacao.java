package br.com.alura.codetickets;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table
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
  private LocalDateTime horaImportacao;
}
