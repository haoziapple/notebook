package com.sample;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestContextListener;

/**
 * @author wanghao
 * @Description 多站点部署配置类
 * @date 2019-02-22 14:38
 */
@Configuration
@ConditionalOnProperty(name = "fast.webfront.subsites.enable", havingValue = "true")
public class SubSitesConfiguration {
    @Value("${fast.webfront.root-locations: your-root-locations}")
    private String[] rootLocations;

    @Value("${fast.webfront.error-page.notfound: your-404-page}")
    private String notFoundPage;

    /**
     * 将404错误重定向到自定义的Controller，针对站点不同再进行页面转发
     *
     * @return
     * @see FastErrorController
     */
    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {
        return container -> {
            ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/fastError/404");
            container.addErrorPages(error404Page);
        };
    }

    /**
     * RequestContextHolder.getRequestAttributes()返回null，造成NPE
     *
     * @return
     */
    @Bean
    public RequestContextListener requestContextListener() {
        return new RequestContextListener();
    }

    public String[] getRootLocations() {
        return rootLocations;
    }

    public void setRootLocations(String[] rootLocations) {
        this.rootLocations = rootLocations;
    }

    public String getNotFoundPage() {
        return notFoundPage;
    }

    public void setNotFoundPage(String notFoundPage) {
        this.notFoundPage = notFoundPage;
    }
}
