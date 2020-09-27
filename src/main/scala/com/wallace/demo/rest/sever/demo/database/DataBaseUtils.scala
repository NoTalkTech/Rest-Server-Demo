package com.wallace.demo.rest.sever.demo.database

import java.sql._

import com.wallace.demo.rest.sever.demo.common.Using

import scala.util.{Failure, Success, Try}

/**
  * Created by 10192057 on 2018/4/17 0017.
  */
object DataBaseUtils extends Using {
   implicit def resultSetToArray[String](rs: ResultSet): Array[Array[String]] = {
    val tmp: ArrayBuffer[Array[String]] = new ArrayBuffer[Array[String]]()
    val columnCount: Int = rs.getMetaData.getColumnCount //获得列数    
    while (rs.next()) {
      val row: Array[String] = (0 to columnCount).map(i => s"${rs.getObject(i)}").toArray[String]
      tmp.append(row)
    }
    tmp.result().toArray[Array[String]]
  }
 
  //TODO implement jdbc driver
  def createConnection(configData: DataBaseInfo): Connection = {
    log.debug(s"DataBase Config: $configData.")
    Class.forName(configData.driver)
    log.debug(s"DataBase LoginTimeOut: ${DriverManager.getLoginTimeout}.")
    DriverManager.setLoginTimeout(0)
    DriverManager.getConnection(configData.url + configData.dbName, configData.user, configData.password)
  }

  def executeSQL(conn: Connection, sql: String): ResultSet = {
    using(conn) {
      conn =>
        using(conn.createStatement()) {
          stmt =>
            log.debug(s"DataBase Query SQL: $sql")
            stmt.executeQuery(sql)
        }
    }
  }

  def execute(sql: String, configData: DataBaseInfo): Boolean = {
    using(createConnection(configData)) {
      conn =>
        Try {
          executeSQL(conn, sql)
        } match {
          case Success(_) => true
          case Failure(_) => false
        }
    }
  }

  def query(sql: String, configData: DataBaseInfo): ResultSet = {
    using(createConnection(configData)) {
      conn => executeSQL(conn, sql)
    }
  }
}

object SybaseDatabaseInfo {
  def apply(host: String,
            port: Int = 3000,
            dbName: String = "",
            user: String = "root",
            password: String = "",
            driver: String = "com.sybase.jdbc4.jdbc.SybDriver"): DataBaseInfo = {
    DataBaseInfo(s"jdbc:sybase:Tds:$host:$port", user, password, driver, dbName)
  }
}


object MySQLDatabaseInfo {
  def apply(host: String,
            port: Int = 3306,
            dbName: String = "",
            user: String = "root",
            password: String = "",
            driver: String = "com.mysql.jdbc.Driver"): DataBaseInfo = {
    DataBaseInfo(s"jdbc:mysql://$host:$port/", user, password, driver, dbName)
  }
}

case class DataBaseInfo(url: String,
                        user: String,
                        password: String,
                        driver: String,
                        dbName: String,
                        dbConfigs: List[String] = Nil)
