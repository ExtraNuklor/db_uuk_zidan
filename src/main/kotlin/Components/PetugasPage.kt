package Components

import Database.CRUD
import Database.Login
import Database.Session
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun PetugasPage(navController: NavController) {
    val crud = CRUD()
    val login = Login()

    // Manage the state of the transaction list
    var transaksiList by remember { mutableStateOf(loadTransaksiList(crud)) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Text(
            text = "Halo, ${Session.nama}",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
        )
        Text(
            text = "Level anda adalah : ${Session.level}",
            modifier = Modifier
        )
        Spacer(modifier = Modifier.size(20.dp))
        Row {
            Button(
                onClick = {
                    navController.navigate("TambahkanTransaksiPage")
                },
                content = {
                    Text(
                        text = "Tambahkan Transaksi"
                    )
                }
            )
            Spacer(modifier = Modifier.width(10.dp))
            Button(
                onClick = {
                    navController.popBackStack()
                    login.logout()
                },
                content = {
                    Text(
                        text = "Log Out"
                    )
                }
            )
        }
        Spacer(modifier = Modifier.size(20.dp))
        Text(
            text = "Transaksi: ",
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            modifier = Modifier
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(transaksiList) { transaksi ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    border = BorderStroke(1.dp, Color.Black),
                    shape = RoundedCornerShape(8.dp),
                    elevation = 4.dp
                ) {
                    Column(
                        modifier = Modifier
                            .padding(10.dp)
                    ) {
                        Text(
                            text = "Id: ${transaksi["id_transaksi"]}",
                            fontWeight = FontWeight.Bold
                        )
                        Text(text = "Barang: ${transaksi["nama_barang"]}")
                        Text(text = "Pelanggan: ${transaksi["nama_pelanggan"]}")
                        Text(text = "Petugas: ${transaksi["nama_petugas"]}")
                        Text(text = "Tanggal : ${transaksi["tanggal"]}")
                        Text(text = "Jumlah : ${transaksi["jumlah"]}")
                        Text(text = "Total Harga : ${transaksi["total_harga"]}")
                        Spacer(modifier = Modifier.size(5.dp))
                        Row(modifier = Modifier) {
                            Button(
                                onClick = {
                                    crud.hapusTransaksi(transaksi["id_transaksi"].toString())
                                    // Update the state to trigger recomposition
                                    transaksiList =
                                        transaksiList.filter { it["id_transaksi"] != transaksi["id_transaksi"] }
                                }
                            ) {
                                Text(
                                    text = "Hapus"
                                )
                            }
                            Spacer(modifier = Modifier.size(10.dp))
                            Button(
                                onClick = {
                                navController.navigate("EditTransaksiPage")
                                }
                            ) {
                                Text(
                                    text = "Edit"
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

// Helper function to load the transaction list
fun loadTransaksiList(crud: CRUD): List<Map<String, String>> {
    val dataTransaksi = crud.tampilDataTransaksi()
    val transaksiList = mutableListOf<Map<String, String>>()
    while (dataTransaksi.next()) {
        transaksiList.add(
            mapOf(
                "id_transaksi" to dataTransaksi.getString("id_transaksi"),
                "nama_barang" to dataTransaksi.getString("nama_barang"),
                "nama_pelanggan" to dataTransaksi.getString("nama_pelanggan"),
                "nama_petugas" to dataTransaksi.getString("nama_petugas"),
                "tanggal" to dataTransaksi.getDate("tanggal").toString(),
                "total_harga" to dataTransaksi.getInt("total_harga").toString(),
                "jumlah" to dataTransaksi.getInt("jumlah").toString(),
            )
        )
    }
    return transaksiList
}
