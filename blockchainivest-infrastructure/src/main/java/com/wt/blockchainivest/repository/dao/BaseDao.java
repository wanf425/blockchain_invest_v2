package com.wt.blockchainivest.repository.dao;

import com.google.gson.Gson;
import com.wt.blockchainivest.repository.dto.BaseDto;
import com.xiaoleilu.hutool.db.Session;
import com.xiaoleilu.hutool.db.SqlRunner;
import com.xiaoleilu.hutool.db.ds.DSFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;

public class BaseDao<T extends BaseDto> {
	protected static SqlRunner runner = SqlRunner.create();
	protected static Session session = Session.create();

	Gson gson = new Gson();

	public static void main(String[] args) {
		System.out.println(Thread.currentThread().getContextClassLoader().getResource(""));
		System.out.println(System.getProperty("user.dir"));
		DataSource ds = DSFactory.get();
		System.out.println("ok");
	}
}
