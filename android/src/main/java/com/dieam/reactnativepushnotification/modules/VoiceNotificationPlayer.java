package com.dieam.reactnativepushnotification.modules;

import android.app.Application;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;

public class VoiceNotificationPlayer {

    private long activatedAt = Long.MAX_VALUE;
    private static final int DURATION = 8000;
    AudioManager audio;

    public VoiceNotificationPlayer(Application context) {
        this.audio = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
    }

    public boolean isActive() {
        long activeFor = System.currentTimeMillis() - activatedAt;
        return activeFor >= 0 && activeFor <= DURATION;
    }

    public void playNotification(final String soundName) {
        if (isActive() || audio.getRingerMode() == AudioManager.RINGER_MODE_SILENT)
            return;

        activatedAt = System.currentTimeMillis();
        new Thread(new Runnable() {
            @Override
            public void run() {
                MediaPlayer mediaPlayer = new MediaPlayer();
                try {
                    mediaPlayer.setDataSource(soundName);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}