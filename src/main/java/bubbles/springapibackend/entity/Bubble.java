package bubbles.springapibackend.entity;

import bubbles.springapibackend.enums.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

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
    private String name;
    private String description;
    private LocalDate creationDate;

    private Category category;

    @ManyToOne
    private User creator;
}
