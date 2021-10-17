package com.ebook.ebook.webserviceimpl;

import com.ebook.ebook.service.BookService;
import com.ebook.ebook.webservice.SearchWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jws.WebService;


@Component
@WebService(name="searchBook",
        targetNamespace = "http://webservice.ebbok.ebook.com",
        endpointInterface = "com.ebook.ebook.webservice.SearchWebService")
public class SearchWebServiceImpl implements SearchWebService {

    @Autowired
    private BookService bookService;


    @Override
    public String searchBook(String key)
    {
        return bookService.searchBook(key);
    }
}
