package com.example.mediaplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.os.Handler;

import java.io.IOException;
/**
 * @BY_KISEL4KO
 * */
public class MainActivity extends AppCompatActivity {
    /**
     * создание кнопок
     */
    ImageView playView;
    ImageView nextView;
    MediaPlayer mediaPlayer;
    private SeekBar seekBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
/**присваиваем id*/
        playView = findViewById(R.id.PlayImage);
        nextView = findViewById(R.id.ImageNext);
        seekBar = findViewById(R.id.seekBar);
        mediaPlayer.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
            @Override
            public void onSeekComplete(MediaPlayer mediaPlayer) {
                updateImage();// использование метода обновление картинок
            }
        });
        mediaPlayer = new MediaPlayer();//подключаем mediaPlayer
        String[] tracks = {"Jojo.mp3", "GraveChill_-_Twilight.mp3", "Rammstein_-_DEUTSCHLAND.mp3"};// подключаем треки
        final int[] currentTrackIndex = {0};//начальное значение 1 трека чтобы листать их

            try {

                AssetFileDescriptor descriptor = getAssets().openFd("Jojo.mp3");
                // запись файла в mediaPlayer, задаются параметры (путь файла, смещение относительно начала файла, длина аудио в файле)
                mediaPlayer.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
                descriptor.close(); // закрытие дескриптора
                mediaPlayer.prepare(); // ассинхронная подготовка плейера к проигрыванию

                seekBar.setMax(mediaPlayer.getDuration()); // ограниечение seekBar длинной трека
                seekBar.setProgress(0); // присваивание seekBar значения 0

            } catch (IOException e) {
                Log.e("error", "Файл mp3 не найден");
            }

            playView.setOnClickListener(view -> {
                Log.i("info", "Медиаплеер запускается");
                mediaPlayer.start();

                new SeekBarThread(mediaPlayer, seekBar).start();
            });

/**
 * отвечает за передвижение ползунка и прокрутки песни соответсвенно
 * */
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (fromUser) {
                        mediaPlayer.seekTo(progress);
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    // ничего не делаем
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    // ничего не делаем
                }
            });
            /**отвечает за скип треков*/
            nextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentTrackIndex[0]++;
                    if (currentTrackIndex[0] >= tracks.length) {
                        currentTrackIndex[0] = 0;
                    }
                    try {
                        mediaPlayer.reset();
                        mediaPlayer.setDataSource(getAssets().openFd(tracks[currentTrackIndex[0]]));
                        mediaPlayer.prepare();
                        mediaPlayer.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });



        }
/**метод отвечающий за обновление обложек*/
    private void updateImage() {
        int currentPosition = mediaPlayer.getCurrentPosition();
        // Здесь нужно определить, какую картинку нужно показать в зависимости от currentPosition
        // и установить ее в ImageView
    }


    /**метод для кнопки паузы*/
        public void stop (View view){
            if (mediaPlayer != null) {
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
            }
            if (mediaPlayer != null) {
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
                updateImage();
            }
        }

    }





    
