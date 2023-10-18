package com.manage.service;

import com.manage.bean.Category;
import com.manage.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Service
public class CategoryService {
	@Autowired
	private CategoryMapper categoryMapper;


	public List<Category> selectAll() {
		return categoryMapper.selectList(null);
	}

	public int deleteById(long id) {
		System.out.println("CategoryService->deleteById--> id: " + id + " 即将被删除出数据库");
		return categoryMapper.deleteById(id);
	}

	public int insertOne(Category ca) {
		System.out.println("CategoryService->insertOne--> " + ca + " 即将插入数据库");
		return categoryMapper.insert(ca);
	}
	public String storeFile(MultipartFile file) {
		System.out.println("CategoryService->storeFile--> " + file + " 开始运行存储程序...");
		String fileName = file.getOriginalFilename();
		Format dateFormat = new SimpleDateFormat("yyyy-MM-dd-");
		String format = dateFormat.format(new Date());
		String realPath = System.getProperty("user.dir") + "/src/main/resources/static/img";
		System.out.println("CategoryService->storeFile--> " + realPath + " 此位置将被用作存储");
		File folder = new File(realPath);
		if (!folder.exists()) {
			System.out.println("CategoryService->storeFile--> " + realPath + " 正在创建文件夹！");
			//若不存在该目录，则创建目录
			folder.mkdirs();
		}
		String newName;
		if (fileName.endsWith(".png") || fileName.endsWith(".PNG")) {
			newName = format + UUID.randomUUID().toString() + ".png";
		} else if (fileName.endsWith(".jpg") || fileName.endsWith(".JPG")) {
			newName = format + UUID.randomUUID().toString() + ".jpg";
		} else if (fileName.endsWith(".jpeg") || fileName.endsWith(".JPEG")) {
			newName = format + UUID.randomUUID().toString() + ".jpeg";
		} else {
			System.out.println("CategoryService->storeFile--> 文件类型不受支持！程序停止");
			return null;
		}
		try {
			System.out.println("CategoryService->storeFile--> " + folder + newName + " 正在存储拥有新名字的文件");
			file.transferTo(new File(folder, newName));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("CategoryService->storeFile--> " + folder + newName + " 此位置存储失败！程序停止");
			return null;
		}
		System.out.println("CategoryService->storeFile--> " + folder + newName + " 成功存储新文件，返回主调函数");
		return newName;
	}

	public String getURL(String fileName, HttpServletRequest request) {
		System.out.println("CategoryService->getURL-->  开始运行...");
		String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/img/" + fileName;
		System.out.println("CategoryService->getURL--> " + url + " 成功获得URL");
		return url;
	}
}
