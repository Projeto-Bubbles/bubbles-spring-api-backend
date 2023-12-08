package bubbles.springapibackend.domain.user;

import bubbles.springapibackend.domain.address.Address;
import bubbles.springapibackend.domain.post.Post;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "tb_user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String email;
    private String cpf;
    private String username;
    private String password;

    @OneToOne
    private Address address;

    @OneToMany
    private List<Post> posts;
}
