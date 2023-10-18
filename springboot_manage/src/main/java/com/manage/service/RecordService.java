package com.manage.service;

import com.manage.bean.Record;
import com.manage.mapper.RecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecordService {
	@Autowired
	private RecordMapper recordMapper;

	public List<Record> selectAll() {
		return recordMapper.selectList(null);
	}

	public int deleteById(long id) {
		System.out.println("RecordService->deleteById--> id: " + id + " 即将被删除出数据库");
		return recordMapper.deleteById(id);
	}

	public int insertOne(Record re) {
		System.out.println("RecordService->insertOne--> " + re + " 即将插入数据库");
		return recordMapper.insert(re);
	}
}
