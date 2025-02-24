package com.example.apirestaad

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun EditarProductoScreen(
    producto: Producto,
    viewModel: ProductoViewModel = viewModel(),
    onProductoActualizado: () -> Unit
) {
    var nombre by remember { mutableStateOf(producto.nombre) }
    var precio by remember { mutableStateOf(producto.precio.toString()) }
    var mensajeError by remember { mutableStateOf<String?>(null) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Editar Producto", style = MaterialTheme.typography.headlineMedium)

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre del Producto") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = precio,
            onValueChange = { precio = it },
            label = { Text("Precio") },
            modifier = Modifier.fillMaxWidth()
        )

        if (mensajeError != null) {
            Text(text = mensajeError!!, color = MaterialTheme.colorScheme.error)
        }

        Row(modifier = Modifier.padding(top = 16.dp)) {
            Button(
                onClick = {
                    val precioDouble = precio.toDoubleOrNull()
                    if (nombre.isBlank() || precioDouble == null || precioDouble <= 0) {
                        mensajeError = "Nombre y precio válidos son obligatorios"
                    } else {
                        val productoActualizado = producto.copy(nombre = nombre, precio = precioDouble)
                        viewModel.actualizarProducto(productoActualizado)
                        onProductoActualizado()
                    }
                }
            ) {
                Text("Guardar Cambios")
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(onClick = { onProductoActualizado() }) {
                Text("Cancelar")
            }
        }
    }
}
