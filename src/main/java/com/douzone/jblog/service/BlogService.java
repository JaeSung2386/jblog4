package com.douzone.jblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.jblog.repository.BlogDao;
import com.douzone.jblog.repository.CategoryDao;
import com.douzone.jblog.repository.CommentDao;
import com.douzone.jblog.repository.PostDao;
import com.douzone.jblog.repository.UserDao;
import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.CategoryVo;
import com.douzone.jblog.vo.CommentVo;
import com.douzone.jblog.vo.PageVo;
import com.douzone.jblog.vo.PostVo;

@Service
public class BlogService {

	@Autowired
	private BlogDao blogDao;

	@Autowired
	private CategoryDao categoryDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private PostDao postDao;

	@Autowired
	private CommentDao commentDao;
	
	public List<CommentVo> getComment(long post_no) {
		return commentDao.getList(post_no);
	}
	
	public List<CategoryVo> getCategory(String id) {
		return categoryDao.getList(userDao.get(id).getNo());
	}
	
	public List<PostVo> getPost(String id) {
		return postDao.getList(userDao.get(id).getNo());
	}
	
	public List<PostVo> getPost(String id, Long category_no, int page) {
		PageVo pageVo = new PageVo();
		pageVo.setPageNo(page);
		pageVo.setPageSize(10);
		page = (page - 1) * 10;
		return postDao.getList(userDao.get(id).getNo(), category_no, page, pageVo.getPageSize());
	}
	
	public PostVo getPost(String id, Long category_no, Long postNo) {
		return postDao.get(userDao.get(id).getNo(), category_no, postNo);
	}
	
	public PostVo getPost(String id, Long category_no) {
		return postDao.get(userDao.get(id).getNo(), category_no);
	}
	
	public BlogVo getBlog(String id) {
		return blogDao.get(userDao.get(id).getNo());
	}
	
	public long getNo(String id) {
		return userDao.get(id).getNo();
	}
	
	public long getCategoryNo(long user_no, String name) {
		return categoryDao.get(user_no, name);
	}
	
	public void update(BlogVo blogVo) {
		blogDao.update(blogVo);
	}
	
	public CategoryVo insert(CategoryVo categoryVo) {
		long no = categoryDao.insert(categoryVo);
		if(no == 0) {
			return null;
		} else {
			return categoryDao.get(categoryVo.getNo());
		}
	}
	
	public void insert(PostVo postVo) {
		postDao.insert(postVo);
	}
	
	public long delete(long no) {
		long result = categoryDao.delete(no);
		
		// 삭제 실패
		if(result == -1) {
			return -1;
		} else {
			return result;
		}
	}
	
	public PageVo page(long categoryNo, int page) {
		PageVo pagevo = new PageVo();
		
		pagevo.setPageNo(page);
		pagevo.setPageSize(10);
		// 해당 카테고리의 포스트 갯수
		System.out.println("BlogService 카테고리의 포스트 개수: " + postDao.getTotalCount(categoryNo));
		//System.out.println("page서비스: " + categoryNo);
		pagevo.setTotalCount(postDao.getTotalCount(categoryNo));
		page = (page - 1) * 10;
		
		return pagevo;
	}
	
	public void commentInsert(CommentVo commentVo) {
		commentDao.insert(commentVo);
	}
}
