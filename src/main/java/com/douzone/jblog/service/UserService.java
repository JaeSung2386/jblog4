package com.douzone.jblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.jblog.repository.BlogDao;
import com.douzone.jblog.repository.CategoryDao;
import com.douzone.jblog.repository.UserDao;
import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.CategoryVo;
import com.douzone.jblog.vo.UserVo;

@Service
public class UserService {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private BlogDao blogDao;
	
	@Autowired
	private CategoryDao categoryDao;
	
	// 1. 회원가입
	public void join(UserVo userVo) {
		// 1. DB에 가입 회원 정보 insert 하기
		long no = userDao.insert(userVo);
		// 회원가입 실패시
		if(no == 0) {
		} else {
			BlogVo blogVo = new BlogVo();
			blogVo.setTitle(userVo.getId() + "의 블로그");
			blogVo.setLogo("");
			blogVo.setUser_no(userVo.getNo());
			blogDao.insert(blogVo);
			
			CategoryVo categoryVo = new CategoryVo();
			categoryVo.setName("미분류");
			categoryVo.setDescription("기본 카테고리");
			categoryVo.setUser_no(userVo.getNo());
			categoryDao.insert(categoryVo);
		}
	}
	
	public UserVo getUser( String id, String password ) {
		return userDao.get( id, password );
	}
	
	public UserVo getUser(long no) {
		return userDao.get(no);
	}
	
	public boolean existEmail( String id ) {
		UserVo userVo = userDao.get( id );
		return userVo != null;
	}
}
