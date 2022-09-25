package com.digitalbook.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import org.springframework.stereotype.Repository;

import com.digitalbook.entity.Order;
@Repository
@Transactional
public interface OrderRepository extends CrudRepository<Order, Integer>{

	@Query(value="SELECT b.book_title,b.book_content,b.book_author,b.book_category,b.book_logo,b.book_price,b.book_published_date,b.book_publisher FROM digitalbooksdb.books b,digitalbooksdb.users u ,digitalbooksdb.order_details o where b.book_id=:bookId and b.book_active='ACTIVE' and o.reader_id=u.id and u.email=:emailId and u.role='READER' and u.status='LOGIN'",nativeQuery = true)
	List<Object[]> checkExistUserByEmailAndBook(String emailId, int bookId);

	@Query(value="SELECT b.book_title,b.book_content,b.book_author,b.book_category,b.book_logo,b.book_price,b.book_published_date,b.book_publisher,b.book_id FROM digitalbooksdb.books b,digitalbooksdb.users u ,digitalbooksdb.order_details o where o.payment_id=:pid and b.book_id=o.book_id and b.book_active='ACTIVE' and o.reader_id=u.id and u.email=:emailId and u.role='READER' and u.status='LOGIN'",nativeQuery = true)
	List<Object[]> checkExistUserByEmailAndBookByPaymentId(String emailId, String pid);

	@Modifying
	@Query(value="DELETE FROM `digitalbooksdb`.`order_details` WHERE (book_id=:bookId and reader_id=:readerId)",nativeQuery = true)
	void deleteBook(int bookId, int readerId);

}
