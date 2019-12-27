package danisik.pia.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

	@GetMapping("/admin/manage")
	public String index() {

		return "admin/admin/manage";
	}
}
