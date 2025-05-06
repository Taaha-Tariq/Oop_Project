package com.oopsproject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

@Configuration
@EnableJdbcHttpSession
public class SessionConfig {
    
    @Bean
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
        serializer.setCookieName("OOPSPROJECTSESSION"); // Custom cookie name
        serializer.setCookiePath("/");
        // serializer.setDomainNamePattern("^.+?\\.(\\w+\\.[a-z]+)$");
        serializer.setCookieMaxAge(3600); // 1 hour
        serializer.setUseHttpOnlyCookie(true);

        serializer.setSameSite("Lax");
        // For HTTPS environments, enable this:
        serializer.setUseSecureCookie(false);
        return serializer;
    }
}
