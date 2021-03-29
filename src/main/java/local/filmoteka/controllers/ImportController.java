package local.filmoteka.controllers;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import local.filmoteka.dao.Film;
import local.filmoteka.repositories.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;

@Controller
public class ImportController
{
    private final FilmRepository filmRepository;

    @Autowired
    public ImportController(FilmRepository filmRepository)
    {
        this.filmRepository = filmRepository;
    }

    @GetMapping("/_import")
    public String start(Model model)
    {
        // form
        return "import/start";
    }

    @PostMapping("/_import/load")
    public String load(@RequestParam MultipartFile file, Model model)
    {
        int imported = 0;

        try {
            CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream()));
            String[] line;
            reader.readNext(); // skip header line
            while ((line = reader.readNext()) != null) {
                Film film = new Film();
                // mapping columns
                film.setDiskNumber(Integer.parseInt(line[1]));
                film.setTitle(line[2]);
                film.setYear(Integer.parseInt(line[3]));
                film.setGenre(line[4]);
                film.setDirector(line[5]);
                film.setRole(line[6]);

                filmRepository.save(film);
                imported++;
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }

        model.addAttribute("imported", imported);

        return "import/success";
    }
}
