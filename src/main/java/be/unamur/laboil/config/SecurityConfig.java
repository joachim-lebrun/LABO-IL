package be.unamur.laboil.config;

import be.unamur.laboil.component.CustomAccessDeniedHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static Logger logger = LoggerFactory.getLogger(CustomAccessDeniedHandler.class);

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.jdbcAuthentication()
                .dataSource(dataSource)
                /*.usersByUsernameQuery(
                        "select EMAIL, PASSWORD, ENABLED " +
                                " from EMPLOYEE e" +
                                " where e.EMAIL=? " +
                                " union " +
                                " select  EMAIL, PASSWORD, ENABLED" +
                                " from CITIZEN c" +
                                " where c.EMAIL=?")*/
                .usersByUsernameQuery(
                        " select  EMAIL, PASSWORD, ENABLED" +
                                " from CITIZEN c" +
                                " where c.EMAIL=?")
                .authoritiesByUsernameQuery("select EMAIL, AUTHORITY "
                        + "from AUTHORITIES where EMAIL=?")
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/img/*","/css/*","/js/*","/public/*").permitAll()
                .antMatchers("/employee/**").hasAnyRole("EMPLOYEE")
                .antMatchers("/citizen/**").hasAnyRole("CITIZEN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .successForwardUrl("/citizen/demand")
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler);
    }
}
