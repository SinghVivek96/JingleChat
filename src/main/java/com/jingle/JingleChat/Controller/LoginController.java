package com.jingle.JingleChat.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.jingle.JingleChat.Service.LoginService;

@Controller
@SessionAttributes("name")
public class LoginController {

	@Autowired
	LoginService loginService;
	
	@RequestMapping(value = {"","/login"},method = RequestMethod.GET)
	public ModelAndView showLoginPage(ModelMap model)
	{
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		return modelAndView;	
	}	

	@RequestMapping(value = "/login",method=RequestMethod.POST)
	public ModelAndView showWelcomePage(@RequestParam String inputEmail,@RequestParam String inputPassword,HttpSession httpSession)
	{
		httpSession.setAttribute("userName", inputEmail);	
		ModelAndView modelAndView = new ModelAndView();
		System.out.println("email address is "+inputEmail);
		//validation
		boolean isValid = loginService.validateUser(inputEmail,inputPassword);
		modelAndView.addObject("userName", inputEmail);
		modelAndView.addObject("userPassword", inputPassword);
		if(!isValid)
		{
			modelAndView.addObject("errorMessage", "Invalid Login Credentials");
			modelAndView.setViewName("login");
			return modelAndView;
		}	
		modelAndView.setViewName("welcome");
		return modelAndView;
	}

	@RequestMapping("/chat")
    public ModelAndView index(HttpServletRequest request,@RequestParam(value = "topic") String topic) {
		ModelAndView model = new ModelAndView();
		String username = (String) request.getSession().getAttribute("userName");
		model.addObject("username",username);
		model.addObject("topicName",topic);
        model.setViewName("chatstart");
        System.out.println(username);
        System.out.println(topic);
 
        return model;
    }
	
	@RequestMapping(path = "/welcome")
    public String welcome(HttpServletRequest request,ModelMap model) {
       System.out.println("in here");
       String userName = (String) request.getSession().getAttribute("userName");
       System.out.println(userName);
		model.addAttribute("userName",userName);
        return "welcome";		
    }
	
	@RequestMapping(path = "/chat/back")
    public String logout(HttpServletRequest request) {
       
         
        return "redirect:/welcome";
    }
	
}

