package com.wanyueliang.ffmpeg;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.wanyueliang.ffmpeg.utils.FFmpegHepler;
import com.wanyueliang.ffmpeg.utils.FFmpegKit;

public class MainActivity extends AppCompatActivity {
    private final String input = Environment.getExternalStorageDirectory().getPath() + "/AVM" + "/99.mp4";

    private final String outputMp4 = Environment.getExternalStorageDirectory().getPath() + "/AVM" + "/99_out.mp4";
    private final String outputMp4Cut = Environment.getExternalStorageDirectory().getPath() + "/AVM" + "/99_cut.mp4";

    private final String outputJpg = Environment.getExternalStorageDirectory().getPath() + "/AVM" + "/99_cut.jpg";
    private final String outputJpgArray = Environment.getExternalStorageDirectory().getPath() + "/AVM" + "/image-%03d.jpeg";
    /*View*/
    private Button mBtnCut;

    private ProgressDialog progressDialog;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        findView();
        initListener();
    }

    private void findView() {
        mBtnCut = (Button) findViewById(R.id.btn_cut);
    }

    private void initListener() {
        mBtnCut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                run();
            }
        });
    }

    private void run() {
        String[] ffmpegValues = null;

//        ffmpegValues = FFmpegHepler.cutVideo(input, "00:02:00", "00:04:00", outputMp4Cut);
        ffmpegValues = FFmpegHepler.cutFrameVideo(input, "00:00:30", outputJpg);
//        ffmpegValues = FFmpegHepler.cutVideoToArrayImage(input, "00:00:00", outputJpgArray);
//        ffmpegValues = FFmpegHepler.imageToVideo(outputJpgArray, "20", outputMp4);

        FFmpegKit.execute(ffmpegValues, new FFmpegKit.KitInterface() {
            @Override
            public void onStart() {
                showProgress();
                Log.i("FFmpegLog LOGCAT", "FFmpeg 命令行开始执行了...");
            }

            @Override
            public void onProgress(int progress) {
                Log.i("FFmpegLog LOGCAT", "done com" + "FFmpeg 命令行执行进度..." + progress);
            }

            @Override
            public void onEnd(int result) {
                dismissPeogress();
                Log.i("FFmpegLog LOGCAT", "FFmpeg 命令行执行完成...");
//                        getWindow().setFlags(0, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//                        Message msg = new Message();
//                        msg.what = 1;
//                        mHandler.sendMessage(msg);
            }
        });

    }


    private synchronized void showProgress() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(mContext);
        }
        progressDialog.show();
    }

    private synchronized void dismissPeogress() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
}