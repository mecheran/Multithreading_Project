package BusinessLogic;

import GUI.SimulationFrame;
import Model.Server;
import Model.Task;

import java.io.*;
import java.util.*;

public class SimulationManager implements Runnable {
    private Scheduler scheduler;
    private SimulationFrame frame;
    private List<Task> generatedTasks;
    Integer timeLimit;
    Integer maxProcessingTime;
    Integer minProcessingTime;
    Integer minArrivalTime;
    Integer maxArrivalTime;
    Integer noOfServers;
    Integer noOfClients;
    SelectionPolicy selectionPolicy=SelectionPolicy.SHORTEST_TIME;

    public SimulationManager(){

        this.frame=new SimulationFrame();
        setValues(frame.getInitValues());
        this.scheduler= new Scheduler(noOfServers,5,selectionPolicy);
        generatedTasks=Collections.synchronizedList(new ArrayList<>());
        generateNRandomTasks();
    }

    public void generateNRandomTasks(){
        Random random=new Random();
        int arrival;
        int service;
        for (Integer i=0;i<noOfClients;i++){
            service=random.nextInt(maxProcessingTime-minProcessingTime)+minProcessingTime;
            arrival=random.nextInt(maxArrivalTime-minArrivalTime)+minArrivalTime;
            generatedTasks.add(new Task(service,arrival));
        }
        generatedTasks.sort((Comparator.comparing(Task::getArrivalTime)));
        for (int i=0;i<noOfClients;i++)
            generatedTasks.get(i).setId(i);
    }
    private String printStack(){
        String stack="Waiting clients: ";
        for (Task i:generatedTasks)
        {
            stack=stack+ "("+i.getTaskId()+","+i.getArrivalTime()+","+i.getServiceTime()+");";
        }
        stack=stack+"\n";
        for (Server i:scheduler.getServers()){
            stack=stack+"Queue "+(i.getServerId()+1)+": ";
            if (i.getQueueSize()==0)
                stack=stack+"closed";
            else {
                Task[] tasks=i.getTasks();
                for(int j=0;j<i.getQueueSize();j++)
                    stack=stack+"("+tasks[j].getTaskId()+","+tasks[j].getArrivalTime()+","+tasks[j].getServiceTime()+");";

            }
            stack=stack+"\n";
        }
        return stack;
    }
    @Override
    public void run(){
        Integer currentTime=0;
        String display;
        String fin=" ";
        while (currentTime<timeLimit)
        {
            int i=0;
            while(i==0)
            {
                if (generatedTasks.size()!=0){
                    Task j=generatedTasks.get(0);
                    if (j.getArrivalTime().equals(currentTime))
                    {
                        scheduler.dispatchTask(j);
                        generatedTasks.remove(j);
                    }
                    else if (j.getArrivalTime()>currentTime)
                    {
                        i=-1;
                    }
                }

                if (generatedTasks.size()==0)
                i=-1;
            }
            display="Time "+ currentTime +"\n"+printStack();
            fin=fin+display;
            System.out.println(display);
            frame.refreshFrame(display);
            currentTime++;
            try{
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try (FileWriter fileWriter = new FileWriter("log.txt"))
        {
            fileWriter.write(fin);
        }
        catch (IOException ioException)
        {
            ioException.printStackTrace();
        }

    }
    public void setValues(List<Integer> list){
        this.noOfClients=list.get(0);
        this.noOfServers=list.get(1);
        this.timeLimit=list.get(2);
        this.minArrivalTime=list.get(3);
        this.maxArrivalTime=list.get(4);
        this.minProcessingTime=list.get(5);
        this.maxProcessingTime=list.get(6);
    }
    public static void main(String[] args){
        SimulationManager gen= new SimulationManager();
        Thread t=new Thread(gen);
        t.start();
    }
}
