package com.yfz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.yfz.yfzcustomview.R;
/**
 * 简介：自定义验证码输入框 (组合+自绘)
 * 作者：游丰泽
 * 主要功能: 展示如何获取单个权限，多个权限
 */

public class MainActivity extends AppCompatActivity {
    private static final int PERMISSION_STORAGE= 100; //储存requestCode
    private static final int PERMISSION_MICROPHONE_CAMERA= 101; //麦克风,相机requestCode

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    /**
     * 获取单个权限-储存
     * checkSelfPermission检查app是否已获取到权限?已获取到返回值为 0，否则前去获取
     * requestPermissions用于获取权限，需传递三个参数，分别为:
     * 1.activity
     * 2.String[] 想要获取的哪些权限，储存在字符串数组内.(可以同时获取多个权限，或单个权限)
     * 3.int      请求代码 - 自己定义
     **/

    public void getStoragePermission(View view) {
        //检查app是否已获取到权限
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(getApplicationContext(),"已获取到 读取储存权限 ",Toast.LENGTH_SHORT).show();
        }else {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}
                    ,PERMISSION_STORAGE);
        }
    }
    //同时获取多个个权限-麦克风-照相机
    public void getMicrophoneAndCameraPermission(View view) {
        //检查app是否已获取到权限
        if((ActivityCompat.checkSelfPermission(this,Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED)
                &&ActivityCompat.checkSelfPermission(this,Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(getApplicationContext(),"已获取到 麦克风 和 照相机 权限 ",Toast.LENGTH_SHORT).show();

        }else {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.RECORD_AUDIO,Manifest.permission.CAMERA},
                    PERMISSION_MICROPHONE_CAMERA);
        }
    }

    /**
     * 用户拒绝或同意权限后的回调。
     * requestCode 请求的时候设置的int
     * String[] permissions 请求过的权限
     * int[] grantResults  请求权限的结果，同意 == 0，拒绝 == -1
     **/
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_STORAGE:
                Toast.makeText(getApplicationContext(),"储存 权限回调结果:>> "+grantResults[0],Toast.LENGTH_SHORT).show();
                break;
            case PERMISSION_MICROPHONE_CAMERA:
                for(int i=0;i<permissions.length;i++){
                    if(permissions[i].equals(Manifest.permission.RECORD_AUDIO)){
                        Toast.makeText(getApplicationContext(),"麦克风 权限回调结果:>> "+grantResults[i],Toast.LENGTH_SHORT).show();
                    }
                    if(permissions[i].equals(Manifest.permission.CAMERA)){
                        Toast.makeText(getApplicationContext(),"照相机 权限回调结果:>> "+grantResults[i],Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            default:
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}