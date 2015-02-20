package hammondtr.timersheets;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private String initialDuration;
    private String currentDuration;
    private String recap;

    public Timer() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate layout to edit
        View timerView = inflater.inflate(R.layout.fragment_timer, container, false);
        TextView timerName = (TextView) timerView.findViewById(R.id.timerName);

        // setup custom layout details
        Bundle timerInfo = getArguments();
        timerName.setText(timerInfo.getString("name"));

        return timerView;
    }

}
