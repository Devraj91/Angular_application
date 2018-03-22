package com.nasscom.einvoice.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.templatemode.StandardTemplateModeHandlers;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@Configuration
public class ThymeleafConfiguration {
  @Bean
  public ClassLoaderTemplateResolver emailTemplateResolver(){
    ClassLoaderTemplateResolver emailTemplateResolver=new ClassLoaderTemplateResolver();
    emailTemplateResolver.setPrefix("static/");
    //template mode use if npm compiler skip close tag in html pages
    emailTemplateResolver.setTemplateMode(StandardTemplateModeHandlers.LEGACYHTML5.getTemplateModeName());
    emailTemplateResolver.setSuffix(".html");
    emailTemplateResolver.setCacheable(false);
    emailTemplateResolver.setCharacterEncoding("UTF-8");
    emailTemplateResolver.setOrder(2);
    return emailTemplateResolver;
  }
}