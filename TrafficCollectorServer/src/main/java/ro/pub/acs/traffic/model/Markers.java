package ro.pub.acs.traffic.model;

import java.util.List;
import javax.xml.bind.annotation.*;

@XmlRootElement(name = "markers")
public class Markers {

	private List<Marker> markers;

	public List<Marker> getMarkers() {
		return markers;
	}

	@XmlElement
	public void setMarkers(List<Marker> markers) {
		this.markers = markers;
	}

}
