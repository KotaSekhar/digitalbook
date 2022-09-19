package com.digitalbook.service;

import java.math.BigDecimal;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

import com.digitalbook.entity.Book;
import com.digitalbook.repository.BookRepository;
import com.digitalbook.repository.ReadresRepository;


@Service
public class ReaderBookService {
	@Autowired
	BookRepository bookRepository;

	@Autowired
	MyQueryRepositoryCustom myQueryRepositoryCustom;
	
	@Autowired
	ReadresRepository readresRepository;

	public String searchBooks(String category, String author, String price, String publisher) throws JSONException {
		String dynamicquery = "select * from books where ";
		String query = "";
		HashMap<String, String> hashMap = new HashMap<String, String>();
		if (!category.equalsIgnoreCase("NA")) {
			hashMap.put("book_category", category);
		}
		if (!author.equalsIgnoreCase("NA")) {
			hashMap.put("book_author", author);
		}
		if (!price.equalsIgnoreCase("NA")) {
			hashMap.put("book_price", price);
		}

		if (!publisher.equalsIgnoreCase("NA")) {
			hashMap.put("book_publisher", publisher);
		}
		Set<Entry<String,String>> entrySet = hashMap.entrySet();
		if(!hashMap.isEmpty()) {
			Iterator<Entry<String, String>> iterator = entrySet.iterator();
		while(iterator.hasNext()) {
			Entry<String, String> map = iterator.next();
			query=query.concat(" ").concat(map.getKey()).concat("= '").concat(map.getValue()).concat("' and ");
		}
		System.out.println("jquery===="+query);
		dynamicquery = dynamicquery + query;
		 dynamicquery = dynamicquery.substring(0, dynamicquery.length() - 5);
		System.out.println("dynamic dyquery====" + dynamicquery);
		}
		JSONArray jsonArray = new JSONArray();
		if (category.equalsIgnoreCase("NA") && author.equalsIgnoreCase("NA") && price.equalsIgnoreCase("NA")
				&& publisher.equalsIgnoreCase("NA")) {
			 List<Book> bookDetails = bookRepository.getBookDetails();
			 
			 for(Book books:bookDetails) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("bookId", books.getId());
				jsonObject.put("bookTitle", books.getTitle());
				jsonObject.put("bookAuthor", books.getAuthor());
				jsonObject.put("bookCategory", books.getCategory());
				jsonObject.put("bookPrice", books.getPrice());
				jsonObject.put("bookpublisher", books.getPublisher());
				jsonObject.put("bookLogo", books.getLogo());
				jsonObject.put("bookPublishedDate", books.getPublishedDate());
				jsonArray.put(jsonObject);
			 }
			return jsonArray.toString();
		} else {
			List<Object[]> executeQuery = myQueryRepositoryCustom.executeQuery(dynamicquery); 
			for(Object[] obj:executeQuery) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("bookId", obj[0]);
				jsonObject.put("bookName",obj[1]);
				jsonObject.put("bookAuthor",obj[2]);
				jsonObject.put("bookCategory",obj[3]);
				jsonObject.put("bookPrice", obj[4]);
				jsonObject.put("bookPublisher",obj[5]);
				jsonObject.put("bookLogo", obj[6]);
				jsonObject.put("bookPublishedDate", obj[7]);
				jsonArray.put(jsonObject);
			}
			return jsonArray.toString();
		}

	}

	public String getAllPurchagedBooks(String emailId) {
		
		JSONArray jsonArray = new JSONArray();
		try {
			
			List<Object[]> allPurchagedBooks = readresRepository.getAllPurchagedBooks(emailId);
			for(Object[] obj:allPurchagedBooks) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("bookId", obj[0]);
				jsonObject.put("bookName",obj[1]);
				jsonObject.put("bookAuthor",obj[2]);
				jsonObject.put("bookCategory",obj[3]);
				jsonObject.put("bookPrice", obj[4]);
				jsonObject.put("bookPublisher",obj[5]);
				jsonObject.put("bookLogo", obj[6]);
				jsonObject.put("bookPublishedDate", obj[7]);
				jsonObject.put("readerId", obj[8]);
				jsonObject.put("readerName", obj[9]);
				jsonObject.put("readerEmail", obj[10]);
				jsonArray.put(jsonObject);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return jsonArray.toString();
	}

}
