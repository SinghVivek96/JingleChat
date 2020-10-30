package com.jingle.JingleChat.Controller;

import java.security.Principal;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.jingle.JingleChat.DAO.TopicDAO;
import com.jingle.JingleChat.Entities.Topics;
import com.jingle.JingleChat.Entities.TopicsRepository;
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
	
	@Autowired
	private TopicsRepository topicRepo;
	
	@Autowired
	private TopicDAO topicDAORepo;
	
//	@RequestMapping(value = {""},method = RequestMethod.GET)
//	public ModelAndView welcomePage(ModelMap model)
//	{
//		ModelAndView modelAndView = new ModelAndView();
//		modelAndView.setViewName("welcome");
//		return modelAndView;	
//	}	
//	
	@RequestMapping(value = {"","/","/login"})
	public String showLoginPage()
	{	
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication ==  null || authentication instanceof AnonymousAuthenticationToken)
		{
			return "login";
		}
		
//		//String userName= (String) request.getAttribute("userName");
//		//System.out.println("username fetched is   "+userName);
//		System.out.println("from authentication "+authentication.getAuthorities());
//		System.out.println("from authentication "+authentication.getPrincipal());
//		System.out.println("from authentication "+authentication.getDetails());
//		System.out.println("from authentication "+authentication.getName());
//		System.out.println("from authentication "+authentication.getCredentials());
//		System.out.println("in login welcome");
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
	
//	public OAuth2User getCurrentUser() {
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		return ((OAuth2AuthenticationToken)auth).getPrincipal();
//	}
	
	@RequestMapping("/chat/{topicName}")
    public ModelAndView index(HttpServletRequest request,@PathVariable(name = "topicName") String topicName,@AuthenticationPrincipal OAuth2User principal_google) {
		ModelAndView model = new ModelAndView();
		
		//Google
		if(principal_google != null)
		{
			Map<String, Object> user = Collections.singletonMap("name", principal_google.getAttribute("name"));
			System.out.println(user.get("name"));
			model.addObject("userName",user.get("name"));
			model.addObject("topicName",topicName);
			model.setViewName("chatstart");
			return model;
		}
		
		
		//local
		
		
		Principal principal = request.getUserPrincipal();
		String userName = principal.getName();
		model.addObject("userName",userName);
		model.addObject("topicName",topicName);
        model.setViewName("chatstart");
        return model;
    }
	
	@RequestMapping(path = "/welcome")
    public String welcome(HttpServletRequest request,ModelMap model,@AuthenticationPrincipal OAuth2User principal_google) {
//		List<Topics> allTopics = topicRepo.findAll();
//		
//		System.out.println(allTopics);
		
		List<String> allpublicTopics = topicDAORepo.fetchPublicTopics();
		
		String[] arr = new String[allpublicTopics.size()]; 	
//		System.out.println("All public topics are "+ allpublicTopics.get(0));
		
		for (int i =0; i < allpublicTopics.size(); i++) 
            arr[i] = allpublicTopics.get(i); 
		
		//google login
		System.out.println("All public topics are "+ arr);
		if(principal_google != null)
		{
			Map<String, Object> user = Collections.singletonMap("name", principal_google.getAttribute("name"));
			System.out.println(user.get("name"));
			model.addAttribute("userName",user.get("name"));
			model.addAttribute("allpublicTopics", arr);
			return "welcome";
			
		}
		
		System.out.println("sdafghjhgfdf");
		//local login
		
		Principal principal = request.getUserPrincipal();
		String userName = principal.getName();
		model.addAttribute("userName",userName);
		
		model.addAttribute("allpublicTopics", arr);
        return "welcome";		
    }
	
	@RequestMapping(path = "/chat/back")
    public String logout(HttpServletRequest request) 
	{
        return "redirect:/welcome";
    }
	
}

