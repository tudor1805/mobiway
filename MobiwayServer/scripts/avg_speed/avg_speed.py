#!/usr/bin/python

import app_configs
from mysql_wrapper import *
from psql_wrapper  import *

def get_streets_list(psql_db):
  query = "SELECT osm_id FROM ways";
  cursor = psql_db.query(query, ())

  return cursor

def clear_avg_speed(psql_db):
  query = ("UPDATE ways "
           "SET practical_speed_forward = '', "
           "practical_speed_backward = '' ");

  cursor = psql_db.query(query, ())

def update_avg_speed(psql_db, osm_id, speed_forw, speed_backw):
  query = ("UPDATE ways "
           "SET practical_speed_forward = %s, "
           "practical_speed_backward = %s "
           "WHERE osm_id = %s");

  cursor = psql_db.query(query, (speed_forw, speed_backw, osm_id))
  return 0

def calc_way_avg_speed(mysql_db, osm_id):
  averages = {
           0 : 0.0, 1  : 0.0, 2  : 0.0, 3  : 0.0,
           4 : 0.0, 5  : 0.0, 6  : 0.0, 7  : 0.0,
           8 : 0.0, 9  : 0.0, 10 : 0.0, 11 : 0.0,
           12: 0.0, 13 : 0.0, 14 : 0.0, 15 : 0.0,
           16: 0.0, 17 : 0.0, 18 : 0.0, 19 : 0.0,
           20: 0.0, 21 : 0.0, 22 : 0.0, 23 : 0.0
            }

  query = ("SELECT AVG(speed) AS avg, HOUR(timestamp) AS hour "
           "FROM journey_data "
           "WHERE osm_way_id = %s "
           "GROUP BY HOUR(timestamp)");

  cursor = mysql_db.query(query, (osm_id, ))

  for (speed, hour) in cursor:
      print(str(speed) + "=" + str(hour))
      averages[hour] = speed

  serialized_avg = ""
  for k in averages:
      line = "%s %s" %(str(k), str(averages[k]))
      if k < 23:
          line += ","

      serialized_avg += line
  return serialized_avg

def mysql_test():
  mysql_db = MySqlWrapper(app_configs.mysql_config)
  mysql_db.connect()

  osm_id = 2838257353
  calc_way_avg_speed(mysql_db, osm_id)

  mysql_db.disconnect()

def calculate_speed_averages():
  mysql_db = MySqlWrapper(app_configs.mysql_config)
  mysql_db.connect()

  psql_db = PSqlWrapper(app_configs.psql_config)
  psql_db.connect()

  updated_streets = 0
  street_list = get_streets_list(psql_db).fetchall()
  for street_osm in street_list:
    street_id = street_osm[0]
    avg = calc_way_avg_speed(mysql_db, street_id)
    update_avg_speed(psql_db, int(street_id), avg, avg)

    updated_streets += 1
    if updated_streets % 1000 == 0:
        print("Updated %s Streets ==" % (updated_streets))

  print("Finished updating %s Streets ==" %(updated_streets));

  psql_db.disconnect()
  mysql_db.disconnect()

if __name__ == "__main__":
  calculate_speed_averages()

