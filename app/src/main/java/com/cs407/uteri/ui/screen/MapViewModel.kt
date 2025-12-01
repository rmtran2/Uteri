package com.cs407.uteri.ui.screen


import android.content.Context
import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.Place
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Collections.emptyList
import java.util.UUID

data class MapState(
    val currentLocation: LatLng? = null,
    val locationPermissionGranted: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null,
)

// Data class to represent marker information

class MapViewModel : ViewModel() {
    data class MarkerData(
        val id: String,
        val name: String,
        val latLng: LatLng
    )
    private val _markers = MutableStateFlow<List<MarkerData>>(emptyList())
    val markers = _markers.asStateFlow()

    fun addNearbyMarkers(places: List<Place>) {
        val newMarkers = places.mapNotNull { place ->
            val latLng = place.latLng ?: return@mapNotNull null
            MarkerData(
                id = place.id ?: "",
                name = place.name ?: "Unknown",
                latLng = latLng
            )
        }
        _markers.value = newMarkers
    }


    private val _uiState = MutableStateFlow(MapState())
    val uiState = _uiState.asStateFlow()
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    fun initializeLocationClient(context: Context) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    }


    fun getCurrentLocation() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            try {
                fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                    if (location != null) {
                        val latLng = LatLng(location.latitude, location.longitude)
                        _uiState.value = _uiState.value.copy(
                            currentLocation = latLng,
                            isLoading = false
                        )
                    } else {
                        _uiState.value = _uiState.value.copy(
                            error = "Unable to get current location.",
                            isLoading = false
                        )
                    }
                }.addOnFailureListener { e ->
                    _uiState.value = _uiState.value.copy(
                        error = "Failed to get location: ${e.message}",
                        isLoading = false
                    )
                }
            } catch (e: SecurityException) {
                _uiState.value = _uiState.value.copy(
                    error = "Location permission not granted.",
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Failed to get location: ${e.message}",
                    isLoading = false
                )
            }
        }
    }

    fun updateLocationPermission(granted: Boolean) {
        _uiState.value = _uiState.value.copy(locationPermissionGranted = granted)
    }

}
