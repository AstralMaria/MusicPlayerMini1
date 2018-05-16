package com.example.android.musicplayermini;

        import android.media.MediaPlayer;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.os.Handler;
        import android.widget.SeekBar;
        import android.widget.TextView;
        import android.widget.Toast;

        import org.w3c.dom.Text;

        import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private Button b1, b2, b3;
    private MediaPlayer mediaPlayer;
    private double startTime= 0;
    private double endTime = 0;
    private Handler handler = new Handler();
    private SeekBar seekbar;
    private TextView tx1;
    private TextView tx2;



    public static int oneTimeOnly = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1 = (Button) findViewById(R.id.playButton);
        b2 = (Button) findViewById(R.id.pauseButton);
        b3 = (Button) findViewById(R.id.stopButton);

        tx1 = (TextView) findViewById(R.id.tx1);
        tx2 = (TextView) findViewById(R.id.tx2);

        mediaPlayer = MediaPlayer.create(this, R.raw.underground);
        seekbar = (SeekBar) findViewById(R.id.seekBar);
        seekbar.setClickable(false);
        b2.setEnabled(false);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Playing", Toast.LENGTH_SHORT).show();
                mediaPlayer.start();

                endTime = mediaPlayer.getDuration();
                startTime = mediaPlayer.getCurrentPosition();

                if (oneTimeOnly == 0) {
                    seekbar.setMax((int) endTime);
                    oneTimeOnly = 1;
                }

                tx2.setText(String.format("%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes((long) endTime),
                        TimeUnit.MILLISECONDS.toSeconds((long) endTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                        endTime)))
                );

                tx1.setText(String.format("%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                        TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                        startTime)))
                );

                seekbar.setProgress((int) startTime);
                handler.postDelayed(UpdateSongTime, 100);
                b2.setEnabled(true);
                b3.setEnabled(true);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Paused", Toast.LENGTH_SHORT).show();
                mediaPlayer.pause();
                b1.setEnabled(true);
                b3.setEnabled(true);
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Stopped", Toast.LENGTH_SHORT).show();
                mediaPlayer.reset();
                b1.setEnabled(true);
                b2.setEnabled(false);
                seekbar.setProgress((int)startTime);


                }

        });

    }


    private Runnable UpdateSongTime = new Runnable() {
        public void run() {
            startTime = mediaPlayer.getCurrentPosition();
            tx1.setText(String.format("%d min, %d sec",
                    TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                    TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                    toMinutes((long) startTime)))
            );
            seekbar.setProgress((int)startTime);
            handler.postDelayed(this, 100);
        }
    };
}
