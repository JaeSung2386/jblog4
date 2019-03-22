package com.douzone.jblog.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.jblog.vo.CommentVo;

@Repository
public class CommentDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	public long insert(CommentVo commentVo) {
		return sqlSession.insert("comment.insert", commentVo);
	}
	public List<CommentVo> getList(long post_no) {
		return sqlSession.selectList("comment.getList", post_no);
	}
}
