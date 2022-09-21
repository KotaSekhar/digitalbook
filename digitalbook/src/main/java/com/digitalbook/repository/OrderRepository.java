package com.digitalbook.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import org.springframework.stereotype.Repository;

import com.digitalbook.entity.Order;
@Repository
public interface OrderRepository extends CrudRepository<Order, Integer>{

	@Query(value="SELECT b.book_title,b.book_content,b.book_author,b.book_category,b.book_logo,b.book_price,b.book_published_date,b.book_publisher FROM digitalbooksdb.books b,digitalbooksdb.users u ,digitalbooksdb.order_details o where b.book_id=:bookId and b.book_active='ACTIVE' and o.reader_id=u.id and u.email=:emailId and u.role='READER' and u.status='LOGIN'",nativeQuery = true)
	List<Object[]> checkExistUserByEmailAndBook(String emailId, int bookId);

	@Query(value="SELECT b.book_title,b.book_content,b.book_author,b.book_category,b.book_logo,b.book_price,b.book_published_date,b.book_publisher FROM digitalbooksdb.books b,digitalbooksdb.users u ,digitalbooksdb.order_details o where o.paymentId=:pid and b.book_id=o.book_id and b.book_active='ACTIVE' and o.reader_id=u.id and u.email=:emailId and u.role='READER' and u.status='LOGIN'",nativeQuery = true)
	List<Object[]> checkExistUserByEmailAndBookByPaymentId(String emailId, String pid);

}
