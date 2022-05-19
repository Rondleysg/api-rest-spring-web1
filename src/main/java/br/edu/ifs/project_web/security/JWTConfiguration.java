package br.edu.ifs.project_web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import br.edu.ifs.project_web.filter.TokenAuthenticationFilter;
import br.edu.ifs.project_web.repository.UsuarioRepository;
import br.edu.ifs.project_web.service.DetailUsuarioServiceImpl;
import br.edu.ifs.project_web.service.TokenService;

@Configuration
@EnableWebSecurity
public class JWTConfiguration extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private DetailUsuarioServiceImpl usuarioService;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private UsuarioRepository repository;
	
	public JWTConfiguration(DetailUsuarioServiceImpl usuarioService) {
		this.usuarioService=usuarioService;
	}
	
	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(usuarioService);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
		.antMatchers(HttpMethod.POST, "/login")
		.permitAll()
		.anyRequest()
		.authenticated()
		.and()
		.addFilterBefore(new TokenAuthenticationFilter(tokenService, repository), UsernamePasswordAuthenticationFilter.class)
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
	
	
	@Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
	
	@SuppressWarnings("deprecation")
	@Bean
	public static NoOpPasswordEncoder passwordEncoder() {
	return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
	}
	
	
	
}
