package com.cecilia.biblioteca;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cecilia.biblioteca.servicios.UsuarioServicio;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // Esta anotación se utiliza para habilitar la seguridad a nivel de
                                                   // método. El atributo prePostEnabled habilita las anotaciones
                                                   // @PreAuthorize y @PostAuthorize.
@Deprecated
public class SeguridadWeb extends WebSecurityConfigurerAdapter { // Aquí estamos definiendo una clase que extiende
                                                                 // WebSecurityConfigurerAdapter para personalizar la
                                                                 // seguridad web proporcionada por Spring.
        @Autowired
        public UsuarioServicio usuarioServicio;

        @Autowired
        public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
                // con este objeto que recibimos por parámetro configuramos el manejador de
                // seguridad de Spring security
                // y le decimos que servicio tiene que usar para autenticar a un usuario
                auth.userDetailsService(usuarioServicio)
                                .passwordEncoder(new BCryptPasswordEncoder());

        }

    @Override
    protected void configure(HttpSecurity http) throws Exception { // Este método se sobrescribe para agregar más                                                                 // configuraciones

        http
                .authorizeRequests(requests -> requests // utilizando sintaxis Lambda DSL para configurar las  solicitudes HTTP.                                                        
                        .antMatchers("/admin/*").hasRole("ADMIN") // para preautorizar a los roles del controlador ADMIN
                        .antMatchers("/css/*", "/js/*", "/img/*", "/**") //// autorizando todas las solicitudes a los patrones de URL especificados                                                                       
                        .permitAll())

                .formLogin(login -> login
                        .loginPage("/login")
                        .loginProcessingUrl("/logincheck")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/inicio") // falta crear la vista inicio con funcionalidades, esta no debería tener nada
                        .permitAll())

                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .permitAll())

                .csrf(csrf -> csrf
                   .disable());

               

    }

}