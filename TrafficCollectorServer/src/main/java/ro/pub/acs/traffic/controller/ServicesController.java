package ro.pub.acs.traffic.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
import ro.pub.acs.traffic.dao.UserContactDAO;
import ro.pub.acs.traffic.dao.UserDAO;
import ro.pub.acs.traffic.model.Location;
import ro.pub.acs.traffic.model.User;
import ro.pub.acs.traffic.model.UserContact;

@RestController
@RequestMapping("/services")
public class ServicesController {

	@Autowired
	private UserDAO userDao;
	
	@Autowired
	private LocationDAO locationDao;

	@Autowired
	private UserContactDAO userContactDao;

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
		/* Get Facebook profile */
		Facebook facebook = new FacebookTemplate(user.getFacebook_token());
		org.springframework.social.facebook.api.User profile = facebook
				.userOperations().getUserProfile();
		if (profile == null || profile.getEmail() == null
				|| !user.getUsername().equals(profile.getEmail())) {
			return null;
		}

		/* Update user into database */
		User oldUser = userDao.getUser(user.getUsername());
		if (oldUser != null) {
			user.setId_user(oldUser.getId_user());
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

		/* Get friend from Facebook and insert into database */
		List<String> friendIds = facebook.friendOperations().getFriendIds();
		
		for(String friendId : friendIds){
			User userFried = userDao.getUser(friendId);
			UserContact userContact1 = new UserContact();
			UserContact userContact2 = new UserContact();
			userContact1.setId_user(userId);
			userContact1.setId_friend_user(userFried.getId_user());
			userContact2.setId_user(userFried.getId_user());
			userContact2.setId_friend_user(userId);
			
			userContactDao.addFriend(userContact1);
			userContactDao.addFriend(userContact2);
		}
		
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
	
	@RequestMapping(value = "/social/getFriendsNames", method = RequestMethod.GET)
	public @ResponseBody List<User> getFriendsNames(
			@RequestHeader("X-Auth-Token") String authToken) {
		User user = userDao.getUser(authToken, 0);
		List<User> friends = new ArrayList<User>();
		
		if(user != null){
			friends = userContactDao.getFriends(user.getId_user());
		}
		
		return friends;
	}
	
	@RequestMapping(value = "/social/getFriendsLocations", method = RequestMethod.GET)
	public @ResponseBody List<Location> getFriendsLocations(
			@RequestHeader("X-Auth-Token") String authToken) {
		User user = userDao.getUser(authToken, 0);
		List<User> friends = new ArrayList<User>();
		List<Location> locations = new ArrayList<Location>();
		
		if(user != null){
			friends = userContactDao.getFriends(user.getId_user());
			for(User friend : friends){
				Location location = locationDao.getLocation(friend.getId_user());
				if(location != null){
					location.setTimestamp(null);
					locations.add(location);
				}
			}
		}
		
		return locations;
	}
	
	@RequestMapping(value = "/social/getUsersWithPhone", method = RequestMethod.GET)
	public @ResponseBody List<User> getUsersWithPhone(
			@RequestHeader("X-Auth-Token") String authToken) {
		User user = userDao.getUser(authToken, 0);
		List<User> users = new ArrayList<User>();
		
		if(user != null){
			users = userDao.getUsersWithPhone();
		}
		
		return users;
	}
	
	@RequestMapping(value = "/social/addFriends", method = RequestMethod.PUT)
	public @ResponseBody boolean addFriends(
			@RequestHeader("X-Auth-Token") String authToken,
			@RequestBody List<User> friends) {
		User user = userDao.getUser(authToken, 0);
		
		if(user == null){
			return false;
		} else {
			for(User friend : friends){
				if(user.getId_user() != friend.getId_user()){
					UserContact userContact1 = new UserContact();
					UserContact userContact2 = new UserContact();
					userContact1.setId_user(user.getId_user());
					userContact1.setId_friend_user(friend.getId_user());
					userContact2.setId_user(friend.getId_user());
					userContact2.setId_friend_user(user.getId_user());
					
					userContactDao.addFriend(userContact1);
					userContactDao.addFriend(userContact2);
				}
			}
		}
		
		return true;
	}

	@RequestMapping(value = "/location/update", method = RequestMethod.PUT)
	public @ResponseBody boolean updateLocation(
			@RequestBody Location location,
			@RequestHeader("X-Auth-Token") String authToken) {
		User user = userDao.getUser(authToken, location.getId_user());
		
		if(user != null){
			Location locationUser = locationDao.getLocation(user.getId_user());
			if(locationUser != null){
				locationUser.setLatitude(location.getLatitude());
				locationUser.setLongitude(location.getLongitude());
				locationUser.setSpeed(location.getSpeed());
				locationUser.setTimestamp(new Date());
				locationDao.updateLocation(locationUser);
			} else {
				location.setId_user(user.getId_user());
				location.setTimestamp(new Date());
				locationDao.addLocation(location);
			}
			
			return true;
		}
		
		return false;
	}

}