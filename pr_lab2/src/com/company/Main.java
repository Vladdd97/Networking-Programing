package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        List<Process> processes = new ArrayList<>(Arrays.asList(
                new Process("Node1"),
                new Process("Node2"),
                new Process("Node3"),
                new Process("Node4"),
                new Process("Node5"),
                new Process("Node6"),
                new Process("Node7")

        ));


        processes.get(3).setWaitForProcesses(new ArrayList<>(Arrays.asList(
                processes.get(4),
                processes.get(5),
                processes.get(6)
        )));
        processes.get(0).setWaitForProcesses(new ArrayList<>(Arrays.asList(processes.get(3))));
        processes.get(1).setWaitForProcesses(new ArrayList<>(Arrays.asList(processes.get(3))));
        processes.get(2).setWaitForProcesses(new ArrayList<>(Arrays.asList(processes.get(3))));



        processes.forEach(p -> p.start());


        processes.forEach(p -> {
            try {
                p.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println("Finish");

    }
}
