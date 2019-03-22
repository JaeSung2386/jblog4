package com.douzone.jblog.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.dto.JSONResult;
import com.douzone.jblog.service.BlogService;
import com.douzone.jblog.service.FileuploadService;
import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.CategoryVo;
import com.douzone.jblog.vo.CommentVo;
import com.douzone.jblog.vo.PostVo;
import com.douzone.jblog.vo.UserVo;

@Controller
@RequestMapping("/{user_id:(?!assets|uploads).*}")
@SessionAttributes("blogVo")
public class BlogController {

	@Autowired
	private FileuploadService fileuploadService;
	
	@Autowired
	private BlogService blogService;
	
	// 블로그 메인 페이지
	@RequestMapping({"", "/{categoryNo}", "/{categoryNo}/{postNo}"})
	public String list(
			Model model,
			@RequestParam (value="page", required=false, defaultValue = "1") Integer page,
			@PathVariable Optional <Long> categoryNo,
			@PathVariable Optional <Long> postNo,
			@PathVariable(value="user_id") String user_id) {
		
		model.addAttribute("id", user_id);
		model.addAttribute("blogVo", blogService.getBlog(user_id));
		
		model.addAttribute("categorylist", blogService.getCategory(user_id));
		long user_no = blogService.getNo(user_id);
		long post_no = 0;
		
		// 블로그 메인 페이지 처음 접속
		if(!categoryNo.isPresent() && !postNo.isPresent()) {
			System.out.println("블로그 메인 컨트롤러 categoryNo, postNo 없음");
			// default 카테고리(미분류)의 카테고리 번호를 가져옴
			long defaultNo = blogService.getCategoryNo(user_no, "미분류");
			
			PostVo vo = blogService.getPost(user_id, defaultNo);
			
			// 게시글이 존재하지 않을경우
			if(vo != null) {
				post_no = blogService.getPost(user_id, defaultNo).getNo();
			}
			
			model.addAttribute("categoryNo", defaultNo);
			model.addAttribute("postlist", blogService.getPost(user_id, defaultNo, page));
			model.addAttribute("pageVo", blogService.page(defaultNo, page));
			
			model.addAttribute("postVo", blogService.getPost(user_id, defaultNo));
			model.addAttribute("commentlist", blogService.getComment(post_no));
		} 
		
		// 카테고리만 클릭
		else if(categoryNo.isPresent() && !postNo.isPresent()) {
			System.out.println("블로그 메인 컨트롤러 categoryNo 있음 postNo 없음");
			
			// 포스트가 존재하면 조건문 실행
			if(!blogService.getPost(user_id, categoryNo.get(), page).isEmpty()) {
				post_no = blogService.getPost(user_id, categoryNo.get()).getNo();
				model.addAttribute("categoryNo", categoryNo.get());
				model.addAttribute("postlist", blogService.getPost(user_id, categoryNo.get(), page));
				model.addAttribute("pageVo", blogService.page(categoryNo.get(), page));
				model.addAttribute("postVo", blogService.getPost(user_id, categoryNo.get()));
				model.addAttribute("commentlist", blogService.getComment(post_no));
			}
		}
		
		// 카테고리의 포스트 클릭
		else if(categoryNo.isPresent() && postNo.isPresent()) {
			System.out.println("블로그 메인 컨트롤러 categoryNo, postNo 있음");
			model.addAttribute("categoryNo", categoryNo.get());
			model.addAttribute("postVo", blogService.getPost(user_id, categoryNo.get(), postNo.get()));
			model.addAttribute("postlist", blogService.getPost(user_id, categoryNo.get(), page));
			model.addAttribute("pageVo", blogService.page(categoryNo.get(), page));
			model.addAttribute("commentlist", blogService.getComment(postNo.get()));
		}
		
		return "jblog/blog-main";
	}
	
	// 블로그 관리 페이지 ok
	@RequestMapping("/admin/basic")
	public String basic(
			Model model,
			HttpSession session,
			@PathVariable(value="user_id") String user_id) {
		//model.addAttribute("blogVo", blogService.getBlog(user_id));
		UserVo vo = (UserVo)session.getAttribute("authuser");
		
		if(vo.getId().equals(user_id)) {
			model.addAttribute("id", user_id);
			return "jblog/blog-admin-basic";
		}
		
		return "error/404";
	}
	
