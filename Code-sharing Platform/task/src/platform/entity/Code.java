package platform.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "Code")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Code {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @JsonIgnore
    private Long id;

    String code;
    String date;

}
