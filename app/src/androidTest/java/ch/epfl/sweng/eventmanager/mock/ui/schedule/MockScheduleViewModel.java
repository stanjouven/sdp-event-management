package ch.epfl.sweng.eventmanager.mock.ui.schedule;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import ch.epfl.sweng.eventmanager.mock.repository.MockConcertRepository;
import ch.epfl.sweng.eventmanager.repository.data.ScheduledItem;

import javax.inject.Inject;
import java.util.List;

public class MockScheduleViewModel extends ViewModel {
    private LiveData<List<ScheduledItem>> concerts;

    private MockConcertRepository repository;

    @Inject
    public MockScheduleViewModel(MockConcertRepository repository) {
        this.repository = repository;
    }

    public void init(int eventId) {
        if (this.concerts != null) {
            return;
        }

        this.concerts = repository.getConcerts(eventId);
    }

    public LiveData<List<ScheduledItem>> getConcerts() {
        return concerts;
    }
}