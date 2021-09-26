package com.ebook.ebook.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface MessageSendService {
    String sendCartToOrderMessage(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);
}
