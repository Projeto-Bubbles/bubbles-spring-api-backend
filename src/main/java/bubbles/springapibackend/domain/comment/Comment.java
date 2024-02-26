package bubbles.springapibackend.domain.comment;

import bubbles.springapibackend.domain.post.Post;
import bubbles.springapibackend.domain.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_comment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Transactional
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idComment;

    private LocalDateTime moment;

    @Column(columnDefinition = "VARCHAR(300)")
    private String contents;

    @ManyToOne
    @JoinColumn(name = "fk_user")
    private User fkUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_post")
    @JsonIgnore
    private Post fkPost;
}
