package Components

import Database.Login
import Database.Session
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun MainPage(navController: NavController) {

    val judulLabel = remember {
        mutableStateOf("Aplikasi Kasir")
    }

    val login = Login()

    val usernameTextfieldValue = remember {
        mutableStateOf("")
    }
    val passwordTextfieldValue = remember {
        mutableStateOf("")
    }


    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = judulLabel.value,
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(50.dp))

        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = "Username"
            )

            Spacer(modifier = Modifier.height(10.dp))

            TextField(
                value = usernameTextfieldValue.value,
                onValueChange = {
                    usernameTextfieldValue.value = it
                },
                label = {
                    Text(
                        text = "Masukkan Username anda.."
                    )
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Password"
            )

            Spacer(modifier = Modifier.height(10.dp))

            TextField(
                value = passwordTextfieldValue.value,
                onValueChange = {
                    passwordTextfieldValue.value = it
                },
                label = {
                    Text(
                        text = "Masukkan Password anda.."
                    )
                },
                visualTransformation = PasswordVisualTransformation()

            )
            Button(
                onClick = {
                    var username = usernameTextfieldValue.value
                    var password = passwordTextfieldValue.value
                    if (username.trim().equals("") && password.trim().equals("")) {
                        judulLabel.value = "Berikan masukan dengan benar"
                    } else {
                        val pesan = login.cekLogin(username,password)
                        if (Session.loginStatus.equals("ON")) {
                            navController.navigate("PetugasPage")
                        } else {
                            judulLabel.value = pesan
                        }
                    }
                }
            ) {
                Text(
                    text = "LOGIN"
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
    }
}
