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

public class Timer extends Fragment {

    private String name;
    private long id;
    private String category;
    private int initialDuration;
    private String recap;

    private long currentDurationMs;              // in milliseconds
    private CountDownTimer countDownTimer;
    private TimerDbHelper dbHelper;

    private TextView timerDuration;
    private Button timerControl;

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

        // initialize timer, setup custom layout details
        Bundle timerInfo = getArguments();
        id = timerInfo.getLong("id");
        name = timerInfo.getString("name");
        currentDurationMs = timerInfo.getInt("duration") * 1000;
        dbHelper = new TimerDbHelper(getActivity());

        timerName.setText(name);
        timerDuration.setText(timeLeftAsString());


        // add onClick listener
        timerControl = (Button) timerView.findViewById(R.id.timerControl);
        timerControl.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Button toggleButton = (Button) v;
                String currentText = toggleButton.getText().toString();

                if (currentText.equals(getString(R.string.timer_stop))) {
                    stopTimer();
                } else {
                    startTimer();
                }
            }
        });

        return timerView;
    }

    private void startTimer() {
        timerControl.setText(R.string.timer_stop);
        countDownTimer = new CountDownTimer(currentDurationMs, 1000) {

            public void onTick(long timeLeft) {     // timeLeft is in milliseconds
                currentDurationMs = (int) timeLeft;   // living dangerously; > 500 hour timer = bad
                timerDuration.setText(timeLeftAsString());
            }

            public void onFinish() {
                // alarm goes off
                // prompt for recap
                // set timer as finished (greyed out, check-mark button)
            }
        }.start();

    }
    // this should probably be called onQuit or w/e from main activity
    public void stopTimer() {
        if (countDownTimer != null) {
            // stop timer and insert info into DB
            timerControl.setText(R.string.timer_start);
            countDownTimer.cancel();
            dbHelper.updateTimerTime(id, (int) currentDurationMs/1000);
        }
    }

    private String timeLeftAsString() {
        int seconds = (int) currentDurationMs / 1000;
        int hours, minutes;
        hours = seconds / 3600;
        seconds = seconds % 3600;
        minutes = seconds / 60;
        seconds = seconds % 60;

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    private void finishTimer(){}

}
