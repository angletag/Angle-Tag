package in.angletag.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class EntryPointController {

	@RequestMapping("/")
	public String entry() {
		return "index";
	}
}
