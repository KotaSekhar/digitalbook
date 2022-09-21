package com.digitalbook.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.digitalbook.entity.Book;
@Transactional
@Repository
public interface BookRepository extends CrudRepository<Book, Integer> {

	@Query("From Book")
	List<Book> getBookDetails();
	
	@Query(value="SELECT b.book_title,u.id FROM digitalbooksdb.books b,digitalbooksdb.users u where b.book_id=:bookId and u.username=:readerName and u.email=:readerEmailId and u.role='READER' and u.status='LOGIN'",nativeQuery = true)
	List<Object[]> checkExistUserAndBook(int bookId, String readerName, String readerEmailId);

	@Query(value="SELECT b.book_title,b.book_author,b.book_category,b.book_logo,b.book_price,b.book_published_date,b.book_publisher,b.book_content,b.book_active FROM digitalbooksdb.books b,digitalbooksdb.users u  where u.id=:authorId and b.book_id=:bookId and u.username=b.book_author and u.role='AUTHOR' and u.status='LOGIN'",nativeQuery = true)
	List<Object[]> checkExistUserAndBook(int bookId, String authorId);
	
	Book findById(int bookId);

	@Modifying(clearAutomatically = true)
	@Query("update Book b set b.active=:type where b.id=:bookId")
	void blockUnblockBook(@Param("bookId") int bookId,@Param("type") String type);

}
