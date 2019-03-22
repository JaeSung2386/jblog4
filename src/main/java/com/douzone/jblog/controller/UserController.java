package com.douzone.jblog.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.douzone.dto.JSONResult;
import com.douzone.jblog.service.UserService;
import com.douzone.jblog.vo.UserVo;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	// 회원가입 ok
	@RequestMapping(value="/join", method=RequestMethod.GET)
	public String join() {
		return "user/join";
	}

	@RequestMapping(value="/join", method=RequestMethod.POST)
	public String join(
			@ModelAttribute @Valid UserVo userVo,
			BindingResult result,
			Model model) {
		
		if(result.hasErrors()) {
			model.addAllAttributes(result.getModel());
			return "user/join";
		}
		userService.join(userVo);
		return "redirect:/user/joinsuccess";
	}
	
	// 회원가입 성공 ok
	@RequestMapping("/joinsuccess")
	public String joinsuccess() {
		return "user/joinsuccess";
	}
	
	// 로그인 ok
	@RequestMapping("/login")
	public String login() {
		return "user/login";
	}
	
	// id 찾기
	@RequestMapping("/find_id")
	public String find_id() {
		return "user/find_id";
	}

	// password 찾기
	@RequestMapping("/find_pass")
	public String find_pass() {
		return "user/find_pass";
	}
	
	@ResponseBody
	@RequestMapping("/checkid")
	public JSONResult checkId(@RequestParam(value="id", required=true, defaultValue="") String id) {
		boolean exist = userService.existEmail(id);
		return JSONResult.success(exist);
	}

//	@ExceptionHandler(UserDaoException.class)
//	public String handleUserDaoException() {
//		// 1. 로깅 작업
//		// 2. 페이지 전환(사과 페이지)
//		return "error/exception";
//	}
}
