package ro.pub.acs.mobiway.model;

import java.util.List;
import javax.xml.bind.annotation.*;

@XmlRootElement(name = "markers")
public class Markers {

	private List<Marker> marker;

	public List<Marker> getMarker() {
		return marker;
	}

	@XmlElement
	public void setMarker(List<Marker> marker) {
		this.marker = marker;
	}
}
