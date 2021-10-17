package com.ebook.ebook.configuration;

import com.ebook.ebook.webservice.SearchWebService;
import com.ebook.ebook.webserviceimpl.SearchWebServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

@Configuration
public class CxfConfig {
    @Autowired
    private SearchWebService searchWebService;

    @Value("${spring.webservices.path}")
    private String path;

    @Bean
    public Endpoint endpoint()
    {
        System.out.println("发布地址:"+path);
        Endpoint publish=Endpoint.publish(path,searchWebService);
        System.out.println("发布成功");
        return publish;
    }
}
