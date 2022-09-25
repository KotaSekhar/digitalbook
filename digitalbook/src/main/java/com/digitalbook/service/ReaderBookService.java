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


@Service
public class ReaderBookService {
	
	@Autowired
	private BookRepository bookRepository;
	@Autowired
	private MyQueryRepositoryCustom myQueryRepositoryCustom;
	
	@Autowired
	private OrderRepository orderRepository;
	public String searchBooks(String category, String author, String price, String publisher) {
		String dynamicquery = "select * from books where ";
		String query = "";
		HashMap<String, String> hashMap = new HashMap<String, String>();
		if (!category.equalsIgnoreCase("NA")) {
			hashMap.put("book_category", category);
		}else {
			hashMap.put("book_category", "NA");
		}
		if (!author.equalsIgnoreCase("NA")) {
			hashMap.put("book_author", author);
		}else {
			hashMap.put("book_author", "NA");
		}
		if (!price.equalsIgnoreCase("NA")) {
			hashMap.put("book_price", price);
		}else {
			hashMap.put("book_price", "NA");
		}

		if (!publisher.equalsIgnoreCase("NA")) {
			hashMap.put("book_publisher", publisher);
		}else {
			hashMap.put("book_publisher", "NA");
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
				jsonObject.put("title", books.getTitle());
				jsonObject.put("author", books.getAuthor());
				jsonObject.put("category", books.getCategory());
				jsonObject.put("price", books.getPrice());
				jsonObject.put("publisher", books.getPublisher());
				jsonObject.put("logo", books.getLogo());
				jsonObject.put("publishedDate", books.getPublishedDate());
				jsonObject.put("active", books.getActive());
				jsonObject.put("content", books.getContent());
				jsonArray.put(jsonObject);
			 }
			return jsonArray.toString();
		} else {
			List<Object[]> executeQuery = myQueryRepositoryCustom.executeQuery(dynamicquery); 
			for(Object[] obj:executeQuery) {
				System.out.println("obj==0"+obj[0]+"obj==1"+obj[1]+"obj==2"+obj[2]+"obj==3"+obj[3]+"obj==4"+obj[4]+"obj==5"+obj[5]+"obj==6"+obj[6]+"obj==7"+obj[7]+"obj==8"+obj[8]+"obj==9"+obj[9]);
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("bookId", obj[0]);
				jsonObject.put("title",obj[7]);
				jsonObject.put("author",obj[1]);
				jsonObject.put("category",obj[2]);
				jsonObject.put("price", obj[4]);
				jsonObject.put("publisher",obj[6]);
				jsonObject.put("logo", obj[3]);
				jsonObject.put("publishedDate", obj[5]);
				jsonObject.put("active", obj[8]);
				jsonObject.put("content", obj[9]);
				jsonArray.put(jsonObject);
			}
			
			return jsonArray.toString();
		}

	}

	public String getAllPurchagedBooks(String emailId) {
		String response="failure";
		JSONArray jsonArray = new JSONArray();
		JSONObject object = new JSONObject();
			List<Object[]> allPurchagedBooks = bookRepository.getAllPurchagedBooks("kumar@gamil.com");
			System.out.println("allPurchagedBooks=="+allPurchagedBooks.size());
			if(!allPurchagedBooks.isEmpty()) {
			for(Object[] obj:allPurchagedBooks) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("bookId", obj[0]);
				jsonObject.put("title",obj[1]);
				jsonObject.put("author",obj[2]);
				jsonObject.put("category",obj[3]);
				jsonObject.put("price", obj[4]);
				jsonObject.put("publisher",obj[5]);
				jsonObject.put("logo", obj[6]);
				jsonObject.put("publishedDate", obj[7]);
				jsonObject.put("content", obj[11]);
				jsonObject.put("readerId", obj[8]);
				jsonArray.put(jsonObject);
			}
		}else {
			response="failure";
			object.put("response", response);
			jsonArray.put(object);
		}
			
		return jsonArray.toString();
	}

	public String buyABook(String request) {
		String response="failure";
		JSONObject jsonObject = new JSONObject(request);
		int bookId = jsonObject.getInt("bookId");
		String readerName = jsonObject.getString("readerName");
		String readerEmailId = jsonObject.getString("readerEmailId");
		List<Object[]> book = bookRepository.checkExistUserAndBooks(bookId,readerName,readerEmailId);
		System.out.println("book"+book);
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
			response="failure";
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
			jsonObject.put("bookId", objects[8]);
		}else {
			response="failure";
			jsonObject.put("response", response);
		}
		
		
		return jsonObject.toString();
	}

	public String validateBuyABookRequest(String request) {
		String response="failure";
		JSONObject jsonObject = new JSONObject(request);
		Integer bookId = jsonObject.getInt("bookId");
		String readerName = jsonObject.getString("readerName");
		String readerEmailId = jsonObject.getString("readerEmailId");
		
		if(readerName.isEmpty()) {
			response="Reader Name Should Not Be Empty";
		}else if(readerEmailId.isEmpty()) {
			response="Reader Email Should Not Be Empty";
		}else if(bookId.equals("")) {
			response="Book Id Should Not Be Empty";
		}else {
			response="success";
		}
		return response;
		
	}

	public String deleteBook(int bookId, int readerId) {
		orderRepository.deleteBook(bookId,readerId);
		
		return new JSONObject().put("response", "Successfully Deleted").toString();
	}

}
