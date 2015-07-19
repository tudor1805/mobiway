package ro.pub.acs.mobiway.controller;

import ro.pub.acs.mobiway.dao.*;
import ro.pub.acs.mobiway.model.*;
import ro.pub.acs.mobiway.utils.*;

import java.util.*;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.*;
import org.springframework.web.servlet.ModelAndView;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

@org.springframework.stereotype.Controller
@RequestMapping(value = "/traffic")
public class MgmtAppController {

	@Autowired
	private LocationDAO locationDao;

	@Autowired
	private UserDAO userDao;

	@Autowired
	private JourneyDAO journeyDao;

	@Autowired
	private JourneyDataDAO journeyDataDao;

	@Autowired
	private ServletContext context;

	// Retrieve session object
	private static HttpSession getSession() {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder
				.currentRequestAttributes();
		return attr.getRequest().getSession(true); // true == allow create
	}

	// Retrieve current user from the session object
	private User getCurrentUser() {
		HttpSession session = getSession();
		return (User) session.getAttribute("user");
	}

	@RequestMapping(method = RequestMethod.GET, value = "/")
	public ModelAndView showDefaultPage() {

		return new ModelAndView("redirect:login");
	}

	@RequestMapping(method = RequestMethod.GET, value = "/login")
	public ModelAndView loginPage() {
		ModelAndView mav = new ModelAndView();

		User user = getCurrentUser();
		if (user != null) {
			mav.setViewName("redirect:table");
		} else {
			mav.setViewName("/traffic/login");
		}

		return mav;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/login")
	public ModelAndView loginUser(Map<String, Object> map,
			@RequestParam("username") String username,
			@RequestParam("password") String password) {

		ModelAndView mav = new ModelAndView();

		password = new String(Hex.encodeHex(DigestUtils.sha(password)));
		User user = userDao.loginUser(username, password);
		if (user == null) {
			mav.setViewName("/traffic/login");
			mav.addObject("login_error", "yes");
		} else {
			HttpSession session = getSession();
			session.setAttribute("user", user);
			session.setAttribute("logged_at", new Date());

			mav.setViewName("redirect:table");
		}

		return mav;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/logout")
	public @ResponseBody ModelAndView logoutUser() {
		// Destroy the current session
		HttpSession session = getSession();
		session.invalidate();

		return new ModelAndView("redirect:login");
	}

	@RequestMapping(method = RequestMethod.GET, value = "/account")
	public ModelAndView showAccountInfo() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/traffic/account");

		User user = getCurrentUser();
		if (user != null) {
			mav.addObject("firstname", user.getFirstname());
			mav.addObject("lastname", user.getLastname());
			mav.addObject("username", user.getUsername());
		} else {
			// User is not logged in. Redirect to login page
			mav.setViewName("redirect:login");
		}

		return mav;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/account")
	public ModelAndView updateAccountInfo(
			@RequestParam("firstname") String firstname,
			@RequestParam("lastname") String lastname,
			@RequestParam("username") String username,
			@RequestParam("password") String password,
			@RequestParam("password_re") String password_re) {

		ModelAndView mav = new ModelAndView();

		User user = getCurrentUser();
		if (user != null) {
			if (!password.isEmpty() && !password.equals(password_re)) {
				mav.addObject("account_update_error", "yes");
			} else {
				user.setFirstname(firstname);
				user.setLastname(lastname);
				user.setUsername(username);

				if (!password.isEmpty()) {
					password = new String(Hex.encodeHex(DigestUtils
							.sha(password)));
					user.setPassword(password);
				}

				userDao.update(user);
			}

			mav.setViewName("/traffic/account");
			mav.addObject("account_try_update", "yes");
			mav.addObject("name",
					user.getFirstname() + " " + user.getLastname());
			mav.addObject("firstname", user.getFirstname());
			mav.addObject("lastname", user.getLastname());
			mav.addObject("username", user.getUsername());
		} else {
			// User is not logged in. Redirect to login page
			mav.setViewName("redirect:login");
		}

		return mav;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/table")
	public ModelAndView showTable(
			@RequestParam(value = "page", required = false, defaultValue = "1") int currPage) {

		ModelAndView mav = new ModelAndView();

		User user = getCurrentUser();
		if (user != null) {
			final int journeysPerPage = 5;

			List<Journey> journeysOnThisPage = new ArrayList<Journey>();
			List<Journey> journeys = journeyDao.getByUserId(user);

			int startIndex = (currPage - 1) * journeysPerPage;
			int endIndex = startIndex + journeysPerPage;
			for (int currIndex = startIndex; currIndex < endIndex
					&& currIndex < journeys.size(); currIndex++) {
				try {
					Journey j = journeys.get(currIndex);
					journeysOnThisPage.add(j);
				} catch (Exception ex) {
				}
			}

			int totalPages = journeys.size() / journeysPerPage + 1;

			mav.addObject("totalPages", totalPages);
			mav.addObject("userJourneys", journeysOnThisPage);
			mav.addObject("currentPage", currPage);
			mav.setViewName("/traffic/table");
		} else {
			// User is not logged in. Redirect to login page
			mav.setViewName("redirect:login");
		}

		return mav;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/marker", produces = "application/xml")
	public @ResponseBody Markers showMarkers(
			@RequestParam(value = "id") int journeyId) {

		// Return an xml with location markers during this journey
		Markers markers = new Markers();

		User user = getCurrentUser();
		if (user != null) {

			Journey journey = journeyDao.get(journeyId);

			List<Marker> markersList = new ArrayList<Marker>();

			// Check that the user is logged in and is the owner of the journey
			try {
				if (user.getId().equals(journey.getIdUser().getId())) {

					List<JourneyData> journeyDataList = journeyDataDao.getByJourneyId(journey);

					for (JourneyData jd : journeyDataList) {
						Marker newMarker = new Marker();

						// Categorize the marker based on speed
						float speed = jd.getSpeed();
						String speedMarker = "";
						if (speed > 80) {
							speedMarker = "fastest";
						} else if (speed > 40) {
							speedMarker = "fast";
						} else if (speed > 1) {
							speedMarker = "slow";
						} else if (speed <= 1) {
							speedMarker = "stop";
						}

						newMarker.setName("Date:" + jd.getTimestamp()
								+ "\"");
						newMarker.setAddress("Speed:" + jd.getSpeed()
								+ "km/h\"");
						newMarker.setLat("" + jd.getLatitude());
						newMarker.setLng("" + jd.getLongitude());
						newMarker.setSpeed("" + jd.getSpeed());
						newMarker.setType("" + speedMarker);
						markersList.add(newMarker);
					}
				}
			} catch (Exception ex) {
				// Skip
			}

			markers.setMarker(markersList);
		}

		return markers;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/map")
	public ModelAndView showMap(@RequestParam(value = "id") int journeyId) {
		ModelAndView mav = new ModelAndView();

		User user = getCurrentUser();
		if (user != null) {
			mav.setViewName("/traffic/map");
		} else {
			mav.setViewName("redirect:login");
		}

		return mav;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/stats", produces = "application/html")
	public ModelAndView showJourneyStats(
			@RequestParam(value = "id") int journeyId) {

		ModelAndView mav = new ModelAndView();

		User user = getCurrentUser();
		if (user != null) {
			Journey journey = journeyDao.get(journeyId);

			if (journey != null) {
				Date timeStart = null, timeEnd = null;
				Double maxSpeedKmH = 0.0;

				Float lastLat = null, lastLon = null;
				Double totalLengthKm = 0.0;

				/*
				 * Collection<JourneyData> journeyData = journey
				 * .getJourneyDataCollection();
				 */

				for (JourneyData jd : journey.getJourneyDataCollection()) {

					if (timeStart == null) {
						timeStart = jd.getTimestamp();
					} else {
						timeEnd = jd.getTimestamp();
					}

					Double speed = jd.getSpeed() * 3.6;

					// Calculate the maximum speed
					if (speed > maxSpeedKmH) {
						maxSpeedKmH = speed;
					}

					// Calculate the total length
					if (lastLat == null && lastLon == null) {
						lastLat = jd.getLatitude();
						lastLon = jd.getLongitude();
					} else {
						Double length = Utils.distanceHaversine(new Double(
								lastLat), new Double(lastLon),
								new Double(jd.getLatitude()),
								new Double(jd.getLongitude()), "km");
						totalLengthKm += length;

						lastLat = jd.getLatitude();
						lastLon = jd.getLongitude();
					}
				}

				// Calculate total traveling time and speed
				String totalTimeFormatted = "0";
				Double avgSpeed = 0.0;
				if (timeStart != null && timeEnd != null) {
					totalTimeFormatted = Utils.getTime(timeEnd.getTime()
							- timeStart.getTime());
					Long totalTimeSeconds = (timeEnd.getTime() - timeStart
							.getTime()) / 1000;
					avgSpeed = totalLengthKm * 3600 / totalTimeSeconds;
				}

				mav.addObject("maxSpeed", maxSpeedKmH);
				mav.addObject("tripLength", totalLengthKm);
				mav.addObject("tripTime", totalTimeFormatted);
				mav.addObject("avgSpeed", avgSpeed);
			}

			mav.setViewName("/traffic/stats");
		} else {
			mav.setViewName("redirect:login");
		}

		return mav;
	}

}
