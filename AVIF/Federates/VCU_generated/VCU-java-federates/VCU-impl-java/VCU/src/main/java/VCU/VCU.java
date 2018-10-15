package VCU;

import org.cpswt.config.FederateConfig;
import org.cpswt.config.FederateConfigParser;
import org.cpswt.hla.base.ObjectReflector;

import VCU.VCUConfig;

import org.cpswt.hla.ObjectRoot;
import org.cpswt.hla.base.AdvanceTimeRequest;

import java.util.concurrent.ThreadLocalRandom;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The VCU type of federate for the federation designed in WebGME.
 *
 */
public class VCU extends VCUBase {
	
	/* definition & initialization block */
	
    private final static Logger log = LogManager.getLogger();
    private double currentTime = 0;
    
    public VCUConfig params = new VCUConfig();
    
    
    public String placeholder = "0";
    public double ST1 = params.ST1;
    public double ST2 = params.ST2;
    public double ST3 = params.ST3;
    public double ST4 = params.ST4;
    public double ST5 = params.ST5;
    public double ST6 = params.ST6;
    public double ST7 = params.ST7;
    public double ST8 = params.ST8;
    public double ST9 = params.ST9;
    public double ST10 = params.ST10;
    public boolean sendCondition = false;

     // J1931-71 compliant local Messages

    String Motor_Operating_Mode=params.Motor_Operating_Mode; // pgn ? SPN ?
    String Motor_Torque=params.Motor_Torque; // pgn ? SPN ?
    String Motor_Speed=params.Motor_Speed; // pgn ? SPN ?
    String Volt_Cmd=params.Volt_Cmd; // pgn ? SPN ?
    String Contactor_Override_Commands=params.Contactor_Override_Commands; // pgn ? SPN ?
    String Battery_Commands=params.Battery_Commands; // pgn ? SPN ?
    String Torque_Commands=params.Torque_Commands; // pgn ? SPN ?
    String InverterCoolingAndHeatingCommands=params.InverterCoolingAndHeatingCommands;    // pgn ? SPN ?
    
    
    // initialize parameters
    

    
    // J1931-71 compliant external Messages : (ExtrenalEntity_Message)  
   
    String MCU_Motor_Temperature = params.MCU_Motor_Temperature;
    String MCU_Motor_Speed = params.MCU_Motor_Speed;
    String MCU_Motor_Power_Limits = params.MCU_Motor_Power_Limits;
    String ABS_Wheel_Speed = params.ABS_Wheel_Speed;
    String BMS_Peak_Current = params.BMS_Peak_Current;
    String VCU_Torque_Request = params.VCU_Torque_Request;
    
    // J1931-71 NON compliant external Messages : (ExtrenalEntity_Message)  
    String ABS_Braking_Torque_split = params.ABS_Braking_Torque_split;
    String ABS_traction_Stability_torque_request = params.ABS_traction_Stability_torque_request;
    
    // Messages sent/received to/from actuators/sensors
    
String Accel_Pedal_Position=params.Accel_Pedal_Position;
String Brake_Pressure=params.Brake_Pressure;
    



// PGN : multiple PGNs per Federate are possible. we use the Federate name as a placeholder for now.


String VCUPGN = params.VCUPGN;


// SPNs 

String VCUSPNs = params.VCUSPNs;

    
    ///////////////////////////////////////////////////////////////////////
    // TODO Instantiate objects that must be sent every logical time step
    //
    CAN VcuCAN = new CAN();
    //
    ///////////////////////////////////////////////////////////////////////

    public VCU(FederateConfig params) throws Exception {
        super(params);

        ///////////////////////////////////////////////////////////////////////
        // TODO Must register object instances after super(args)
        //
       VcuCAN.registerObject(getLRC());
        //
        ///////////////////////////////////////////////////////////////////////
    }

    private void checkReceivedSubscriptions() {

        ObjectReflector reflector = null;
        while ((reflector = getNextObjectReflectorNoWait()) != null) {
            reflector.reflect();
            ObjectRoot object = reflector.getObjectRoot();
            if (object instanceof CAN) {
                handleObjectClass((CAN) object);
            }
            else {
                log.debug("unhandled object reflection: {}", object.getClassName());
            }
        }
    }

   
    public void Battery_Power_Available () 
    
    {

	}
    
    
   public void Motor_Power_Available () 
    
    {

	}
    
 


    
    public void Torque_request ()
    
