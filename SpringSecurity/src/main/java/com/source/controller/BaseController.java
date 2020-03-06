package com.source.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.source.bean.ResponseBean;


@RestController
public abstract class BaseController {
	protected static final Logger LOGGER = LoggerFactory.getLogger(BaseController.class);

	protected ResponseEntity<ResponseBean> responseError(ResponseBean response, String message) {
		if (response.getCode().equals(HttpStatus.UNAUTHORIZED.value() + ""))
			return new ResponseEntity<ResponseBean>(response, HttpStatus.UNAUTHORIZED);
		response.setMessage(message);
		response.setCode("500");
		return new ResponseEntity<ResponseBean>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	protected ResponseEntity<ResponseBean> response(ResponseBean response) {
		if (response.getCode().equals(HttpStatus.UNAUTHORIZED.value() + ""))
			return new ResponseEntity<ResponseBean>(response, HttpStatus.UNAUTHORIZED);
		return new ResponseEntity<ResponseBean>(response, HttpStatus.OK);
	}

}
