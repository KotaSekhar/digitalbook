package com.digitalbook.DTO;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class EditBookDTO {

	private String title;

	private String category;

	private BigDecimal price;

	private String publisher;

	private String logo;

	private String content;
	
	private String type;

}
