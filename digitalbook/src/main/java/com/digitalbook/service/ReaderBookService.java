package com.digitalbook.service;



import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalbook.entity.Book;
import com.digitalbook.entity.Order;
import com.digitalbook.repository.BookRepository;
import com.digitalbook.repository.OrderRepository;
import com.digitalbook.repository.ReadresRepository;


@Service
public class ReaderBookService {
	@Autowired
	BookRepository bookRepository;

	@Autowired
	MyQueryRepositoryCustom myQueryRepositoryCustom;
	
	@Autowired
	ReadresRepository readresRepository;
	@Autowired
	OrderRepository orderRepository;
	public String searchBooks(String category, String author, String price, String publisher) {
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

	public String buyABook(String request) {
		String response="failure";
		JSONObject jsonObject = new JSONObject(request);
		int bookId = jsonObject.getInt("bookId");
		String readerName = jsonObject.getString("readerName");
		String readerEmailId = jsonObject.getString("readerEmailId");
		
		List<Object[]> book = bookRepository.checkExistUserAndBook(bookId,readerName,readerEmailId);
		if(!book.isEmpty()) {
			Random random = new Random();
			String paymentId = String.format("%04d", random.nextInt(10000));
			Object[] objects = book.get(0);
			Order order = new Order();
			order.setBookId(bookId);
			order.setReaderId(Integer.parseInt(objects[1].toString()));
			order.setPaymentId(paymentId);
			order.setStatus("COMPLETED");
			orderRepository.save(order);
			response="Buy a book Successfully";
		}else {
			response="failure";
		}
		JSONObject object = new JSONObject();
		object.put("response", response);
		return object.toString();
	}

	public String readABook(String emailId, int bookId) {
		String response=null;
		List<Object[]> book = orderRepository.checkExistUserByEmailAndBook(emailId,bookId);
		JSONObject jsonObject = new JSONObject();
		if(!book.isEmpty()) {
			Object[] objects = book.get(0);
			
			jsonObject.put("title", objects[0]);
			jsonObject.put("content", objects[1]);
			jsonObject.put("author", objects[2]);
			jsonObject.put("category", objects[3]);
			jsonObject.put("logo", objects[4]);
			jsonObject.put("price", objects[5]);
			jsonObject.put("publishedDate", objects[6]);
			jsonObject.put("publisher", objects[7]);
		}else {
			response="invalid user";
			jsonObject.put("response", response);
		}
		
		
		return jsonObject.toString();
	}

	public String getBookByPaymentId(String emailId, String pid) {
		String response=null;
		System.out.println(emailId+""+pid);
		List<Object[]> book = orderRepository.checkExistUserByEmailAndBookByPaymentId(emailId,pid);
		JSONObject jsonObject = new JSONObject();
		if(!book.isEmpty()) {
			Object[] objects = book.get(0);
			
			jsonObject.put("title", objects[0]);
			jsonObject.put("content", objects[1]);
			jsonObject.put("author", objects[2]);
			jsonObject.put("category", objects[3]);
			jsonObject.put("logo", objects[4]);
			jsonObject.put("price", objects[5]);
			jsonObject.put("publishedDate", objects[6]);
			jsonObject.put("publisher", objects[7]);
		}else {
			response="invalid user";
			jsonObject.put("response", response);
		}
		
		
		return jsonObject.toString();
	}

}
