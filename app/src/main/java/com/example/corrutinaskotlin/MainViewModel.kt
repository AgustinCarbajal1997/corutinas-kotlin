package com.example.corrutinaskotlin

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel: ViewModel() {
    var resultState by mutableStateOf("")
        private set

    var isLoading by mutableStateOf(false)
        private set



    fun fetchData(){
        // en general las corutinas se crean desde un viewModel
        viewModelScope.launch {
            try {
                isLoading = true
                llamarApi()
            } catch (e: Exception) {
                println(e.message)
            } finally {
                isLoading = false
            }
        }
    }

    private suspend fun llamarApi(){ //para que una funcion de corutina pueda funcionar dentro de un viewModel
        //tiene que llevar la palabra suspend
        val result = withContext(Dispatchers.IO){
            delay(5000)
            "Respuesta de la API"
        }
        resultState = result
    }


    fun bloqueoApp(){
        Thread.sleep(5000)
        resultState = "Respesuta de la API"
    }
}