package bubbles.springapibackend.domain.post;

import bubbles.springapibackend.domain.bubble.Bubble;
import bubbles.springapibackend.domain.comment.Comment;
import bubbles.springapibackend.domain.user.User;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tb_post")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPost;

    private LocalDateTime moment;

    private String image;

    @Column(columnDefinition = "VARCHAR(650)")
    private String contents;

    @ManyToOne
    @JoinColumn(name = "fk_user")
    private User author;

    @ManyToOne
    @JoinColumn(name = "fk_bubble")
    private Bubble bubble;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments;
}
