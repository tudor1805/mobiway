import psycopg2
import sys

class PSqlWrapper:

  def __init__(self, db_config):
    self.db_config = db_config

  def connect(self):
    try:
      self.cnx = psycopg2.connect(**self.db_config)
      self.cursor = self.cnx.cursor()
    except Exception, e:
      print(e)

  def update(self, query, args):
    try:
      self.cursor.execute(query, args)
      self.cnx.commit()
      return self.cursor
    except psycopg2.Error as err:
      print(err)

  def query(self, query, args):
    try:
      self.cursor.execute(query, args)
      return self.cursor
    except psycopg2.Error as err:
      print(err)

  def disconnect(self):
    try:
      self.cursor.close()
      self.cnx.close()
    except:
      e = sys.exc_info()[0]
      print(e)
