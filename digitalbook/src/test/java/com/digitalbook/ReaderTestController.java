package com.digitalbook;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.digitalbook.controller.ReaderController;
import com.digitalbook.service.ReaderBookService;

@ExtendWith(MockitoExtension.class)
public class ReaderTestController {

	@InjectMocks
	private ReaderController readerController;
	@Mock
	ReaderBookService readerBookService;
	
	
}
