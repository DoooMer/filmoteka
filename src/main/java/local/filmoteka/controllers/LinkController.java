package local.filmoteka.controllers;

import local.filmoteka.dao.Film;
import local.filmoteka.dao.Link;
import local.filmoteka.repositories.FilmRepository;
import local.filmoteka.repositories.LinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class LinkController
{
    private final FilmRepository filmRepository;

    private final LinkRepository linkRepository;

    @Autowired
    public LinkController(FilmRepository filmRepository, LinkRepository linkRepository)
    {
        this.filmRepository = filmRepository;
        this.linkRepository = linkRepository;
    }

    @GetMapping("/film/{film_id}/links/add")
    public String add(@PathVariable("film_id") Integer id, Model model)
    {
        Film film = filmRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Film #" + id + " not found"));

        model.addAttribute("film", film);
        model.addAttribute("link", new Link());

        return "link/add";
    }

    @PostMapping("/film/{film_id}/links/create")
    public String create(@PathVariable("film_id") Integer id, @ModelAttribute Link link, BindingResult result)
    {
        Film film = filmRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Film #" + id + " not found"));

        if (result.hasErrors()) {
            return "link/add";
        }

        link.setFilm(film);

        linkRepository.save(link);

        return "redirect:/film/" + id;
    }

    @GetMapping("/links/{id}/edit")
    public String edit(Link link, Model model)
    {
        model.addAttribute("link", link);

        return "link/edit";
    }

    @PostMapping("/links/{id}/update")
    public String update(@PathVariable Integer id, @ModelAttribute Link link, BindingResult result)
    {
        if (result.hasErrors()) {
            link.setId(id);
            return "link/edit";
        }

        linkRepository.save(link);

        return "redirect:/film/" + link.getFilm().getId();
    }
}
