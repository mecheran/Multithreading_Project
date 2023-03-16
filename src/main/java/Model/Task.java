package Model;

public class Task extends Thread{
    private Integer id;
    private final Integer arrivalTime;
    private Integer serviceTime;
    public Task (Integer serviceTime,Integer arrivalTime){
        this.arrivalTime=arrivalTime;
        this.serviceTime=serviceTime;
    }
    public Integer getTaskId(){
        return id;
    }
    public void setId(Integer id){
        this.id=id;
    }
    public Integer getArrivalTime() {
        return arrivalTime;
    }

    public Integer getServiceTime() {
        return serviceTime;
    }
    public void setServiceTime(Integer serviceTime){
        this.serviceTime=serviceTime;
    }
}
