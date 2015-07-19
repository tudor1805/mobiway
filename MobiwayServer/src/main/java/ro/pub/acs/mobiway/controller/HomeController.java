package ro.pub.acs.mobiway.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ro.pub.acs.mobiway.dao.UserDAO;
import ro.pub.acs.mobiway.model.User;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	@Autowired
	private UserDAO userDao;
	
	@RequestMapping(value="/")
	public ModelAndView home() {
		List<User> listUsers = userDao.list();
		User user = userDao.get(4);
		
		ModelAndView model = new ModelAndView("home");
		model.addObject("userList", listUsers);
		model.addObject("cosmina", user);
		
		return model;
	}
	
}
