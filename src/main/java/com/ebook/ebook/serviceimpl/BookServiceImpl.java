package com.ebook.ebook.serviceimpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ebook.ebook.dao.BookDao;
import com.ebook.ebook.dao.CartDao;
import com.ebook.ebook.dao.OrderDao;
import com.ebook.ebook.entity.Book;
import com.ebook.ebook.entity.Order;
import com.ebook.ebook.entity.OrderDetail;
import com.ebook.ebook.entity.User;
import com.ebook.ebook.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookDao bookDao;

    @Autowired
    private CartDao cartDao;

    @Autowired
    private OrderDao orderDao;

    @Override
    public String getAllBook()
    {
        List<Book> books = new ArrayList<Book>();
        books=bookDao.findAll();
        ArrayList<JSONArray> booksJson = new ArrayList<JSONArray>();
        Iterator<Book> iter = books.iterator();
        while (iter.hasNext()) {
            Book book = (Book) iter.next();
            if(book.getDeleted())
                continue;
            ArrayList<Object> arrayList = new ArrayList<Object>();
            arrayList.add(book.getBookId());
            arrayList.add(book.getName());
            arrayList.add(book.getAuthor());
            arrayList.add(book.getPress());
            arrayList.add(book.getPrice());
            arrayList.add(book.getIsbn());
            arrayList.add(book.getInventory());
            arrayList.add(book.getImage());
            arrayList.add(book.getDescription());
            booksJson.add((JSONArray) JSONArray.toJSON(arrayList));
        }
        String booksString = JSON.toJSONString(booksJson, SerializerFeature.BrowserCompatible);
        return booksString;
    }

    @Override
    public String getBookDetail(Integer bookId)
    {
        ArrayList<JSONArray> booksJson = new ArrayList<JSONArray>();
        Book book=bookDao.getOne(bookId);
        ArrayList<Object> arrayList = new ArrayList<Object>();
        arrayList.add(book.getBookId());
        arrayList.add(book.getName());
        arrayList.add(book.getIsbn());
        arrayList.add(book.getAuthor());
        arrayList.add(book.getPress());
        arrayList.add(book.getPrice());
        arrayList.add(book.getDescription());
        arrayList.add(book.getInventory());
        arrayList.add(book.getImage());
        booksJson.add((JSONArray) JSONArray.toJSON(arrayList));
        String booksString = JSON.toJSONString(booksJson, SerializerFeature.BrowserCompatible);
        return booksString;
    }

    @Override
    public String getAllBookInManager(){
        List<Book> books = new ArrayList<Book>();
        books=bookDao.findAll();
        ArrayList<JSONArray> booksJson = new ArrayList<JSONArray>();
        Iterator<Book> iter = books.iterator();
        while (iter.hasNext()) {
            Book book = (Book) iter.next();
            if(book.getDeleted())
                continue;
            ArrayList<Object> arrayList = new ArrayList<Object>();
            arrayList.add(book.getBookId());
            arrayList.add(book.getName());
            arrayList.add(book.getIsbn());
            arrayList.add(book.getAuthor());
            arrayList.add(book.getPress());
            arrayList.add(book.getPrice());
            arrayList.add(book.getInventory());
            arrayList.add(book.getImage());
            booksJson.add((JSONArray) JSONArray.toJSON(arrayList));
        }
        String booksString = JSON.toJSONString(booksJson, SerializerFeature.BrowserCompatible);
        return booksString;
    }

    @Override
    public void setBookDeletedByBookId(Integer bookId){
        bookDao.setBookDeletedByBookId(bookId);
        cartDao.deleteBookFromCartById(bookId);
    }

    @Override
    public Integer changeBook(Book book){
        return bookDao.changeBook(book);
    }

    @Override
    public String getSalesRanking(Timestamp beginTime, Timestamp endTime)
    {
        List<Order> orderList=orderDao.findAll();
        orderList=orderList.stream().filter(order -> {
            Timestamp timestamp=order.getOrderTime();
            if(order.getOrderTime().before(beginTime)||order.getOrderTime().after(endTime))
                return false;
            return true;
        }).collect(Collectors.toList());
        ArrayList<JSONArray> booksJson = new ArrayList<JSONArray>();
        Map<Book,Integer> map=new HashMap<>();
        for (Order order:orderList)
        {
            List<OrderDetail> orderDetailList=order.getOrderDetails();
            for(OrderDetail orderDetail:orderDetailList)
            {
                Book book=bookDao.getOne(orderDetail.getOrderDetailPK().getBookId());
                if(map.containsKey(book))
                {
                    Integer oldQuantity = map.get(book);
                    map.replace(book,oldQuantity+orderDetail.getQuantity());
                }
                else
                    map.put(book,orderDetail.getQuantity());
            }
        }
        List<Map.Entry<Book,Integer>> entryList=new ArrayList<Map.Entry<Book,Integer>>(map.entrySet());
        Collections.sort(entryList, new Comparator<Map.Entry<Book, Integer>>() {
            @Override
            public int compare(Map.Entry<Book, Integer> o1, Map.Entry<Book, Integer> o2) {
                return -o1.getValue().compareTo(o2.getValue());
            }
        });

        for(Map.Entry<Book,Integer> entry:entryList)
        {
            Book book = entry.getKey();
            Integer quantity=entry.getValue();
            ArrayList<Object> arrayList=new ArrayList<>();
            arrayList.add(book.getName());
            arrayList.add(book.getIsbn());
            arrayList.add(book.getAuthor());
            arrayList.add(book.getPress());
            arrayList.add(quantity);
            booksJson.add((JSONArray) JSONArray.toJSON(arrayList));
        }
        String booksString=JSON.toJSONString(booksJson,SerializerFeature.BrowserCompatible);
        return booksString;
    }

}
