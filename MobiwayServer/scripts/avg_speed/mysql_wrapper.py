import mysql.connector
import sys

class MySqlWrapper:

  def __init__(self, db_config):
    self.db_config = db_config

  def connect(self):
    try:
      self.cnx = mysql.connector.connect(**self.db_config)
      self.cursor = self.cnx.cursor()
    except mysql.connector.Error as err:
      if err.errno == errorcode.ER_ACCESS_DENIED_ERROR:
        print("Something is wrong with your user name or password")
      elif err.errno == errorcode.ER_BAD_DB_ERROR:
        print("Database does not exist")
      else:
        print(err)

  def update(self, query, args):
    try:
      self.cursor.execute(query, args)
      self.cnx.commit()
      return self.cursor
    except mysql.connector.errors.OperationalError as err:
      print(err)

  def query(self, query, args):
    try:
      self.cursor.execute(query, args)
      return self.cursor
    except mysql.connector.errors.OperationalError as err:
      print(err)

  def disconnect(self):
    try:
      self.cursor.close()
      self.cnx.close()
    except:
      e = sys.exc_info()[0]
      print(e)
