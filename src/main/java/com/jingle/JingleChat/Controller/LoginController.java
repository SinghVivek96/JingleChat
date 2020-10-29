package com.jingle.JingleChat.Controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.jingle.JingleChat.Entities.User;
import com.jingle.JingleChat.Entities.UserRepository;
import com.jingle.JingleChat.Service.LoginService;

@Controller
@SessionAttributes("name")
public class LoginController {

	@Autowired
	LoginService loginService;
	
	@Autowired
	private UserRepository repo;
	
//	@RequestMapping(value = {""},method = RequestMethod.GET)
//	public ModelAndView welcomePage(ModelMap model)
//	{
//		ModelAndView modelAndView = new ModelAndView();
//		modelAndView.setViewName("welcome");
//		return modelAndView;	
//	}	
//	
	@RequestMapping(value = {"","/login"})
	public String showLoginPage()
	{	
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication ==  null || authentication instanceof AnonymousAuthenticationToken)
		{
			System.out.println("in null login");
			return "login";
		}
		//String userName= (String) request.getAttribute("userName");
		//System.out.println("username fetched is   "+userName);
		System.out.println("in login welcome");
		return "redirect:/welcome";
	}	
//	
//
//	@RequestMapping(value = "/login",method=RequestMethod.POST)
//	public ModelAndView showWelcomePage(@RequestParam String inputEmail,@RequestParam String inputPassword,HttpSession httpSession)
//	{
//		httpSession.setAttribute("userName", inputEmail);	
//		ModelAndView modelAndView = new ModelAndView();
//		System.out.println("email address is "+inputEmail);
//		//validation
//		boolean isValid = loginService.validateUser(inputEmail,inputPassword);
//		modelAndView.addObject("userName", inputEmail);
//		modelAndView.addObject("userPassword", inputPassword);
//		if(!isValid)
//		{
//			modelAndView.addObject("errorMessage", "Invalid Login Credentials");
//			modelAndView.setViewName("login");
//			return modelAndView;
//		}	
//		modelAndView.setViewName("welcome");
//		return modelAndView;
//	}
//	
	@RequestMapping(value = "/register")
	public ModelAndView registrationProgress()
	{
		ModelAndView model = new ModelAndView();
		model.addObject("user",new User());
		System.out.println("in here");
		model.setViewName("register");
		return model;
	}
	
	@RequestMapping(value = "/process_register")

	public String registrationProcess(User user)
	{
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		user.setPassword(encoder.encode(user.getPassword()));
		repo.save(user);
		return "redirect:/login";
	}
	
	
	@RequestMapping("/chat")
    public ModelAndView index(HttpServletRequest request,@RequestParam(value = "topic") String topic) {
		ModelAndView model = new ModelAndView();
		Principal principal = request.getUserPrincipal();
		String userName = principal.getName();
		if(userName==null)
		{
			model.setViewName("login");
			return model;
		}
		model.addObject("userName",userName);
		model.addObject("topicName",topic);
        model.setViewName("chatstart");
//        System.out.println(username);
        System.out.println(topic);
        return model;
    }
	
	@RequestMapping(path = "/welcome")
    public String welcome(HttpServletRequest request,ModelMap model) {
		Principal principal = request.getUserPrincipal();
		String userName = principal.getName();
		System.out.println("in here welcome");
		model.addAttribute("userName",userName);
        return "welcome";		
    }
	
	@RequestMapping(path = "/chat/back")
    public String logout(HttpServletRequest request) 
	{
        return "redirect:/welcome";
    }
	
}

