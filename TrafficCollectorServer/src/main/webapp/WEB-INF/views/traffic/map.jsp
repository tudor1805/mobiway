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
    <script type="text/javascript" src="js/StyledMarker.js"></script>
    <script type="text/javascript">
        //<![CDATA[

        var customIcons = {
            fastest: {
                icon: 'images/c_blue.png'
            },
            fast: {
                icon: 'images/c_green.png'
            },
            slow: {
                icon: 'images/c_red.png'
            },
            stop: {
                icon: 'images/c_black.png'
            }
        };

        var geocoder;
        var tableid = 260197;

        function load() {
            var map = new google.maps.Map(document.getElementById("map"), {
                center: new google.maps.LatLng(44.44417, 26.05382),
                zoom: 11,
                mapTypeId: 'roadmap'
            });

            var layer = new google.maps.FusionTablesLayer(tableid);
            layer.setMap(map);

            // Create the legend and display on the map
            var legendDiv = document.createElement('DIV');
            legendDiv.index = 1;
            map.controls[google.maps.ControlPosition.RIGHT_BOTTOM].push(legendDiv);

            var infoWindow = new google.maps.InfoWindow;

            // Change this depending on the name of your file
            downloadUrl("marker?type=1&id=<%= request.getParameter("id")%>", function(data) {
                var xml = data.responseXML;
                var markers = xml.documentElement.getElementsByTagName("marker");
                for (var i = 0; i < markers.length; i++) {
                    var name = markers[i].getAttribute("name");
                    var address = markers[i].getAttribute("address");
                    var type = markers[i].getAttribute("type");
                    var address_geo = "";
                    var speed = markers[i].getAttribute("speed");
                    var point = new google.maps.LatLng(
                            parseFloat(markers[i].getAttribute("lat")),
                            parseFloat(markers[i].getAttribute("lng")));

                    var innerColor = borderColor = "FFFFFF";

                    if (speed > 80) {
                        // blue
                        var r = 0, g = 0, b = 255;
                        b -= (Math.round(speed) - 80) * 4;
                        if (b < 40)
                            b = 40;
                        innerColor = "0000" + b.toString(16);
                    }

                    if (speed >= 40 && speed <= 80) {
                        // green
                        var r = 0, g = 255, b = 0;
                        g -= (Math.round(speed) - 40) * 4;
                        innerColor = "00" + g.toString(16) + "00";
                    }

                    if (speed > 1 && speed < 40) {
                        // red
                        var r = 255, g = 0, b = 0;
                        r -= (Math.round(speed) - 1) * 4;
                        innerColor = r.toString(16) + "0000";
                    }
                    borderColor = innerColor;

                    var html = "<b>" + name + "</b> <br/>" + address + "<br/>" + address_geo;

                    var url = "http://chart.apis.google.com/chart?cht=d&chdp=mapsapi&chl=pin%27i%5C%27[%27-3%27f%5Chv%27a%5C]h%5C]o%5C" + innerColor + "%27fC%5Cffffff%27tC%5C" + borderColor + "%27eC%5CLauto%27f%5C&ext=.png";
                    var pinImage = new google.maps.MarkerImage(url,
                            new google.maps.Size(21, 34),
                            new google.maps.Point(0, 0),
                            new google.maps.Point(10, 34), new google.maps.Size(10, 16));

                    var marker = new google.maps.Marker({
                        map: map,
                        position: point,
                        icon: pinImage
                    });
                    bindInfoWindow(marker, map, infoWindow, html);
                }
            });
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
                if (request.readyState === 4) {
                    request.onreadystatechange = doNothing;
                    callback(request, request.status);
                }
            };

            request.open('GET', url, true);
            request.send(null);
        }

        function Legend(controlDiv, map) {
            // Set CSS styles for the DIV containing the control
            // Setting padding to 5 px will offset the control
            // from the edge of the map
            controlDiv.style.padding = '5px';

            // Set CSS for the control border
            var controlUI = document.createElement('DIV');
            controlUI.style.backgroundColor = 'white';
            controlUI.style.borderStyle = 'solid';
            controlUI.style.borderWidth = '1px';
            controlUI.title = 'Legend';
            controlDiv.appendChild(controlUI);

            // Set CSS for the control text
            var controlText = document.createElement('DIV');
            controlText.style.fontFamily = 'Arial,sans-serif';
            controlText.style.fontSize = '12px';
            controlText.style.paddingLeft = '4px';
            controlText.style.paddingRight = '4px';

            // Add the text
            controlText.innerHTML = '<b>Speeds*</b><br />' +
                    '<img src="images/slowest.png" /> stoped<br />' +
                    '<img src="images/slow.png" /> &lt;40 km/h<br />' +
                    '<img src="images/fast.png" /> 40 - 80 km/h<br />' +
                    '<img src="images/fastest.png" /> &gt;80 km/h<br />';
            controlUI.appendChild(controlText);
        }

        function doNothing() {
        }

    </script>

</head>

<body onload="load()">
    <div id="map"></div>
</body>
</html>
