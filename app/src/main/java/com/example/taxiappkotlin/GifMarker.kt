package com.example.taxiappkotlin

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class GifMarker(
    private val context: Context,
    private val googleMap: GoogleMap,
    private val gifUrl: String,
    private val position: LatLng
) {
    private lateinit var marker: Marker

    init {
        createMarker()
    }

    private fun createMarker() {
        val markerOptions = MarkerOptions().position(position)
        marker = googleMap.addMarker(markerOptions)

        Glide.with(context)
            .asGif()
            .load(gifUrl)
            .into(GlideMarkerTarget(marker, context))
    }

    private class GlideMarkerTarget(
        private val marker: Marker,
        private val context: Context
    ) : com.bumptech.glide.request.target.SimpleTarget<GifDrawable>() {

        override fun onResourceReady(resource: GifDrawable, transition: com.bumptech.glide.request.transition.Transition<in GifDrawable>?) {
            val imageView = ImageView(context)
            imageView.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            imageView.setImageDrawable(resource)

            val markerIcon: BitmapDescriptor = getMarkerIconFromView(imageView)
            marker.setIcon(markerIcon)
        }

        private fun getMarkerIconFromView(view: View): BitmapDescriptor {
            val displayMetrics = DisplayMetrics()
            (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager)
                .defaultDisplay.getMetrics(displayMetrics)
            view.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels)
            view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels)
            val bitmap = Bitmap.createBitmap(
                view.measuredWidth,
                view.measuredHeight,
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            view.draw(canvas)
            return BitmapDescriptorFactory.fromBitmap(bitmap)
        }
    }
}
