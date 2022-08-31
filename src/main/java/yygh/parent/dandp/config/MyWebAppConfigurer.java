package yygh.parent.dandp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import yygh.parent.dandp.config.properties.PenintProperties;
import yygh.parent.dandp.utils.SpringContextUtil;

/**
 * 资源映射路径
 */
@Configuration
public class MyWebAppConfigurer implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        PenintProperties properties = SpringContextUtil.getBean(PenintProperties.class);
        String image = properties.getResourcePath().getImage();
        registry.addResourceHandler("/adminApi/image/**").addResourceLocations(image);
    }
}
