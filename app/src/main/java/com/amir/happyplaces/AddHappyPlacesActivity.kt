package com.amir.happyplaces

import android.Manifest
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.android.synthetic.main.activity_add_happy_places.*
import java.text.SimpleDateFormat
import java.util.*


/**
 * to make whole class an onClickListener, we follow the instructions of below
 */
class AddHappyPlacesActivity : AppCompatActivity(), View.OnClickListener {
    //from java.util
    private var cal = Calendar.getInstance()
    private lateinit var dateSetListener: DatePickerDialog.OnDateSetListener


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_happy_places)

        setSupportActionBar(toolbar_add_place)

        //this line will add the back button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toolbar_add_place?.setNavigationOnClickListener {
            onBackPressed()
        }
//OnDateSetListener: is very similar ro setNavigationListener, but here we wait to someone set the date
        dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, month)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateDateInView()
        }
        et_date.setOnClickListener(this)
       tv_add_image.setOnClickListener(this)
    }

    //instead of to write for every item an OnclickListener, we follow this instruction
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.et_date -> {
                DatePickerDialog(
                    this@AddHappyPlacesActivity,
                    dateSetListener,
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)

                ).show()
            }
            R.id.tv_add_image -> {
                val pictureDialog = AlertDialog.Builder(this)
                pictureDialog.setTitle("Select Action")
                val pictureDialogItems =
                    arrayOf("Select Photo from Gallery", "Capture photo from camera")
                pictureDialog.setItems(pictureDialogItems) { dialog, which ->
                    when (which) {
                        0 -> choosePhotoFromGallery()
                        1 -> Toast.makeText(this, "camera selection coming soon", Toast.LENGTH_LONG)
                            .show()
                    }

                }
                pictureDialog.show()
            }

        }
    }

    private fun choosePhotoFromGallery() {
        Dexter.withContext(this)
            .withPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ).withListener(object : MultiplePermissionsListener {  //instead of new we use object
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    if (report!!.areAllPermissionsGranted()) {
                        Toast.makeText(
                            this@AddHappyPlacesActivity,
                            "Storage READ/WRITE are granted. Now you can select an image from GALLERY",
                            Toast.LENGTH_LONG).show()
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions:MutableList<PermissionRequest?>?,
                    token: PermissionToken?
                ) {
                    showRationalDialogForPermissions()
                }
            }).onSameThread().check()
    }

    private fun showRationalDialogForPermissions() {
        AlertDialog.Builder(this).setMessage(
            "It looks like you have turned off permission required for this feature." +
                    "It can be enabled under the Application Setting."
        ).
            //_ it means we don't or didn't use it
        setPositiveButton("GO TO SETTINGS") { _, _ ->
            //it sends the user to setting
            try {
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri = Uri.fromParts("package", packageName, null)
                intent.data = uri
                startActivity(intent)
            } catch (e : ActivityNotFoundException){
                e.printStackTrace()
            }
        }.setNegativeButton("Cancel"){dialog,_->
           dialog.dismiss()
        }.show()
    }

    private fun updateDateInView() {
        val mFormat = "dd.MM.yyyy"
        val sdf = SimpleDateFormat(mFormat, Locale.getDefault())
        et_date.setText(sdf.format(cal.time).toString())
    }
}