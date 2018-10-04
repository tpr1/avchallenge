### This Code Simulates an interaction between the ABS and the VCU during the Braking process.
### instructions on how to make and export a federation on WebGME are described in the following link. 
https://pages.nist.gov/ucef/development/webgme/plugins/
### This federation takes into consideration two federates only. below is the experimentConfig.json used for this experiment .

```
{
  "federateTypesAllowed": [
    "ABS",
    "BMS",
    "BUSGW",
    "MCU",
    "VCU"
  ],
  "expectedFederates": [
    {
      "federateType": "ABS",
      "count": 1
    },
    {
      "federateType": "BMS",
      "count": 0
    },
    {
      "federateType": "BUSGW",
      "count": 0
    },
    {
      "federateType": "MCU",
      "count": 0
    },
    {
      "federateType": "VCU",
      "count": 1
    }
  ],
  "lateJoinerFederates": [
    {
      "federateType": "ABS",
      "count": 0
    },
    {
      "federateType": "BMS",
      "count": 0
    },
    {
      "federateType": "BUSGW",
      "count": 0
    },
    {
      "federateType": "MCU",
      "count": 0
    },
    {
      "federateType": "VCU",
      "count": 0
    }
  ]
}
```
### How to Run : 

- Create the AV folder in home/vagrant/Downloads
- git clone https://github.com/usnistgov/avchallenge.git

#### REST Start/Terminate Commands

```
[vagrant@vagrant AV]$ curl -X POST http://10.0.2.15:8083/fedmgr --data '{"action": "START"}' -H "Content-Type: application/json"
{"prevState":"INITIALIZED","newState":"STARTING","message":null}

[vagrant@vagrant AV]$ curl -X POST http://10.0.2.15:8083/fedmgr --data '{"action": "START"}' curl -X POST http://10.0.2.15:8083/fedmgr --data '{"action": "TERMINATE"}' -H "Content-Type: application/json"
{"prevState":"RUNNING","newState":"TERMINATING","message":null}

```

