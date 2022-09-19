package com.digitalbook.service;

import java.sql.Date;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.digitalbook.DTO.BookDTO;
import com.digitalbook.entity.Author;
import com.digitalbook.entity.Book;
import com.digitalbook.entity.Category;
import com.digitalbook.repository.AuthorRpository;
import com.digitalbook.repository.BookRepository;


@Service
public class AuthorBookService {
	@Autowired
	AuthorRpository authorRpository;
	@Autowired
	BookRepository bookRepository;

	public String createBookByAuthor(int authorid, BookDTO bookDTO) {
		String response = "";
		try {
			Timestamp timestamp =new Timestamp(System.currentTimeMillis());
			Author author = authorRpository.findById(authorid);
			Book books = bookRepository.save(Book.builder().title(bookDTO.getTitle())
					.category(Category.valueOf(bookDTO.getCategory())).author(author.getName())
					.price(bookDTO.getPrice()).publisher(bookDTO.getPublisher())
					.logo(bookDTO.getLogo()).publishedDate(timestamp).build());
			if (books != null) {
				response = "create book successfully";
			} else {
				response = "create book failure";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;

	}

}
