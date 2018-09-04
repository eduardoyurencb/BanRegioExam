package examen.com.banregioapp.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.support.v4.content.ContextCompat
import android.view.Window
import android.widget.ImageView
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.squareup.picasso.Picasso
import examen.com.banregioapp.R
import examen.com.corebanregio.utils.Constants

class ViewUtils {
    companion object {
        fun showLoadingDialog(context: Context): Dialog {
            val alertDialog = Dialog(context, R.style.Theme_Dialog_Translucent)
            alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            alertDialog.setCancelable(false)
            alertDialog.setContentView(R.layout.dialog_loading)
            alertDialog.show()
            return alertDialog
        }

        fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor {
            val vectorDrawable = ContextCompat.getDrawable(context, vectorResId)
            vectorDrawable?.setBounds(0, 0, vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight)
            var bitmap: Bitmap? = null
            vectorDrawable?.let {
                bitmap = Bitmap.createBitmap(it.intrinsicWidth, it.intrinsicHeight, Bitmap.Config.ARGB_8888)
            }
            val canvas = Canvas(bitmap)
            vectorDrawable?.draw(canvas)
            return BitmapDescriptorFactory.fromBitmap(bitmap)
        }

        fun loadImgWithPicasso(context: Context?, nameImg: String, view: ImageView?) {
            Picasso.with(context)
                    .load(Constants.HOST + nameImg)
                    .placeholder(R.drawable.ban_regio)
                    .into(view)
        }
    }
}