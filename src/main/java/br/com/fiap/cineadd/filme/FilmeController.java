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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/filme")
public class FilmeController {

    @Autowired
    FilmeService service;

    @Autowired
    MessageSource messageSource;

    @GetMapping
    public String index(Model model, @AuthenticationPrincipal OAuth2User user){
        model.addAttribute("username", user.getAttribute("name"));
        model.addAttribute("avatar_url", user.getAttribute("avatar_url"));
        model.addAttribute("filmes", service.findAll());
        return "folme/index";
    }
    
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirect){
        if (service.delete(id)){
            redirect.addFlashAttribute("success", messageSource.getMessage("filme.delete.success", null, LocaleContextHolder.getLocale()));
        }else{
            redirect.addFlashAttribute("error", "Filme não encontrado");
        }
        return "redirect:/filme";
    }

    @GetMapping("new")
    public String form(Filme filme, Model model, @AuthenticationPrincipal OAuth2User user){
        model.addAttribute("username", user.getAttribute("name"));
        model.addAttribute("avatar_url", user.getAttribute("avatar_url"));
        return "filme/form";
    }

    @PostMapping
    public String create(@Valid Filme filme, BindingResult result, RedirectAttributes redirect){
        if (result.hasErrors()) return "filme/form";
        
        service.save(filme);
        redirect.addFlashAttribute("success", "Filme cadastrado com sucesso");
        return "redirect:/filme";
    }

    @GetMapping("/dec/{id}")
    public String decrement(@PathVariable Long id){
        service.decrement(id);
        return "redirect:/filme";
    }

    @GetMapping("/inc/{id}")
    public String increment(@PathVariable Long id){
        service.increment(id);
        return "redirect:/filme";
    }

    @GetMapping("/catch/{id}")
    public String cacthFilme(@PathVariable Long id, @AuthenticationPrincipal OAuth2User user){
        service.cacthFilme(id, user);
        return "redirect:/filme";
    }

    @GetMapping("/drop/{id}")
    public String dropFilme(@PathVariable Long id, @AuthenticationPrincipal OAuth2User user){
        service.dropFilme(id, user);
        return "redirect:/filme";
    }

    
}