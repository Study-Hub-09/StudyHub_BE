package trillion9.studyarcade_be.global.config;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import trillion9.studyarcade_be.global.exception.CustomAuthenticationEntryPoint;
import trillion9.studyarcade_be.global.jwt.JwtAuthFilter;
import trillion9.studyarcade_be.global.jwt.JwtUtil;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig {
    private final JwtUtil jwtUtil;
    private final RedisTemplate<String, String> redisTemplate;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // resources 접근 허용 설정
        return web -> web.ignoring()
                // static files
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                // Swagger
                .antMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-resources/**","/api-docs","/swagger-ui.html");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();

        // 기본 설정인 Session 방식은 사용하지 않고 JWT 방식을 사용하기 위한 설정
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeHttpRequests()
                //회원가입, 로그인페이지, 메인 페이지
                .antMatchers("/**").permitAll()
//                .antMatchers("/members/signup").permitAll()
//                .antMatchers("/emails/**").permitAll()
//                .antMatchers(HttpMethod.GET,"/posts/**").permitAll()

                .anyRequest().authenticated()
                // JWT 인증/인가를 사용하기 위한 설정
                .and().addFilterBefore(new JwtAuthFilter(jwtUtil, redisTemplate), UsernamePasswordAuthenticationFilter.class);

        http.cors();
        http.exceptionHandling().authenticationEntryPoint(customAuthenticationEntryPoint);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){

        CorsConfiguration config = new CorsConfiguration();

        config.addAllowedOriginPattern("*"); // "http://localhost:3000"

        config.addExposedHeader(JwtUtil.ACCESS_TOKEN);
        config.addExposedHeader(JwtUtil.REFRESH_TOKEN);

        config.addAllowedMethod("*");
        config.addAllowedHeader("*");

        config.setAllowCredentials(true);
        config.validateAllowCredentials();

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }

}
