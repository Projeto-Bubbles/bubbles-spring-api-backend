package bubbles.springapibackend.entity;

import bubbles.springapibackend.enums.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_event")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private LocalDateTime date;
    private String category;
    private Integer duration;

    private String author;

    private String bubble;

//  o que é isso aqui ?
    public abstract String sendConfirmationCode();
}
