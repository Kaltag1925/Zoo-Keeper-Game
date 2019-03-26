package Engine.Logic;

import java.util.LinkedHashMap;

public class Profiler {
    private LinkedHashMap<String, Long> map = new LinkedHashMap<>();
    private String lastLog;
    private long lastLabelChange;

    public Profiler() {
    }

    public void log(){
        long now = System.currentTimeMillis();
        String label = String.valueOf(map.size());
        if( lastLog != null ) {
            long old = 0;
            if (map.containsKey(label)) {
                old = map.get(label);
            }
            long spent = now - lastLabelChange;
            old += spent;
            map.put( lastLog, old );
        }
        lastLog = label;
        lastLabelChange = System.currentTimeMillis();
    }

    public void log( String label ){
        long now = System.currentTimeMillis();
        if( lastLog != null ) {
            long old = 0;
            if (map.containsKey(label)) {
                old = map.get(label);
            }
            long spent = now - lastLabelChange;
            old += spent;
            map.put( lastLog, old );
        }
        lastLog = label;
        lastLabelChange = System.currentTimeMillis();
    }

    public String toString() {
        return map.toString();
    }
}
