package local.filmoteka.controllers;

import local.filmoteka.dao.Film;
import local.filmoteka.repositories.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class DiskController
{
    private final FilmRepository filmRepository;

    @Autowired
    public DiskController(FilmRepository filmRepository)
    {
        this.filmRepository = filmRepository;
    }

    @GetMapping("/disk/{disk}")
    public String view(@PathVariable Integer disk, @RequestParam Optional<Integer> page, Model model)
    {
        final Integer currentPage = page.orElse(1);
        Page<Film> filmsList = filmRepository.findAllByDiskNumber(disk,
                PageRequest.of(currentPage - 1, 20, Sort.by("title").ascending()));

        model.addAttribute("films", filmsList.iterator());
        List<Integer> pages = new ArrayList<>();
        for (int i = 1; i <= filmsList.getTotalPages(); i++) {
            pages.add(i);
        }
        model.addAttribute("pages", pages);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("total", filmsList.getTotalElements());
        model.addAttribute("search", "");

        return "film/list";
    }
}
