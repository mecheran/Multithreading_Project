package GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class SimulationFrame implements ActionListener {
    JTextField tf1,tf2,tf3,tf4,tf5,tf6,tf7;
    JLabel jl1,jl2,jl3,jl4,jl5,jl6,jl7;
    JButton bt;
    JTextArea textArea;
    ArrayList<Integer> list;
    Boolean ready=false;
    JFrame frame;
    public SimulationFrame (){
        list=new ArrayList<>();
        frame=new JFrame("Multithreading");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(600,400);
        tf1=new JTextField("4");//clients
        tf1.setBounds(172,86,35,25);
        tf2=new JTextField("2");//min arrival
        tf2.setBounds(172,180,35,25);
        tf3=new JTextField("2");//threads
        tf3.setBounds(445,86,35,25);
        tf4=new JTextField("30");//max arrival
        tf4.setBounds(445,180,35,25);
        tf5=new JTextField("60");//time limit
        tf5.setBounds(294,141,35,25);
        tf6=new JTextField("2");//min service
        tf6.setBounds(172,218,35,25);
        tf7=new JTextField("4");//max service
        tf7.setBounds(445,218,35,25);
        jl1=new JLabel("Number Of Clients");
        jl1.setBounds(64,90,135,17);
        jl2=new JLabel("Number Of Threads");
        jl2.setBounds(325,90,135,17);
        jl3=new JLabel("Simulation Interval");
        jl3.setBounds(176,145,135,17);
        jl4=new JLabel("Min Arrival Time");
        jl4.setBounds(57,184,105,17);
        jl5=new JLabel("Max Arrival Time");
        jl5.setBounds(325,184,105,17);
        jl6=new JLabel("Min Service Time");
        jl6.setBounds(57,222,105,17);
        jl7=new JLabel("Max Service Time");
        jl7.setBounds(325,222,105,17);
        bt=new JButton("Start Simulation");
        bt.setBounds(189,282,135,25);
        frame.add(tf1);
        frame.add(tf2);
        frame.add(tf3);
        frame.add(tf4);
        frame.add(tf5);
        frame.add(tf6);
        frame.add(tf7);
        frame.add(jl1);
        frame.add(jl2);
        frame.add(jl3);
        frame.add(jl4);
        frame.add(jl5);
        frame.add(jl6);
        frame.add(jl7);
        frame.add(bt);
        bt.addActionListener(this);
        frame.setLayout(null);
        frame.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent event){
        Integer noOfClients=Integer.parseInt(tf1.getText());
        Integer noOfThreads=Integer.parseInt(tf3.getText());
        Integer timeLimit=Integer.parseInt(tf5.getText());
        Integer minArrivalTime=Integer.parseInt(tf2.getText());
        Integer maxArrivalTime=Integer.parseInt(tf4.getText());
        Integer minServiceTime=Integer.parseInt(tf6.getText());
        Integer maxServiceTime=Integer.parseInt(tf7.getText());
            list.add(noOfClients);
            list.add(noOfThreads);
            list.add(timeLimit);
            list.add(minArrivalTime);
            list.add(maxArrivalTime);
            list.add(minServiceTime);
            list.add(maxServiceTime);
            ready=true;
    }
    private void newFrame(){

        frame=new JFrame("Multithreading");
        frame.setSize(600,400);
        textArea=new JTextArea();
        textArea.setBounds(20,20,540,340);
        frame.add(textArea);
        frame.setLayout(null);
        frame.setVisible(true);
    }
    public void refreshFrame(String string){
        textArea.setText(string);
    }
    public List<Integer> getInitValues (){

        while (!ready)
        {
            bt.setName("Start Simulation");
        }
        newFrame();
        return list;
    }
}
