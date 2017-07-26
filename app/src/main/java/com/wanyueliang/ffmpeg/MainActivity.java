package com.wanyueliang.ffmpeg;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.wanyueliang.ffmpeg.utils.FFmpegKit;

public class MainActivity extends AppCompatActivity {
    private final String input = Environment.getExternalStorageDirectory().getPath() + "/AVM" + "/99.mp4";
    private final String output = Environment.getExternalStorageDirectory().getPath() + "/AVM" + "/99_cut.mp4";
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

        //ffmpeg -ss 00:00:00 -t 00:00:30 -i test.mp4 -vcodec copy -acodec copy output.mp4
        //ffmpeg -y -i filename -ss start -t duratio -codec copy

        String[] commands = new String[13];
        commands[0] = "ffmpeg";
        commands[1] = "-i";
        commands[2] = input;
        commands[3] = "-ss";
        commands[4] = "00:00:05";
        commands[5] = "-t";
        commands[6] = "00:00:30";
        commands[7] = "-vcodec";
        commands[8] = "copy";
        commands[9] = "-acodec";
        commands[10] = "copy";
        commands[11] = "-y";
        commands[12] = output;

//        String[] commands = new String[13];
//        commands[0] = "ffmpeg";
//        commands[1] = "-ss";
//        commands[2] = "00:00:00";
//        commands[3] = "-t";
//        commands[4] = "00:00:30";
//        commands[5] = "-i";
//        commands[6] = input;
//        commands[7] = "-vcodec";
//        commands[8] = "copy";
//        commands[9] = "-acodec";
//        commands[10] = "copy";
//        commands[11] = "-y";
//        commands[12] = output;

        FFmpegKit.execute(commands, new FFmpegKit.KitInterface() {
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