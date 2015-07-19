<!DOCTYPE html >
  <head>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
    <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
    <title>Google Maps Example</title>
    <style>
		#map {
			position: absolute; left: 0;top: 0; right: 0; bottom: 0;
		}
	</style>
	<script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false&key=AIzaSyB_JRyT6pqtylf36u66OEPp0M8Qkpp0G8g"></script>
    <script type="text/javascript">
    //<![CDATA[
	
	var map;
	var mapMarker= [];
	
    var customIcons = {
      restaurant: {
        icon: 'http://labs.google.com/ridefinder/images/mm_20_blue.png',
        shadow: 'http://labs.google.com/ridefinder/images/mm_20_shadow.png'
      },
      bar: {
        icon: 'http://labs.google.com/ridefinder/images/mm_20_red.png',
        shadow: 'http://labs.google.com/ridefinder/images/mm_20_shadow.png'
      },
      unknown: {
        icon: 'http://labs.google.com/ridefinder/images/mm_20_white.png',
        shadow: 'http://labs.google.com/ridefinder/images/mm_20_shadow.png'
      }
    };

    function load() {
      
		var infoWindow = new google.maps.InfoWindow;

		// Change this depending on the name of your PHP file
		downloadUrl("markers.php?id_user=<?php echo $_GET['id_user']?>", function(data) {
			var xml = data.responseXML;
			var markers = xml.documentElement.getElementsByTagName("marker");
			for (var i = 0; i < markers.length; i++) {
				var name = markers[i].getAttribute("name");
				var address = markers[i].getAttribute("address");
				var type = markers[i].getAttribute("type");
				var point = new google.maps.LatLng(
				parseFloat(markers[i].getAttribute("lat")),
				parseFloat(markers[i].getAttribute("lng")));
				var html = "<b>" + name + "</b> <br/>" + address;
				var icon = customIcons[type] || {};
				marker = new google.maps.Marker({
					map: map,
					position: point,
					icon: icon.icon,
					shadow: icon.shadow
				});
				var isUnknown = markers[i].getAttribute("unknown") == "true" ? true : false;
				if (isUnknown) {
					html = "<b>unknown position</b>";
					marker = new google.maps.Marker({
						map: map,
						position: point,
						icon: customIcons["unknown"].icon,
						shadow: customIcons["unknown"].shadow
					});
				}
				mapMarker.push(marker);
				if(mapMarker.length > 1) {
					mapMarker[0].setMap(null);
					mapMarker.shift();
				}
				map.setCenter(new google.maps.LatLng(parseFloat(markers[i].getAttribute("lat")),
				parseFloat(markers[i].getAttribute("lng"))));

				bindInfoWindow(marker, map, infoWindow, html);
			}
		});
    }
	
	function watch() {
		map = new google.maps.Map(document.getElementById("map"), {
			center: new google.maps.LatLng(44.44417, 26.05382),
			zoom: 11,
			mapTypeId: 'roadmap'
		});
		load()
		setInterval('load()', 5000);
		}

	function bindInfoWindow(marker, map, infoWindow, html) {
		google.maps.event.addListener(marker, 'click', function() {
			infoWindow.setContent(html);
			infoWindow.open(map, marker);
		});
	}

    function downloadUrl(url, callback) {
		var request = window.ActiveXObject ? new ActiveXObject('Microsoft.XMLHTTP') : new XMLHttpRequest;

		request.onreadystatechange = function() {
			if (request.readyState == 4) {
				request.onreadystatechange = doNothing;
				callback(request, request.status);
			}
		};
		var params = "id_user=<?php echo $_GET['id_user']?>";
		request.open("POST",url,true);
		request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		request.setRequestHeader("Content-length", params.length);
		request.setRequestHeader("Connection", "close");
		request.send(params);
    }

    function doNothing() {}

    //]]>

  </script>

  </head>

  <body onload="watch();">
    <div id="map"></div>
  </body>

</html>
