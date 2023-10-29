package br.com.fiap.cineadd.filme;

import br.com.fiap.cineadd.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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

    @NotBlank (message = "{filme.titulo.blank}")
    String titulo;

    @Size(min = 10, message = "{filme.sinopse.size}")
    String sinopse;

    @Min(1) @Max(100)
    Integer score;

    @Min(0) @Max(100)
    Integer status;

    @ManyToOne
    User user;
}
