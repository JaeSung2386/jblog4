package com.douzone.jblog.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.jblog.vo.BlogVo;

@Repository
public class BlogDao {

	@Autowired
	private SqlSession sqlSession;
	
	public int insert(BlogVo blogVo) {
		return sqlSession.insert("blog.insert", blogVo);
	}
	
	public BlogVo get(long user_no) {
		System.out.println("블로그 DAO " + user_no);
		return sqlSession.selectOne("blog.get", user_no);
	}
	
	public int update(BlogVo blogVo) {
		System.out.println("Dao에서 blogVo출력: " + blogVo);
		return sqlSession.update("blog.update", blogVo);
	}
}
