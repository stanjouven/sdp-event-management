package ch.epfl.sweng.eventmanager.ui.eventShowcase.fragments.schedule;

import android.arch.lifecycle.LiveData;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import ch.epfl.sweng.eventmanager.R;
import ch.epfl.sweng.eventmanager.repository.data.ScheduledItem;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class MyScheduleFragment extends AbstractScheduleFragment {
    private static String TAG = "MyScheduleFragment";

    @Override
    protected void setNullConcertsTV() {
        super.nullConcertsTV.setText(R.string.my_schedule_empty);
    }


    @Override
    protected int getLayout() {
        return R.layout.activity_my_schedule;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);

        Button b = v.findViewById(R.id.addToCalendar);
        b.setOnClickListener(v1 -> {
            this.writeEventsToCalendar(this.getScheduledItems().getValue());
        });

        return v;
    }

    @Override
    protected LiveData<List<ScheduledItem>> getScheduledItems() {
        return this.model.getJoinedScheduleItems();
    }

    private void writeEventsToCalendar(List<ScheduledItem> mySchedule) {
        FileOutputStream outputStream;
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss");

        try {
            outputStream = getContext().openFileOutput("myschedule.ical", Context.MODE_PRIVATE);
            PrintStream printer = new PrintStream(outputStream);

            printer.println("BEGIN:VCALENDAR\n" +
                    "VERSION:2.0\n" +
                    "PRODID:-//EventManager/MySchedule//Event Schedule//EN");

            for (ScheduledItem item : mySchedule) {
                printer.println("BEGIN:VEVENT");
                printer.println("SUMMARY:" + item.getArtist());
                printer.println("DTSTART;VALUE=DATE-TIME:" + dateFormat.format(item.getDate()));
                printer.println("DTEND;VALUE=DATE-TIME:" + dateFormat.format(item.getEndOfConcert()));
                if (item.getItemLocation() != null) {
                    printer.println("LOCATION:" + item.getItemLocation());
                }
                printer.println("END:VEVENT");
            }
            printer.println("END:VCALENDAR");

            printer.close();
            outputStream.close();


            Intent openFile = new Intent(Intent.ACTION_VIEW);
            Uri uri = FileProvider.getUriForFile(getContext(), "ch.epfl.sweng.eventmanager.fileprovider", new File(getContext().getFilesDir(), "myschedule.ical"));
            openFile.setDataAndType(uri, "text/calendar");

            // https://developer.android.com/reference/android/support/v4/content/FileProvider#GetUri
            openFile.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            openFile.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);


            try {
                getContext().startActivity(openFile);
            } catch (ActivityNotFoundException e) {
                Log.i(TAG, "Cannot open file.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
