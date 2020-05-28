package main.security;

import main.security.jwt.JwtSecurityConfigurer;
import main.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;


@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic()
                .disable()
                .csrf().disable()
                .formLogin().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/bat/auth/singin").permitAll()
                .antMatchers(HttpMethod.POST, "/bat/addAuto").hasRole("USER")
                .antMatchers(HttpMethod.POST, "/bat/setAutoPersonnelInAuto/{id}").permitAll()
                .antMatchers(HttpMethod.POST, "/bat/addAutoPersonnel").permitAll()
                .antMatchers(HttpMethod.POST, "/bat/addJournal").permitAll()
                .antMatchers(HttpMethod.POST, "/bat/setTimeOutInJournal/{id}").permitAll()
                .antMatchers(HttpMethod.POST, "/bat/setRouteInJournal/{id}").permitAll()
                .antMatchers(HttpMethod.POST, "/bat/setAutoInJournal/{id}").permitAll()
                .antMatchers(HttpMethod.DELETE, "/bat/deleteJournal/{id}").permitAll()
                .antMatchers(HttpMethod.GET, "/bat/routes").permitAll()
                .antMatchers(HttpMethod.GET, "/bat/route/byId/{id}").permitAll()
                .antMatchers(HttpMethod.GET, "/bat/route/byName/{name}").permitAll()
                .antMatchers(HttpMethod.GET, "/bat/autos").permitAll()
                .antMatchers(HttpMethod.GET, "/bat/auto/byId/{id}").permitAll()
                .antMatchers(HttpMethod.GET, "/bat/auto/byNum/{num}").permitAll()
                .antMatchers(HttpMethod.GET, "/bat/auto/filterByMark/{mark}").permitAll()
                .antMatchers(HttpMethod.GET, "/bat/auto/filterByColor/{color}").permitAll()
                .antMatchers(HttpMethod.GET, "/bat/autoPersonnels").permitAll()
                .antMatchers(HttpMethod.GET, "/bat/autoPersonnel/byId/{id}").permitAll()
                .antMatchers(HttpMethod.GET, "/bat/autoPersonnel/byFirstName/{name}").permitAll()
                .antMatchers(HttpMethod.GET, "/bat/autoPersonnel/byLastName/{name}").permitAll()
                .antMatchers(HttpMethod.GET, "/bat/autoPersonnel/byPatherName/{name}").permitAll()
                .antMatchers(HttpMethod.GET, "/bat/journals").permitAll()
                .antMatchers(HttpMethod.GET, "/bat/journal/byId/{id}").permitAll()
                .anyRequest().authenticated()
                .and()
                .apply(new JwtSecurityConfigurer(jwtTokenProvider));
    }
}
