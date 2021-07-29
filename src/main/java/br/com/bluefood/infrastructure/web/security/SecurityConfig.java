package br.com.bluefood.infrastructure.web.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean //produzem instancias automaticamente
    public AuthenticationSuccessHandler authenticationSuccessHandler(){
        return new AuthenticationSuccessHandlerImpl();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.csrf().disable()
                .authorizeRequests()
                .antMatchers("/images/**","/css/**", "/js/**", "/public", "/sbpay").permitAll() //autoriza acesso a todas essas urls
                .antMatchers("/cliente/**").hasRole(Role.CLIENTE.toString()) //no caso de cliente tem que ter o role cliente
                .antMatchers("/restaurante/**").hasRole(Role.RESTAURANTE.toString()) // no caso de restaurante tem que ter o role restaurante
                .anyRequest().authenticated() //todas as outras necessario autenticação
                .and()
                .formLogin()//a forma de autenicação será por login
                    .loginPage("/login")// url do login
                    .failureUrl("/login-error")//url caso o login de problema
                    .successHandler(authenticationSuccessHandler())//objeto que será chamado quando o login der certo
                    .permitAll()// qualquer pessoa pode acessar a url de login
                .and()
                    .logout().logoutUrl("/logout")//url de logout
                    .permitAll();// qualquer pessoa pode acessar a url de logout

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //autorizar cadastro de usuario
        web.ignoring().antMatchers(HttpMethod.GET, "/public/cliente/new");
        web.ignoring().antMatchers(HttpMethod.GET, "/public/restaurante/new");
        web.ignoring().antMatchers(HttpMethod.POST, "/public/restaurante/save");
        web.ignoring().antMatchers(HttpMethod.POST, "/public/cliente/save");
    }




}
