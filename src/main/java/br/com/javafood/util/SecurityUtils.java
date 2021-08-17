package br.com.javafood.util;

import br.com.javafood.domain.cliente.Cliente;
import br.com.javafood.domain.restaurante.Restaurante;
import br.com.javafood.infrastructure.web.security.LoggedUser;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    //pega o usuario do contexto e retonar
    //assim teremos todas as informações do usuario logado
    public static LoggedUser loggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof AnonymousAuthenticationToken) {
            return null;
        }

        return (LoggedUser) authentication.getPrincipal();
    }

    //verifica se cliente está logado
    //retorna o cliente logado, usar esse método para pegar usuario logado
    public static Cliente loggedCliente() {
        LoggedUser loggedUser = loggedUser();

        if (loggedUser == null) {
            throw new IllegalStateException("Não existe um usuário logado");
        }

        if (!(loggedUser.getUsuario() instanceof Cliente)) {
            throw new IllegalStateException("O usuário logado não é um cliente");
        }

        return (Cliente) loggedUser.getUsuario();
    }

    public static Boolean loggedClienteByHome() {
        LoggedUser loggedUser = loggedUser();

        if (loggedUser == null) {
            return false;
        }

        if (!(loggedUser.getUsuario() instanceof Cliente)) {
            return false;
        }

        return true;
    }

    //verifica se restaurante está logado
    public static Restaurante loggedRestaurante() {
        LoggedUser loggedUser = loggedUser();

        if (loggedUser == null) {
            throw new IllegalStateException("Não existe um usuário logado");
        }

        if (!(loggedUser.getUsuario() instanceof Restaurante)) {
            throw new IllegalStateException("O usuário logado não é um restaurante");
        }

        return (Restaurante) loggedUser.getUsuario();
    }

    public static Boolean loggedRestauranteByHome() {
        LoggedUser loggedUser = loggedUser();

        if (loggedUser == null) {
            return false;
        }

        if (!(loggedUser.getUsuario() instanceof Restaurante)) {
            return false;
        }

        return true;
    }
}