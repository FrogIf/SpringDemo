package sch.frog.learn.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Configuration
public class SpringAdminClientSecurityConfigure extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
        successHandler.setTargetUrlParameter("redirectTo");
        successHandler.setDefaultTargetUrl("/actuator");

        http.authorizeRequests()
                .antMatchers("/assets/**").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/actuator/health").permitAll()    // 注意这个应该在下一个之前!!!
                .antMatchers("/actuator/**").authenticated()
                .anyRequest().permitAll()
                .and()
                .formLogin().loginPage("/login").successHandler(successHandler).and()
                .logout().logoutUrl("/logout").and()
                .httpBasic()
                .and()
                .addFilterBefore(new SecurityAccessLog(), ChannelProcessingFilter.class).httpBasic()    // 定义一个访问过滤器, 在所有认证之前
                .and()
                .csrf().disable();
    }


    @Slf4j
    private static class SecurityAccessLog implements Filter{
        @Override
        public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
            if(servletRequest instanceof HttpServletRequest){
                HttpServletRequest request = (HttpServletRequest) servletRequest;
                log.info("access : {}", request.getRequestURL());
            }
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
