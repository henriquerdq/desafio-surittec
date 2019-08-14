package br.com.surittec.desafio.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import br.com.surittec.desafio.dao.UsuarioDAO;


@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UsuarioDAO usuarioDao;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
//			.antMatchers("/resources/**").permitAll()
			.antMatchers("/Produtos/form").hasRole("ADMIN")
			.antMatchers("/Produtos").hasRole("ADMIN")
			.antMatchers("/Produtos/").hasRole("ADMIN")
			.antMatchers("/Produtos/**").permitAll()
			.antMatchers("/carrinho/**").permitAll()	
			.antMatchers("/pagamento/**").permitAll()	
			.antMatchers("/").permitAll()
			.antMatchers("/url-magica-maluca-oajksfbvad6584i57j54f9684nvi658efnoewfmnvowefnoeijn").permitAll()
			.anyRequest().authenticated()
			.and()
				.formLogin().loginPage("/login").defaultSuccessUrl("/Produtos").permitAll()
			.and()
				.logout()
	            .logoutRequestMatcher(new AntPathRequestMatcher("/logout")).permitAll() 
	            .logoutSuccessUrl("/login");
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(usuarioDao)
				.passwordEncoder(new BCryptPasswordEncoder());
	}
	
    // Forma recomendada de ignorar no filtro de segurança as requisições para recursos estáticos
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**");
    }
}
