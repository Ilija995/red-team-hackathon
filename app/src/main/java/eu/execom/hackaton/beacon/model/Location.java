package eu.execom.hackaton.beacon.model;

public class Location implements Comparable<Location> {

    private static final int SIGNAL_STRENGTH_STRONG = -60;
    private static final int SIGNAL_STRENGTH_WEAK = -120;
    private static final int RANGE = -(SIGNAL_STRENGTH_WEAK - SIGNAL_STRENGTH_STRONG);
    private static final String PROGRESS_CHARACTER = "I";

    public long id;

    public String uuid;

    public String description;

    public String name;

    public int signalStrength;

    @Override
    public String toString() {
        final int signalStrength = (int) getProgressValue() / 20;
        final StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < signalStrength; i++) {
            stringBuilder.append(PROGRESS_CHARACTER);
        }

        return String.format("%-25s %s", uuid, stringBuilder.toString());
    }

    private double getProgressValue() {
        if (signalStrength >= SIGNAL_STRENGTH_STRONG) {
            return 100;
        } else if (signalStrength <= SIGNAL_STRENGTH_WEAK) {
            return 0;
        } else {
            final double percentage = (SIGNAL_STRENGTH_STRONG - signalStrength) / (double) RANGE;
            return (1 - percentage) * 100;
        }
    }

	private double roundOnTwoDigits(double val) {
		return (int) (val * 100.0) / 100.0;
	}

	public String printSignalStrength() {
	    return roundOnTwoDigits(getProgressValue()) + "%, " + signalStrength + "db";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Location that = (Location) o;

        if (uuid != null ? !uuid.equals(that.uuid) : that.uuid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return uuid != null ? uuid.hashCode() : 0;
    }

	@Override
	public int compareTo(Location that) {
		return that.uuid.compareTo(that.uuid);
	}

}
