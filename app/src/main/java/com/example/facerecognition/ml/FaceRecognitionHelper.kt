package com.example.facerecognition.ml

import android.content.Context
import android.graphics.Bitmap
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.channels.FileChannel

class FaceRecognitionHelper(
    private val context: Context
) {

    private var interpreter: Interpreter

    init {
        interpreter = Interpreter(loadModelFile())
    }

    private fun loadModelFile() =
        FileInputStream(
            context.assets.openFd("mobilefacenet.tflite").fileDescriptor
        ).channel.map(
            FileChannel.MapMode.READ_ONLY,
            context.assets.openFd("mobilefacenet.tflite").startOffset,
            context.assets.openFd("mobilefacenet.tflite").declaredLength
        )

    fun getEmbedding(bitmap: Bitmap): FloatArray {

        val resizedBitmap =
            Bitmap.createScaledBitmap(
                bitmap,
                112,
                112,
                true
            )

        val inputBuffer =
            ByteBuffer.allocateDirect(
                1 * 112 * 112 * 3 * 4
            )

        inputBuffer.order(
            ByteOrder.nativeOrder()
        )

        for (y in 0 until 112) {

            for (x in 0 until 112) {

                val pixel =
                    resizedBitmap.getPixel(x, y)

                inputBuffer.putFloat(
                    ((pixel shr 16 and 0xFF) - 128f) / 128f
                )

                inputBuffer.putFloat(
                    ((pixel shr 8 and 0xFF) - 128f) / 128f
                )

                inputBuffer.putFloat(
                    ((pixel and 0xFF) - 128f) / 128f
                )
            }
        }

        val embedding =
            Array(1) { FloatArray(192) }

        interpreter.run(
            inputBuffer,
            embedding
        )
        println("Embedding Size = ${embedding[0].size}")

        return embedding[0]
    }

    fun compareFaces(
        embedding1: FloatArray,
        embedding2: FloatArray
    ): Float {

        var distance = 0f

        for (i in embedding1.indices) {

            val diff =
                embedding1[i] - embedding2[i]

            distance += diff * diff
        }

        return kotlin.math.sqrt(distance)
    }
    fun getConfidence(
        embedding1: FloatArray,
        embedding2: FloatArray
    ): Float {

        val distance =
            compareFaces(
                embedding1,
                embedding2
            )

        var confidence =
            (1f - (distance / 2f)) * 100f

        if (confidence < 0f)
            confidence = 0f

        if (confidence > 100f)
            confidence = 100f

        return confidence
    }

    fun isMatch(
        embedding1: FloatArray,
        embedding2: FloatArray
    ): Boolean {

        val distance =
            compareFaces(
                embedding1,
                embedding2
            )

        return distance < 1.0f
    }
}