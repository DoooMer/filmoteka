package local.filmoteka.controllers;

import local.filmoteka.dao.Film;
import local.filmoteka.dao.Rent;
import local.filmoteka.repositories.FilmRepository;
import local.filmoteka.repositories.RentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class RentController
{
    private final FilmRepository filmRepository;

    private final RentRepository rentRepository;

    @Autowired
    public RentController(FilmRepository filmRepository, RentRepository rentRepository)
    {
        this.filmRepository = filmRepository;
        this.rentRepository = rentRepository;
    }

    @GetMapping("/film/{id}/rent")
    public String view(@PathVariable Integer id, Model model)
    {
        Film film = filmRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Film #" + id + " not found"));

        if (film.getRent() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Film already rented to " + film.getRent().getName() + ".");
        }

        model.addAttribute("film", film);
        model.addAttribute("rent", new Rent());

        return "rent/add";
    }

    @PostMapping("/film/{film_id}/rent")
    public String create(@PathVariable("film_id") Integer id, @ModelAttribute Rent rent, BindingResult result)
    {
        Film film = filmRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Film #" + id + " not found"));

        if (film.getRent() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Film already rented to " + film.getRent().getName() + ".");
        }

        if (result.hasErrors()) {
            return "rent/add";
        }

        rent.setFilm(film);

        rentRepository.save(rent);

        return "redirect:/film/" + id;
    }

    @GetMapping("/film/{film_id}/rent/back")
    public String back(@PathVariable("film_id") Integer id)
    {
        Film film = filmRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Film #" + id + " not found"));

        if (film.getRent() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Film not rented yet.");
        }

        rentRepository.delete(film.getRent());

        return "redirect:/film/" + id;
    }

}
