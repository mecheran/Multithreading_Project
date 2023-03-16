package Model;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable{
    private Integer serverId;
    private BlockingQueue<Task> tasks;
    private AtomicInteger waitingPeriod;
    public Integer maxTasksPerQueue;
    public Server(Integer maxTasksPerQueue,Integer serverId){
        tasks=new ArrayBlockingQueue<>(maxTasksPerQueue);
        waitingPeriod= new AtomicInteger();
        this.waitingPeriod.set(0);
        this.maxTasksPerQueue=maxTasksPerQueue;
        this.serverId=serverId;
    }
    public Boolean taskListNotFull()
    {
        return (long) tasks.size() < maxTasksPerQueue;
    }
    public Integer getServerId() {
        return serverId;
    }

    public Integer getWaitingPeriod() {
        return waitingPeriod.get();
    }
    public Integer getQueueSize(){
        return tasks.size();
    }
    private synchronized void increment(Integer integer){
        for (int i=0;i<integer;i++){
            waitingPeriod.incrementAndGet();
        }
    }
    private synchronized void decrement(Integer integer){
        for (int i=0;i<integer;i++)
            waitingPeriod.decrementAndGet();
    }
    public void addTask(Task newTask){
        try {
            tasks.put(newTask);
        } catch (InterruptedException e) {
        }
        increment(newTask.getServiceTime());
    }
    public void run(){
        while (true){
            try{
                Thread.sleep(1000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            if (tasks.size()>0){
                tasks.element().setServiceTime(tasks.element().getServiceTime()-1);
                decrement(1);
            if (tasks.element().getServiceTime()==0){
                try{
                    tasks.take();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
            }
        }
    }
    public Task[] getTasks(){
        BlockingQueue<Task> q=new LinkedBlockingQueue<>(tasks);
        Task[] tasks1 =new Task[q.size()];
        Integer j=0;
        for (Task i:q)
        {
            tasks1[j]=i;
            j++;
        }
        return tasks1;
    }
}
