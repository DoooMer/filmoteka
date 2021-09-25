package local.filmoteka.dao;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "film_rents")
@NoArgsConstructor
@AllArgsConstructor
public class Rent
{
    @Id
    @GeneratedValue
    @Getter
    @Setter
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "film_id", referencedColumnName = "id", nullable = false)
    @Getter
    @Setter
    private Film film;

    @Column
    @Getter
    @Setter
    private String name;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    @Getter
    @Setter
    private Date createdAt;

}
