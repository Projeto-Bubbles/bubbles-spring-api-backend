package bubbles.springapibackend.domain.address;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_address")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAddress;

    @Column(columnDefinition = "CHARACTER(8)")
    private String cep;

    @Column(columnDefinition = "VARCHAR(60)")
    private String estate;

    @Column(columnDefinition = "VARCHAR(60)")
    private String city;

    @Column(columnDefinition = "VARCHAR(60)")
    private String neighborhood;

    @Column(columnDefinition = "VARCHAR(60)")
    private String street;

    @Column(columnDefinition = "VARCHAR(10)")
    private String houseNumber;
}
