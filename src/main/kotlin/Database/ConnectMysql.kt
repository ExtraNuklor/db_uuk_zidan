package Database

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class ConnectMysql {

    var koneksi: Connection? = null

    val jdbc = "jdbc:mariadb://"
    val localhost = "localhost:"
    val port = "3306/"
    val database = "db_zidan"
    val url = jdbc + localhost + port + database

    val username = "root"
    val password = "root"

    fun getConnection(): Connection {
        if (koneksi == null) {
            try {
                koneksi = DriverManager.getConnection(url,username,password)
                println("Koneksi Database Berhasil")
            } catch (e: SQLException) {
                println("Koneksi database gagal : $e")
            }
        }
        return koneksi as Connection
    }
}