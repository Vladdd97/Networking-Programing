package com.company;

import java.util.ArrayList;
import java.util.List;

public class Process extends Thread {

    private List<Process> waitForProcesses;
    private boolean executed = false;

    public Process(String name) {
        super(name);
    }

    public List<Process> getWaitForProcesses() {
        return waitForProcesses;
    }

    public void setWaitForProcesses(List<Process> waitForProcesses) {
        this.waitForProcesses = waitForProcesses;
    }

    public boolean isExecuted() {
        return executed;
    }

    public void setExecuted(boolean executed) {
        this.executed = executed;
    }


    @Override
    public void run() {
        this.executeProcess();
    }

    public void executeProcess() {

        if (waitForProcesses != null) {
            for (Process process : waitForProcesses) {
                if (!process.isExecuted()) {
                    synchronized (process) {
                        try {
                            process.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        }

        CustomTask.executeTask(super.getName());
        this.setExecuted(true);
        synchronized (this) {
            this.notifyAll();
        }
    }

}