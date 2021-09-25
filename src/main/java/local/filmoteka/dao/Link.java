package local.filmoteka.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "film_links")
@AllArgsConstructor
@NoArgsConstructor
public class Link
{
    @Id
    @GeneratedValue
    @Getter
    @Setter
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "film_id", nullable = false)
    @Getter
    @Setter
    private Film film;

    @Column
    @Getter
    @Setter
    private String title;

    @Column(name = "link")
    @Getter
    @Setter
    private String url;
}
