package com.example.sweater.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //когда система заходит в объект(хттп), включаем: авторизацию, доступ к пути "/", остальные требуем авторизацию, логин по адресу и логаут
        http
                .authorizeRequests()
                    .antMatchers("/", "/registration").permitAll()
                    .anyRequest().authenticated()
                .and()
                    .formLogin()
                    .loginPage("/login")
                    .permitAll()
                .and()
                    .logout()
                    .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)//дает  доступ менеджеру к базе данных
                .passwordEncoder(NoOpPasswordEncoder.getInstance())//шифрует пароли чтоб они не хранились в явном виде
                .usersByUsernameQuery("select username, password, active from usrq where username =?")
                .authoritiesByUsernameQuery("select u.username, ur.roles from usrq u inner join user_role ur on u.id = ur.user_id where u.username =?");// помогает спрингу получить список пользователей с их ролями
    }
//Пользуемся мемори вместо базы данных
//    @Bean
//    @Override
//    public UserDetailsService userDetailsService() {
////в памяти создается менеджер который обслуживает все учетные записи
//        UserDetails user =
//                User.withDefaultPasswordEncoder()//деприкэйтет потому что служит только для отладки
//                        .username("u")
//                        .password("1")
//                        .roles("USER")
//                        .build();
//
//        return new InMemoryUserDetailsManager(user);
//    }
}