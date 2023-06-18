package com.example.fragaria

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.fragaria.databinding.ActivityScanBinding
import com.example.fragaria.ml.TfliteConvert
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.File
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.ByteOrder

class ScanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScanBinding
    private val imageSize = 224
    private var getMyFile: File? = null
    private var resultDetection: Float = 0.0f
    private var resultDetectionIntent: Int = 0
    private var resultText: String = ""
    private lateinit var imagePredict: Bitmap

    companion object {
        const val CAMERA_X_RESULT = 700

        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    "Tidak mendapatkan permission.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }
        binding.cameraButton.setOnClickListener { startTakePhoto() }
        binding.galleryButton.setOnClickListener { startGallery() }
        binding.uploadButton.setOnClickListener { uploadImage() }

    }

    private fun startTakePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(packageManager)

        createTempFile(application).also {
            val photoURI: Uri = FileProvider.getUriForFile(
                this@ScanActivity,
                "com.example.fragaria",
                it
            )
            currentPhotoPath = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            launcherIntentCamera.launch(intent)
        }
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private lateinit var currentPhotoPath: String
    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val myFile = File(currentPhotoPath)
            getMyFile = myFile

            val result = rotateBitmap(
                BitmapFactory.decodeFile(getMyFile?.path),
                true
            )

            binding.previewImageView.setImageBitmap(result)
            imagePredict = Bitmap.createScaledBitmap(result, imageSize,imageSize,false)
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val myFile = uriToFile(selectedImg, this@ScanActivity)
            getMyFile = myFile
            val result = BitmapFactory.decodeFile(getMyFile?.path)
            binding.previewImageView.setImageBitmap(result)
            imagePredict = Bitmap.createScaledBitmap(result, imageSize,imageSize,false)
        }
    }

    private fun uploadImage() {
        Log.d("cekFile", getMyFile.toString())
            if (getMyFile != null) {
                predictImage(imagePredict)
                val intentToResult = Intent(this@ScanActivity, ResultActivity::class.java)
                intentToResult.putExtra(ResultActivity.PHOTO, getMyFile)
                intentToResult.putExtra(ResultActivity.NAME, resultText)
                startActivity(intentToResult)
                Log.d("kirimfoto", getMyFile.toString())
                Log.d("kirimResultPercent", resultDetectionIntent.toString())
                Log.d("kirimResultText", resultText)
                Log.d("cekFoto", imagePredict.toString())
            } else {
                Toast.makeText(applicationContext, "Masukkan foto daun terlebih dahulu", Toast.LENGTH_SHORT).show()
            }
        }

    private fun predictImage(imagePredict: Bitmap) {
        try {
            val model = TfliteConvert.newInstance(applicationContext)

            // Creates inputs for reference.
            val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, imageSize, imageSize, 3), DataType.FLOAT32)
            val byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3)
            byteBuffer.order(ByteOrder.nativeOrder())

            inputFeature0.loadBuffer(byteBuffer)
            val intValues = IntArray(imageSize*imageSize)
            imagePredict.getPixels(intValues,0,imagePredict.width,0,0,imagePredict.width,imagePredict.height)
            var pixel = 0

            for (i in 0 until imageSize) {
                for (j in 0 until imageSize) {
                    val rgb = intValues[pixel++]
                    byteBuffer.putFloat((rgb shr 16 and 0xFF) * (1f/1))
                    byteBuffer.putFloat((rgb shr 8 and 0xFF) * (1f/1))
                    byteBuffer.putFloat((rgb and 0xFF) * (1f/1))
                }
            }
            inputFeature0.loadBuffer(byteBuffer)


            // Runs model inference and gets result.
            val outputs = model.process(inputFeature0)
            val outputFeature0 = outputs.outputFeature0AsTensorBuffer
            val confidences = outputFeature0.floatArray

            //find index of the class with biggest confidence
            var maxConfidence = 0f
            for (i in confidences.indices) {
                if (confidences[i] > maxConfidence) {
                    maxConfidence = confidences[i]
                }
            }

            resultDetection = maxConfidence

            //declaration class from index
            when (resultDetection) {
                confidences[0] -> {
                    resultText = "Embun Tepung"
                }
                confidences[1] -> {
                    resultText = "Mite"
                }
                confidences[2] -> {
                    resultText = "Sehat"
                }
                confidences[3] -> {
                    resultText = "Ulat"
                }
            }

            Log.d("cekOutput", resultDetection.toString())
            Log.d("cekConfidences", maxConfidence.toString())
            Log.d("cekOutput", confidences.indices.toString())
            Log.d("cekIndex1", confidences[0].toString())
            Log.d("cekIndex2", confidences[1].toString())
            Log.d("cekIndex3", confidences[2].toString())
            Log.d("cekIndex4", confidences[3].toString())

            // Releases model resources if no longer used.
            model.close()
        }
        catch (e: IOException) {
            Toast.makeText(this,R.string.image_error, Toast.LENGTH_SHORT).show()
        }
    }

}