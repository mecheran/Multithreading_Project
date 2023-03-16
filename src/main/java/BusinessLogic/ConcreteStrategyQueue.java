package BusinessLogic;

import Model.Server;
import Model.Task;

import java.util.List;

public class ConcreteStrategyQueue implements Strategy{
    @Override
    public void addTask(List<Server> servers, Task t)
    {
        int serverId=-1;
        Integer minQueueSize=null;
        for (int i=0;i<servers.size();i++)
        {
            if (minQueueSize==null){
                if(servers.get(i).taskListNotFull())
                {
                    serverId=i;
                    minQueueSize=servers.get(i).getQueueSize();
                }
            }
            else if (minQueueSize>servers.get(i).getQueueSize())
            {
                serverId=i;
                minQueueSize=servers.get(i).getQueueSize();
            }
        }
        servers.get(serverId).addTask(t);
    }
}
