package bubbles.springapibackend.domain.bubble;

import bubbles.springapibackend.api.enums.Category;
import bubbles.springapibackend.domain.event.Event;
import bubbles.springapibackend.domain.member.Member;
import bubbles.springapibackend.domain.post.Post;
import bubbles.springapibackend.domain.user.User;
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
    private Integer idBubble;

    @Column(columnDefinition = "VARCHAR(100)")
    private String title;

    @Column(columnDefinition = "VARCHAR(500)")
    private String explanation;

    private LocalDate creationDate;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(10)")
    private Category category;

    @OneToOne
    @JoinColumn(name = "fk_user")
    private User fkUser;

    @OneToMany(mappedBy = "fkBubble", cascade = CascadeType.ALL)
    private List<Member> members;

    @OneToMany(mappedBy = "fkBubble", cascade = CascadeType.ALL)
    private List<Post> posts;

    @OneToMany(mappedBy = "fkBubble", cascade = CascadeType.ALL)
    private List<Event> events;
}
