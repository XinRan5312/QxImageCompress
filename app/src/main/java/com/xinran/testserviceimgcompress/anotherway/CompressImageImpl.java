package com.xinran.testserviceimgcompress.anotherway;

import java.io.File;

/**
 * 压缩照片2.0
 * Created by qixinh on 16/6/13.
 */
public class CompressImageImpl implements CompressImage {
    private CompressImageUtil compressImageUtil;

    public CompressImageImpl(CompressConfig config) {
        compressImageUtil = new CompressImageUtil(config);
    }

    @Override
    public void compress(String imagePath, CompressListener listener) {
        File file = new File(imagePath);
        if (file == null || !file.exists() || !file.isFile()) {//如果文件不存在，则不做任何处理
            listener.onCompressFailed(imagePath, "要压缩的文件不存在");
            return;
        }
        compressImageUtil.compress(imagePath, listener);
    }
}
