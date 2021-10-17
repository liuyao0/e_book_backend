package com.ebook.ebook.webservice;
import javax.jws.WebService;

@WebService(name="searchBook",
targetNamespace = "http://webservice.ebbok.ebook.com")
public interface SearchWebService {
    public String searchBook(String key);
}
