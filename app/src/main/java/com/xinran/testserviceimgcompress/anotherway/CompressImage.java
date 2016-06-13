package com.xinran.testserviceimgcompress.anotherway;

/**
 * 压缩照片2.0
 * <p/>
 * Created by qixinh on 16/6/13.
 */
public interface CompressImage {
    void compress(String imagePath, CompressListener listener);

    /**
     * 压缩结果监听器
     */
    interface CompressListener {
        /**
         * 压缩成功
         *
         * @param imgPath 压缩图片的路径
         */
        void onCompressSuccessed(String imgPath);

        /**
         * 压缩失败
         *
         * @param imgPath 压缩失败的图片
         * @param msg     失败的原因
         */
        void onCompressFailed(String imgPath, String msg);
    }
}
