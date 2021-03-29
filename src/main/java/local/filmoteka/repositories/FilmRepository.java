package local.filmoteka.repositories;

import local.filmoteka.dao.Film;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmRepository extends PagingAndSortingRepository<Film, Integer>
{
    @Query("select f from Film f where f.title like :search or concat('', f.year) like :search or concat('', f.diskNumber) like :search or f.genre like :search or f.director like :search or f.role like :search")
    public Page<Film> findAllBySearch(String search, Pageable pageable);

    public Page<Film> findAllByDiskNumber(Integer diskNumber, Pageable pageable);
}
