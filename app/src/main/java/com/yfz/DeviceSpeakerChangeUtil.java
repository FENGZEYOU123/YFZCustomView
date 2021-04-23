package com.yfz;

import android.content.Context;
import android.media.AudioManager;

public class DeviceSpeakerChangeUtil {
    /**
     * 简介：设备语音切换-蓝牙-外放-听筒
     * 作者：游丰泽
     * 主要功能: 设备语音切换-蓝牙-外放-听筒
     */
    private static AudioManager mAudioManager;
    /**
     * 切换到外放
     */
    public static void changeToSpeaker(Context context){
        deviceChangeToHeadPhone(context);
        mAudioManager.stopBluetoothSco();
        mAudioManager.setBluetoothScoOn(false);
        mAudioManager.setMicrophoneMute(false);
        mAudioManager.setSpeakerphoneOn(true);
    }

    /**
     * 切换到蓝牙音箱
     */
    public static void changeToBlueTooth(Context context){
        deviceChangeToHeadPhone(context);
        mAudioManager.startBluetoothSco();
        mAudioManager.setBluetoothScoOn(true);
        mAudioManager.setSpeakerphoneOn(false);
    }

    /**
     * 切换到耳机模式
     */
    public static void  deviceChangeToHeadPhone(Context context){
        if(mAudioManager==null) {
            mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        }
        mAudioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
        mAudioManager.stopBluetoothSco();
        mAudioManager.setBluetoothScoOn(false);
        mAudioManager.setSpeakerphoneOn(false);
    }



}
