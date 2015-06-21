package ro.pub.acs.traffic.utils;

public class Utils {

	private static Double toRad(Double value) {
		return value * Math.PI / 180;
	}

	public static Double distanceHaversine(Double lat1, Double lon1,
			Double lat2, Double lon2, String param) {

		final Double earthRadius = 3960.00;
		Double latDistance = lat2 - lat1;
		Double lonDistance = lon2 - lon1;

		Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
				+ Math.cos(toRad(lat1)) * Math.cos(toRad(lat2))
				* Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
		Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		Double distance = earthRadius * c;

		if (param.equals("miles"))
			return distance;
		else if (param.equals("km"))
			return distance * 1.609344;
		else
			return (distance * 1.609344) / 1000;

	}

	public static String getTime(Long seconds) {
		Double diffSeconds = new Double(seconds);

		Double diffWeeks = Math.floor(diffSeconds / 604800);
		diffSeconds -= diffWeeks * 604800;

		Double diffDays = Math.floor(diffSeconds / 86400);
		diffSeconds -= diffDays * 86400;

		Double diffHours = Math.floor(diffSeconds / 3600);
		diffSeconds -= diffHours * 3600;

		Double diffMinutes = Math.floor(diffSeconds / 60);
		diffSeconds -= diffMinutes * 60;

		return diffHours + " h " + diffMinutes + " m " + diffSeconds + " s";
	}
}
