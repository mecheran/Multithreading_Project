package BusinessLogic;

import Model.Server;
import Model.Task;

import java.util.ArrayList;
import java.util.List;

public class Scheduler {
    private List<Server> servers=new ArrayList<>();
    private Integer maxNoServers;
    private Integer maxTasksPerServer;
    private Strategy strategy;

    public Scheduler(Integer maxNoServers,Integer maxTasksPerServer,SelectionPolicy selectionPolicy){
        this.maxNoServers=maxNoServers;
        this.maxTasksPerServer=maxTasksPerServer;
        for (Integer i=0;i<maxNoServers;i++)
        {
            servers.add(new Server(maxTasksPerServer,i));
            Thread thread=new Thread(servers.get(i));
            thread.start();
        }
        changeStrategy(selectionPolicy);
    }

    public void changeStrategy(SelectionPolicy policy){

        if(policy==SelectionPolicy.SHORTEST_QUEUE){
            strategy=new ConcreteStrategyQueue();
        }
        if(policy==SelectionPolicy.SHORTEST_TIME){
            strategy=new ConcreteStrategyTime();
        }
    }

    public void dispatchTask(Task t){
        strategy.addTask(servers,t);
    }
    public List<Server> getServers()
    {
        return servers;
    }
}
