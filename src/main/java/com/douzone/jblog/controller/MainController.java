package com.douzone.jblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
	
	
	// 무엇을 return할 것인가? 1.ModelAndView 2.String 3.Object
	// 1, 2는 viewname
	// ViewResolver 씨바새기 존나 어렵
	// ViewResolver한테 viewname을 전달해서 view를 return해달라고 요청함 
	// viewResolver은 view를 전달하기 위해서viewrender을 호출함
	// viewrender은 model이나 ModelAndView를 인수로 받음. - forwarding 작업이 일어나는 곳
	@RequestMapping({"", "/main"})
	public String main() {
		return "main/index";
	}
}
