package com.digitalbook.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@Table(name = "books")
@Builder(toBuilder = true)
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "book_id")
	private int id;

	@Column(name = "book_title")
	private String title;
	
	
	@Column(name = "book_category")
	 @Enumerated(EnumType.STRING)
	private Category category;

	@Column(name = "book_author")
	private String author;

	@Column(name = "book_price")
	private BigDecimal price;

	@Column(name = "book_publisher")
	private String publisher;

	@Column(name = "book_logo")
	private String logo;

	@Column(name = "book_published_date")
	private Timestamp publishedDate;

	@Column(name = "book_active")
	private String active;
	
	@Column(name = "book_content")
	private String content;
}
