package bubbles.springapibackend.domain.user;

import bubbles.springapibackend.domain.address.Address;
import bubbles.springapibackend.domain.comment.Comment;
import bubbles.springapibackend.domain.member.Member;
import bubbles.springapibackend.domain.participation.Participation;
import bubbles.springapibackend.domain.post.Post;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "tb_user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUser;

    private String username;

    @Email
    private String email;

    @CPF
    @Column(columnDefinition = "CHARACTER(11)")
    private String cpf;

    @Column(unique = true)
    private String nickname;

    @Column(columnDefinition = "VARCHAR(60)")
    private String secretKey;

    @OneToOne
    @JoinColumn(name = "fk_address")
    private Address address;

    @OneToMany(mappedBy = "fkUser", cascade = CascadeType.ALL)
    private List<Member> members;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Participation> participants;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Post> posts;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Comment> comments;

    public User(String email, String password) {
        this.email = email;
        this.secretKey = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return secretKey;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
