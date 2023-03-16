package BusinessLogic;

import Model.Server;
import Model.Task;

import java.util.List;

public class ConcreteStrategyTime implements Strategy{
    @Override
    public void addTask(List<Server> servers, Task t)
    {
        int serverId=-1;
        Integer minWaitingTime =null;
        for (int i = 0; i< servers.size(); i++)
        {
            if (minWaitingTime ==null)
            {
                if (servers.get(i).taskListNotFull())
                {
                    minWaitingTime =servers.get(i).getWaitingPeriod();
                    serverId=i;
                }
            } else if (servers.get(i).taskListNotFull()&& minWaitingTime >servers.get(i).getWaitingPeriod())
            {
                minWaitingTime=servers.get(i).getWaitingPeriod();
                serverId=i;
            }
        }
        servers.get(serverId).addTask(t);
    }
}
