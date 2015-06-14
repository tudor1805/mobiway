package ro.pub.acs.traffic.controller;

import java.util.List;
import java.util.UUID;

import javax.persistence.Embeddable;

import org.joda.time.DateTime;
import org.joda.time.Seconds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ro.pub.acs.traffic.dao.LocationDAO;
import ro.pub.acs.traffic.dao.UserDAO;
import ro.pub.acs.traffic.model.Location;
import ro.pub.acs.traffic.model.User;

@RestController
@RequestMapping("/services")
public class ServicesController {

	@Autowired
	private UserDAO userDao;
	
	@Autowired
	private LocationDAO locationDao;

	@RequestMapping(value = "user/getUser/{userId}", method = RequestMethod.GET)
	public @ResponseBody User getUser(@PathVariable long userId) {
		User user = userDao.getUser(userId);

		return user;
	}

	@RequestMapping(value = "/signup/userpass", method = RequestMethod.POST)
	public @ResponseBody User userPassCreateAccount(@RequestBody User user) {
		User userExists = userDao.getUser(user.getUsername());
		if(userExists != null){
			return null;
		}
		
		String uuid = UUID.randomUUID().toString();
		DateTime dateTime = new DateTime();
		DateTime threeMontsLater = dateTime.plusMonths(3);
		long seconds = Seconds.secondsBetween(dateTime, threeMontsLater)
				.getSeconds();
		user.setAuth_token(uuid);
		user.setUuid(uuid);
		user.setAuth_expires_in(seconds);

		/* Save and return the new user */
		long userId = userDao.addUser(user);
		User newUser = userDao.getUser(userId);

		return newUser;
	}

	@RequestMapping(value = "/authenticate/facebook", method = RequestMethod.POST)
	public @ResponseBody User loginWithFacebook(@RequestBody User user) {
		User oldUser = userDao.getUser(user.getUsername());
		if (oldUser != null) {
			user.setId_user(oldUser.getId_user());
		}

		Facebook facebook = new FacebookTemplate(user.getFacebook_token());
		org.springframework.social.facebook.api.User profile = facebook
				.userOperations().getUserProfile();
		List<String> friendIds = facebook.friendOperations().getFriendIds();
		System.out.println(friendIds);

		if (profile == null || profile.getEmail() == null
				|| !user.getUsername().equals(profile.getEmail())) {
			return null;
		}

		String uuid = UUID.randomUUID().toString();
		DateTime dateTime = new DateTime(0);
		DateTime threeMonthsLater = new DateTime().plusMonths(3);
		long seconds = Seconds.secondsBetween(dateTime, threeMonthsLater)
				.getSeconds();
		user.setAuth_token(uuid);
		user.setAuth_expires_in(seconds);

		/* Save and return the new user */
		long userId = userDao.addUser(user);
		User newUser = userDao.getUser(userId);

		return newUser;
	}

	@RequestMapping(value = "/authenticate/userpass", method = RequestMethod.POST)
	public @ResponseBody User loginWithUserAndPass(@RequestBody User user) {
		User oldUser = userDao.getUser(user.getUsername(), user.getPassword());
		if (oldUser == null) {
			return null;
		}

		String uuid = UUID.randomUUID().toString();
		DateTime dateTime = new DateTime(0);
		DateTime threeMonthsLater = new DateTime().plusMonths(3);
		long seconds = Seconds.secondsBetween(dateTime, threeMonthsLater)
				.getSeconds();
		oldUser.setAuth_token(uuid);
		oldUser.setAuth_expires_in(seconds);

		/* Save and return the new user */
		userDao.updateUser(oldUser);

		return oldUser;
	}

	@RequestMapping(value = "/location/update", method = RequestMethod.PUT)
	public @ResponseBody boolean updateLocation(
			@RequestBody Location location,
			@RequestHeader("X-Auth-Token") String authToken) {
		User user = userDao.getUser(authToken, location.getId_user());
		if(user != null){
			location.setId_user(user.getId_user());
			locationDao.updateLocation(location);
			return true;
		}
		
		return false;
	}

}