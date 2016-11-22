package com.fdoochann.employservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Anna_Bildyug on 11/22/2016.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

	public BadRequestException(String message) {
		super(message);
	}
}

