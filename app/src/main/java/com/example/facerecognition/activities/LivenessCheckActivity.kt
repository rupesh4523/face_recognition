package com.example.facerecognition.activities

import android.content.Intent
import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.facerecognition.R
import com.example.facerecognition.ml.FaceDetectorHelper
import com.google.mlkit.vision.common.InputImage
import android.widget.Toast
import androidx.camera.core.ExperimentalGetImage
import android.graphics.BitmapFactory
import androidx.activity.result.contract.ActivityResultContracts
import com.example.facerecognition.database.CandidateRepository
import com.example.facerecognition.ml.FaceRecognitionHelper

@ExperimentalGetImage
class LivenessCheckActivity : AppCompatActivity() {

    private lateinit var previewView: PreviewView
    private lateinit var txtStatus: TextView

    private val faceDetector = FaceDetectorHelper()
    private var candidateId = -1
    private var candidateName = ""
    private var imagePath = ""

    private var eyesClosedDetected = false
    private var livenessPassed = false
    private val verificationCaptureLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->

            if (result.resultCode == RESULT_OK) {

                val verificationImagePath =
                    result.data?.getStringExtra(
                        "IMAGE_PATH"
                    ) ?: ""

                compareFaces(
                    verificationImagePath
                )
            }
        }
    private fun compareFaces(
        verificationImagePath: String
    ) {
        if (
            verificationImagePath.isEmpty()
        ) {

            Toast.makeText(
                this,
                "Verification Image Missing",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        val repository =
            CandidateRepository(this)

        val candidate =
            repository.getCandidateById(
                candidateId
            )

        if (candidate == null) {

            Toast.makeText(
                this,
                "Candidate Not Found",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        val registeredBitmap =
            BitmapFactory.decodeFile(
                candidate.imagePath
            )

        val verificationBitmap =
            BitmapFactory.decodeFile(
                verificationImagePath
            )

        val helper =
            FaceRecognitionHelper(this)

        val embedding1 =
            helper.getEmbedding(
                registeredBitmap
            )

        val embedding2 =
            helper.getEmbedding(
                verificationBitmap
            )

        val matched =
            helper.isMatch(
                embedding1,
                embedding2
            )
        val confidence =
            helper.getConfidence(
                embedding1,
                embedding2
            )

        val intent =
            Intent(
                this,
                VerificationResultActivity::class.java
            )

        intent.putExtra(
            "CANDIDATE_ID",
            candidate.candidateId
        )

        intent.putExtra(
            "CANDIDATE_NAME",
            candidate.name
        )

        intent.putExtra(
            "IMAGE_PATH",
            candidate.imagePath
        )

        intent.putExtra(
            "MATCH_RESULT",
            matched
        )
        intent.putExtra(
            "CONFIDENCE",
            confidence
        )

        startActivity(intent)

        finish()
    }

    companion object {
        private const val CAMERA_PERMISSION_CODE = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_liveness_check)
        candidateId =
            intent.getIntExtra(
                "CANDIDATE_ID",
                -1
            )

        candidateName =
            intent.getStringExtra(
                "CANDIDATE_NAME"
            ) ?: ""

        imagePath =
            intent.getStringExtra(
                "IMAGE_PATH"
            ) ?: ""
        Toast.makeText(
            this,
            "Candidate: $candidateName",
            Toast.LENGTH_SHORT
        ).show()

        previewView = findViewById(R.id.previewView)
        txtStatus = findViewById(R.id.txtStatus)

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

            preview.surfaceProvider =
                previewView.surfaceProvider

            val imageAnalysis =
                ImageAnalysis.Builder()
                    .setBackpressureStrategy(
                        ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST
                    )
                    .build()

            imageAnalysis.setAnalyzer(
                ContextCompat.getMainExecutor(this)
            ) { imageProxy ->

                processImage(imageProxy)

            }

            val cameraSelector =
                CameraSelector.DEFAULT_FRONT_CAMERA

            try {

                cameraProvider.unbindAll()

                cameraProvider.bindToLifecycle(
                    this,
                    cameraSelector,
                    preview,
                    imageAnalysis
                )

            } catch (e: Exception) {
                e.printStackTrace()
            }

        }, ContextCompat.getMainExecutor(this))
    }

    @ExperimentalGetImage
    private fun processImage(
        imageProxy: ImageProxy
    ) {

        val mediaImage = imageProxy.image

        if (mediaImage != null) {

            val image =
                InputImage.fromMediaImage(
                    mediaImage,
                    imageProxy.imageInfo.rotationDegrees
                )

            faceDetector.detector
                .process(image)
                .addOnSuccessListener { faces ->

                    if (faces.isNotEmpty()) {

                        val face = faces[0]

                        val leftEye =
                            face.leftEyeOpenProbability ?: 1f

                        val rightEye =
                            face.rightEyeOpenProbability ?: 1f

                        if (!livenessPassed) {

                            if (
                                !eyesClosedDetected &&
                                leftEye < 0.4f &&
                                rightEye < 0.4f
                            ) {

                                eyesClosedDetected = true

                                txtStatus.text =
                                    "Status : Eyes Closed Detected"

                            }

                            else if (
                                eyesClosedDetected &&
                                leftEye > 0.8f &&
                                rightEye > 0.8f
                            ) {

                                livenessPassed = true

                                txtStatus.text =
                                    "Status : Liveness Passed"

                                Toast.makeText(
                                    this,
                                    "Liveness Verification Successful",
                                    Toast.LENGTH_SHORT
                                ).show()

                                val intent =
                                    Intent(
                                        this,
                                        VerificationFaceCaptureActivity::class.java
                                    )

                                verificationCaptureLauncher.launch(
                                    intent
                                )
                            }

                            else {

                                txtStatus.text =
                                    "Status : Blink To Verify"
                            }
                        }

                        else {

                            txtStatus.text =
                                "Status : Liveness Passed"
                        }

                    }

                    else {

                        txtStatus.text =
                            "Status : No Face Detected"
                    }

                }

                .addOnFailureListener {

                    txtStatus.text =
                        "Status : Detection Error"

                }

                .addOnCompleteListener {

                    imageProxy.close()

                }

        }

        else {

            imageProxy.close()

        }
    }
}