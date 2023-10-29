package br.com.fiap.cineadd.filme;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import br.com.fiap.cineadd.user.User;
import br.com.fiap.cineadd.user.UserService;

@Service
public class FilmeService {

    @Autowired
    FilmeRepository repository;

    @Autowired
    UserService userService;

    public List<Filme> findAll() {
        return repository.findAll();
    }

    public boolean delete(Long id) {
        var filme = repository.findById(id);

        if (filme.isEmpty())
            return false;

        repository.deleteById(id);
        return true;
	    }
    public void save(Filme filme) {
        repository.save(filme);
    }

    public void decrement(Long id) {
        // buscar o filme no bd
        var optional = repository.findById(id);

        if (optional.isEmpty())
            throw new RuntimeException("filme não encontrado");

        var filme = optional.get();

        if (filme.getStatus() == null || filme.getStatus() <= 0)
            throw new RuntimeException("Filme não pode ter status negativo");

        filme.setStatus(filme.getStatus() - 10);

        // salvar
        repository.save(filme);
    }

    public void increment(Long id) {
        // buscar o filme no bd
        var optional = repository.findById(id);

        if (optional.isEmpty())
            throw new RuntimeException("Filme não encontrado");

        var filme = optional.get();

        if (filme.getStatus() == null)
            filme.setStatus(0);

        if (filme.getStatus() == 100) {
            throw new RuntimeException("Filme não pode ter status maior que 100");
        }

        filme.setStatus(filme.getStatus() + 10);

        if (filme.getStatus() == 100){
            var user = (OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            userService.addScore(User.convert(user) , filme.getScore());
        }

        // salvar
        repository.save(filme);
    }

    public void cacthFilme(Long id, OAuth2User user) {
        var optional = repository.findById(id);

        if (optional.isEmpty())
            throw new RuntimeException("Filme não encontrado");

        var filme = optional.get();

        if (filme.getUser() != null)
            throw new RuntimeException("Filme já atribuído");

        filme.setUser(User.convert(user));

        repository.save(filme);

    }

    public void dropFilme(Long id, OAuth2User user) {
        var optional = repository.findById(id);

        if (optional.isEmpty())
            throw new RuntimeException("Filme não encontrado");

        var filme = optional.get();

        if (filme.getUser() == null)
            throw new RuntimeException("Filme não atribuído");

        if (!filme.getUser().equals(User.convert(user)))
            throw new RuntimeException("não pode largar filme de outra pessoa");

        filme.setUser(null);

        repository.save(filme);
    }

}