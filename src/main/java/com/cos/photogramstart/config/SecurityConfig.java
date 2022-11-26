package com.cos.photogramstart.config;


import com.cos.photogramstart.config.oauth.OAuth2Provider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cos.photogramstart.config.oauth.OAuth2DetailsService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@EnableWebSecurity // 해당 파일로 시큐리트를 활성화 1-3. 해당 파일로 Security를 활성한다는 의미.
@Configuration // IoC 1-2. IoC에등록
public class SecurityConfig extends WebSecurityConfigurerAdapter { // 1-1. Spring Security를 구현하기 위해 WebSecurityConfigurerAdapter 상속
//		super.configure(http);  // 1-4. super.configure(http); -> 이게 default로 실행되고 있기 때문에 계속 Spring Security가 가지고 있는 기본 로그인 페이지가 뜨게 된다. -> 주석 처리, 기존 시큐리티 기능이 비활성화 된다.

//    @Resource
//    private Environment env;

    private final OAuth2DetailsService oAuth2DetailsService;
//    private static String CLIENT_PROPERTY_KEY= "spring.security.oauth2.client.registration.";
//    private static List<String> clients = Arrays.asList("google", "facebook", "github", "kakao", "naver");

    @Bean
    public BCryptPasswordEncoder encode(){
        return new BCryptPasswordEncoder();
    }
////
//    @Bean
//    public ClientRegistrationRepository clientRegistrationRepository() {
//        List<ClientRegistration> registrations = clients.stream()
//                .map(c -> getRegistration(c))
//                .filter(registration -> registration != null)
//                .collect(Collectors.toList());
//        return new InMemoryClientRegistrationRepository(registrations);
//    }
//
//    private ClientRegistration getRegistration(String client){
//        // API Client Id 불러오기
//        String clientId = env.getProperty(
//                CLIENT_PROPERTY_KEY + client + ".client-id");
//
//        // API Client Id 값이 존재하는지 확인하기
//        if (clientId == null) {
//            return null;
//        }
//
//        // API Client Secret 불러오기
//        String clientSecret = env.getProperty(
//                CLIENT_PROPERTY_KEY + client + ".client-secret");
//
//        if (client.equals("facebook")) {
//            return OAuth2Provider.FACEBOOK.getBuilder(client)
//                    .clientId(clientId)
//                    .clientSecret(clientSecret)
//                    .build();
//        }
//
//
//        return null;
//    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //super.configure(http); sper삭제 - 기존 시큐리티가 가지고 있는 기능이 다 비활성화됨.   가로체는것을 막는다.

        //csrf 시큐리티 비활성화 ~
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/", "user/**", "/image/**", "/subscribe/**", "/comment/**", "/api/**").authenticated()  //인증이 필요한 페이지  // 1-5. 해당 URL로 매핑되는건 인증이 필요하고
                .anyRequest().permitAll() // 1-6. 그외의 모든 요청은 허용하겠다.
                .and()
                .formLogin()
                .loginPage("/auth/signin") //GET  // 1-7. 사용자가 1-5의 URL로 요청하면(GET 방식) /auth/signin으로 리다이렉트.
                .loginProcessingUrl("/auth/signin") //POST -> 스프링 시큐리티가 로그인 프로세스를 진행한다.  // 1-8. URL로 요청(POST 방식) -> 스프링 시큐리티가 구현한 UserDetailsService가 낚아채서 로그인 프로세스 진행
                .defaultSuccessUrl("/") // 1-9. 1-8이 정상적으로 처리가 되었으면 /로 이동.
                .and()
                .oauth2Login()// form로그인도 하는데, oauth2 로그인도 할거야.  // 1-11. oauth2 로그인도 허용.
                .userInfoEndpoint()// oauth2로그인을 하면 최종응답을 회원정보를 바로 받을 수 있다.  // 1-2. oauth2 로그인을 하면 최종 응답으로 회원정보를 바로 받을 수 있다.
                .userService(oAuth2DetailsService);


//                .clientRegistrationRepository(clientRegistrationRepository())
//                .authorizedClientService(authorizedClientService());
//                .and()
//                .and()
//                .build();




        //http.csrf().disable();  // 1-10. CSRF[Cross Site Request Forgery] 토큰 비활성화
        // -> 여기다 안적고 form태그 안에다가  넣어줘도 된다.
        // -> <input type="hidden" th:name="${_csrf.parameterName}" th:value="${_csrf.token}">

    }



//    @Override
//    public void configure(AuthenticationManagerBuilder auth) throws Exception{
//		/*auth.inMemoryAuthentication()
//			.withUser("user1")
//			.password("{noop}password1")
//			.authorities("ROLE_USER")
//			.and()
//			.withUser("user2")
//			.password("{noop}password2")
//			.authorities("ROLE_USER");
//		*/
//        auth
//                .jdbcAuthentication()
//                .dataSource(dataSource)
//                .usersByUsernameQuery("select username, password, enabled from users" +
//                        "where username=?")
//                .authoritiesByUsernameQuery("select username, authority from authorities " +
//                        "where username=?")
//                .passwordEncoder(new NoEncodingPasswordEncoder());
//    }
//
//    // Security 무시하기
//    public void configure(WebSecurity web)throws Exception{
//        web.ignoring().antMatchers("/h2-console/**");
//    }



//    @Bean
//    public OAuth2AuthorizedClientService authorizedClientService(){
//        return new InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository());
//    }


//    @Bean
//    public BCryptPasswordEncoder encode() {
//        return new BCryptPasswordEncoder();
//    }


}
