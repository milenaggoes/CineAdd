package br.com.fiap.cineadd.filme;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data

public class Filme {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotBlank
    String titulo;

    @Size(min = 10)
    String sinopse;

    @Min(1) @Max(5)
    Integer estrelas;

    @Min(0) @Max(100)
    Integer status;
}