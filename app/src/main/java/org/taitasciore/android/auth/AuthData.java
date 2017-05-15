package org.taitasciore.android.auth;

import java.util.Date;

/**
 * Created by roberto on 10/05/17.
 * Helper class that holds values used for authentication on the Marvel API
 */

public class AuthData {

    private long timestamp;
    private String hash;

    public AuthData() {
        timestamp = new Date().getTime();
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
