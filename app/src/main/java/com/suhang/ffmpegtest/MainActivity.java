package com.suhang.ffmpegtest;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {

    static {
        System.loadLibrary("jxffmpegrun");
        System.loadLibrary("avcodec");
        System.loadLibrary("avformat");
        System.loadLibrary("avutil");
        System.loadLibrary("swscale");
        System.loadLibrary("fdk-aac");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new RxPermissions(this)
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {

                    }
                });
    }

    public void onClick(View v) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String basePath = Environment.getExternalStorageDirectory().getPath();

                String cmd_transcoding = String.format("ffmpeg -i %s -b:v 640k %s",
                        basePath + "/" + "girl.mp4",
                        basePath + "/" + "my_girl.mp4");
                int i = jxFFmpegCMDRun(cmd_transcoding);
                Log.i("MainActivity", i + " ");
            }
        }).start();
    }

    public int jxFFmpegCMDRun(String cmd) {
        String regulation = "[ \\t]+";
        final String[] split = cmd.split(regulation);

        return ffmpegRun(split);
    }

    public native int ffmpegRun(String[] cmd);
}
