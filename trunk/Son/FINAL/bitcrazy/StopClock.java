package bitcrazy;

/**
 *
 * @author T'PHaM
 */
public class StopClock {
    private long lastTime = -1;
    
    public StopClock() {
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
        //System.out.println("StopClock.press(): " + result);
        return result;
    }
    
}
