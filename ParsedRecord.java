package pcflayout;

import java.util.HashMap;
import java.util.Map;

public class ParsedRecord {
    private Map<String, String> fields = new HashMap<>();

    public void addField(String name, String value) {
        fields.put(name, value);
    }

    public String getField(String name) {
        return fields.get(name);
    }

    @Override
    public String toString() {
        return fields.toString();
    }
}