	// 블로그 관리 update ok
	@RequestMapping(value="/admin/basic", method=RequestMethod.POST)
	public String basicUpdate(
			@ModelAttribute BlogVo blogVo,
			@RequestParam(value="logo-file") MultipartFile multipartFile,
			@PathVariable(value="user_id") String user_id) {
		
		String logo = fileuploadService.restore(multipartFile);
		
		blogVo.setUser_no(blogService.getNo(user_id));
		blogVo.setLogo(logo);
		System.out.println("update 컨트롤러: " + blogVo);
		blogService.update(blogVo);
		
		return "redirect:/" + user_id + "/admin/basic";
	}
	
	@RequestMapping("/admin/category")
	public String ajax(
			Model model,
			HttpSession session,
			@PathVariable(value="user_id") String user_id) {
		
		UserVo vo = (UserVo)session.getAttribute("authuser");
		
		if(vo.getId().equals(user_id)) {
			model.addAttribute("id", user_id);
			return "jblog/blog-admin-category";
		}
		
		return "error/404";
	}
	
	@ResponseBody
	@RequestMapping("/{categoryNo}/{postVo.no}/commentlist")
	public JSONResult commentlist(
			@PathVariable(value="user_id") String user_id,
			@PathVariable(value="categoryNo") Long categoryNo,
			@PathVariable(value="postVo.no") Long postNo) {
		System.out.println("commentlist postNo: " + postNo);
		List<CommentVo> list = blogService.getComment(postNo);
		System.out.println(list);
		return JSONResult.success(list);
	}
	
	// 관리자 페이지 카테고리 view ok
	@ResponseBody
	@RequestMapping("/admin/category/list")
	public JSONResult category(
			@PathVariable(value="user_id") String user_id) {
		
		List<CategoryVo> list = blogService.getCategory(user_id);
		
		return JSONResult.success(list);
	}
	
	// 관리자 페이지 카테고리 insert ok
	@ResponseBody
	@RequestMapping(value = "/admin/category/add", method=RequestMethod.POST)
	public JSONResult categoryInsert(
			@RequestBody CategoryVo categoryVo,
			@PathVariable(value="user_id") String user_id) {
		
		categoryVo.setUser_no(blogService.getNo(user_id));
		
		return JSONResult.success(blogService.insert(categoryVo));
	}
	
	// 관리자 페이지 카테고리 delete ok
	@ResponseBody
	@RequestMapping(value = "/admin/category/delete", method=RequestMethod.POST)
	public JSONResult categoryDelete(
			@RequestParam (value="no", required=true) long no,
			@PathVariable(value="user_id") String user_id) {
		
		return JSONResult.success(blogService.delete(no));
	}
	
	// 관리자 페이지 게시글 작성 view ok
	@RequestMapping("/admin/write")
	public String write(
			Model model,
			HttpSession session,
			@PathVariable(value="user_id") String user_id) {
	
		UserVo vo = (UserVo)session.getAttribute("authuser");
		
		if(vo.getId().equals(user_id)) {
			model.addAttribute("id", user_id);
			model.addAttribute("categorylist", blogService.getCategory(user_id));
			
			return "jblog/blog-admin-write";
		}
		
		return "error/404";	
	}
	
	// 관리자 페이지 게시글 insert ok
	@RequestMapping(value="/admin/write", method=RequestMethod.POST)
	public String postWrite(
			@ModelAttribute PostVo postVo,
			@RequestParam(value="category") String category_name,
			@PathVariable(value="user_id") String user_id) {
		
		postVo.setCategory_no(blogService.getCategoryNo(blogService.getNo(user_id), category_name));
		
		blogService.insert(postVo);
		
		return "redirect:/" + user_id;
	}
	
	@RequestMapping(value="/comment/write", method=RequestMethod.POST)
	public String commentWrite(
			@ModelAttribute CommentVo commentVo,
			@RequestParam (value="post_no", required=true) long post_no,
			@PathVariable(value="user_id") String user_id) {
		
		commentVo.setPost_no(post_no);
		
		blogService.commentInsert(commentVo);
		
		return "redirect:/" + user_id;
	}
	
}
