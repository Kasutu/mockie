package com.splitscale.mockie.user;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@Configuration
public class UserConfig extends WebMvcConfigurationSupport {

  private LocaleChangeInterceptor loggingInterceptor;

  public UserConfig(LocaleChangeInterceptor loggingInterceptor) {
    this.loggingInterceptor = loggingInterceptor;
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    InterceptorRegistration registration = registry.addInterceptor(loggingInterceptor);
    registration.addPathPatterns("/**");
  }

}
