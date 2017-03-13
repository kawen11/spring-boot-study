package com.hexun.boot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hexun.exception.MyException;
import com.hexun.mapper.UserMapper;
import com.hexun.model.User;

/**
 *
 * @author 程序猿DD
 * @version 1.0.0
 * @blog http://blog.didispace.com
 *
 */
@Controller
public class HelloController {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	@Autowired
	private RedisTemplate<String, User> redisTemplate;
	
	@Autowired
	private UserMapper userMapper;

	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	@ResponseBody
	public String hello(@RequestParam String name) {
        return "Hello " + name;
    }

	@RequestMapping("/json")
	public String json() throws MyException {
		throw new MyException("发生错误2");
	}

	@Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED)
	@RequestMapping("/")
	public String index(ModelMap map) {
		stringRedisTemplate.opsForValue().set("aaa", "111");

		// 保存对象存redis
		User user = new User();
		user.setName("jiang");
		user.setAge(20);
		redisTemplate.opsForValue().set(user.getName(), user);
		
		//mysql
		userMapper.insert("CCC", 222);
		User u = userMapper.findByName("CCC");

		map.addAttribute("host", u.getName());
		return "index";
	}

	public static void main(String[] args) {
		SpringApplication.run(HelloController.class, args);
	}
}