#### Federation Manager 
- command to run & Expected Console output  / Logs 
```
cd /home/vagrant/Downloads/AV/AV_deployment
[vagrant@vagrant AV_deployment]$ mvn exec:java -P FederationManagerExecJava
[INFO] Scanning for projects...                                                                                                                       
[INFO]                                                                                                                                                
[INFO] ------------------------------------------------------------------------                                                                       
[INFO] Building AV_exec 0.1.0-SNAPSHOT                                                                                                                                   
[INFO] ------------------------------------------------------------------------                                                                                          
[INFO]                                                                                                                                                                   
[INFO] --- exec-maven-plugin:1.5.0:java (default-cli) @ AV_exec ---                                                                                                      
01:53:09.689 [org.cpswt.host.FederationManagerHostApp.main()] INFO  org.cpswt.hla.FederationManager - No COA definitions were provided!                                  
01:53:09.691 [org.cpswt.host.FederationManagerHostApp.main()] INFO  org.cpswt.hla.FederationManager - No COA selections were provided!                                   
01:53:09.691 [org.cpswt.host.FederationManagerHostApp.main()] INFO  org.cpswt.hla.FederationManager - No specific COA-selection was specified for execution!             
01:53:09.691 [org.cpswt.host.FederationManagerHostApp.main()] INFO  org.cpswt.hla.FederationManager - No COAs are used in this experiment.                               
01:53:09.698 [org.cpswt.host.FederationManagerHostApp.main()] DEBUG org.cpswt.hla.SynchronizedFederate - Federate FederationManager-ebe3118c-abbb-44e8-95f1-a8f9fa5184a0 acquiring connection to RTI ...                                                                                                                                                                                          
01:53:09.846 [org.cpswt.host.FederationManagerHostApp.main()] DEBUG org.cpswt.hla.SynchronizedFederate - Federate FederationManager-ebe3118c-abbb-44e8-95f1-a8f9fa5184a0 connection to RTI successful.                                                                                                                                                                                            
01:53:09.847 [org.cpswt.host.FederationManagerHostApp.main()] DEBUG org.cpswt.hla.FederationManager - Local RTI component created successfully.                                                  

-------------------------------------------------------------------
GMS: address=vagrant-45568, cluster=AV, physical address=10.0.2.15:49244
-------------------------------------------------------------------
01:53:14.214 [org.cpswt.host.FederationManagerHostApp.main()] DEBUG org.cpswt.hla.FederationManager - Federation "AV" created successfully.
01:53:14.215 [org.cpswt.host.FederationManagerHostApp.main()] DEBUG org.cpswt.hla.SynchronizedFederate - [FederationManager-ebe3118c-abbb-44e8-95f1-a8f9fa5184a0] federate joining federation [AV] attempt #1
01:53:14.241 [org.cpswt.host.FederationManagerHostApp.main()] DEBUG org.cpswt.hla.SynchronizedFederate - [FederationManager-ebe3118c-abbb-44e8-95f1-a8f9fa5184a0] federate joined federation [AV] successfully
01:53:14.250 [org.cpswt.host.FederationManagerHostApp.main()] DEBUG org.cpswt.hla.SimEnd - subscribe: InteractionRoot.C2WInteractionRoot.SimulationControl.SimEnd
01:53:14.252 [org.cpswt.host.FederationManagerHostApp.main()] DEBUG org.cpswt.hla.FederateJoinInteraction - publish: InteractionRoot.C2WInteractionRoot.FederateJoinInteraction
01:53:14.255 [org.cpswt.host.FederationManagerHostApp.main()] DEBUG org.cpswt.hla.FederateResignInteraction - publish: InteractionRoot.C2WInteractionRoot.FederateResignInteraction
01:53:16.266 [org.cpswt.host.FederationManagerHostApp.main()] DEBUG org.cpswt.hla.FederationManager - Synchronization point "readyToPopulate" registered successfully.
01:53:17.267 [org.cpswt.host.FederationManagerHostApp.main()] DEBUG org.cpswt.hla.FederationManager - Synchronization point "readyToRun" registered successfully.
01:53:18.269 [org.cpswt.host.FederationManagerHostApp.main()] DEBUG org.cpswt.hla.FederationManager - Synchronization point "readyToResign" registered successfully.
01:53:18.269 [org.cpswt.host.FederationManagerHostApp.main()] DEBUG org.cpswt.hla.FederateJoinInteraction - subscribe: InteractionRoot.C2WInteractionRoot.FederateJoinInteraction
01:53:18.269 [org.cpswt.host.FederationManagerHostApp.main()] DEBUG org.cpswt.hla.FederateResignInteraction - subscribe: InteractionRoot.C2WInteractionRoot.FederateResignInteraction
01:53:18.270 [org.cpswt.host.FederationManagerHostApp.main()] DEBUG org.cpswt.hla.SimEnd - publish: InteractionRoot.C2WInteractionRoot.SimulationControl.SimEnd
01:53:18.271 [org.cpswt.host.FederationManagerHostApp.main()] DEBUG org.cpswt.hla.SimPause - publish: InteractionRoot.C2WInteractionRoot.SimulationControl.SimPause
01:53:18.273 [org.cpswt.host.FederationManagerHostApp.main()] DEBUG org.cpswt.hla.SimResume - publish: InteractionRoot.C2WInteractionRoot.SimulationControl.SimResume
01:53:19.306 [org.cpswt.host.FederationManagerHostApp.main()] INFO  org.cpswt.host.FederationManagerHostApp - Server online at 0.0.0.0:8083 ...
01:54:09.111 [routes-akka.actor.default-dispatcher-2] DEBUG org.cpswt.host.FederationManagerHostApp - Starting simulation
01:54:09.112 [Thread-1] DEBUG org.cpswt.hla.FederationManager - Starting simulation
01:54:09.120 [Thread-1] DEBUG org.cpswt.hla.FederateObject - subscribe: ObjectRoot.Manager.Federate
01:54:09.397 [Thread-1] DEBUG org.cpswt.hla.FederationManager - All expected federates have joined the federation. Proceeding with the simulation...
01:54:09.906 [Thread-1] DEBUG org.cpswt.hla.FederationManager - Susbcribing to High priority logs
01:54:09.908 [Thread-1] DEBUG org.cpswt.hla.HighPrio - subscribe: InteractionRoot.C2WInteractionRoot.SimLog.HighPrio
01:54:09.910 [Thread-3] DEBUG org.cpswt.hla.FederationManager - Main execution loop of federation started at: 2018-08-27T01:54:09.910+0000
01:54:09.910 [Thread-3] INFO  org.cpswt.hla.FederationManager - Current_time = 0.0 and step = 1.0 and requested_time = 1.0
01:54:10.912 [Thread-3] INFO  org.cpswt.hla.FederationManager - Current_time = 1.0 and step = 1.0 and requested_time = 2.0
01:54:11.912 [Thread-3] INFO  org.cpswt.hla.FederationManager - Current_time = 2.0 and step = 1.0 and requested_time = 3.0
01:54:12.913 [Thread-3] INFO  org.cpswt.hla.FederationManager - Current_time = 3.0 and step = 1.0 and requested_time = 4.0
01:54:13.914 [Thread-3] INFO  org.cpswt.hla.FederationManager - Current_time = 4.0 and step = 1.0 and requested_time = 5.0
01:54:14.915 [Thread-3] INFO  org.cpswt.hla.FederationManager - Current_time = 5.0 and step = 1.0 and requested_time = 6.0
01:54:15.915 [Thread-3] INFO  org.cpswt.hla.FederationManager - Current_time = 6.0 and step = 1.0 and requested_time = 7.0
01:54:16.917 [Thread-3] INFO  org.cpswt.hla.FederationManager - Current_time = 7.0 and step = 1.0 and requested_time = 8.0
01:54:17.917 [Thread-3] INFO  org.cpswt.hla.FederationManager - Current_time = 8.0 and step = 1.0 and requested_time = 9.0
01:54:18.918 [Thread-3] INFO  org.cpswt.hla.FederationManager - Current_time = 9.0 and step = 1.0 and requested_time = 10.0
01:54:18.918 [Thread-3] INFO  org.cpswt.hla.FederationManager - Federation manager current time = 10.0
01:54:19.919 [Thread-3] INFO  org.cpswt.hla.FederationManager - Current_time = 10.0 and step = 1.0 and requested_time = 11.0
01:54:20.920 [Thread-3] INFO  org.cpswt.hla.FederationManager - Current_time = 11.0 and step = 1.0 and requested_time = 12.0
01:54:21.921 [Thread-3] INFO  org.cpswt.hla.FederationManager - Current_time = 12.0 and step = 1.0 and requested_time = 13.0
01:54:22.922 [Thread-3] INFO  org.cpswt.hla.FederationManager - Current_time = 13.0 and step = 1.0 and requested_time = 14.0
01:54:23.923 [Thread-3] INFO  org.cpswt.hla.FederationManager - Current_time = 14.0 and step = 1.0 and requested_time = 15.0
01:54:24.926 [Thread-3] INFO  org.cpswt.hla.FederationManager - Current_time = 15.0 and step = 1.0 and requested_time = 16.0
01:54:25.927 [Thread-3] INFO  org.cpswt.hla.FederationManager - Current_time = 16.0 and step = 1.0 and requested_time = 17.0
01:54:26.928 [Thread-3] INFO  org.cpswt.hla.FederationManager - Current_time = 17.0 and step = 1.0 and requested_time = 18.0
01:54:27.931 [Thread-3] INFO  org.cpswt.hla.FederationManager - Current_time = 18.0 and step = 1.0 and requested_time = 19.0
01:54:28.932 [Thread-3] INFO  org.cpswt.hla.FederationManager - Current_time = 19.0 and step = 1.0 and requested_time = 20.0
01:54:28.933 [Thread-3] INFO  org.cpswt.hla.FederationManager - Federation manager current time = 20.0
01:54:29.934 [Thread-3] INFO  org.cpswt.hla.FederationManager - Current_time = 20.0 and step = 1.0 and requested_time = 21.0
01:54:30.936 [Thread-3] INFO  org.cpswt.hla.FederationManager - Current_time = 21.0 and step = 1.0 and requested_time = 22.0
01:54:31.937 [Thread-3] INFO  org.cpswt.hla.FederationManager - Current_time = 22.0 and step = 1.0 and requested_time = 23.0
01:54:32.939 [Thread-3] INFO  org.cpswt.hla.FederationManager - Current_time = 23.0 and step = 1.0 and requested_time = 24.0
01:54:33.303 [routes-akka.actor.default-dispatcher-9] DEBUG org.cpswt.host.FederationManagerHostApp - Terminate simulation
01:54:33.310 [Thread-4] DEBUG org.cpswt.hla.FederationManager - Terminating simulation
01:54:33.310 [Thread-4] DEBUG org.cpswt.hla.FederationManager - Main execution loop of federation stopped at: 2018-08-27T01:54:33.310+0000
01:54:33.311 [Thread-4] DEBUG org.cpswt.hla.FederationManager - Total execution time of the main loop: 23.401 seconds
01:54:33.939 [Thread-3] INFO  org.cpswt.hla.FederationManager - Current_time = 24.0 and step = 1.0 and requested_time = 25.0
01:54:33.941 [Thread-3] INFO  org.cpswt.hla.FederationManager - Waiting for "ABS-a9224304-6a92-4640-889e-21b7436d2161" federate to resign ...
01:54:33.942 [Thread-3] INFO  org.cpswt.hla.FederationManager - Waiting for "VCU-6658fea7-9ae3-483b-9408-a246e1e460b0" federate to resign ...
01:54:33.942 [Thread-3] INFO  org.cpswt.hla.FederationManager - Waiting for "ReadyToResign" ... 
01:54:38.335 [Thread-4] INFO  org.cpswt.hla.FederationManager - Simulation terminated
01:54:39.071 [Thread-3] INFO  org.cpswt.hla.FederationManager - Done with resign
01:54:39.072 [Thread-3] INFO  org.cpswt.hla.FederationManager - All federates have resigned the federation.  Simulation terminated.


```
#### ABS
- command to run & Expected Console output  / Logs - command to run & Expected Console output  / Logs 
```

cd /home/vagrant/Downloads/AV/AV_generated/AV-java-federates/AV-impl-java/ABS/target

[vagrant@vagrant target]$ java  -Dlog4j.configurationFile=conf/log4j2.xml -jar ABS-0.1.0-SNAPSHOT.jar  -federationId=AV -configFile=conf/ABSConfig.json
                                                                                                                                                      
-------------------------------------------------------------------                                                                                   
GMS: address=vagrant-56302, cluster=AV, physical address=10.0.2.15:51000                                                                              
-------------------------------------------------------------------                                                                                                      
01:53:48.710 [main] INFO  AV.ABS - waiting on readyToPopulate...                                                                                                         
01:54:09.450 [main] INFO  AV.ABS - ...synchronized on readyToPopulate                                                                                                    
01:54:09.450 [main] INFO  AV.ABS - waiting on readyToRun...                                                                                                              
01:54:09.701 [main] INFO  AV.ABS - ...synchronized on readyToRun                                                                                                         
01:54:09.703 [main] INFO  AV.ABS - started logical time progression                                                                                                      
01:54:09.704 [main] INFO  AV.ABS - Measured Speed is 0 Mph                                                                                                               
01:54:09.961 [main] INFO  AV.ABS - Measured Speed is 54 Mph                                                                                                              
01:54:10.956 [main] INFO  AV.ABS - VCU Troque Distribution 0 Nm                                                                                                          
01:54:10.956 [main] INFO  AV.ABS - Measured Speed is 63 Mph                                                                                                                                      
01:54:11.962 [main] INFO  AV.ABS - VCU Troque Distribution 1542 Nm                                                                                                                               
01:54:11.964 [main] INFO  AV.ABS - Measured Speed is 67 Mph                                                                                                                                      
01:54:12.964 [main] INFO  AV.ABS - VCU Troque Distribution 1214 Nm                                                                                                                               
01:54:12.964 [main] INFO  AV.ABS - Measured Speed is 85 Mph
01:54:13.959 [main] INFO  AV.ABS - VCU Troque Distribution 2818 Nm
01:54:13.959 [main] INFO  AV.ABS - Measured Speed is 42 Mph
01:54:14.958 [main] INFO  AV.ABS - VCU Troque Distribution 2702 Nm
01:54:14.959 [main] INFO  AV.ABS - Measured Speed is 65 Mph
01:54:15.965 [main] INFO  AV.ABS - VCU Troque Distribution 2077 Nm
01:54:15.965 [main] INFO  AV.ABS - Measured Speed is 41 Mph
01:54:16.961 [main] INFO  AV.ABS - VCU Troque Distribution 2543 Nm
01:54:16.961 [main] INFO  AV.ABS - Measured Speed is 89 Mph
01:54:17.959 [main] INFO  AV.ABS - VCU Troque Distribution 1958 Nm
01:54:17.959 [main] INFO  AV.ABS - Measured Speed is 38 Mph
01:54:18.960 [main] INFO  AV.ABS - VCU Troque Distribution 1757 Nm
01:54:18.960 [main] INFO  AV.ABS - Measured Speed is 87 Mph
01:54:19.962 [main] INFO  AV.ABS - VCU Troque Distribution 2816 Nm
01:54:19.963 [main] INFO  AV.ABS - Measured Speed is 36 Mph
01:54:20.963 [main] INFO  AV.ABS - VCU Troque Distribution 2837 Nm
01:54:20.964 [main] INFO  AV.ABS - Measured Speed is 80 Mph
01:54:21.964 [main] INFO  AV.ABS - VCU Troque Distribution 2249 Nm
01:54:21.964 [main] INFO  AV.ABS - Measured Speed is 87 Mph
01:54:22.966 [main] INFO  AV.ABS - VCU Troque Distribution 1516 Nm
01:54:22.966 [main] INFO  AV.ABS - Measured Speed is 80 Mph
01:54:23.970 [main] INFO  AV.ABS - VCU Troque Distribution 1679 Nm
01:54:23.970 [main] INFO  AV.ABS - Measured Speed is 80 Mph
01:54:24.969 [main] INFO  AV.ABS - VCU Troque Distribution 1032 Nm
01:54:24.970 [main] INFO  AV.ABS - Measured Speed is 43 Mph
01:54:25.977 [main] INFO  AV.ABS - Measured Speed is 86 Mph
01:54:26.980 [main] INFO  AV.ABS - VCU Troque Distribution 1731 Nm
01:54:26.981 [main] INFO  AV.ABS - Measured Speed is 48 Mph
01:54:27.986 [main] INFO  AV.ABS - VCU Troque Distribution 2414 Nm
01:54:27.987 [main] INFO  AV.ABS - Measured Speed is 32 Mph
01:54:28.980 [main] INFO  AV.ABS - VCU Troque Distribution 2959 Nm
01:54:28.980 [main] INFO  AV.ABS - Measured Speed is 86 Mph
01:54:29.980 [main] INFO  AV.ABS - VCU Troque Distribution 2405 Nm
01:54:29.980 [main] INFO  AV.ABS - Measured Speed is 73 Mph
01:54:30.980 [main] INFO  AV.ABS - VCU Troque Distribution 1579 Nm
01:54:30.980 [main] INFO  AV.ABS - Measured Speed is 66 Mph
01:54:31.981 [main] INFO  AV.ABS - VCU Troque Distribution 1415 Nm
01:54:31.982 [main] INFO  AV.ABS - Measured Speed is 63 Mph
01:54:32.985 [main] INFO  AV.ABS - VCU Troque Distribution 1825 Nm
01:54:32.985 [main] INFO  AV.ABS - Measured Speed is 63 Mph
01:54:33.987 [main] INFO  org.cpswt.hla.SynchronizedFederate - ABS-a9224304-6a92-4640-889e-21b7436d2161: SimEnd interaction received, exiting...
01:54:33.987 [main] INFO  AV.ABS - VCU Troque Distribution 1431 Nm
01:54:33.988 [main] INFO  AV.ABS - Measured Speed is 69 Mph
01:54:33.989 [main] INFO  org.cpswt.hla.SynchronizedFederate - Exiting gracefully ....
01:54:39.008 [main] INFO  AV.ABS - Done.



```

