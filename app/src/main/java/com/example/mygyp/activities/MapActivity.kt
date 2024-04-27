package com.example.mygyp.activities
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mygyp.R
import com.example.mygyp.databinding.ActivityMapBinding
import com.example.mygyp.models.Location
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MapActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerDragListener {
    private lateinit var map: GoogleMap
    private lateinit var binding: ActivityMapBinding
    private var location = Location()
    /**
     * Called when the activity is starting. This is where most initialization should go:
     * calling setContentView(int) to inflate the activity's UI, using findViewById(int)
     * to programmatically interact with widgets in the UI, and calling
     * [AppCompatActivity.setSupportActionBar] to set up the app bar.
     *
     * This method also initializes the map by inflating the layout, retrieving the location
     * data from the intent extras, and asynchronously loading the map.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in [AppCompatActivity.onSaveInstanceState].
     *     Note: Otherwise, it is null.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)
        location = intent.extras?.getParcelable<Location>("location")!!
        val mapFragment =
            supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }
    /**
     * Called when the GoogleMap object is ready for use.
     *
     * This method initializes the map by setting up the GoogleMap object, adding a marker
     * for the specified location, setting up marker drag listener, and moving the camera
     * to the specified location with the specified zoom level.
     *
     * @param googleMap The GoogleMap object that is ready for use.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        val loc = LatLng(location.lat, location.lng)
        val options =
            MarkerOptions()
                .title("Placemark")
                .snippet("GPS : $loc")
                .draggable(true)
                .position(loc)
        map.addMarker(options)
        map.setOnMarkerDragListener(this)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, location.zoom))
    }

    override fun onMarkerDrag(p0: Marker) { }
    /**
     * Called when the user finishes dragging a marker on the map.
     *
     * This method updates the location data (latitude, longitude, and zoom level)
     * based on the new position of the dragged marker and the current camera position.
     *
     * @param marker The marker that was dragged to a new position.
     */
    override fun onMarkerDragEnd(marker: Marker) {
        location.lat = marker.position.latitude
        location.lng = marker.position.longitude
        location.zoom = map.cameraPosition.zoom
    }
    /**
     * Called when the user starts dragging a marker on the map.
     *
     * This method is not utilized in this implementation.
     *
     * @param p0 The marker that is being dragged.
     */
    override fun onMarkerDragStart(p0: Marker) { }
    /**
     * Called when the user presses the device's back button.
     *
     * This method prepares the result intent containing the updated location data
     * to be sent back to the calling activity, sets the result code to indicate success,
     * finishes the current activity, and invokes the superclass implementation of
     * `onBackPressed`.
     */

    override fun onBackPressed() {
        val resultIntent = Intent()
        resultIntent.putExtra("location", location)
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
        super.onBackPressed()
    }
}
