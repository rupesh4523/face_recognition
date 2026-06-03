package com.example.facerecognition.activities

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.facerecognition.R
import android.widget.Button
import android.widget.Toast
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale
import android.content.Intent
import android.net.Uri
import android.widget.ImageView


class VerificationFaceCaptureActivity : AppCompatActivity() {

    private lateinit var previewView: PreviewView
    private lateinit var imageCapture: ImageCapture

    companion object {
        private const val CAMERA_PERMISSION_CODE = 101
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
            R.layout.activity_verification_face_capture
        )


        previewView = findViewById(R.id.previewView)
        val btnTakePhoto =
            findViewById<Button>(R.id.btnTakePhoto)
        btnTakePhoto.setOnClickListener {

            takePhoto()

        }

        if (allPermissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                CAMERA_PERMISSION_CODE
            )
        }
    }

    private fun allPermissionsGranted(): Boolean {

        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(
            requestCode,
            permissions,
            grantResults
        )

        if (requestCode == CAMERA_PERMISSION_CODE) {

            if (
                grantResults.isNotEmpty() &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {

                startCamera()
            }
        }
    }

    private fun startCamera() {

        val cameraProviderFuture =
            ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({

            val cameraProvider =
                cameraProviderFuture.get()

            val preview =
                Preview.Builder().build()
            imageCapture =
                ImageCapture.Builder().build()

            preview.surfaceProvider =
                previewView.surfaceProvider

            val cameraSelector =
                CameraSelector.DEFAULT_FRONT_CAMERA

            try {

                cameraProvider.unbindAll()

                cameraProvider.bindToLifecycle(
                    this,
                    cameraSelector,
                    preview,
                    imageCapture
                )

            } catch (e: Exception) {

                e.printStackTrace()
            }

        }, ContextCompat.getMainExecutor(this))
    }
    private fun takePhoto() {

        val imageFile = File(

            getExternalFilesDir("images"),

            SimpleDateFormat(
                "yyyyMMdd_HHmmss",
                Locale.getDefault()
            ).format(System.currentTimeMillis()) + ".jpg"
        )

        val outputOptions =
            ImageCapture.OutputFileOptions.Builder(
                imageFile
            ).build()

        imageCapture.takePicture(

            outputOptions,

            ContextCompat.getMainExecutor(this),

            object : ImageCapture.OnImageSavedCallback {

                override fun onImageSaved(
                    outputFileResults: ImageCapture.OutputFileResults
                ) {

                    val resultIntent = Intent()

                    resultIntent.putExtra(
                        "IMAGE_PATH",
                        imageFile.absolutePath
                    )

                    setResult(
                        RESULT_OK,
                        resultIntent
                    )

                    finish()
                }

                override fun onError(
                    exception: ImageCaptureException
                ) {

                    Toast.makeText(
                        this@VerificationFaceCaptureActivity,
                        "Capture Failed",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }
        )
    }
}