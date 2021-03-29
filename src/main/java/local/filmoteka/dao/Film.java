package local.filmoteka.dao;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "Films")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Film
{
    @Id
    @GeneratedValue
    @Getter
    @Setter
    private int id;

    @Column(name = "disk_number")
    @Getter
    @Setter
    private int diskNumber;

    @Column
    @Getter
    @Setter
    private String title;

    @Column
    @Getter
    @Setter
    private int year;

    @Column
    @Getter
    @Setter
    private String genre;

    @Column
    @Getter
    @Setter
    private String director;

    @Column
    @Getter
    @Setter
    private String role;

    @Column(name = "cover_image_link")
    @Getter
    @Setter
    private String coverImageLink;
}
