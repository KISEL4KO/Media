package com.example.mediaplayer;

import android.media.MediaPlayer;
import android.widget.SeekBar;

public class SeekBarThread extends Thread {

    private MediaPlayer mediaPlayer;
    private SeekBar seekBar;

    public SeekBarThread(MediaPlayer mediaPlayerInput, SeekBar seekBarInput) {
        mediaPlayer = mediaPlayerInput;
        seekBar = seekBarInput;
    }

    @Override
    public void run() {
        int currentPosition = mediaPlayer.getCurrentPosition(); // считывание текущей позиции трека
        int total = mediaPlayer.getDuration(); // считывание длины трека

        // бесконечный цикл при условии не нулевого mediaPlayer, проигрывания трека и текущей позиции трека меньше длины трека
        while (mediaPlayer != null && mediaPlayer.isPlaying() && currentPosition < total) {
            try {
                Thread.sleep(1000); // засыпание вспомогательного потока на 1 секунду

                currentPosition = mediaPlayer.getCurrentPosition(); // обновление текущей позиции трека

            } catch (InterruptedException e) { // вызывается в случае блокировки данного потока
                e.printStackTrace();
            }

            seekBar.setProgress(currentPosition); // обновление seekBar текущей позицией трека
        }
    }

}
