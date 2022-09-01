package com.example.myapplication

import android.Manifest
import android.content.ClipData
import android.content.ClipboardManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.net.toUri
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.myapplication.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        ActivityCompat.requestPermissions(
            this@MainActivity,
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ),
            1
        )

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()

//            val mClipboard: ClipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

//            val path: String = ContentResolver.SCHEME_ANDROID_RESOURCE + "://com.example.myapplication/app/main/res/drawable/netflix"
//            val path: String = "android.resource://com.example.myapplication/drawable/netflix"
//            val path: String = "content://media/external/images/media/853"
            val path: String = "/storage/emulated/0/Download/1661594587746.png"
//            val path: String = MediaStore.Images.Media.EXTERNAL_CONTENT_URI.toString()+"/846"

//            R.drawable.netflix
//
//            Log.d("tag", path)
//            Log.d("tag", "android.resource://"+R.drawable.netflix)
//
//            val uriCopy: Uri = Uri.parse(path)
//
//            val clipData: ClipData = ClipData.newRawUri("some label", uriCopy)
//
//            mClipboard.setPrimaryClip(clipData)


            val file: File = File(path)



            val imageFile = File(getRealPathFromURI(Uri.parse(MediaStore.Images.Media.EXTERNAL_CONTENT_URI.toString()+"/846")))

            Log.d("uri to path", imageFile.path)


            val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
//            val clip = ClipData.newUri(contentResolver, "URI", Uri.parse(MediaStore.Images.Media.EXTERNAL_CONTENT_URI.toString()+"/846"))
            val uri = Uri.fromFile(imageFile)
            val clip = ClipData.newUri(contentResolver, "URI", uri)
//            val clip = ClipData.newUri(contentResolver, "URI", file.toUri())

            //this.grantUriPermission(getPackageName(), imgURI, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);

//            Log.d("path", )
            try {
                clipboard.setPrimaryClip(clip)
            } catch (e: Exception) {

            }


        }
    }


    private fun getRealPathFromURI(contentURI: Uri): String? {
        val result: String?
        val cursor: Cursor? = contentResolver.query(contentURI, null, null, null, null)
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.path
        } else {
            cursor.moveToFirst()
            val idx: Int = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            result = cursor.getString(idx)
            cursor.close()
        }
        return result
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}