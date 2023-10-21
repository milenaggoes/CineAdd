package br.com.fiap.cineadd.filme;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/filme")
public class FilmeController {

    @Autowired
    FilmeService service;

    @Autowired
    MessageSource messageSource;

    @GetMapping
    public String index(Model model){
        model.addAttribute("filmes", service.findAll());
        return "filme/index";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirect){
        if (service.delete(id)){
            redirect.addFlashAttribute("success", messageSource.getMessage("filme.delete.success", null, LocaleContextHolder.getLocale()));
        }else{
            redirect.addFlashAttribute("error", "Filme não encontrada");
        }
        return "redirect:/filme";
    }
    @GetMapping("new")
    public String form(Filme filme){
        return "filme/form";
    }

    @PostMapping
    public String create(@Valid Filme filme, BindingResult result, RedirectAttributes redirect){
        if (result.hasErrors()) return "filme/form";

        service.save(filme);
        redirect.addFlashAttribute("success", "Filme cadastrado com sucesso");
        return "redirect:/filme";
    }


}