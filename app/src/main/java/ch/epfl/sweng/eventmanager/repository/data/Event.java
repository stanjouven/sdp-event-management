package ch.epfl.sweng.eventmanager.repository.data;

import android.graphics.Bitmap;

import java.util.LinkedList;
import java.util.List;

/**
 * This class holds the basic elements about an organized event.<br>
 *     This class might hold way more data in the future, depending on how the backend will be organized
 *
 * @author Louis Vialar
 */
public final class Event {
    /**
     * An internal id, identifying this event uniquely. Might have to be completed by an UUID in the future.
     */
    private final int id;
    /**
     * The name of the event
     */
    private final String name;
    /**
     * A short description of the event
     */
    private final String description;
    /**
     * The entity organizing this event
     */
    private final EventOrganizer organizer;
    /**
     * An image representing the event, may be null
     */
    private final Bitmap image;
    /**
     * A list of all the concerts taking place in the event.
     */
    private List<Concert> concertList;

    public Event(int id, String name, String description, EventOrganizer organizer, Bitmap image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.organizer = organizer;
        this.image = image;

        this.concertList = new LinkedList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public EventOrganizer getOrganizer() {
        return organizer;
    }

    public Bitmap getImage() {
        return image;
    }

    public List<Concert> getConcertList() {
        return concertList;
    }
}
