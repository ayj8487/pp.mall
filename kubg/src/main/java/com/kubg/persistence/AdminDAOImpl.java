package com.kubg.persistence;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.kubg.domain.CategoryVO;

@Repository
public class AdminDAOImpl implements AdminDAO {

	@Inject
	private SqlSession sql;
	
	//매퍼
	private static String namespace = "com.kubg.mappers.adminMapper";

	//카테고리
	@Override
	public List<CategoryVO> category() throws Exception {

		return sql.selectList(namespace + ".category");
	}
	


}
