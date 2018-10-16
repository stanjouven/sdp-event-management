package ch.epfl.sweng.eventmanager.repository.data;

import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

/**
 * Class describing a single scheduled item (concert, activity...) in an event. The class is for the moment only used by
 * the ScheduleFragment.
 */
public final class ScheduledItem {
    /**
     * Indicates the time of the concert, precision required is minutes.
     *
     * This is a long because firebase doesn't understand Date objects.
     */
    private long date;

    /**
     * Indicates the artist or band performing.
     */
    private String artist;

    /**
     * Indicates the music genre (for the moment, it is a string, could become an enum)
     */
    private String genre;

    /**
     * Describes the concert
     */
    private String description;

    /**
     * Duration of the concert in hours
     */
    private double duration;

    /**
     * An UUID identifying this scheduled item uniquely
     */
    private String id;

    /**
     * The type of the item<br>
     * It would make sense to have an enum, but a single string allows the organizer to defines its own types of items
     */
    private String itemType;

    /**
     * The place (room, usually) where the event takes place<br>
     *     // FIXME Depending on how the map works, we might want to add more data here so that the user can click and go to the map.
     */
    private String itemLocation;

    private static final double STANDARD_DURATION = 1;

    public ScheduledItem(@NonNull Date date, @NonNull String artist, @NonNull String genre,  @NonNull String description,
                         double duration, UUID id, String itemType, String itemLocation) {
        this.date = date.getTime();
        this.artist = artist;
        this.genre = genre;
        this.description = description;
        this.id = id.toString();
        this.itemType = itemType;
        this.itemLocation = itemLocation;

        if (duration <= 0) this.duration = STANDARD_DURATION;
        else this.duration = duration;
    }

    public ScheduledItem() {}

    public Date getDate() {
        if (date <= 0) {
            return null;
        }
        return new Date(date);
    }

    public String getArtist() {
        return artist;
    }

    public String getDescription() {
        return description;
    }

    public String getGenre() {
        return genre;
    }

    public double getDuration() {
        return duration;
    }

    public UUID getId() {
        return UUID.fromString(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ScheduledItem) {
            ScheduledItem compared = (ScheduledItem) obj;

            if (!date_verif(getDate()) || ! date_verif(compared.getDate())) return false;

            return compareFields(compared);
        } else return super.equals(obj);
    }

    /**
     * Verifies if the date is valid as it can be null.
     * @param d Date to be verified (should come from getDate()).
     * @return true if date is NonNull.
     */
    public boolean date_verif(Date d) {
        return d == null;
    }

    /**
     * Compares all the fields of the input Concert with those of this Concert.
     * @param compared Concert with checked fields.
     * @return true if compared has all fields similar to this.
     */
    public boolean compareFields(Concert compared) {
        return compared.equals(getDate()) && compared.getArtist().equals(getArtist())&&
                compared.getGenre().equals(getGenre()) &&
                compared.getDescription().equals(getDescription())&&
                compared.getDuration()==getDuration();
    }

    public String dateAsString() {
        if (date <= 0) {
            return null;
        }
        SimpleDateFormat f = new SimpleDateFormat("dd MMMM yyyy 'at' kk'h'mm");
        return f.format(date);
    }

    public String getItemType() {
        return itemType;
    }

    public String getItemLocation() {
        return itemLocation;
    }

    public Date getEndOfConcert() {
        if (getDate() == null) {
            return null;
        }

        int durationMinutes = (int) Math.abs(getDuration() * 60);

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(getDate());
        calendar.add(Calendar.MINUTE, durationMinutes);

        return calendar.getTime();
    }

    public ScheduledItemStatus getStatus() {
        // If we don't have a date, it's in the future
        if (getDate() == null) {
            return ScheduledItemStatus.NOT_STARTED;
        }

        Date currentDate = new Date();

        if (currentDate.after(getDate())) {
            if (currentDate.before(getEndOfConcert())) {
                return ScheduledItemStatus.IN_PROGRESS; // concert is taking place
            } else {
                return ScheduledItemStatus.PASSED; // concert is finished
            }
        } else {
            return ScheduledItemStatus.NOT_STARTED; // concert didn't start yet
        }
    }

    public enum ScheduledItemStatus {
        /**
         * The concert already happened
         */
        PASSED,
        /**
         * The concert is in progress
         */
        IN_PROGRESS,
        /**
         * The concert will happen in the future
         */
        NOT_STARTED
    }
}
