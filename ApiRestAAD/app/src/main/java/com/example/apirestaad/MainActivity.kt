package com.example.apirestaad

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.apirestaad.ui.theme.ApiRestAADTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: ProductoViewModel = viewModel()
            var mostrarDialogo by remember { mutableStateOf(false) }

            ApiRestAADTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    floatingActionButton = {
                        FloatingActionButton(onClick = { mostrarDialogo = true }) {
                            Icon(Icons.Default.Add, contentDescription = "Agregar Producto")
                        }
                    },
                    content = { padding ->
                        ProductoListScreen(
                            modifier = Modifier.padding(padding),
                            viewModel = viewModel
                        )
                    }
                )

                if (mostrarDialogo) {
                    AgregarProductoDialog(
                        onDismiss = { mostrarDialogo = false },
                        onProductoAgregado = { producto ->
                            viewModel.agregarProducto(producto)
                            mostrarDialogo = false
                        }
                    )
                }
            }
        }
    }
}


@Composable
fun ProductoItem(producto: Producto, viewModel: ProductoViewModel) {
    var mostrarEdicion by remember { mutableStateOf(false) }
    var mostrarDialogoEliminar by remember { mutableStateOf(false) }

    if (mostrarEdicion) {
        EditarProductoScreen(
            producto = producto,
            viewModel = viewModel,
            onProductoActualizado = { mostrarEdicion = false }
        )
    } else {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Text(
                    text = producto.nombre,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary
                )

                Text(
                    text = "Precio: ${producto.precio} $",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Row(modifier = Modifier.padding(top = 8.dp)) {
                    Button(
                        onClick = { mostrarDialogoEliminar = true },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                    ) {
                        Text("Eliminar", color = Color.White)
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = { mostrarEdicion = true }
                    ) {
                        Text("Editar")
                    }
                }
            }
        }
    }

    // Diálogo de confirmación para eliminar producto
    if (mostrarDialogoEliminar) {
        AlertDialog(
            onDismissRequest = { mostrarDialogoEliminar = false },
            title = { Text("Eliminar Producto") },
            text = { Text("¿Estás seguro de que quieres eliminar ${producto.nombre}?") },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.eliminarProducto(producto.id!!)
                        mostrarDialogoEliminar = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text("Sí, eliminar", color = Color.White)
                }
            },
            dismissButton = {
                TextButton(onClick = { mostrarDialogoEliminar = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
fun AgregarProductoDialog(
    onDismiss: () -> Unit,
    onProductoAgregado: (Producto) -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var mensajeError by remember { mutableStateOf<String?>(null) }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text("Agregar Producto", style = MaterialTheme.typography.headlineSmall) },
        text = {
            Column(modifier = Modifier.padding(8.dp)) {
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
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val precioDouble = precio.toDoubleOrNull()
                    if (nombre.isBlank() || precioDouble == null || precioDouble <= 0) {
                        mensajeError = "Nombre y precio válidos son obligatorios"
                    } else {
                        onProductoAgregado(Producto(nombre = nombre, precio = precioDouble))
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text("Agregar", color = Color.White)
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Cancelar", color = MaterialTheme.colorScheme.secondary)
            }
        }
    )
}

@Composable
fun AnimatedFab(onClick: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    FloatingActionButton(
        onClick = {
            expanded = !expanded
            onClick()
        },
        containerColor = MaterialTheme.colorScheme.primary
    ) {
        Icon(
            imageVector = if (expanded) Icons.Default.Check else Icons.Default.Add,
            contentDescription = "Agregar Producto",
            tint = Color.White
        )
    }
}



