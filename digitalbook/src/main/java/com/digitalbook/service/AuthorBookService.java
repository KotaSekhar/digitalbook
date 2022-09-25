package com.digitalbook.service;

import java.sql.Date;


import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.digitalbook.DTO.BookDTO;
import com.digitalbook.DTO.EditBookDTO;
import com.digitalbook.entity.Book;
import com.digitalbook.entity.Category;
import com.digitalbook.repository.BookRepository;
import com.digitalbook.repository.UserRepository;

@Service
public class AuthorBookService {
	@Autowired
	BookRepository bookRepository;
	@Autowired
	UserRepository userRepository;

	public String createBookByAuthor(int authorid, BookDTO bookDTO) {
		String response = "";
		JSONObject jsonObject = new JSONObject();
		try {
			 Timestamp timestamp = Timestamp.valueOf( "2022-09-25 12:12:05.638");
			Book books = bookRepository.save(Book.builder().title(bookDTO.getTitle())
					.category(Category.valueOf(bookDTO.getCategory())).author(bookDTO.getAuthor())
					.price(bookDTO.getPrice()).publisher(bookDTO.getPublisher()).logo(bookDTO.getLogo())
					.publishedDate(bookDTO.getPublishedDate()).active(bookDTO.getActive()).content(bookDTO.getContent()).build());
			if (books != null) {
				response = "create book successfully";
				jsonObject.put("bookId", books.getId());
			} else {
				response = "create book failure";
			}
		} catch (Exception e) {
			e.printStackTrace();
			response = "create book failure";
		}
		jsonObject.put("response", response);
		return jsonObject.toString();

	}

	public String validateBookDTO(BookDTO bookDTO) {
		String response = "failure";
		String category = bookDTO.getCategory();
		if (StringUtils.isEmpty(bookDTO.getTitle())) {
			response = "Title Should Not Be Empty";
		} else if (StringUtils.isEmpty(category)) {
			response = "Category Should Not Be Empty";
		} else if (!(category.equalsIgnoreCase("COMIC") || category.equalsIgnoreCase("SIFI")
				|| category.equalsIgnoreCase("ACTION") || category.equalsIgnoreCase("LOVE"))) {
			response = "Please Enter Valid Category";
		} else if (StringUtils.isEmpty(bookDTO.getPublisher())) {
			response = "Publisher Should Not Be Empty";
		} else if (StringUtils.isEmpty(bookDTO.getPublishedDate())) {
			response = "PublishedDate Should Not Be Empty";
		} else if (StringUtils.isEmpty(bookDTO.getLogo())) {
			response = "Logo Should Not Be Empty";
		} else if (StringUtils.isEmpty(bookDTO.getActive())) {
			response = "Active Should Not Be Empty";
		} else if (!(bookDTO.getActive().equalsIgnoreCase("ACTIVE")
				|| bookDTO.getActive().equalsIgnoreCase("INACTIVE"))) {
			response = "Title Should Not Be Empty";
		} else if (StringUtils.isEmpty(bookDTO.getContent())) {
			response = "Content Should Not Be Empty";
		} else {
			response = "Success";
		}
		return response;
	}

	public String editBookByAuthor(int authorId, int bookId,EditBookDTO editBookDTO) {
		String response = "failure";
		List<Object[]> list = bookRepository.checkExistUserAndBook(bookId, authorId);

		JSONObject jsonObject = new JSONObject();
		if (!list.isEmpty()) {
			Book book = bookRepository.findById(bookId);
			System.out.println("book==="+book);
			book.setCategory(Category.valueOf(editBookDTO.getCategory()));
			book.setTitle(editBookDTO.getTitle());
			book.setPublisher(editBookDTO.getPublisher());
			book.setPrice(editBookDTO.getPrice());
			book.setLogo(editBookDTO.getLogo());
			book.setContent(editBookDTO.getContent());
			System.out.println(book);
			bookRepository.save(book);
		
			response = "Edit the book Successfully";
			
		} else {
			response = "invalid user";
			
		}
		jsonObject.put("response", response);
		return jsonObject.toString();
	}

	public String blockUnblockBookByAuthor(int authorId, int bookId, String type) {
		String response = "failure";
		List<Object[]> list = bookRepository.checkExistUserAndBook(bookId, authorId);

		JSONObject jsonObject = new JSONObject();
		if (!list.isEmpty()) {
			bookRepository.blockUnblockBook(bookId,type);
			response =type.concat("Successfully") ;
			jsonObject.put("response", response);
		} else {
			response = "invalid user";
			jsonObject.put("response", response);
		}

		return jsonObject.toString();
		
	}
}
