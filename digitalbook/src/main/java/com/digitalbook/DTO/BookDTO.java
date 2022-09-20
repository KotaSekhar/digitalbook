package com.digitalbook.DTO;

import java.math.BigDecimal;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class BookDTO {


	private String title;

	private String category;

	private String author;

	private BigDecimal price;

	private String publisher;

	private String logo;
	
	private String publisherDate;
	
	private String active;
	
	private String content;

}
