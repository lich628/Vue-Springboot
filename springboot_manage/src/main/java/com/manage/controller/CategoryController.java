package com.manage.controller;

import com.manage.bean.Category;
import com.manage.service.CategoryService;
import com.manage.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin
public class CategoryController {
	@Autowired
	private CategoryService categoryService;

	@GetMapping(path = {"/category/list", "chart/piechart"})
	public Result selectAll() {
		System.out.println("CategoryController->selectAll--> 开始运行...");
		List<Category> categories = categoryService.selectAll();
		return Result.ok().data("items", categories);
	}

	@PostMapping("/category/insert")
	public Result insertOne(Category ca, HttpServletRequest request) {
		System.out.println("CategoryController->insertOne--> 开始运行...");
		System.out.println("CategoryController->insertOne--> " + ca.getFile().getOriginalFilename());

		try {
			ca.setFileurl(categoryService.getURL(categoryService.storeFile(ca.getFile()), request));

			Format dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			ca.setDate(dateFormat.format(new Date()));

			if (categoryService.insertOne(ca) != -1) {
				System.out.println("CategoryController->insert--> insert成功");
				return Result.ok().data("url", ca.getFileurl());
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("CategoryController->insert--> insert失败！");
			return Result.error().msg("出现严重错误，请关闭网页");
		}
		return Result.warn().msg("图片上传成功，但表单上传失败");
	}

	@DeleteMapping("/category/delete")
	public Result deleteById(int id) {
		System.out.println("CategoryController->deleteById--> 开始运行...");
		System.out.println("CategoryController->deleteById--> id: " + id);
		if (categoryService.deleteById(id) != -1) {
			return Result.ok();
		}
		return Result.error().msg("删除失败！请检查");
	}
}
