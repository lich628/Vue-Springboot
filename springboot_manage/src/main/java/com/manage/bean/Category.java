package com.manage.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import org.springframework.web.multipart.MultipartFile;

public class Category {
	private long id;
	private String name;
	private float num;
	private String flag;
	private String phone;
	private String date;
	private String fileurl;
	@TableField(exist = false)
	private MultipartFile file;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getNum() {
		return num;
	}

	public void setNum(float num) {
		this.num = num;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getFileurl() {
		return fileurl;
	}

	public void setFileurl(String fileurl) {
		this.fileurl = fileurl;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	@Override
	public String toString() {
		return "Category{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", num=" + num +
				", flag='" + flag + '\'' +
				", phone='" + phone + '\'' +
				", date='" + date + '\'' +
				", fileurl='" + fileurl + '\'' +
				", file=" + file +
				'}';
	}
}
