package com.ebook.ebook.util;

import com.ebook.ebook.entity.Book;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class SolrUtil {
    @Autowired
    private SolrClient solrClient;

    public void saveBook(Book book)
    {
        SolrInputDocument doc=new SolrInputDocument();
        try {
            doc.setField("id",book.getBookId());
            doc.setField("description",book.getDescription());
            doc.setField("name",book.getName());
            solrClient.add(doc);
            solrClient.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteBook(String id)  {
        try {
            solrClient.deleteById(id);
            solrClient.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Integer> searchBookByDescription(String string)
    {
        List<Integer> result=new ArrayList<>();
        SolrQuery params = new SolrQuery();
        params.add("q","description:"+string);
        try {
            QueryResponse queryResponse = solrClient.query(params);
            SolrDocumentList solrDocuments = queryResponse.getResults();
            for(SolrDocument solrDocument:solrDocuments)
            {
                result.add(Integer.parseInt((String) solrDocument.get("id")));
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    return result;
    }
}