#### VCU
- command to run & Expected Console output  / Logs 
```
cd /home/vagrant/Downloads/AV/AV_generated/AV-java-federates/AV-impl-java/VCU/target
[vagrant@vagrant target]$ java  -Dlog4j.configurationFile=conf/log4j2.xml -jar VCU-0.1.0-SNAPSHOT.jar  -federationId=AV -configFile=conf/VCUConfig.json
                                                                                                                                                      
-------------------------------------------------------------------                                                                                   
GMS: address=vagrant-25057, cluster=AV, physical address=10.0.2.15:39697                                                                              
-------------------------------------------------------------------                                                                                                      
01:53:59.751 [main] INFO  AV.VCU - waiting on readyToPopulate...                                                                                                         
01:54:09.496 [main] INFO  AV.VCU - ...synchronized on readyToPopulate                                                                                                    
01:54:09.496 [main] INFO  AV.VCU - waiting on readyToRun...                                                                                                              
01:54:09.753 [main] INFO  AV.VCU - ...synchronized on readyToRun                                                                                                         
01:54:09.754 [main] INFO  AV.VCU - started logical time progression                                                                                                      
01:54:09.963 [main] INFO  AV.VCU - ABS Measured Speed : 0 Mph =>  VCU Torque Distribution : 0 Nm                                                                         
01:54:10.956 [main] INFO  AV.VCU - ABS Measured Speed : 54 Mph =>  VCU Torque Distribution : 1542 Nm                                                                     
01:54:11.961 [main] INFO  AV.VCU - ABS Measured Speed : 63 Mph =>  VCU Torque Distribution : 1214 Nm                                                                     
01:54:12.964 [main] INFO  AV.VCU - ABS Measured Speed : 67 Mph =>  VCU Torque Distribution : 2818 Nm                                                                                             
01:54:13.959 [main] INFO  AV.VCU - ABS Measured Speed : 85 Mph =>  VCU Torque Distribution : 2702 Nm                                                                                             
01:54:14.962 [main] INFO  AV.VCU - ABS Measured Speed : 42 Mph =>  VCU Torque Distribution : 2077 Nm                                                                                             
01:54:15.965 [main] INFO  AV.VCU - ABS Measured Speed : 65 Mph =>  VCU Torque Distribution : 2543 Nm                                                                                             
01:54:16.961 [main] INFO  AV.VCU - ABS Measured Speed : 41 Mph =>  VCU Torque Distribution : 1958 Nm 
01:54:17.967 [main] INFO  AV.VCU - ABS Measured Speed : 89 Mph =>  VCU Torque Distribution : 1757 Nm 
01:54:18.960 [main] INFO  AV.VCU - ABS Measured Speed : 38 Mph =>  VCU Torque Distribution : 2816 Nm 
01:54:19.962 [main] INFO  AV.VCU - ABS Measured Speed : 87 Mph =>  VCU Torque Distribution : 2837 Nm 
01:54:20.963 [main] INFO  AV.VCU - ABS Measured Speed : 36 Mph =>  VCU Torque Distribution : 2249 Nm 
01:54:21.963 [main] INFO  AV.VCU - ABS Measured Speed : 80 Mph =>  VCU Torque Distribution : 1516 Nm 
01:54:22.975 [main] INFO  AV.VCU - ABS Measured Speed : 87 Mph =>  VCU Torque Distribution : 1679 Nm 
01:54:23.970 [main] INFO  AV.VCU - ABS Measured Speed : 80 Mph =>  VCU Torque Distribution : 1032 Nm 
01:54:25.977 [main] INFO  AV.VCU - ABS Measured Speed : 43 Mph =>  VCU Torque Distribution : 1731 Nm 
01:54:26.980 [main] INFO  AV.VCU - ABS Measured Speed : 86 Mph =>  VCU Torque Distribution : 2414 Nm 
01:54:27.985 [main] INFO  AV.VCU - ABS Measured Speed : 48 Mph =>  VCU Torque Distribution : 2959 Nm 
01:54:28.979 [main] INFO  AV.VCU - ABS Measured Speed : 32 Mph =>  VCU Torque Distribution : 2405 Nm 
01:54:29.980 [main] INFO  AV.VCU - ABS Measured Speed : 86 Mph =>  VCU Torque Distribution : 1579 Nm 
01:54:30.990 [main] INFO  AV.VCU - ABS Measured Speed : 73 Mph =>  VCU Torque Distribution : 1415 Nm 
01:54:31.981 [main] INFO  AV.VCU - ABS Measured Speed : 66 Mph =>  VCU Torque Distribution : 1825 Nm 
01:54:32.985 [main] INFO  AV.VCU - ABS Measured Speed : 63 Mph =>  VCU Torque Distribution : 1431 Nm 
01:54:33.988 [main] INFO  org.cpswt.hla.SynchronizedFederate - VCU-6658fea7-9ae3-483b-9408-a246e1e460b0: SimEnd interaction received, exiting...
01:54:33.988 [main] INFO  org.cpswt.hla.SynchronizedFederate - Exiting gracefully ....
01:54:39.007 [main] INFO  AV.VCU - Done.


```
