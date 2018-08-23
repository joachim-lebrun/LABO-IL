package be.unamur.laboil.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.sql.DataSource;

/**
 * @author Joachim Lebrun on 12-08-18
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(
                        " select  EMAIL, PASSWORD, ENABLED" +
                                " from USER " +
                                " where EMAIL=?")
                .authoritiesByUsernameQuery("select EMAIL, RIGHTS "
                        + "from ROLE where EMAIL=?")
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/", "/img/**", "/css/**", "/js/**", "/signup", "/public/*", "/login/*").permitAll()
                .antMatchers("/demands/**").hasAnyAuthority("EMPLOYEE", "CITIZEN")
                .antMatchers("/employees/**").hasAnyAuthority("EMPLOYEE")
                .antMatchers("/admins/**").hasAnyAuthority("MAYOR")
                .antMatchers("/citizens/**").hasAnyAuthority("CITIZEN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/userType", true)
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/login?logout")
                .permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler);
    }
}
