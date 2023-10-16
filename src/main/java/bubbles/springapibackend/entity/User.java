package bubbles.springapibackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private Integer id;
    private String name;
    private String username;
    private String email;
    private String password;
    private String cpf;

    @OneToOne
    private Address address;
}
