package com.ebook.ebook.serviceimpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ebook.ebook.dao.CartDao;
import com.ebook.ebook.dao.OrderDao;
import com.ebook.ebook.entity.Book;
import com.ebook.ebook.entity.Cart;
import com.ebook.ebook.entity.CartPK;
import com.ebook.ebook.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartDao cartDao;

    @Override
    public void changeCartNum(Integer userId,Integer bookId,Integer cartNum){
        cartDao.changeCartNum(userId,bookId,cartNum);
    }

    @Override
    public Integer addToCart(Integer userId,Integer bookId,Integer cartNum){
        return cartDao.addToCart(userId,bookId,cartNum);
    }

    @Override
    public void deleteFromCart(Integer userId,Integer bookId){
        cartDao.deleteFromCart(userId,bookId);
    }

    @Override
    public String getAllBookInCartByUserId(Integer userId)
    {
        Map<Book,Integer> books=cartDao.getAllBookByUserId(userId);
        ArrayList<JSONArray> booksJson = new ArrayList<JSONArray>();
        for(Map.Entry<Book,Integer> entry:books.entrySet()){
            Book book=entry.getKey();
            Integer cartBookNum=entry.getValue();
            ArrayList<Object> arrayList = new ArrayList<Object>();
            arrayList.add(book.getBookId());
            arrayList.add(book.getIsbn());
            arrayList.add(book.getImage());
            arrayList.add(book.getName());
            arrayList.add(book.getAuthor());
            arrayList.add(book.getPress());
            arrayList.add(book.getPrice());
            arrayList.add(cartBookNum);
            booksJson.add((JSONArray)JSONArray.toJSON(arrayList));
        }
        String booksString = JSON.toJSONString(booksJson, SerializerFeature.BrowserCompatible);
        return booksString;
    }

    @Override
    public String checkBookInCartInventory(Integer userId)
    {
        return cartDao.checkBookInCartInventory(userId);
    }

}
