# Laboratoy work nr.2  
## Multithreading Programming
### Task: create a program in Java/C# in which events will run in the following order (use Event-Driven Concurrency)
![varianta_1](https://user-images.githubusercontent.com/29525730/53685709-f16cc600-3d26-11e9-99a4-9b9eeaf13cdc.PNG)
### In this image are represented 7 events:
- events: 5, 6, 7 should run first in any order;
- event 4 is dependent on (it can run only after)  events: 5,6,7;
- events: 1, 2, 3 are dependent on event 4;

## Task Implementation

For implementing this task I created a **Process.java** class which extends **Thread.java** class. 

#### A Process object has: 
- a private boolean property **executed** which is false by default when an object is instantiated, the value of **executed** becomes true after the Process is executed;
- a list of Processes the object has to wait for, this means that the Process will be executed only after all Processes from **waitForProcesses** list will be executed.


### Here is the Process.java class implementation: 
```java
public class Process extends Thread {

    private List < Process > waitForProcesses;
    private boolean executed = false;

    public Process(String name) {
        super(name);
    }

    public List < Process > getWaitForProcesses() {
        return waitForProcesses;
    }

    public void setWaitForProcesses(List < Process > waitForProcesses) {
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
            for (Process process: waitForProcesses) {
                if (!process.isExecuted()) {
                    synchronized(process) {
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
        synchronized(this) {
            this.notifyAll();
        }
    }

}
```

A Process is considered executed after the `executeProcess()` method is executed.

### Implementation:
- so when a Process is instantiated it by default has `executed = false;`;
- when we call `start()` method the `run()` is called;
- in `run()` method we call `executeProcess()` method;
- in `executeProcess()` method we check if that Process has to wait for other Processes;
  -  if not then Process is just executed, executed become true `executed = true` and it notify all other threads which are locked on it that they now can be executed   `this.notifyAll()`;
		```java
		synchronized(this) {
		    this.notifyAll();
		}
		```
   -   if it has to wait for other Processes then we loop through them and check if the Process it has to wait for was executed or not;
       - if it was executed `executed = true`, then we go to the next Process from the list;
       - if it wasn't executed `executed = false` then we lock the Thread on that Process  `process.wait()`;
			```java
			synchronized(process) {
			    try {
			        process.wait();
			    } catch (InterruptedException e) {
			        e.printStackTrace();
			    }
			}
			```

