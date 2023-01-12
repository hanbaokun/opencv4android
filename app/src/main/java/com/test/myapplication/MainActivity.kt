package com.test.myapplication

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.lanjingren.myapplication.R
import com.yanjie.opencv.CompareUtil
import org.opencv.android.InstallCallbackInterface
import org.opencv.android.LoaderCallbackInterface
import org.opencv.android.OpenCVLoader
import org.opencv.android.Utils
import org.opencv.core.Mat

class MainActivity : AppCompatActivity() {

    init
    {
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
                        var mat1 = Mat()
                        var mat2 = Mat()
                        var decodeResource = BitmapFactory.decodeResource(resources, R.drawable.account_head_default)
                        var decodeResource2 = BitmapFactory.decodeResource(resources, R.drawable.account_head)
                        Utils.bitmapToMat(decodeResource, mat1)
                        Utils.bitmapToMat(decodeResource2, mat2)
                        var ssim = CompareUtil.compareSSIM2(mat1, mat2)
                        Log.i("OpenCV ssim", ssim.toString())
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