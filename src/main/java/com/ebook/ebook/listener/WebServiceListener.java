package com.ebook.ebook.listener;

import com.ebook.ebook.webserviceimpl.SearchWebServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.xml.ws.Endpoint;

@Component
public class WebServiceListener implements ApplicationRunner {
    @Value("${spring.webservices.path}")
    private String path;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("发布地址:"+path);
        Endpoint.publish(path, new SearchWebServiceImpl());
        System.out.println("发布成功");
    }
}
