package danisik.pia.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PurserController {

	@GetMapping("/addressbook")
	public String addressbook() {

		return "purser/addressbook";
	}

	@GetMapping("/invoice/edit")
	public String invoiceedit() {

		return "purser/invoice/edit";
	}

	@GetMapping("/invoice/info")
	public String invoiceinfo() {

		return "purser/invoice/info";
	}
}
