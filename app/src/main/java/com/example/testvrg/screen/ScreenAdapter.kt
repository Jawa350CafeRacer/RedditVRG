package com.example.testvrg.screen


import android.content.ContentValues
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.drawToBitmap
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testvrg.R
import com.example.testvrg.model.Children
import kotlinx.android.synthetic.main.post.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList


class ScreenAdapter : RecyclerView.Adapter<ScreenAdapter.StartViewHolder>() {
    class StartViewHolder(view: View) : RecyclerView.ViewHolder(view)

    var listener: ((String?) -> Unit)? = null
    private var listScreen = ArrayList<Children>()
    var after: String? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StartViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.post, parent, false)
        return StartViewHolder(view)
    }

    override fun onBindViewHolder(holder: StartViewHolder, position: Int) {

        val item = listScreen[position].data
        val simpleDateFormat = SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss")
        val datePost = (item.created)
        fun getDataString(datePost: Int): String = simpleDateFormat.format(datePost * 1000L)
        val past: Date = simpleDateFormat.parse(getDataString(datePost))
        val now = Date()
        val hours: Long = TimeUnit.MILLISECONDS.toHours(now.getTime() - past.getTime())

        holder.itemView.textView2.text = item.title
        holder.itemView.item_title.text = "Posted by ${item.author}, ${hours} h ago"
        holder.itemView.textView.text = "${item.num_comments} comments"

        if (item.is_video) {
            Glide.with(holder.itemView.context).load(item.thumbnail).into(holder.itemView.imageView)
        } else {
            Glide.with(holder.itemView.context).load(item.url_overridden_by_dest)
                .into(holder.itemView.imageView)
        }


        holder.itemView.button.setOnClickListener() {
            CoroutineScope(Dispatchers.IO).launch {


                // this method saves the image to gallery
                fun saveMediaToStorage(bitmap: Bitmap) {
                    // Generating a file name
                    val filename = "${System.currentTimeMillis()}.jpg"

                    // Output stream
                    var fos: OutputStream? = null

                    // For devices running android >= Q
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        // getting the contentResolver
                        holder.itemView.context.contentResolver?.also { resolver ->

                            // Content resolver will process the contentvalues
                            val contentValues = ContentValues().apply {

                                // putting file information in content values
                                put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                                put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                                put(
                                    MediaStore.MediaColumns.RELATIVE_PATH,
                                    Environment.DIRECTORY_PICTURES
                                )
                            }

                            // Inserting the contentValues to
                            // contentResolver and getting the Uri
                            val imageUri: Uri? = resolver.insert(
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                contentValues
                            )

                            // Opening an outputstream with the Uri that we got
                            fos = imageUri?.let { resolver.openOutputStream(it) }
                        }
                    } else {
                        // These for devices running on android < Q
                        val imagesDir =
                            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                        val image = File(imagesDir, filename)
                        fos = FileOutputStream(image)
                    }

                    fos?.use {
                        // Finally writing the bitmap to the output stream that we opened
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)

                    }

                }
                saveMediaToStorage(holder.itemView.imageView.drawToBitmap())
            }
            Toast.makeText(
                holder.itemView.context,
                "Image saved to Gallery",
                Toast.LENGTH_SHORT
            ).show()
        }

        if (position == itemCount - 1 && after != null) {
            listener?.invoke(after)
        }
    }


    override fun getItemCount(): Int {
        return listScreen.size
               }

    fun setList(list: List<Children>, after: String?) {
        listScreen.addAll(list)
        this.after = after
        notifyDataSetChanged()
    }
}

