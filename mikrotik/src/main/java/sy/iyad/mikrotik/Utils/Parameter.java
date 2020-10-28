package sy.iyad.mikrotik.Utils;

/**
 * A command parameter
 *
 * @author iyadFawwaz
 */
class Parameter {

    @SuppressWarnings("NullableProblems")
    @Override
    public String toString() {
        if (hasValue()) {
            return String.format("%s=%s", name, value);
        } else {
            return name;
        }
    }

    Parameter(String name, String value) {
        this.name = name;
        this.value = value;
    }

    Parameter(String name) {
        this(name, null);
    }

    boolean hasValue() {
        return value != null;
    }

    String getName() {
        return name;
    }

    String getValue() {
        return value;
    }
    private final String name;
    private final String value;
}
