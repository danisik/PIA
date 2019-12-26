package danisik.pia.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

	@GetMapping("/useredit")
	public String useredit() {
		return "user/useredit";
	}

	@GetMapping("/userinfo")
	public String userinfo() {
		return "user/userinfo";
	}
}
