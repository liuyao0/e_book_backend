package com.ebook.ebook.serviceimpl;

import com.ebook.ebook.service.CartService;
import com.ebook.ebook.service.MessageSendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class MessageSendServiceImpl implements MessageSendService {

    @Autowired
    CartService cartService;

    @Autowired
    JmsTemplate jmsTemplate;

    public String sendCartToOrderMessage(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
    {
        String checkResult=cartService.checkBookInCartInventory(httpServletRequest,httpServletResponse);
        if(!checkResult.isEmpty())
            return checkResult;
        Integer userId=(Integer)(httpServletRequest.getSession().getAttribute("userId"));
        if(userId==null)
            return "您还没有登录！";
        jmsTemplate.convertAndSend("orderRequestBox",userId);
        return "";
    }

}
