package com.douzone.jblog.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.jblog.vo.PostVo;


@Repository
public class PostDao {

	@Autowired
	private SqlSession sqlSession;
	
	public List<PostVo> getList(long user_no) {
		return sqlSession.selectList("post.getList", user_no);
	}
	
	public List<PostVo> getList(long user_no, long category_no, int startRow, int endRow) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("user_no", user_no);
		map.put("category_no", category_no);
		map.put("startRow", startRow);
		map.put("endRow", endRow);
		
		return sqlSession.selectList("post.getCategoryList", map);
	}
	
	public PostVo get(
					long user_no, 
					long category_no,
					long post_no) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("user_no", user_no);
		map.put("category_no", category_no);
		map.put("post_no", post_no);
		return sqlSession.selectOne("post.getPost", map);
	}
	
	public PostVo get(
					long user_no, 
					long category_no) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("user_no", user_no);
		map.put("category_no", category_no);
		
		return sqlSession.selectOne("post.getPost", map);
	}
	
	public int insert(PostVo postVo) {
		return sqlSession.insert("post.insert", postVo);
	}
	
	public int getTotalCount(long category_no) {
		return sqlSession.selectOne("post.getTotalCount", category_no);
	}
}
