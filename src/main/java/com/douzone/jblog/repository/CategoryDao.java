package com.douzone.jblog.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.jblog.vo.CategoryVo;

@Repository
public class CategoryDao {
	@Autowired
	private SqlSession sqlSession;
	
	public long insert(CategoryVo categoryVo) {
		return sqlSession.insert("category.insert", categoryVo);
	}
	
	public List<CategoryVo> getList(long user_no) {
		return sqlSession.selectList("category.getList", user_no);
	}
	
	public long get(long user_no, String name) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("user_no", user_no);
		map.put("name", name);
		
		return sqlSession.selectOne("category.get", map);
	}
	
	public CategoryVo get(long no) {
		CategoryVo categoryVo = sqlSession.selectOne("category.getNo", no);
		return categoryVo;
	}
	
	public long delete(long no) {
		int count = sqlSession.delete("category.delete", no);
		
		if(count != 0) {
			return no;
		} else {
			return -1;
		}
	}
}
