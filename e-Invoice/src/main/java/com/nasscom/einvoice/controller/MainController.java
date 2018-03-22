package com.nasscom.einvoice.controller;

import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
/**
 *  Don't use RestController as this method is mapping to a static file not a JSON
 *  its only use for integration
 * @author manoj.kumar4
 *
 */
@Controller 
public class MainController {

  @RequestMapping(value={"/"})
	public String index() {
		return "index";
	}

}
