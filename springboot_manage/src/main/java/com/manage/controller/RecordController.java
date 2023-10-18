package com.manage.controller;

import com.manage.bean.Record;
import com.manage.service.RecordService;
import com.manage.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/record")
public class RecordController {
	@Autowired
	private RecordService recordService;

	@GetMapping("/list")
	public Result selectAll() {
		System.out.println("RecordController->selectAll--> 开始运行...");
		List<Record> records = recordService.selectAll();
		return Result.ok().data("items", records);
	}

	@PostMapping("/insert")
	public Result insertOne(@RequestBody Record re) {
		//接受前端传入JSON格式的参数，需要加上RequestBody的注解
		System.out.println("RecordController->insertOne--> 开始运行...");
		System.out.println("RecordController->insertOne--> 前端数据：" + re);
		try {
			recordService.insertOne(re);
			return Result.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error().msg("插入记录失败");
		}
	}

	@DeleteMapping("/delete")
	public Result deleteById(int id) {
		System.out.println("RecordController->deleteById--> 开始运行...");
		System.out.println("RecordController->deleteById--> 前端数据：" + id);
		try {
			recordService.deleteById(id);
			return Result.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error().msg("删除记录失败");
		}
	}
}
