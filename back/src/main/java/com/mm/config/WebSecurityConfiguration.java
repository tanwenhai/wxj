package com.mm.config;

import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Objects;

import static com.google.common.base.Charsets.UTF_8;

/**
 * Spring Security 配置
 * @author tanwenhai@gusoftware.com
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Value("${management.context-path}")
    String endPoint;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new SHA256SimplePasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        configurePublicResource(http);
        configureLogin(http);
        configureLogout(http);
        configureApi(http);
        configureCsrf(http);
        denyOther(http);
    }

    /**
     * 开放公共可以访问的
     * @param http
     * @throws Exception
     */
    private void configurePublicResource(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable();
        http.authorizeRequests().antMatchers("/static/**").permitAll();
        http.authorizeRequests().antMatchers("/upload/**").permitAll();
        http.authorizeRequests().antMatchers("/").permitAll();
        http.authorizeRequests().antMatchers("/favicon.ico").permitAll();
        http.authorizeRequests().antMatchers("/admin/language").permitAll();

        http.authorizeRequests().mvcMatchers("/admin/index").authenticated();
        http.authorizeRequests().antMatchers(endPoint + "/**").authenticated();
        http.authorizeRequests().antMatchers("/ueditor/**").permitAll();
    }

    /**
     * csrf
     * @param http
     * @throws Exception
     */
    private void configureCsrf(HttpSecurity http) throws Exception {
        http.csrf().ignoringAntMatchers("/api/**", "/ueditor/**");
    }

    /**
     * 登陆用户可以调用接口请求
     * @param http
     * @throws Exception
     */
    private void configureApi(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/api/**").permitAll();
        http.authorizeRequests().antMatchers("/admin/**").authenticated();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("wxj").password("123456").roles("admin");
    }

    /**
     * 所有请求需要验证 失败跳转登录 登录页所有人可以访问 登录成功跳转到/admin/index
     * @param http
     * @throws Exception
     */
    private void configureLogin(HttpSecurity http) throws Exception {
        http.formLogin().loginPage("/user/login")
                .defaultSuccessUrl("/admin/index")
                .permitAll();
    }

    /**
     * 注销
     * @param http
     * @throws Exception
     */
    private void configureLogout(HttpSecurity http) throws Exception {
        http.logout().logoutUrl("/user/logout").logoutSuccessUrl("/user/login").invalidateHttpSession(true).deleteCookies("SESSION");
    }

    /**
     * 其他没有配置的全部拒绝访问
     * @param http
     * @throws Exception
     */
    private void denyOther(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().denyAll();
    }

    /**
     * 两次sha256加密
     */
    public static class SHA256SimplePasswordEncoder implements PasswordEncoder {
        @Override
        public String encode(CharSequence rawPassword) {
            Hasher hasher = Hashing.sha256().newHasher();
            String one = hasher.putString(rawPassword, UTF_8).hash().toString();
            hasher = Hashing.sha256().newHasher();

            return hasher.putString(one, UTF_8).hash().toString();
        }

        @Override
        public boolean matches(CharSequence rawPassword, String encodedPassword) {
            return Objects.equals(encode(rawPassword), encodedPassword);
        }
    }
}
