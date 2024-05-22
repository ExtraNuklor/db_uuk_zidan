
package Components

import Database.CRUD
import Database.Session
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun EditTransaksiPage(navController: NavController) {

    val crud = CRUD()
    val dataBarang = crud.tampilSemuaBarang()
    val dataPelanggan = crud.tampilSemuaPelanggan()

    val barangList = mutableListOf<Map<String, String>>()
    while (dataBarang.next()) {
        barangList.add(
            mapOf(
                "id_barang" to dataBarang.getInt("id_barang").toString(),
                "nama_barang" to dataBarang.getString("nama_barang"),
                "harga_barang" to dataBarang.getString("harga_barang"),
                "stok" to dataBarang.getString("stok"),
            )
        )
    }
    val pelangganList = mutableListOf<Map<String,String>>()
    while (dataPelanggan.next()) {
        pelangganList.add(
            mapOf(
                "id_pelanggan" to dataPelanggan.getInt("id_pelanggan").toString(),
                "nama_pelanggan" to dataPelanggan.getString("nama_pelanggan"),
                "alamat" to dataPelanggan.getString("alamat"),
                "no_telp" to dataPelanggan.getInt("no_telp").toString()
            )
        )
    }

    val itemPositionBarang = remember {
        mutableStateOf(0)
    }

    val itemPositionPelanggan = remember {
        mutableStateOf(0)
    }

    val jumlahStok = remember {
        mutableStateOf(barangList[itemPositionBarang.value]["stok"])
    }


    val expandedStatusBarang = remember {
        mutableStateOf(false)
    }

    val expandedStatusjumlah = remember {
        mutableStateOf(false)
    }

    val expandedStatusPelanggan = remember {
        mutableStateOf(false)
    }

    val stokDibeli = remember {
        mutableStateOf(1)
    }

    val totalHarga = remember {
        mutableStateOf(0)
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Update Transaksi (Belum)",
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp
        )
        Spacer(modifier = Modifier.size(50.dp))
        Button(
            onClick = {
                navController.popBackStack()
            }
        ) {
            Text(
                text = "Kembali"
            )
        }
        Spacer(modifier = Modifier.size(20.dp))
        Card(
            border = BorderStroke(1.dp, Color.Black),
            shape = RoundedCornerShape(8.dp),
            elevation = 4.dp,
            modifier = Modifier
                .size(250.dp),
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.size(10.dp))
                Column(
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Barang : "
                        )
                        Box(
                            modifier = Modifier
                        ) {
                            Row(
                                modifier = Modifier.clickable {
                                    expandedStatusBarang.value = true
                                },
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                barangList[itemPositionBarang.value]["nama_barang"]?.let {
                                    Text(
                                        text = it,
                                        modifier = Modifier.border(BorderStroke(1.dp, Color.Black)).padding(5.dp)
                                    )
                                }
                                DropdownMenu(
                                    expanded = expandedStatusBarang.value,
                                    onDismissRequest = {
                                        expandedStatusBarang.value = false
                                    },
                                ) {
                                    barangList.forEachIndexed { index, value ->
                                        DropdownMenuItem(
                                            onClick = {
                                                expandedStatusBarang.value = false
                                                itemPositionBarang.value = index
                                            }
                                        ) {
                                            value["nama_barang"]?.let {
                                                Text(
                                                    text = it
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.size(10.dp))
                    Text(
                        text = "Stok : ${barangList[itemPositionBarang.value]["stok"]}"
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Jumlah : "
                        )
                        Box(
                            modifier = Modifier
                        ) {
                            Row(
                                modifier = Modifier.clickable {
                                    expandedStatusjumlah.value = true
                                },
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = stokDibeli.value.toString(),
                                    modifier = Modifier.border(BorderStroke(1.dp, Color.Black)).padding(5.dp)
                                )
                                DropdownMenu(
                                    expanded = expandedStatusjumlah.value,
                                    onDismissRequest = {
                                        expandedStatusjumlah.value = false
                                    },
                                ) {
                                    if (jumlahStok.value != null) {
                                        for (i in 1..jumlahStok.value!!.toInt()) {
                                            DropdownMenuItem(
                                                onClick = {
                                                    expandedStatusjumlah.value = false
                                                    stokDibeli.value = i
                                                    totalHarga.value = i * barangList[itemPositionBarang.value]["harga_barang"]!!.toInt()
                                                }
                                            ) {
                                                Text(
                                                    text = i.toString()
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.size(10.dp))
                    Text(
                        text = "Harga : ${totalHarga.value}"
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Pelanggan : "
                        )
                        Box(
                            modifier = Modifier
                        ) {
                            Row(
                                modifier = Modifier.clickable {
                                    expandedStatusPelanggan.value = true
                                },
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                pelangganList[itemPositionPelanggan.value]["nama_pelanggan"]?.let {
                                    Text(
                                        text = it,
                                        modifier = Modifier.border(BorderStroke(1.dp, Color.Black)).padding(5.dp)
                                    )
                                }
                                DropdownMenu(
                                    expanded = expandedStatusPelanggan.value,
                                    onDismissRequest = {
                                        expandedStatusPelanggan.value = false
                                    },
                                ) {
                                    pelangganList.forEachIndexed { index, value ->
                                        DropdownMenuItem(
                                            onClick = {
                                                expandedStatusPelanggan.value = false
                                                itemPositionPelanggan.value = index
                                            }
                                        ) {
                                            value["nama_pelanggan"]?.let {
                                                Text(
                                                    text = it
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.size(10.dp))
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Button(
                            onClick = {
                                navController.popBackStack()
                            }
                        ) {
                            Text(
                                text = "Update"
                            )
                        }
                    }
                    Spacer(modifier = Modifier.size(10.dp))
                }
            }
        }
    }
}
