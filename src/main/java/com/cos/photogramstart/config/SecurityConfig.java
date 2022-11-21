package com.cos.photogramstart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity // 해당 파일로 시큐리트를 활성화
@Configuration // IoC
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder encode(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //super.configure(http); sper삭제 - 기존 시큐리티가 가지고 있는 기능이 다 비활성화됨.   가로체는것을 막는다.

        //csrf 시큐리티 비활성화 ~
        http.csrf().disable();

        http.authorizeRequests()
                .antMatchers("/", "user/**", "/imge/**", "/subscribe/**", "/comment/**", "/api/**").authenticated()  //인증이 필요한 페이지
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/auth/signin") //GET
                .loginProcessingUrl("/auth/signin") //POST -> 스프링 시큐리티가 로그인 프로세스를 진행한다.
                .defaultSuccessUrl("/");



    }
}
