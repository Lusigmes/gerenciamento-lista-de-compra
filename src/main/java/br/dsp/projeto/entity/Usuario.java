package br.dsp.projeto.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import br.dsp.projeto.entity.enums.Sexo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.springframework.data.mongodb.core.mapping.Document;

@NamedQueries({
        @NamedQuery(name = "Usuario.usuarioPorNomeUsuario", query = "select u from Usuario u where u.nomeUsuario = :nomeUsuario")
})

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usuarios")
@Document(collection = "usuarios")
public class Usuario {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotBlank(message = "O nome completo é obrigatório")
    @Size(min = 3, max = 100, message = "O nome completo deve ter entre 3 e 100 caracteres")
    private String nomeCompleto;

    @NotBlank(message = "O nome de usuário é obrigatório")
    @Size(min = 3, max = 50, message = "O nome de usuário deve ter entre 3 e 50 caracteres")
    @Column(unique = true)
    private String nomeUsuario;

    @Email(message = "O e-mail deve ser válido")
    @Column(unique = true)
    private String email;

    @NotNull(message = "O sexo é obrigatório")
    @Enumerated(EnumType.STRING)
    private Sexo sexo;

    @NotNull(message = "A data de nascimento é obrigatória")
    private LocalDate dataNascimento;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<ListaDeCompras> listasDeCompras;

    @Override
    public String toString() {
        return "Usuario [id=" + id + ", nomeCompleto=" + nomeCompleto + ", nomeUsuario=" + nomeUsuario
                + ", email=" + email + ", sexo=" + sexo.getLabel() + ", dataNascimento="
                + dataNascimento.format(formatter) + "]";
    }

    public String toStringCompleto() {
        return "Usuario [id=" + id + ", nomeCompleto=" + nomeCompleto + ", nomeUsuario=" + nomeUsuario
                + ", email=" + email + ", sexo=" + sexo.getLabel() + ", dataNascimento="
                + dataNascimento.format(formatter) + "]";
    }
}
