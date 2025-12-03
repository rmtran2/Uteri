package com.cs407.uteri.ui.screen

import android.Manifest
import android.app.Application
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.cs407.uteri.ui.utils.Navbar
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.CircularBounds
import com.google.android.libraries.places.api.model.LocationRestriction
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.PlaceTypes
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.api.net.SearchNearbyRequest
import com.google.android.libraries.places.api.net.SearchNearbyResponse
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import android.location.Geocoder
import androidx.compose.runtime.snapshotFlow
import com.cs407.uteri.ui.screen.settings.getAbortionLawByState
import com.cs407.uteri.ui.screen.settings.getColorFromCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.withContext
import java.util.Locale

lateinit var includedPlaceTypes: List<String>
private lateinit var placesClient: PlacesClient

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun ResourceMapScreen(
    onNavigateBack: () -> Unit,
    navController: NavController,
    viewModel: MapViewModel = viewModel(),
){
    val markers by viewModel.markers.collectAsState()

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()

    var currentState by rememberSaveable{ mutableStateOf("Unknown") }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        if (!::placesClient.isInitialized) {
            placesClient = Places.createClient(context)
        }
    }
    // Get the permission access and remember for future
    val locationPermissionState = rememberPermissionState(
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    LaunchedEffect(Unit) {
        if (!locationPermissionState.status.isGranted) {
            locationPermissionState.launchPermissionRequest()
        } else {
            viewModel.updateLocationPermission(true)
            viewModel.initializeLocationClient(context)
            viewModel.getCurrentLocation()
        }
    }

    LaunchedEffect(locationPermissionState.status.isGranted) {
        viewModel.updateLocationPermission(locationPermissionState.status.isGranted)
        if (locationPermissionState.status.isGranted) {
            viewModel.initializeLocationClient(context)
            viewModel.getCurrentLocation()
        }
    }

    val defaultLocation = LatLng(43.0731, -89.4012)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(defaultLocation, 12f)
    }

    LaunchedEffect(cameraPositionState) {
        snapshotFlow { cameraPositionState.position.target }
            .distinctUntilChanged()
            .collect { latLng ->
                val state = withContext(Dispatchers.IO) {
                    getStateFromLatLng(context, latLng.latitude, latLng.longitude)
                }
                currentState = state
            }
    }

    LaunchedEffect(uiState.currentLocation) {
        println("DEBUG — currentState = $currentState")
        uiState.currentLocation?.let { location ->
            cameraPositionState.animate(
                CameraUpdateFactory.newLatLngZoom(location, 15f),
                1000
            )

            val state = withContext(Dispatchers.IO) {
                getStateFromLatLng(context, location.latitude, location.longitude)
            }
            currentState = state

            searchNearbyPlaces(
                location.latitude,
                location.longitude,
                viewModel
            )

        }
    }

    var expanded by rememberSaveable { mutableStateOf(false) }
    var query by remember { mutableStateOf("") }
    var showAbortionInfo by rememberSaveable { mutableStateOf(false) }

    Scaffold (
        bottomBar = {
            Navbar(navController)
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState
            ) {
                markers.forEach { marker ->
                    Marker(
                        state = MarkerState(position = marker.latLng),
                        title = marker.name
                    )
                }
            }
            Column(
                modifier = Modifier.align(Alignment.TopCenter).padding(top = 16.dp)
            ) {
                SearchBar(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                    inputField = {
                        SearchBarDefaults.InputField(
                            query = query,
                            onQueryChange = { query = it },
                            onSearch = {
                                expanded = false
                            },
                            expanded = expanded,
                            onExpandedChange = { expanded = it },
                            placeholder = { Text("Search") }
                        )
                    },
                    expanded = expanded,
                    onExpandedChange = { expanded = it },
                ) {
                    Text("TODO.")
                }

                FilterChip(
                    selected = showAbortionInfo,
                    onClick = { showAbortionInfo = !showAbortionInfo },
                    label = { Text("Display abortion information") },
                    Modifier.align(Alignment.CenterHorizontally)
                )
            }
            if (showAbortionInfo) {
                val law = getAbortionLawByState(currentState)

                val bgColor = law?.let { getColorFromCode(it.color) }
                    ?: Color.LightGray

                Surface(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(start = 16.dp, bottom = 80.dp)
                        .size(width = 300.dp, height = 200.dp),
                    shape = RoundedCornerShape(12.dp),
                    color = bgColor
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        if (law != null) {
                            Text(
                                "State: $currentState\n" +
                                        "Policy: ${law.currentPolicy}\n" +
                                        "Exceptions: ${law.exceptions}",
                                color = Color.Black
                            )
                        } else {
                            Text("Abortion law info not available.", color = Color.Black)
                        }
                    }
                }
            }
        }
    }
}

private fun searchNearbyPlaces(latitude: Double, longitude: Double, viewModel: MapViewModel) {
    val placeFields = listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG)

    val locationRestriction: LocationRestriction = CircularBounds.newInstance(
        LatLng(latitude, longitude),
        1500.0 // radius in meters
    )

    val request = SearchNearbyRequest.builder(locationRestriction, placeFields)
        .setIncludedTypes(listOf(PlaceTypes.RESTAURANT))
        .setMaxResultCount(20)
        .setPlaceFields(placeFields)
        .build()

    placesClient.searchNearby(request)
        .addOnSuccessListener { response ->
            viewModel.addNearbyMarkers(response.places)
        }
        .addOnFailureListener { exception ->
            exception.printStackTrace()
        }
}


class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()

        if (!Places.isInitialized()) {
            Places.initialize(this, "MAPS_API_KEY")
        }
    }
}

fun getStateFromLatLng(context: android.content.Context, lat: Double, lng: Double): String {
    return try {
        val geocoder = Geocoder(context, Locale.getDefault())
        val result = geocoder.getFromLocation(lat, lng, 1)?.firstOrNull()
        val stateName = result?.adminArea

        stateName ?: "Unknown"

    } catch (e: Exception) {
        "Unknown"
    }
}