package com.digitalbook.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name="reader_details")
@ToString
public class Reader {

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "reader_id")
	private int id;
	
	@Column(name = "reader_name")
	private String readerName;
	
	@Column(name = "reader_email")
	private String readerEmail;
}
