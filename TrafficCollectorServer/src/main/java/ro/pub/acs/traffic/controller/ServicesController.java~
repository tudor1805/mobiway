package ro.pub.acs.traffic.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;
import org.joda.time.Seconds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.web.bind.annotation.*;

import ro.pub.acs.traffic.dao.*;
import ro.pub.acs.traffic.model.*;

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
	public @ResponseBody User getUser(@PathVariable int userId) {
		User user = userDao.get(userId);

		return user;
	}

	@RequestMapping(value = "/signup/userpass", method = RequestMethod.POST)
	public @ResponseBody User userPassCreateAccount(@RequestBody User user) {
		User userExists = userDao.get(user.getUsername());
		if (userExists != null) {
			return null;
		}

		String uuid = UUID.randomUUID().toString();
		DateTime dateTime = new DateTime();
		DateTime threeMontsLater = dateTime.plusMonths(3);
		int seconds = Seconds.secondsBetween(dateTime, threeMontsLater)
				.getSeconds();
		user.setAuth_token(uuid);
		user.setUuid(uuid);
		user.setAuthExpiresIn(seconds);

		/* Save and return the new user */
		int userId = userDao.add(user);
		User newUser = userDao.get(userId);

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
		User oldUser = userDao.get(user.getUsername());
		if (oldUser != null) {
			user.setId(oldUser.getId());
		}

		String uuid = UUID.randomUUID().toString();
		DateTime dateTime = new DateTime(0);
		DateTime threeMonthsLater = new DateTime().plusMonths(3);
		int seconds = Seconds.secondsBetween(dateTime, threeMonthsLater)
				.getSeconds();
		user.setAuth_token(uuid);
		user.setAuthExpiresIn(seconds);

		/* Save and return the new user */
		int userId = userDao.add(user);
		User newUser = userDao.get(userId);

		/* Get friend from Facebook and insert into database */
		List<String> friendIds = facebook.friendOperations().getFriendIds();

		for (String friendId : friendIds) {
			User userFried = userDao.get(friendId);
			UserContact userContact1 = new UserContact();
			UserContact userContact2 = new UserContact();
			userContact1.setIdUser(newUser);
			userContact1.setIdFriendUser(userFried);
			userContact2.setIdUser(userFried);
			userContact2.setIdFriendUser(newUser);

			userContactDao.addFriend(userContact1);
			userContactDao.addFriend(userContact2);
		}

		return newUser;
	}

	@RequestMapping(value = "/authenticate/userpass", method = RequestMethod.POST)
	public @ResponseBody User loginWithUserAndPass(@RequestBody User user) {
		User oldUser = userDao.get(user.getUsername(), user.getPassword());
		if (oldUser == null) {
			return null;
		}

		String uuid = UUID.randomUUID().toString();
		DateTime dateTime = new DateTime(0);
		DateTime threeMonthsLater = new DateTime().plusMonths(3);
		int seconds = Seconds.secondsBetween(dateTime, threeMonthsLater)
				.getSeconds();
		oldUser.setAuth_token(uuid);
		oldUser.setAuthExpiresIn(seconds);

		/* Save and return the new user */
		userDao.update(oldUser);

		return oldUser;
	}

	@RequestMapping(value = "/social/getFriendsNames", method = RequestMethod.GET)
	public @ResponseBody List<User> getFriendsNames(
			@RequestHeader("X-Auth-Token") String authToken) {
		User user = userDao.get(authToken, 0);
		List<User> friends = new ArrayList<User>();

		if (user != null) {
			friends = userContactDao.getFriends(user.getId());
		}

		return friends;
	}

	@RequestMapping(value = "/social/getFriendsLocations", method = RequestMethod.GET)
	public @ResponseBody List<Location> getFriendsLocations(
			@RequestHeader("X-Auth-Token") String authToken) {
		User user = userDao.get(authToken, 0);
		List<User> friends = new ArrayList<User>();
		List<Location> locations = new ArrayList<Location>();

		if (user != null) {
			friends = userContactDao.getFriends(user.getId());
			for (User friend : friends) {
				Location location = locationDao.get(friend.getId());
				if (location != null) {
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
		User user = userDao.get(authToken, 0);
		List<User> users = new ArrayList<User>();

		if (user != null) {
			users = userDao.getUsersWithPhone();
		}

		return users;
	}

	@RequestMapping(value = "/social/addFriends", method = RequestMethod.PUT)
	public @ResponseBody boolean addFriends(
			@RequestHeader("X-Auth-Token") String authToken,
			@RequestBody List<User> friends) {
		User user = userDao.get(authToken, 0);
		List<String> oldFriends = new ArrayList<String>();
		
		if (user == null) {
			return false;
		} else {
			for (User friend : friends) {
				oldFriends = userContactDao.getFriendsEmails(user.getId());
				if (user.getId() != friend.getId() && !oldFriends.contains(friend.getUsername())) {
					UserContact userContact1 = new UserContact();
					UserContact userContact2 = new UserContact();
					userContact1.setIdUser(user);
					userContact1.setIdFriendUser(friend);
					userContact2.setIdUser(friend);
					userContact2.setIdFriendUser(user);

					userContactDao.addFriend(userContact1);
					userContactDao.addFriend(userContact2);
				}
			}
		}

		return true;
	}

	@RequestMapping(value = "/social/getNearbyLocations", method = RequestMethod.GET)
	public @ResponseBody List<Place> getNearbyLocations(
			@RequestHeader("X-Auth-Token") String authToken,
			@RequestBody List<String> types) {

		return new ArrayList<Place>();
	}

	@RequestMapping(value = "/social/getRoute", method = RequestMethod.GET)
	public @ResponseBody List<Location> getRoute(
			@RequestHeader("X-Auth-Token") String authToken,
			@RequestBody Location location1, Location location2) {

		return new ArrayList<Location>();
	}

	@RequestMapping(value = "/location/update", method = RequestMethod.PUT)
	public @ResponseBody boolean updateLocation(@RequestBody Location location,
			@RequestHeader("X-Auth-Token") String authToken) {
		User user = userDao.get(authToken, location.getIdUser());

		if (user != null) {
			Location locationUser = locationDao.get(user.getId());
			if (locationUser != null) {
				locationUser.setLatitude(location.getLatitude());
				locationUser.setLongitude(location.getLongitude());
				locationUser.setSpeed(location.getSpeed());
				locationUser.setTimestamp(new Date());
				locationDao.update(locationUser);
			} else {
				location.setUser(user);
				location.setTimestamp(new Date());
				locationDao.add(location);
			}

			return true;
		}

		return false;
	}

}