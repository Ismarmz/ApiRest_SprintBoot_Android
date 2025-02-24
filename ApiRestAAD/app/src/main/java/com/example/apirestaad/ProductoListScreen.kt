package com.example.apirestaad

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ProductoListScreen(
    modifier: Modifier = Modifier,
    viewModel: ProductoViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val productos by viewModel.productos.collectAsState()
    val error by viewModel.error.collectAsState()

    Column(modifier = modifier.padding(16.dp)) {
        if (error != null) {
            Text(text = error!!, color = Color.Red)
        }

        LazyColumn {
            items(productos) { producto ->
                ProductoItem(producto, viewModel)
            }
        }
    }
    val isLoading by viewModel.isLoading.collectAsState()

    if (isLoading) {
        CircularProgressIndicator(modifier = Modifier.padding(16.dp))
    }

}

