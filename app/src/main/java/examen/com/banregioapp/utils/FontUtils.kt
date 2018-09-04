package examen.com.banregioapp.utils

import android.content.Context
import android.graphics.Typeface
import java.text.NumberFormat
import java.util.*

class FontUtils {
    companion object {
        val ROOT = "fonts/"
        val FONTAWESOME = ROOT + "fontawesome-webfont.ttf"


        fun getDataCurrency(
                price: Int
        ): String {
            val currencyFormat = Locale("es", "MX")
            val format = NumberFormat.getCurrencyInstance(currencyFormat)
            return format.format(price)
        }

        @JvmStatic
        fun getTypeface(
                context: Context?,
                font: String
        ): Typeface = Typeface.createFromAsset(
                context?.getAssets(),
                font
        )
    }
}