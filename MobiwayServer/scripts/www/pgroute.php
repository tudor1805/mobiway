<?php

/* Example Usage:
   wget --output-document=test "http://127.0.0.1/pgroute.php?src=44.48998463,26.0272261&dst=44.45294373,26.1105126"
 */

// Constants
define("PG_DB",   "mobiway_pgrouting");
define("PG_HOST", "127.0.0.1");
define("PG_USER", "postgres");
define("PG_PASS", "password");

ini_set('display_errors', 'On');
error_reporting(E_ALL);

/* Find the nearest edge - Example
   $startEdge = findNearestEdge($dbcon, $startPoint);
   $endEdge   = findNearestEdge($dbcon, $endPoint);
 */
function findNearestEdge($dbcon, $lonlat) {
    $sql = "SELECT gid, source, target, the_geom,
                  ST_Distance(the_geom, ST_GeometryFromText(
                        'POINT(". $lonlat[0] . " " . $lonlat[1].")', 4326)) AS dist
                  FROM ways
                  WHERE the_geom && SetSRID(
                        'BOX3D(" . ($lonlat[0] - 0.1) . "
                               " . ($lonlat[1] - 0.1) . ",
                               " . ($lonlat[0] + 0.1) . "
                               " . ($lonlat[1] + 0.1) . ")'::box3d, 4326)
                  ORDER BY dist LIMIT 1";

   $query = pg_query($dbcon, $sql);

   $edge['gid']      = pg_fetch_result($query, 0, 0);
   $edge['source']   = pg_fetch_result($query, 0, 1);
   $edge['target']   = pg_fetch_result($query, 0, 2);
   $edge['the_geom'] = pg_fetch_result($query, 0, 3);

   return $edge;
}

function getPointArrayFromLinestring($linestring) {
    // Eliminate extra Linestring
    preg_match('!\(([^\)]+)\)!', $linestring, $match);
    $parsed = $match[0];

    // Skip paranthesis
    $no_parans  = array('(', ')');
    $parsed2 = str_replace($no_parans, "", $parsed);

    // Retrieve array of points
    $points_arr = explode("," , $parsed2);

    foreach ($points_arr as $point) {
        echo $point . "\n";	
    }
}

function getWayPoints($dbcon, $way_gid) {
    $sql = "SELECT ST_AsText(the_geom) 
            FROM ways
            WHERE gid =" . $way_gid;

    $result = pg_query($dbcon, $sql);
    $row = pg_fetch_row($result);

    $way_geom = $row[0];

    $point_array = getPointArrayFromLinestring($way_geom);
}

function doRoute($dbcon, $startPoint, $endPoint, $hour) {
    $sql = "SELECT gid FROM pgr_fromAtoB(
        'ways'," .
        $startPoint[1] . "," . $startPoint[0] . "," .
        $endPoint[1] . "," . $endPoint[0] . ")";

    // Perform database query
    $result = pg_query($dbcon, $sql);

    while ($row = pg_fetch_row($result)) {

        $way_gid = $row[0];
        getWayPoints($dbcon, $way_gid);
    }
}

/* ==== Start ==== */

try {
    // Connect to database
    $dbcon = pg_connect(
        "dbname=". PG_DB .
        " host=" . PG_HOST .
        " user=" . PG_USER .
        " password=" . PG_PASS);

    /* Example
        $startPoint = array(26.0272261, 44.48998463);
        $endPoint = array(26.1105126, 44.45294373);
     */

    $startPoint = explode(',', htmlspecialchars($_GET["src"]));
    $endPoint = explode(',', htmlspecialchars($_GET["dst"]));

    // echo 'From ' . htmlspecialchars($_GET["src"]), PHP_EOL;
    // echo 'To   ' . htmlspecialchars($_GET["dst"]), PHP_EOL;

    doRoute($dbcon, $startPoint, $endPoint, 0);
} catch (Exception $e) {
    // echo 'Caught exception: ',  $e->getMessage(), "\n";
    header("HTTP/1.1 500 Internal Server Error");
} finally {
    // Close database connection
    pg_close($dbcon);
}

