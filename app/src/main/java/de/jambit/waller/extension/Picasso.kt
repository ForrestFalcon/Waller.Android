package de.jambit.waller.extension

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.ImageView
import java.io.*

/**
 * Converts LiveData into a Flowable
 */
fun ImageView.cachedPicasso(context: Context, url: String?) {
    if (url == null) return

    val bitmap = getCachedBitmap(context, url)
    if (bitmap != null) {
        this.setImageBitmap(bitmap)
    }
}


private fun getCachedBitmap(context: Context, url: String): Bitmap? {
    val CACHE_PATH = context.cacheDir.absolutePath + "/picasso-cache/"

    val files = File(CACHE_PATH).listFiles()
    for (file in files) {
        val fname = file.getName()
        if (fname.contains(".") && fname.substring(fname.lastIndexOf(".")) == ".0") {
            try {
                val br = BufferedReader(FileReader(file))
                if (br.readLine().equals(url)) {
                    val image_path = CACHE_PATH + fname.replace(".0", ".1")
                    if (File(image_path).exists()) {
                        return BitmapFactory.decodeFile(image_path)
                    }
                }
            } catch (e: FileNotFoundException) {
            } catch (e: IOException) {
            }

        }
    }
    return null
}