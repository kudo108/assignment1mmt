/**
 *
 * @author T'PHaM
 */
public class TimeMeter {
    private long lastTime = -1;
    
    public TimeMeter() {
        lastTime = 0;
    }
    
    public long press() {
        long result = 0;
        long newTime = 0;
        try {
            newTime = System.currentTimeMillis();
        } catch (Exception e) {
            System.out.println("Error get system time.");
            return 0;
        }
        if (lastTime == 0) {
            result = 0;
        } else {
            result = newTime - lastTime;
        }
        lastTime = newTime;
        return result;
    }
    
}
