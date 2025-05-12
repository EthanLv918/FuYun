package soft.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class schedules {

    @Scheduled(cron = " 0 0/1 * * * ?")
    public void ssssssssssss() {
        System.out.println("666666666666666666666666666666666666666");
    }
}
