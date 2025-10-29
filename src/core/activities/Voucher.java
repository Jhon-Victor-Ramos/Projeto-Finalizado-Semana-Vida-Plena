package core.activities;

import core.Participant;
import java.time.LocalDate;

public class Voucher {
    private final String id;
    private final Participant owner;
    private final Evento originEvent;
    private final LocalDate issueDate;
    private boolean used;
    private LocalDate usageDate;

    public Voucher(String id, Participant owner, Evento originEvent) {
        this.id = id;
        this.owner = owner;
        this.originEvent = originEvent;
        this.issueDate = LocalDate.now();
        this.used = false;
        this.usageDate = null;
    }

    public void markAsUsed() {
        this.used = true;
        this.usageDate = LocalDate.now();
    }

    // Getters
    public String getId() { return id; }
    public Participant getOwner() { return owner; }
    public Evento getOriginEvent() { return originEvent; }
    public LocalDate getIssueDate() { return issueDate; }
    public boolean isUsed() { return used; }
    public LocalDate getUsageDate() { return usageDate; }
}