package org.taitasciore.android.busevents;

import org.taitasciore.android.model.Comic;

/**
 * Created by roberto on 10/05/17.
 * Event/listener to communicate click events on adapter's row (comic) to host fragment
 */

public class ComicClickEvent {

    public Comic comic;

    public ComicClickEvent(Comic comic) {
        this.comic = comic;
    }
}
