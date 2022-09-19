package com.digitalbook.service;

import java.util.List;




public interface MyQueryRepositoryCustom {

	List<Object[]> executeQuery(String substring);

}
