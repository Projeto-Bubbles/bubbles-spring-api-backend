package bubbles.springapibackend.domain.member;

import bubbles.springapibackend.domain.bubble.Bubble;
import bubbles.springapibackend.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_member")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idMember;

    @ManyToOne
    @JoinColumn(name = "fk_user")
    private User fkUser;

    @ManyToOne
    @JoinColumn(name = "fk_bubble")
    private Bubble fkBubble;
}
