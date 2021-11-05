package local.filmoteka.controllers;

import local.filmoteka.dao.Film;
import local.filmoteka.repositories.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
public class FilmController
{
    private final FilmRepository filmRepository;

    @Autowired
    public FilmController(FilmRepository filmRepository)
    {
        this.filmRepository = filmRepository;
    }

    @GetMapping("/")
    public String index(@RequestParam Optional<String> search, @RequestParam Optional<Integer> page, Model model)
    {
        final int currentPage = page.orElse(1);
        PageRequest pagination = PageRequest.of(currentPage - 1, 20, Sort.by("title").ascending());
        Page<Film> filmsList;

        if (search.isPresent()) {
            final String searchLike = "%" + search.get() + "%";
            filmsList = filmRepository.findAllBySearch(searchLike, pagination);
        } else {
            filmsList = filmRepository.findAll(pagination);
        }

        model.addAttribute("films", filmsList.getContent());

        List<Integer> pages = new ArrayList<>(filmsList.getTotalPages());

        for (int i = 1; i <= filmsList.getTotalPages(); i++) {
            pages.add(i);
        }

        pages = pages.subList(Math.max(currentPage - 3, 0), Math.min(currentPage + 2, pages.size()));

        model.addAttribute("pages", pages);
        model.addAttribute("maxPages", Collections.max(pages));
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("total", filmsList.getTotalElements());
        model.addAttribute("search", search.orElse(""));

        return "film/list";
    }

    @GetMapping("/film/add")
    public String add(Film film)
    {
        return "film/add";
    }

    @PostMapping("/film/create")
    public String create(@Validated Film film, BindingResult result)
    {
        if (result.hasErrors()) {
            return "film/add";
        }

        filmRepository.save(film);
        return "redirect:/";
    }

    @GetMapping("/film/{id}")
    public String view(@PathVariable Integer id, Model model)
    {
        Film film = filmRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Film #" + id + " not found"));
        model.addAttribute("film", film);
        return "film/view";
    }

    @GetMapping("/film/{id}/edit")
    public String edit(@PathVariable Integer id, Model model)
    {
        Film film = filmRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Film #" + id + " not found"));
        model.addAttribute("film", film);
        return "film/edit";
    }

    @PostMapping("/film/{id}/update")
    public String update(@PathVariable Integer id, @Validated Film film, BindingResult result)
    {
        if (result.hasErrors()) {
            film.setId(id);
            return "film/edit";
        }

        filmRepository.save(film);
        return "redirect:/";
    }

    @GetMapping("/film/{id}/delete")
    public String delete(@PathVariable Integer id)
    {
        Film film = filmRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Film #" + id + " not found"));
        filmRepository.delete(film);
        return "redirect:/";
    }
}
