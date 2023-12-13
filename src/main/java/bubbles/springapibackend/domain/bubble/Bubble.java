package bubbles.springapibackend.domain.bubble;

import bubbles.springapibackend.api.enums.Category;
import bubbles.springapibackend.domain.post.Post;
import bubbles.springapibackend.domain.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "tb_bubble")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Bubble {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "VARCHAR(100)")
    private String headline;

    @Column(columnDefinition = "VARCHAR(500)")
    private String explanation;

    private LocalDate creationDate;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(10)")
    private Category category;

    @OneToOne
    private User creator;

    @OneToMany(mappedBy = "bubble", cascade = CascadeType.ALL)
    private List<Post> posts;
}
