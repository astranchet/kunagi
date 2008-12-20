









// ----------> GENERATED FILE - DON'T TOUCH! <----------

// generator: ilarkesto.mda.gen.GwtEntityGenerator










package scrum.client.sprint;

import java.util.*;

public abstract class GSprintDaySnapshot
            extends scrum.client.common.AGwtEntity {

    public GSprintDaySnapshot() {
    }

    public GSprintDaySnapshot(Map data) {
        super(data);
        updateProperties(data);
    }

    public static final String ENTITY_TYPE = "sprintDaySnapshot";

    @Override
    public final String getEntityType() {
        return ENTITY_TYPE;
    }

    // --- dateCrap ---

    private java.util.Date dateCrap ;

    public final java.util.Date getDateCrap() {
        return this.dateCrap ;
    }

    public final SprintDaySnapshot setDateCrap(java.util.Date dateCrap) {
        this.dateCrap = dateCrap ;
        propertyChanged("dateCrap", this.dateCrap);
        return (SprintDaySnapshot)this;
    }

    public final boolean isDateCrap(java.util.Date dateCrap) {
        return equals(this.dateCrap, dateCrap);
    }

    // --- sprint ---

    private String sprintId;

    public final scrum.client.sprint.Sprint getSprint() {
        return getDao().getSprint(this.sprintId);
    }

    public final SprintDaySnapshot setSprint(scrum.client.sprint.Sprint sprint) {
        String id = sprint == null ? null : sprint.getId();
        if (equals(this.sprintId, id)) return (SprintDaySnapshot) this;
        this.sprintId = id;
        propertyChanged("sprint", this.sprintId);
        return (SprintDaySnapshot)this;
    }

    public final boolean isSprint(scrum.client.sprint.Sprint sprint) {
        return equals(this.sprintId, sprint);
    }

    // --- burnedWork ---

    private int burnedWork ;

    public final int getBurnedWork() {
        return this.burnedWork ;
    }

    public final SprintDaySnapshot setBurnedWork(int burnedWork) {
        this.burnedWork = burnedWork ;
        propertyChanged("burnedWork", this.burnedWork);
        return (SprintDaySnapshot)this;
    }

    public final boolean isBurnedWork(int burnedWork) {
        return equals(this.burnedWork, burnedWork);
    }

    // --- remainingWork ---

    private int remainingWork ;

    public final int getRemainingWork() {
        return this.remainingWork ;
    }

    public final SprintDaySnapshot setRemainingWork(int remainingWork) {
        this.remainingWork = remainingWork ;
        propertyChanged("remainingWork", this.remainingWork);
        return (SprintDaySnapshot)this;
    }

    public final boolean isRemainingWork(int remainingWork) {
        return equals(this.remainingWork, remainingWork);
    }

    // --- update properties by map ---

    public void updateProperties(Map props) {
        dateCrap  = (java.util.Date) props.get("dateCrap");
        sprintId = (String) props.get("sprintId");
        burnedWork  = (Integer) props.get("burnedWork");
        remainingWork  = (Integer) props.get("remainingWork");
    }

    @Override
    public void storeProperties(Map properties) {
        super.storeProperties(properties);
        properties.put("dateCrap", this.dateCrap);
        properties.put("sprintId", this.sprintId);
        properties.put("burnedWork", this.burnedWork);
        properties.put("remainingWork", this.remainingWork);
    }

}