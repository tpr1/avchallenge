# This guide describes how to add and initialize custom parameters to an object-based federate.

The Simple Adder project (https://github.com/usnistgov/ucef-samples/tree/develop/SimpleAdder) implements the 
methods we describe in this tutorial and we're leveraging its implementation to bring custom parameters initialization to the AV project.

Let's pick the ABS federate from the AV Federation to implement custom parameters initialization:  

We need to modify and/or Create the following 3 files : 

1) avchallenge/AVIF/Federates/ABS_generated/ABS-java-federates/ABS-impl-java/ABS/conf/ABSConfig.json
2) avchallenge/AVIF/Federates/ABS_generated/ABS-java-federates/ABS-impl-java/ABS/src/main/java/ABS/ABSConfig.java
3) avchallenge/AVIF/Federates/ABS_generated/ABS-java-federates/ABS-impl-java/ABS/src/main/java/ABS/ABS.java


## ABSConfig.json


### the ABSConfig.json file contains the old and new parameters initialized. It should be defined in the pom.xml files below : 

```
.avchallenge/AVIF/Federates/ABS_generated/ABS-java-federates/ABS-impl-java/ABS/pom.xml:20:        <configFile>ABSConfig.json</configFile>
.avchallenge/AVIF/Federates/ABS_generated/ABS-java-federates/ABS/dependency-reduced-pom.xml:129:    <configFile>ABSConfig.json</configFile>
````


###  As we can see the POM files already points to the ABSConfig.json file. This file defaults to the following parameters and their initial values :

"federateRTIInitWaitTimeMs": 200,
"federateType": "ABS",
"federationId": "AV",
"isLateJoiner": false,
"lookAhead": 0.2,
"stepSize": 1.0,


### The parametrs above are pre-defined in the FederateConfig class by default.

After adding our custom values to the file we end up with the follwing : 


ABSConfig.jsob
```
{
"federateRTIInitWaitTimeMs": 200,
"federateType": "ABS",
"federationId": "AV",
"isLateJoiner": false,
"lookAhead": 0.2,
"stepSize": 1.0,
"Wheel_Speed": "88",
"Vehicle_Speed": "88",
"ABS_Event_Status":"placeholder",
"Traction_Stability_Torque_Request":"placeholder",   
"VCU_Motor_Torque" : "placeholder", 
"VCU_PGN" : "placeholder",
"Wheel_Speed_Sensors":"placeholder", 
"Hydraulic_Valve_Commands":"placeholder",
"ABSPGN" : "ABS",
"ABSSPNs" :   "placeholder",
"placeholder" : "placeholder",
"ST":0.0
}
```

### The added parameters must be defined as well, and that's where ABSConfig.java file comes handy : It extends from the FederateConfig Class.

## ABSConfig.java 


### After adding the custom parameters to the json file, we need to Declare them in the ABSConfig.java file as well.
Each custom parameter must be declared as a public variable with the following annotation : @FederateParameter.


### Our ABSConfig.java file will look like this : 


ABSConfig.json
```
public class ABSConfig extends FederateConfig {
    /**
     * ABS Measured Wheel Speed.
     */
	@FederateParameter
    public String placeholder;
    @FederateParameter
    public double ST;
    @FederateParameter
    public String Wheel_Speed;
    @FederateParameter
    public String ABS_Event_Status;   // pgn ?    SPN ?
    // J1931-71 NON-compliant local Messages
    @FederateParameter
    public String Traction_Stability_Torque_Request;   
    // J1931-71 compliant external Messages : (ExtrenalEntity_Message)
    @FederateParameter
    public String VCU_Motor_Torque; 
    @FederateParameter
    public String VCU_PGN ;
   // Messages sent/received to/from actuators/sensors
    @FederateParameter
    public String Wheel_Speed_Sensors; // see graph i`n slide 3
    @FederateParameter
    public String Hydraulic_Valve_Commands; // see graph in slide 3
   // PGN : multiple PGNs per Federate are possible. we use the Federate name as a placeholder for now.
    @FederateParameter
    public  String ABSPGN;
   // SPNs 
    @FederateParameter
    public String ABSSPNs; 
    @FederateParameter
    public String Vehicle_Speed;
}


```



## ABS.java
 
Now that we have declared the new variables in ABSConfig.java and we have initialized them, Let's call these configuration file within the ABS.java ABS Federate source file.

### In the ABS.java file below we highlight the lines where a change should be made with a [Number] sign at the beggining of the line and we explain the change to be made with comments.


ABS.java

```
package ABS;


import org.cpswt.config.FederateConfigParser;
import org.cpswt.config.FederateParameter;
import org.cpswt.hla.base.ObjectReflector;

import ABS.ABSConfig;
import org.cpswt.hla.ObjectRoot;
import org.cpswt.hla.base.AdvanceTimeRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.concurrent.ThreadLocalRandom;

/**
 * The ABS type of federate for the federation designed in WebGME.
 *
 */
public class ABS extends ABSBase {
    
	
	/* definition & initialization block */
	
	public final static Logger log = LogManager.getLogger();
    public double currentTime = 0;
    

   
// [1]  call an instance of the config file. This instance will be called each time we want to update / initializa our variables.

        public ABSConfig ABSparameter = new ABSConfig();

// [2]     create an instance of the CAN object. 

        CAN AbsCAN = new CAN();

// [3]  Create a Federate with "ABSConfig" as a config file.

