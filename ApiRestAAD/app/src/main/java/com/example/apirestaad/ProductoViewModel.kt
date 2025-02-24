package com.example.apirestaad

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductoViewModel : ViewModel() {
    private val _productos = MutableStateFlow<List<Producto>>(emptyList())
    val productos: StateFlow<List<Producto>> = _productos

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading


    init {
        cargarProductos()
    }

    fun cargarProductos() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _productos.value = ApiClient.getProductos()
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Error al cargar productos: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }


    fun agregarProducto(producto: Producto) {
        viewModelScope.launch {
            try {
                ApiClient.addProducto(producto)
                cargarProductos()
            } catch (e: Exception) {
                _error.value = "Error al agregar producto: ${e.message}"
        }
        }
    }
    fun eliminarProducto(id: Int) {
        viewModelScope.launch {
            try {
                ApiClient.deleteProducto(id)
                cargarProductos() // Recargar la lista después de eliminar
            } catch (e: Exception) {
                _error.value = "Error al eliminar producto: ${e.message}"
            }
        }
    }

    fun actualizarProducto(producto: Producto) {
        viewModelScope.launch {
            try {
                ApiClient.updateProducto(producto)
                cargarProductos() // Recargar lista después de actualizar
            } catch (e: Exception) {
                _error.value = "Error al actualizar producto: ${e.message}"
            }
        }
    }

}


