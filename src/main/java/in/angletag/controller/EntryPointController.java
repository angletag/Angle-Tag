package in.angletag.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.angletag.service.RecaptchaService;

@Controller
public class EntryPointController {

	
	@RequestMapping("/index")
	public String entry() {
		return "index";
	}
	
	/*
	 @RequestMapping("/verify")
	 
	public ResponseEntity<?> verify(@RequestParam(name="g-recaptcha-response") String recaptchaResponse,HttpServletRequest request) {
		String ip = request.getRemoteAddr();
		  //String captchaVerifyMessage = 		      captchaService.verifyRecaptcha(ip, recaptchaResponse);
		 
		  if ( !StringUtils.isEmpty(captchaVerifyMessage)) {
		    Map<String, Object> response = new HashMap<>();
		    response.put("message", captchaVerifyMessage);
		    return ResponseEntity.badRequest().body(response);
		  }
		  return ResponseEntity.ok().build();
	}
	
	*/
}
