package hammondtr.timersheets;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

/*
    notes: It's gonna take a bit of figuring out how to save the timers; dunno if I'll use
    something like TimeSpan in C# or if I need to write something myself to convert an int
    or what. Currently, the DB will save durations as TEXT and I'm planning to convert
    that in the code. We'll see what's up. Just making a note.

    DateUtils: android framework
*/


public class Timer extends Fragment {

    private String name;
    private String category;
    private int initialDuration;
    private String recap;

    private int currentDurationMs;              // in milliseconds
    private CountDownTimer countDownTimer;
    private TextView timerDuration;

    public Timer() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate layout to edit, set details
        View timerView = inflater.inflate(R.layout.fragment_timer, container, false);
        TextView timerName = (TextView) timerView.findViewById(R.id.timerName);
        timerDuration = (TextView) timerView.findViewById(R.id.timerDuration);

        // setup custom layout details
        Bundle timerInfo = getArguments();
        timerName.setText(timerInfo.getString("name"));
        timerDuration.setText(new Integer(timerInfo.getInt("duration")).toString());
        currentDurationMs = timerInfo.getInt("duration") * 1000;

        // add onClick listener
        Button timerControl = (Button) timerView.findViewById(R.id.timerControl);
        timerControl.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Button toggleButton = (Button) v;
                String currentText = toggleButton.getText().toString();

                if (currentText.equals(getString(R.string.timer_stop))) {
                    toggleButton.setText(R.string.timer_start);
                    stopTimer();
                } else {
                    toggleButton.setText(R.string.timer_stop);
                    startTimer();
                }
            }
        });

        return timerView;
    }

    private void startTimer() {

        countDownTimer = new CountDownTimer(currentDurationMs, 1000) {

            public void onTick(long timeLeft) {     // timeLeft is in milliseconds
                currentDurationMs = (int) timeLeft;   // living dangerously; > 500 hour timer = bad
                timerDuration.setText(new Integer(currentDurationMs/1000).toString());
            }

            public void onFinish() {

            }
        }.start();

    }
    // this should probably be called onQuit or w/e from main activity
    public void stopTimer() {
        // stop timer and insert info into DB
        countDownTimer.cancel();
    }

}
