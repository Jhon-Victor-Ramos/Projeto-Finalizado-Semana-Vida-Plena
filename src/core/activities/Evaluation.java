package core.activities;

import core.Participant;

public class Evaluation {
    private final Participant client;
    private final int rating;
    private final String comment;

    public Evaluation(Participant client, int rating, String comment) {
        this.client = client;
        this.rating = rating;
        this.comment = comment;
    }

    public Participant getClient() { return client; }
    public int getRating() { return rating; }
    public String getComment() { return comment; }
}