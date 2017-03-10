package com.hexun.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hexun.exception.MyException;

@Controller
@EnableAutoConfiguration
public class SimpleController {

//	@Value("${com.didispace.blog.name}")
//	private String name;
//	@Value("${com.didispace.blog.title}")
//	private String title;

	@RequestMapping(value = "/hello2", method = RequestMethod.GET)
	@ResponseBody
	public String hello2() throws Exception {
		// return name;
		throw new Exception("发生错误");
	}

	@RequestMapping("/hello3")
	public String index2(ModelMap map) {
		// 加入一个属性，用来在模板中读取
		map.addAttribute("host", "http://blog.didispace.com");
		// return模板文件的名称，对应src/main/resources/templates/index.html
		return "index";
	}

	@RequestMapping("/json2")
    public String json() throws MyException {
        throw new MyException("发生错误2");
    }
	
	public static void main(String[] args) {
		SpringApplication.run(SimpleController.class, args);
	}
}
