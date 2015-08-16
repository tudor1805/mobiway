<?php

/* Example Usage:
    wget --output-document=test "http://127.0.0.1/pgroute.php?src=44.48998463,26.0272261&dst=44.45294373,26.1105126"
    wget --output-document=test "http://127.0.0.1/pgroute.php?src=44.48998463,26.0272261&dst=44.45294373,26.1105126&hour=4"
 */

/* Constants */
define("PG_DB",   "mobiway_pgrouting");
define("PG_HOST", "127.0.0.1");
define("PG_USER", "postgres");
define("PG_PASS", "gr0k");

/* Enable for debugging */
/*
    ini_set('display_errors', 'On');
    error_reporting(E_ALL);
*/

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

function doRoute($dbcon, $startPoint, $endPoint, $hour) {
    $selected_column = sprintf("practical_speed_forw_%02d", $hour);
    $sql = "SELECT ST_AsText(geom) FROM pgr_fromAtoB(
                'ways'" . "," .
                "'". $selected_column ."'" . "," .
                $startPoint[1] . "," . $startPoint[0] . "," .
                $endPoint[1]   . "," . $endPoint[0]   . ")";

    // Perform database query to get road geometry
    $result = pg_query($dbcon, $sql);

    while ($row = pg_fetch_row($result)) {
        $way_geom = $row[0];
        getPointArrayFromLinestring($way_geom);
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
        $endPoint   = array(26.1105126, 44.45294373);
     */

    $src_arg = htmlspecialchars($_GET["src"]);
    $dst_arg = htmlspecialchars($_GET["dst"]);
    $hour = htmlspecialchars($_GET["hour"]);

    if (!$hour) {
        $hour = 1;
    }

    $startPoint = explode(',', $src_arg);
    $endPoint   = explode(',', $dst_arg);

    // echo 'From ' . $src_arg, PHP_EOL;
    // echo 'To   ' . $dst_arg, PHP_EOL;

    doRoute($dbcon, $startPoint, $endPoint, $hour);
} catch (Exception $e) {
    // echo 'Caught exception: ',  $e->getMessage(), "\n";
    header("HTTP/1.1 500 Internal Server Error");
} finally {
    // Close database connection
    pg_close($dbcon);
}

