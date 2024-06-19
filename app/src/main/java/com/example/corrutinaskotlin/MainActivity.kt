package com.example.corrutinaskotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import com.example.corrutinaskotlin.ui.theme.CorrutinaskotlinTheme
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: ItemsViewModel by viewModels()
        setContent {
            CorrutinaskotlinTheme {
                ItemsView(viewModel)
            }
        }
    }
}

@Composable
fun Content(viewModel: MainViewModel){
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ButtonColor()
        if(viewModel.isLoading) {
            CircularProgressIndicator()
        } else {
            Text(text = viewModel.resultState)
        }
        Button(onClick = { viewModel.fetchData() }) {
            Text(text = "Llamar API")
        }
    }
}

@Composable
fun ButtonColor(){
    var color by remember { mutableStateOf(false) }
    
    Button(onClick = { color = !color }, colors = ButtonDefaults.buttonColors(
        containerColor = if(color) Color.Blue else Color.Red
    )) {
        Text(text = "Cambiar color")
    }
    
}

@Composable
fun ItemsView(viewModel: ItemsViewModel){
    val itemsList = viewModel.itemList
    val lista by viewModel.lista.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchData() // se puede hacer de esta forma o con un init en la clase
    }

    Column {
        if(viewModel.isLoading) {
            CircularProgressIndicator()
        } else {
            LazyColumn {
                items(lista) {item ->
                    Text(text = item.name)
                }
            }
        }
    }

}

