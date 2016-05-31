package com.xinran.testserviceimgcompress.utils;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.xinran.testserviceimgcompress.Constanse;

import java.util.ArrayList;

/**
 * Created by qixinh on 16/5/31.
 */
public class QxIntentService extends IntentService {
    private final String TAG = QxIntentService.class.getSimpleName();

    private ArrayList<QxPhoneImgCompress.CompressResult> compressResults = new ArrayList<>();//存储压缩任务的返回结果

    public QxIntentService() {
        super("QxIntentService");
        setIntentRedelivery(false);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Intent intent = new Intent(Constanse.ACTION_COMPRESS_BROADCAST);
        intent.putExtra(Constanse.KEY_COMPRESS_FLAG, Constanse.FLAG_BEGAIIN);
        sendBroadcast(intent);
        Log.d(TAG, "onCreate...");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent(Constanse.ACTION_COMPRESS_BROADCAST);
        intent.putExtra(Constanse.KEY_COMPRESS_FLAG, Constanse.FLAG_END);
        intent.putParcelableArrayListExtra(Constanse.KEY_COMPRESS_RESULT, compressResults);
        sendBroadcast(intent);
        compressResults.clear();
        Log.d(TAG, "onDestroy...");
    }

    public static void startActionCompress(Context context, CompressServiceParam param) {
        Intent intent = new Intent(context, QxIntentService.class);
        intent.setAction(Constanse.ACTION_COMPRESS);
        intent.putExtra(Constanse.COMPRESS_PARAM, param);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (Constanse.ACTION_COMPRESS.equals(action)) {
                final CompressServiceParam param1 = intent.getParcelableExtra(Constanse.COMPRESS_PARAM);
                handleActionCompress(param1);
            }
        }
    }

    private void handleActionCompress(CompressServiceParam param) {
        int outwidth = param.getOutWidth();
        int outHieight = param.getOutHeight();
        int maxFileSize = param.getMaxFileSize();
        String srcImageUri = param.getSrcImageUri();
        QxPhoneImgCompress.CompressResult compressResult = new QxPhoneImgCompress.CompressResult();
        String outPutPath = null;
        try {
            outPutPath = QxPhoneImgCompress.getInstance(this).compressImage(srcImageUri, outwidth, outHieight, maxFileSize);
        } catch (Exception e) {
        }
        compressResult.setSrcPath(srcImageUri);
        compressResult.setOutPath(outPutPath);
        if (outPutPath == null) {
            compressResult.setStatus(QxPhoneImgCompress.CompressResult.RESULT_ERROR);
        }
        compressResults.add(compressResult);
    }
}
