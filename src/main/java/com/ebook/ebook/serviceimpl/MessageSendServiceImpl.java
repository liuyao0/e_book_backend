package com.ebook.ebook.serviceimpl;

import com.ebook.ebook.service.CartService;
import com.ebook.ebook.service.MessageSendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageSendServiceImpl implements MessageSendService {

    @Autowired
    CartService cartService;

    @Autowired
    JmsTemplate jmsTemplate;

    public String sendCartToOrderMessage(Integer userId)
    {
        String checkResult=cartService.checkBookInCartInventory(userId);
        if(!checkResult.isEmpty())
            return checkResult;
        jmsTemplate.convertAndSend("orderRequestBox",userId);
        return "";
    }

}
