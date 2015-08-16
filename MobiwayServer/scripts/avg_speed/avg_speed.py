#!/usr/bin/python

import datetime
import app_configs
from mysql_wrapper import *
from psql_wrapper  import *

def get_last_run_timestamp(mysql_db):
  query = "SELECT last_timestamp FROM scripts_data";
  cursor = mysql_db.query(query, ()).fetchone()

  return cursor[0]

def set_last_run_timestamp(mysql_db, timestamp):
  query = ("UPDATE scripts_data "
           "SET last_timestamp = %s");

  mysql_db.update(query, (timestamp, ))

def get_streets_list(psql_db):
  query = "SELECT osm_id FROM ways";
  cursor = psql_db.query(query, ())

  return cursor

def update_avg_speed(psql_db, osm_id, hour, speed_forw):
  hour_padded = (str(hour)).zfill(2)
  selected_column = "practical_speed_forw_%s" %( hour_padded )
  query = ("UPDATE ways "
           "SET " + selected_column + " = %s "
           "WHERE osm_id = %s");

  cursor = psql_db.update(query, (speed_forw, osm_id))
  return 0

def calc_way_avg_speed(mysql_db, osm_id, prev_tstamp, curr_tstamp):

  query = ("SELECT AVG(speed) AS avg, HOUR(timestamp) AS hour "
           "FROM journey_data "
           "WHERE osm_way_id = %s "
           "AND speed > 0 "
           "AND timestamp BETWEEN %s AND %s "
           "GROUP BY HOUR(timestamp) HAVING count(speed) > 1000");

  cursor = mysql_db.query(query, (osm_id, prev_tstamp, curr_tstamp, ))

  # Map with key as hour and value as average speed
  averages = { }
  for (speed, hour) in cursor:
      averages[hour] = speed
      #print(str(speed) + "=" + str(hour))

  return averages

def mysql_test():
  mysql_db = MySqlWrapper(app_configs.mysql_config)
  mysql_db.connect()

#  osm_id = 2838257353
#  calc_way_avg_speed(mysql_db, osm_id)

  mysql_db.disconnect()

def calculate_speed_averages():
  mysql_db = MySqlWrapper(app_configs.mysql_config)
  mysql_db.connect()

  psql_db = PSqlWrapper(app_configs.psql_config)
  psql_db.connect()

  # Save the current time
  prev_tstamp = get_last_run_timestamp(mysql_db)
  curr_tstamp = datetime.datetime.now()

  updated_streets = 0
  street_list = get_streets_list(psql_db).fetchall()
  for street_osm in street_list:
    street_id = street_osm[0]
    avg_map = calc_way_avg_speed(mysql_db, street_id, \
                                 prev_tstamp, curr_tstamp)
    for hour in avg_map:
        avg_hour = avg_map[hour]
        update_avg_speed(psql_db, int(street_id), hour, avg_hour)

    updated_streets += 1
    if updated_streets % 1000 == 0:
        print("Updated %s Streets ==" % (updated_streets))

  print("Finished updating %s Streets ==" %(updated_streets));

  print "==== Update timestamp ===="
  dt_format = '%Y-%m-%d %H:%M:%S'
  curr_time_mysql = curr_tstamp.strftime(dt_format)
  set_last_run_timestamp(mysql_db, curr_time_mysql)

  psql_db.disconnect()
  mysql_db.disconnect()

if __name__ == "__main__":
  # mysql_test()
  calculate_speed_averages()

