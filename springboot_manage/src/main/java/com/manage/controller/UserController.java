package com.manage.controller;

import com.manage.bean.User;
import com.manage.service.UserService;
import com.manage.utils.JwtUtils;
import com.manage.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {
	@Autowired
	private UserService userService;

	@PostMapping("/login")
	public Result login(@RequestBody User user) {
		System.out.println("UserController->login--> 开始运行...");
		System.out.println("UserController->login--> " + user);
		String token = JwtUtils.generateToken(user.getUsername() + "&" + user.getPassword());
		String[] u = JwtUtils.getClaimsByToken(token).getSubject().split("&");
		String username = u[0];
		String password = u[1];
		System.out.println("UserController->login--> username: " + username + " || password: " + password);

		int flag = userService.validateUser(user);
		if (flag == 2) {
			return Result.ok().data("token", token);
		} else if (flag == 1) {
			return Result.error().msg("密码错误！请重新输入");
		} else if (flag == 0) {
			return Result.warn().msg("用户未注册");
		}
		return Result.error().msg("出现严重错误，请关闭网页");
	}

	@GetMapping("/info")
	public Result info(String token) {
		System.out.println("UserController->info--> 开始运行...");
		System.out.println("UserController->info--> " + token);

		String[] u = JwtUtils.getClaimsByToken(token).getSubject().split("&");
		String username = u[0];
		String password = u[1];
		System.out.println("UserController->info--> token信息--> username: " + username + " & password: " + password);

		User user = new User(username, password, null);
		int flag = userService.validateUser(user);
		if (flag == 2) {
			user = userService.selectById(userService.findid(user));
			System.out.println("UserController->info--> 返回前端的信息--> " + user);
			return Result.ok().data("username", user.getUsername()).data("password", user.getPassword()).data("avatar", user.getAvatar());
		}
		return Result.error().msg("用户校验失败，请重新登录");
	}

	@PostMapping("/logout")  // "token:xxx"
	public Result logout() {
		System.out.println("UserController->logout--> 开始运行...");
		return Result.ok();
	}

	@PutMapping("/update")
	public Result updateOne(User user, HttpServletRequest request) {
		System.out.println("UserController->updateOne--> 开始运行...");
		System.out.println("UserController->updateOne--> " + user.getFile().getOriginalFilename());

		try {
			user.setAvatar(userService.getURL(userService.storeFile(user.getFile()), request));
			if (userService.updateById(user) != -1) {
				System.out.println("UserController->updateOne--> update成功");
				return Result.ok().data("username", user.getUsername()).data("password", user.getPassword()).data("avatar", user.getAvatar());
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("UserController->updateOne--> update失败！");
			return Result.error().msg("出现严重错误，请关闭网页");
		}
		return Result.warn().msg("图片上传成功，但表单上传失败");
	}
}
