package com.yanjie.opencv;

import android.util.Log;

import org.opencv.android.OpenCVLoader;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

/**
 * Created by chao.wei on 2018/3/29.
 */
public class CompareUtil {
    private static final String TAG = "CompareUtil";

    private CompareUtil() {
    }

    public static native double nativeComparePSNR(long mat1, long mat2);

    public static native double nativeCompareSSIM(long mat1, long mat2);

    public static native double getMSSIM(long mat1, long mat2);

    public static double comparePSNR(Mat mat1, Mat mat2) {
        if (mat1.empty() || mat2.empty()) {
            Log.e(TAG, "mat1.empty() || mat2.empty()");
            return -1;
        }

        Mat mat1Copy = mat1.clone();
        Mat mat2Copy = mat2.clone();
        return nativeComparePSNR(mat1Copy.getNativeObjAddr(), mat2Copy.getNativeObjAddr());
    }

    public static double compareSSIM(Mat mat1, Mat mat2) {
        if (mat1.empty() || mat2.empty()) {
            Log.e(TAG, "mat1.empty() || mat2.empty()");
            return -1;
        }

        Mat mat1Copy = mat1.clone();
        Mat mat2Copy = mat2.clone();
        Log.e(TAG, "mat1.empty() || mat2.empty()" + mat1Copy.getNativeObjAddr() + "==" + mat2Copy.getNativeObjAddr());
        return nativeCompareSSIM(mat1Copy.getNativeObjAddr(), mat2Copy.getNativeObjAddr());
    }

    public static double compareSSIM2(Mat mat1, Mat mat2) {
        if (mat1.empty() || mat2.empty()) {
            Log.e(TAG, "mat1.empty() || mat2.empty()");
            return -1;
        }

        Mat mat1Copy = mat1.clone();
        Mat mat2Copy = mat2.clone();
        Log.e(TAG, "mat1.empty() || mat2.empty()" + mat1Copy.getNativeObjAddr() + "==" + mat2Copy.getNativeObjAddr());
        return getMSSIM(mat1Copy.getNativeObjAddr(), mat2Copy.getNativeObjAddr());
    }

    public static double compareHist(Mat mat1, Mat mat2) {
        if (mat1.empty() || mat2.empty()) {
            Log.e(TAG, "mat1.empty() || mat2.empty()");
            return -1;
        }

        Mat mat1Copy = mat1.clone();
        Mat mat2Copy = mat2.clone();
        Mat grayMat1 = new Mat();
        Mat grayMat2 = new Mat();
        Imgproc.cvtColor(mat1Copy, grayMat1, Imgproc.COLOR_BGR2GRAY);
        Imgproc.cvtColor(mat2Copy, grayMat2, Imgproc.COLOR_BGR2GRAY);
        Mat histConvertMat1 = new Mat();
        Mat histConvertMat2 = new Mat();
        grayMat1.convertTo(histConvertMat1, CvType.CV_32F);
        grayMat2.convertTo(histConvertMat2, CvType.CV_32F);
        return Imgproc.compareHist(histConvertMat1, histConvertMat2, Imgproc.CV_COMP_CORREL);
    }
}