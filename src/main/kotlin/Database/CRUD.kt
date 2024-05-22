package Database

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement

class CRUD {

    private lateinit var crudKoneksi: Connection
    private lateinit var crudPreparedStatement: PreparedStatement
    private lateinit var crudStatement: Statement
    private lateinit var crudHasil: ResultSet
    private lateinit var crudQuery: String

    init {
        try {
            val koneksiMysql = ConnectMysql()
            crudKoneksi = koneksiMysql.getConnection()
        } catch (e: SQLException) {
            println(e)
        }
    }

    fun tampilDataTransaksi(): ResultSet {
        crudQuery =
            """SELECT transaksi.tanggal, transaksi.jumlah, transaksi.total_harga, transaksi.id_transaksi, petugas.nama_petugas, pelanggan.nama_pelanggan, barang.nama_barang
            |       FROM transaksi INNER JOIN petugas ON transaksi.id_petugas = petugas.id_petugas INNER JOIN pelanggan ON transaksi.id_pelanggan = pelanggan.id_pelanggan INNER JOIN barang ON transaksi.id_barang = barang.id_barang
        """.trimMargin()
        try {
            crudStatement = crudKoneksi.createStatement()
            crudHasil = crudStatement.executeQuery(crudQuery)
        } catch (e: SQLException) {
            println("Gagal menampilkan data : $e")
        }
        return crudHasil
    }

    fun tampilSemuaBarang(): ResultSet {
        crudQuery = "SELECT * FROM barang"
        try {
            crudStatement = crudKoneksi.createStatement()
            crudHasil = crudStatement.executeQuery(crudQuery)
        } catch (e: SQLException) {
            println("Gagal memanggil data : $e")
        }
        return crudHasil
    }

    fun tampilSemuaPelanggan(): ResultSet {
        crudQuery = "SELECT * FROM pelanggan"
        try {
            crudStatement = crudKoneksi.createStatement()
            crudHasil = crudStatement.executeQuery(crudQuery)
        } catch (e: SQLException) {
            println("Gagal memanggil data : $e")
        }
        return crudHasil
    }

    fun tambahkanTransaksi(
        id_barang: String,
        id_petugas: String,
        id_pelanggan: String,
        jumlah: String,
        totalHarga: String
    ) {
        crudQuery =
            "INSERT INTO transaksi (id_barang, id_petugas, id_pelanggan, jumlah, total_harga) VALUES (?,?,?,?,?)"
        try {
            crudPreparedStatement = crudKoneksi.prepareStatement(crudQuery)
            crudPreparedStatement.setString(1, id_barang)
            crudPreparedStatement.setString(2, id_petugas)
            crudPreparedStatement.setString(3, id_pelanggan)
            crudPreparedStatement.setString(4, jumlah)
            crudPreparedStatement.setString(5, totalHarga)
            crudPreparedStatement.executeUpdate()
        } catch (e: SQLException) {
            println(e)
        }
    }

    fun updateTransaksi(
        id_barang: String,
        id_petugas: String,
        id_pelanggan: String,
        jumlah: String,
        totalHarga: String,
        id_transaksi: String
    ) {
        crudQuery =
            "UPDATE transaksi SET id_barang=?, id_petugas=?, id_pelanggan=?, jumlah=?, total_harga=? WHERE id_transaksi=?"
        try {
            crudPreparedStatement = crudKoneksi.prepareStatement(crudQuery)
            crudPreparedStatement.setString(1, id_barang)
            crudPreparedStatement.setString(2, id_petugas)
            crudPreparedStatement.setString(3, id_pelanggan)
            crudPreparedStatement.setString(4, jumlah)
            crudPreparedStatement.setString(5, totalHarga)
            crudPreparedStatement.setString(6,id_transaksi)
            crudPreparedStatement.executeUpdate()
        } catch (e: SQLException) {
            println(e)
        }
    }

    fun hapusTransaksi(idTransaksi: String) {
        crudQuery = "DELETE FROM transaksi WHERE id_transaksi=?"
        try {
            crudPreparedStatement = crudKoneksi.prepareStatement(crudQuery)
            crudPreparedStatement.setString(1, idTransaksi)
            crudPreparedStatement.executeUpdate()
        } catch (e: SQLException) {
            println(e)
        }
    }

}