        public ABS(ABSConfig params) throws Exception {
        super(params);

// [4] register the CAN instance (AbsCAN)

        AbsCAN.registerObject(getLRC());             

// [5] initialize the parameters. 

              
         ABSparameter.placeholder= params.placeholder;  
         ABSparameter.ST= params.ST;
         ABSparameter.Wheel_Speed= params.Wheel_Speed;
         ABSparameter.ABS_Event_Status= params.ABS_Event_Status; 
         ABSparameter.Traction_Stability_Torque_Request= params.Traction_Stability_Torque_Request;   
         ABSparameter.VCU_Motor_Torque= params.VCU_Motor_Torque; 
         ABSparameter.VCU_PGN = params.VCU_PGN;
         ABSparameter.Wheel_Speed_Sensors= params.Wheel_Speed_Sensors; 
         ABSparameter.Hydraulic_Valve_Commands= params.Hydraulic_Valve_Commands;
         ABSparameter.ABSPGN= params.ABSPGN;  
         ABSparameter.ABSSPNs= params.ABSSPNs; 
         ABSparameter.Vehicle_Speed= params.Vehicle_Speed;
         
    }

  
    public void checkReceivedSubscriptions() {

        ObjectReflector reflector = null;
        while ((reflector = getNextObjectReflectorNoWait()) != null) {
        	
            reflector.reflect();
            ObjectRoot object = reflector.getObjectRoot();
            if (object instanceof CAN) 
            {
                handleObjectClass((CAN) object);
            }
            
            else {
            	
                log.debug("unhandled object reflection: {}", object.getClassName());
                
                 }
        }
    }

     
    
    
   public void Wheel_Speed ()
    
    {
    	

	  ABSparameter.Wheel_Speed   = Integer.toString(ThreadLocalRandom.current().nextInt(50,90 + 1));

    } 
    
    
    
   public void Determine_Brake_Effort()
    
    {
    	
	   
	   
	   
    }

   public void Traction_Stability_Torque_Request  ()
    
    {
	   ABSparameter.Traction_Stability_Torque_Request = Integer.toString(ThreadLocalRandom.current().nextInt(300,400 + 1));
    }

    
   public void Friction_brake_control()
   
    {
   
    }


   public String Build_SPN()
   
   {
	   return ABSparameter.ABSSPNs= ABSparameter.Wheel_Speed +" "+ ABSparameter.Vehicle_Speed +" "+ ABSparameter.ABS_Event_Status+" " + ABSparameter.Traction_Stability_Torque_Request;
   }
   
   public void Build_and_Send_CAN_Frame(String pgn,String spn)
   
   {

	
	   
	   AbsCAN.set_11BiD(ABSparameter.placeholder);
       AbsCAN.set_18BiD(pgn);
       AbsCAN.set_ACKslot(true);
       AbsCAN.set_CRC(ABSparameter.placeholder);
       AbsCAN.set_DLC(ABSparameter.placeholder);
       AbsCAN.set_DataField(spn);
       AbsCAN.set_EndOfFrame(ABSparameter.placeholder);
       AbsCAN.set_IDE(ABSparameter.placeholder);
       AbsCAN.set_IFS(ABSparameter.placeholder);
       AbsCAN.set_RTR(true);
       AbsCAN.set_ReservedBit1(true);
       AbsCAN.set_ReservedBit2(true);
       AbsCAN.set_SRR(true);
       AbsCAN.set_StartOfFrame(true);
       AbsCAN.updateAttributeValues(getLRC(), currentTime + getLookAhead());
	   
   }
   
   
    public void execute() throws Exception {
        if(super.isLateJoiner()) 
	
        {
         
         	log.info("turning off time regulation (late joiner)");
            currentTime = super.getLBTS() - super.getLookAhead();
            super.disableTimeRegulation();
         
        }
                
        AdvanceTimeRequest atr = new AdvanceTimeRequest(currentTime);
        putAdvanceTimeRequest(atr);

        if(!super.isLateJoiner()) {
            log.info("waiting on readyToPopulate...");
            readyToPopulate();
            log.info("...synchronized on readyToPopulate");
        }

        if(!super.isLateJoiner()) {
            log.info("waiting on readyToRun...");
            readyToRun();
            log.info("...synchronized on readyToRun");
        }

        startAdvanceTimeThread();
        log.info("started logical time progression");
        

       while (!exitCondition) {

            atr.requestSyncStart();
            enteredTimeGrantedState();
            	   
 	 	   int osd = (int)currentTime % 10;

    	   switch (osd)
    	   {
    	   case 0:  
    	   Wheel_Speed (); 
    	   Build_and_Send_CAN_Frame( ABSparameter.ABSPGN, Build_SPN());
           break;
    	   case 6:
    	   Traction_Stability_Torque_Request(); 
    	   Build_and_Send_CAN_Frame( ABSparameter.ABSPGN, Build_SPN());
    	         break;        
    	   }
    	   
    	   if (!exitCondition) {      
            	currentTime += super.getStepSize();
                AdvanceTimeRequest newATR = new AdvanceTimeRequest(currentTime);
                putAdvanceTimeRequest(newATR);
                atr.requestSyncEnd();
                atr = newATR;
            }
       }
        exitGracefully();
    }
    public void handleObjectClass(CAN object) {
    }


public static void main(String[] args) {
        try {
                FederateConfigParser federateConfigParser = new FederateConfigParser();
// [6] The federate config parser must be and instance of the ABSConfig (Not the default FederateConfig) class and it must use ABSConfig.class as an argument.

                 ABSConfig federateConfig = federateConfigParser.parseArgs(args, ABSConfig.class);

// [7] the federate must be intanciated using the federateConfig parser we have just created out of the ABSConfig class.

                ABS federate = new ABS(federateConfig);
                federate.execute();
                log.info("Done.");
                System.exit(0);
        }          
        catch (Exception e) {
            log.error(e);
            System.exit(1);
        }
    }
}

````


















