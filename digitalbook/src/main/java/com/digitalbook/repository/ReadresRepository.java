package com.digitalbook.repository;

import java.util.List;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.digitalbook.entity.Reader;

@Repository
public interface ReadresRepository extends CrudRepository<Reader, Integer> {

	@Query(value="SELECT b.book_id, b.book_title,b.book_category,b.book_author,b.book_price,b.book_publisher,b.book_logo,b.book_published_date,r.reader_id,r.reader_name,r.reader_email "
			+ "from digitalbooksdb.books b ,digitalbooksdb.reader_details r,digitalbooksdb.order_details o "
			+ "where b.book_id =o.book_id and o.reader_id=r.reader_id and r.reader_email=:emailId and o.status='completed';",nativeQuery = true)
	List<Object[]> getAllPurchagedBooks(String emailId);

}
