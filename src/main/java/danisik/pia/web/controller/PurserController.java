package danisik.pia.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PurserController {

	@GetMapping("/addressbook")
	public String addressbook() {
		return "purser/addressbook";
	}

	@GetMapping("/invoiceedit")
	public String invoiceedit() {
		return "purser/invoiceedit";
	}

	@GetMapping("/invoiceinfo")
	public String invoiceinfo() {
		return "purser/invoiceinfo";
	}
}
