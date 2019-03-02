# Laboratoy work nr.2  
## Multithreading Programming
### Task: create a program in Java/C# in which events will run in the following order (use Event-Driven Concurrency)
![varianta_1](https://user-images.githubusercontent.com/29525730/53685709-f16cc600-3d26-11e9-99a4-9b9eeaf13cdc.PNG)
### In this image are represented 7 events:
- events: 5, 6, 7 should run first in any order;
- event 4 is dependent on (it can run only after)  events: 5,6,7;
- events: 1, 2, 3 are dependent on event 4;

