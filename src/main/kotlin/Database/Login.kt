package Database

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException

class Login {
    private lateinit var koneksi: Connection
    private lateinit var query: String
    private lateinit var preparedStatement: PreparedStatement
    private lateinit var dataUser: ResultSet
    private var pesan = ""

    init {
        val koneksiMysql = ConnectMysql()
        try {
            koneksi = koneksiMysql.getConnection()
            println("Koneksi berhasil")
        } catch (e: SQLException) {
            println(e)
        }
    }

    fun cekLogin(username: String, password: String): String {
        query = "SELECT * FROM petugas WHERE username=? AND password=?"
        try {
            preparedStatement = koneksi.prepareStatement(query)
            preparedStatement.setString(1, username)
            preparedStatement.setString(2, password)
            dataUser = preparedStatement.executeQuery()
            if (dataUser.next()) {
                Session.userId = dataUser.getString("id_petugas")
                Session.nama = dataUser.getString("nama_petugas")
                Session.level = dataUser.getString("level")
                Session.loginStatus = "ON"
            } else {
                pesan = "Gagal login"
            }
        } catch (e: SQLException) {
            pesan = "query error"
        }
        return pesan
    }

    fun logout() {
        Session.userId = null
        Session.nama = null
        Session.level = null
        Session.loginStatus = null
    }

}
