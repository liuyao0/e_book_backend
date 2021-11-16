package com.ebook.ebook.serviceimpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ebook.ebook.dao.*;
import com.ebook.ebook.entity.*;
import com.ebook.ebook.service.BookService;
import com.ebook.ebook.util.SolrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import javax.print.DocFlavor;
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

    @Autowired
    private SolrUtil solrUtil;

    @Autowired
    private BookLabelDao bookLabelDao;

    @Autowired
    private LabelDao labelDao;

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

    @Override
    public String searchBook(String string)
    {
        ArrayList<JSONArray> booksJson = new ArrayList<JSONArray>();
        Set<Integer> set=new HashSet<>();
        List<Book> bookList=bookDao.findAll();
        bookList=bookList.stream().filter(book-> book.getName().contains(string)).collect(Collectors.toList());
        for(Book book:bookList)
            set.add(book.getBookId());
        List<Integer> solrBookIdList=solrUtil.searchBookByDescription(string);
        for(Integer bookId:solrBookIdList) {
            Book book= bookDao.getOne(bookId);
            if(book!=null&&!set.contains(bookId))
                bookList.add(book);
        }

        for(Book book:bookList)
        {
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
    public String searchBooksByLabelName(String labelName) {
        Set<Label> labelSetLevel1,labelSetLevel2=new HashSet<>();
        Set<Label> labelSet=new HashSet<>();
        Label label0= labelDao.findByName(labelName);
        labelSet.add(label0);
        labelSetLevel1=label0.getSimilarLabels();
        for(Label label1:labelSetLevel1)
            labelSetLevel2.addAll(label1.getSimilarLabels());
        labelSet.addAll(labelSetLevel1);
        labelSet.addAll(labelSetLevel2);
        List<Book> bookList=new ArrayList<>();
        for(Label label:labelSet)
        {
            List<Integer> bookIdList=bookLabelDao.getBookIdsByLabelName(label.getName());
            for(Integer bookId:bookIdList)
            {
                Book book=bookDao.getOne(bookId);
                if(!bookList.contains(book))
                    bookList.add(book);
            }
        }

        ArrayList<JSONArray> booksJson = new ArrayList<JSONArray>();

        for(Book book:bookList)
        {
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
}