    {
    	
    	
	    int length = 10;
	    boolean useLetters = true;
	    boolean useNumbers = false;
	    String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);
	    
    	Motor_Torque= generatedString;
	}
    
    
    public void Regen_torque_demand ()

    {
    	
    	
    	

	}
    
    
    
    
    
    public void Build_and_Send_CAN_Frame(String pgn, String spn){
    VcuCAN.set_11BiD(placeholder);
    VcuCAN.set_18BiD(pgn);
    VcuCAN.set_ACKslot(true);
    VcuCAN.set_CRC(placeholder);
    VcuCAN.set_DLC(placeholder);
    VcuCAN.set_DataField(spn);
    VcuCAN.set_EndOfFrame(placeholder);
    VcuCAN.set_IDE(placeholder);
    VcuCAN.set_IFS(placeholder);
    VcuCAN.set_RTR(true);
    VcuCAN.set_ReservedBit1(true);
    VcuCAN.set_ReservedBit2(true);
    VcuCAN.set_SRR(true);
    VcuCAN.set_StartOfFrame(true);
   
    
    /* Publishing the CAN Frame */
     	
    VcuCAN.updateAttributeValues(getLRC(), currentTime + getLookAhead());
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
//    public void show_wheel_speed()
//    
//    {
//    	 log.info("ABS_Wheel_Speed "+ABS_Wheel_Speed + " currenttime " + currentTime +" dataTime " + ST2);
//    }
//    
    private void execute() throws Exception {
        if(super.isLateJoiner()) {
            log.info("turning off time regulation (late joiner)");
            currentTime = super.getLBTS() - super.getLookAhead();
            super.disableTimeRegulation();
        }

        /////////////////////////////////////////////
        // TODO perform basic initialization below //
        /////////////////////////////////////////////

        AdvanceTimeRequest atr = new AdvanceTimeRequest(currentTime);
        putAdvanceTimeRequest(atr);

        if(!super.isLateJoiner()) {
            log.info("waiting on readyToPopulate...");
            readyToPopulate();
            log.info("...synchronized on readyToPopulate");
        }

        ///////////////////////////////////////////////////////////////////////
        // TODO perform initialization that depends on other federates below //
        ///////////////////////////////////////////////////////////////////////

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
            
            
            /* Listening on the CAN-BUS and processing messages of interest */
            
            /* behavior and formulas block */
            
            Motor_Operating_Mode="0"; 
            Motor_Speed="0"; 
            Volt_Cmd="0"; 
            Contactor_Override_Commands="0"; 
            Battery_Commands="0"; 
            Torque_Commands="ABCD"; 
            InverterCoolingAndHeatingCommands="0"; 
            
            
         //SPN aggregation : For now We suppose all SPNs coming from a single Federate have the same PGN, to be modified when the right PGNs are described.
            
          
                 
           
         //  VCUSPNs = Motor_Operating_Mode + Motor_Torque + Motor_Speed + Volt_Cmd +  Contactor_Override_Commands + Battery_Commands +Torque_Commands + InverterCoolingAndHeatingCommands  ;
             
             VCUSPNs =Motor_Operating_Mode + " " + Motor_Torque + " " +Motor_Speed + " " + Volt_Cmd+ " " + Contactor_Override_Commands+ " " + Battery_Commands+ " " + Torque_Commands +" "+InverterCoolingAndHeatingCommands ;


           
	 	 	   int osd = (int)currentTime % 10;

	    	   switch (osd)
	    	   {
            
	    	   case 1:
            
            checkReceivedSubscriptions();
            
           	break;
           	
           	
	    	   case 3:
	               
	               checkReceivedSubscriptions();
	               
	              	break;
	    	   case 5:
	               
	               checkReceivedSubscriptions();
	               
	              	break;
	    	   case 7:
	               
	               checkReceivedSubscriptions();
	               
	               
	              	break;
           	
           	
           	
           	
           	
           	
           	
           	
          //  show_wheel_speed();
  	 
          

            ////////////////////////////////////////////////////////////////////////////////////////
            // TODO objects that must be sent every logical time step
            //
           
            
	 
       
	    	       case 8:
	    	    	   
	    	    	   Torque_request ();
	    	    	   
	    	             VCUSPNs =Motor_Operating_Mode + " " + Motor_Torque + " " +Motor_Speed + " " + Volt_Cmd+ " " + Contactor_Override_Commands+ " " + Battery_Commands+ " " + Torque_Commands +" "+InverterCoolingAndHeatingCommands ;


	    	             Build_and_Send_CAN_Frame(VCUPGN, VCUSPNs);
	    	    	
	    	           break;
	    	           
	    	           

	    	   }
	     	 
	    	   
String MCU_VCU_Message1 =  " MCU Sense Speed : " + MCU_Motor_Speed + " & current time is  " + currentTime + "  message time is " + ST1 +  " case " + (int)ST1 % 10 ;
log.info(MCU_VCU_Message1);
String MCU_VCU_Message2 =  " MCU Power Limit : " + MCU_Motor_Power_Limits + " & current time is  " + currentTime + "  message time is " + ST1 +  " case " + (int)ST1 % 10 ;
log.info(MCU_VCU_Message2);

String BMS_VCU_Message =  " BMS Power limit  : BMS_Peak_Current : " + BMS_Peak_Current + " & current time is  " + currentTime+ " AMP & message time is " + ST3 +  " case " + (int)ST3 % 10 ;
log.info(BMS_VCU_Message);

	     
String ABS_traction_Stability_torque_request_Message =  "ABS Speed : " + ABS_Wheel_Speed + "  ABS_traction_Stability_torque_request : " + ABS_traction_Stability_torque_request + " & current time is  " + currentTime+ "  message time is " + ST7 +  " case " +(int)ST7 % 10 ;
log.info(ABS_traction_Stability_torque_request_Message);

   	           
String VCU_Torque_Message =  "VCU_Torque_Request : " + VCU_Torque_Request + " & current time is  " + currentTime+ "  message time is " + ST9 +  " case " +(int)ST9 % 10 ;
log.info(VCU_Torque_Message);
	           
            
            //
            //////////////////////////////////////////////////////////////////////////////////////////


            ////////////////////////////////////////////////////////////////////////////////////////
            // TODO break here if ready to resign and break out of while loop
            ////////////////////////////////////////////////////////////////////////////////////////


            if (!exitCondition) {
                currentTime += super.getStepSize();
                AdvanceTimeRequest newATR = new AdvanceTimeRequest(currentTime);
                putAdvanceTimeRequest(newATR);
                atr.requestSyncEnd();
                atr = newATR;
            }
        }

        // call exitGracefully to shut down federate
        exitGracefully();

        ////////////////////////////////////////////////////////////////////////////////////////
        // TODO Perform whatever cleanups needed before exiting the app
        ////////////////////////////////////////////////////////////////////////////////////////
    }
   
    private void handleObjectClass(CAN object) {

    	String delims = "[ ]+";
    	String[] CSPNs = object.get_DataField().split(delims);
    	

    	
    	        switch (object.get_18BiD()) {
    	                
    	                case "MCU":  
    	            
    	            	MCU_Motor_Temperature  =   CSPNs[0];
    	            	MCU_Motor_Speed        =   CSPNs[1];
    	            	MCU_Motor_Power_Limits =   CSPNs[2];  
    	            	
    	            	ST1=object.getTime();
    	            	ST2=object.get_DataField_time();
    	            	sendCondition = true;
    	            	
    	            	break;     //   case 1:
    	                     
    	                        
    	            case "ABS":  
    	            	
    	            	
    	            	ABS_Wheel_Speed               = CSPNs[0];
    	            	
    	            	ABS_traction_Stability_torque_request      = CSPNs[3];
    	            	
    	            	ST7=object.getTime();
    	            	ST8=object.get_DataField_time();
    	            	
    	                break;  
    	        
    	            case "BMS":
    	        
    	            	BMS_Peak_Current      = CSPNs[7];
    	            	ST3=object.getTime();
    	            	ST4=object.get_DataField_time();
	                    
    	            	break;  
    	            	
    	             	
    	            case "VCU":
    	    	        
    	            	VCU_Torque_Request      = CSPNs[1];
    	            	ST9=object.getTime();
    	            	ST10=object.get_DataField_time();
    	        
    	        
    	        }
    }

    public static void main(String[] args) {
        try {
            FederateConfigParser federateConfigParser = new FederateConfigParser();
            FederateConfig federateConfig = federateConfigParser.parseArgs(args, VCUConfig.class);
            VCU federate = new VCU(federateConfig);
            federate.execute();
            log.info("Done.");
            System.exit(0);
        } catch (Exception e) {
            log.error(e);
            System.exit(1);
        }
    }
}
