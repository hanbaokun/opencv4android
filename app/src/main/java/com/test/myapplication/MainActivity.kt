package com.test.myapplication

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.lanjingren.myapplication.R
import org.opencv.android.InstallCallbackInterface
import org.opencv.android.LoaderCallbackInterface
import org.opencv.android.OpenCVLoader
import org.opencv.android.Utils
import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.core.Size
import org.opencv.imgproc.Imgproc
import java.io.ByteArrayOutputStream
import java.io.FileOutputStream


class MainActivity : AppCompatActivity() {

    init {
        System.loadLibrary("opencv")
    }

    var callback = object : LoaderCallbackInterface {
        override fun onManagerConnected(status: Int) {
            Log.i("OpenCV", status.toString())
            when (status) {
                LoaderCallbackInterface.SUCCESS -> {
                    println("OpenCV library init success. Using it!")

                    try {
                        println("======");

//                        var mat1 = Utils.loadResource(this@MainActivity, R.raw.ic_launcher)
//                        var mat2 = Utils.loadResource(this@MainActivity, R.raw.ic_launcher)
//                        var mat1 = Mat()
//                        var mat2 = Mat()

                        val options = BitmapFactory.Options()
                        options.inSampleSize = 2
                        options.inJustDecodeBounds = false
                        val bitmap = BitmapFactory.decodeFile(this@MainActivity.externalCacheDir?.path + "/aa.jpg", options)
//                        var bitmap = BitmapFactory.decodeResource(resources, R.drawable.account_head)
//                        Utils.bitmapToMat(android.R.attr.bitmap, mat1)
//                        Utils.bitmapToMat(decodeResource2, mat2)
//                        var ssim = CompareUtil.compareSSIM2(mat1, mat2)
// 将Bitmap转换为OpenCV的Mat对象

                        // 将Bitmap转换为OpenCV的Mat对象
                        val mat = Mat(bitmap.height, bitmap.width, CvType.CV_8UC4)
                        Utils.bitmapToMat(bitmap, mat)

                        // 缩放Mat对象

                        // 缩放Mat对象
                        val scaledMat = Mat()
                        Imgproc.resize(mat, scaledMat, Size(1200.0, 900.0), 0.0, 0.0, Imgproc.INTER_AREA)

                        // 将缩放后的Mat对象转换为Bitmap

                        // 将缩放后的Mat对象转换为Bitmap
                        val scaledBitmap = Bitmap.createBitmap(scaledMat.cols(), scaledMat.rows(), Bitmap.Config.ARGB_8888)
                        Utils.matToBitmap(scaledMat, scaledBitmap)

                        val mByteArrayOutputStream = ByteArrayOutputStream(scaledBitmap.width * scaledBitmap.height)

                        scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 70, mByteArrayOutputStream)
                        scaledBitmap.recycle()

                        val fos = FileOutputStream(this@MainActivity.externalCacheDir?.path + "/aaaa.jpg")
                        mByteArrayOutputStream.writeTo(fos)
                        fos.close()

                        // 释放Mat对象
                        mat.release()
                        scaledMat.release()


//                        Log.i("OpenCV ssim", ssim.toString())
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }

        override fun onPackageInstall(operation: Int, callback: InstallCallbackInterface?) {
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        if (!OpenCVLoader.initDebug()) {
            Log.d("TAG", "Internal OpenCV library not found. Using OpenCV Manager for initialization");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION, this, callback);
        } else {
            Log.d("TAG", "OpenCV library found inside package. Using it!");
            callback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }
}