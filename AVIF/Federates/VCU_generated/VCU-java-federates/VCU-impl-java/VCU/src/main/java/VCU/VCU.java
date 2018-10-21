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
    
    public VCUConfig VCUparameter = new VCUConfig();
    
    CAN VcuCAN = new CAN();
    
    public VCU(VCUConfig params) throws Exception {
        super(params);


       VcuCAN.registerObject(getLRC());
       VCUparameter.placeholder = params.placeholder ; 
       VCUparameter.ST1 = params.ST1 ; 
       VCUparameter.ST2 = params.ST2 ; 
       VCUparameter.ST3 = params.ST3 ; 
       VCUparameter.ST4 = params.ST4 ; 
       VCUparameter.ST5 = params.ST5 ; 
       VCUparameter.ST6 = params.ST6 ; 
       VCUparameter.ST7 = params.ST7 ; 
       VCUparameter.ST8 = params.ST8 ; 
       VCUparameter.ST9 = params.ST9 ; 
       VCUparameter.ST10 = params.ST10 ; 
       VCUparameter.Motor_Operating_Mode = params.Motor_Operating_Mode ; 
       VCUparameter.Motor_Torque = params.Motor_Torque ; 
       VCUparameter.Motor_Speed = params.Motor_Speed ; 
       VCUparameter.Volt_Cmd = params.Volt_Cmd ; 
       VCUparameter.Contactor_Override_Commands = params.Contactor_Override_Commands ; 
       VCUparameter.Battery_Commands = params.Battery_Commands ; 
       VCUparameter.Torque_Commands = params.Torque_Commands ; 
       VCUparameter.InverterCoolingAndHeatingCommands = params.InverterCoolingAndHeatingCommands ; 
       VCUparameter.MCU_Motor_Temperature = params.MCU_Motor_Temperature ; 
       VCUparameter.MCU_Motor_Speed = params.MCU_Motor_Speed ; 
       VCUparameter.MCU_Motor_Power_Limits = params.MCU_Motor_Power_Limits ; 
       VCUparameter.ABS_Wheel_Speed = params.ABS_Wheel_Speed ; 
       VCUparameter.BMS_Peak_Current = params.BMS_Peak_Current ; 
       VCUparameter.VCU_Torque_Request = params.VCU_Torque_Request ; 
       VCUparameter.ABS_Braking_Torque_split = params.ABS_Braking_Torque_split ; 
       VCUparameter.ABS_traction_Stability_torque_request = params.ABS_traction_Stability_torque_request ; 
       VCUparameter.Accel_Pedal_Position = params.Accel_Pedal_Position ; 
       VCUparameter.Brake_Pressure = params.Brake_Pressure ; 
       VCUparameter.VCUPGN = params.VCUPGN ; 
       VCUparameter.VCUSPNs = params.VCUSPNs ; 
        
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
	    VCUparameter.Torque_Commands= generatedString;
	}
    
    
    public void Regen_torque_demand ()

    {
    		

	}
    
  
    
    
    public void AV_Log()
    
    {
    String MCU_VCU_Message1 =  " MCU Sense Speed : " + VCUparameter.MCU_Motor_Speed + " & current time is  " + currentTime + "  message time is " + VCUparameter.ST1 +  " case " + (int)VCUparameter.ST1 % 10 ;
    log.info(MCU_VCU_Message1);
    String MCU_VCU_Message2 =  " MCU Power Limit : " + VCUparameter.MCU_Motor_Power_Limits + " & current time is  " + currentTime + "  message time is " + VCUparameter.ST1 +  " case " + (int)VCUparameter.ST1 % 10 ;
    log.info(MCU_VCU_Message2);
    String BMS_VCU_Message =  " BMS Power limit  : BMS_Peak_Current : " + VCUparameter.BMS_Peak_Current + " & current time is  " + currentTime+ " AMP & message time is " + VCUparameter.ST3 +  " case " + (int)VCUparameter.ST3 % 10 ;
    log.info(BMS_VCU_Message);	     
    String ABS_traction_Stability_torque_request_Message =  "ABS Speed : " + VCUparameter.ABS_Wheel_Speed + "  ABS_traction_Stability_torque_request : " + VCUparameter.ABS_traction_Stability_torque_request + " & current time is  " + currentTime+ "  message time is " + VCUparameter.ST7 +  " case " +(int)VCUparameter.ST7 % 10 ;
    log.info(ABS_traction_Stability_torque_request_Message);  	           

    
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public String Build_SPN()
    
    {
 	   return VCUparameter.VCUSPNs= VCUparameter.Motor_Operating_Mode + " " + VCUparameter.Motor_Torque + " " +VCUparameter.Motor_Speed + " " + VCUparameter.Volt_Cmd+ " " + VCUparameter.Contactor_Override_Commands+ " " + VCUparameter.Battery_Commands+ " " + VCUparameter.Torque_Commands +" "+VCUparameter.InverterCoolingAndHeatingCommands ;
    }
    
    
    
    public void Build_and_Send_CAN_Frame(String pgn, String spn){
    VcuCAN.set_11BiD(VCUparameter.placeholder);
    VcuCAN.set_18BiD(pgn);
    VcuCAN.set_ACKslot(true);
    VcuCAN.set_CRC(VCUparameter.placeholder);
    VcuCAN.set_DLC(VCUparameter.placeholder);
    VcuCAN.set_DataField(spn);
    VcuCAN.set_EndOfFrame(VCUparameter.placeholder);
    VcuCAN.set_IDE(VCUparameter.placeholder);
    VcuCAN.set_IFS(VCUparameter.placeholder);
    VcuCAN.set_RTR(true);
    VcuCAN.set_ReservedBit1(true);
    VcuCAN.set_ReservedBit2(true);
    VcuCAN.set_SRR(true);
    VcuCAN.set_StartOfFrame(true);
    VcuCAN.updateAttributeValues(getLRC(), currentTime + getLookAhead());
    }
    
        
    private void execute() throws Exception {
        if(super.isLateJoiner()) {
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
	    	   case 1:
           checkReceivedSubscriptions();
           AV_Log();
           	break;
 	    	   case 3:
	               checkReceivedSubscriptions();
	               AV_Log();
	              	break;
	    	   case 5:
	               checkReceivedSubscriptions();
	               AV_Log();
	              	break;
	    	   case 7:
	               checkReceivedSubscriptions();
	               AV_Log();
	              	break;
	    	       case 8:
       	    	   Torque_request ();
       	      	   Build_and_Send_CAN_Frame( VCUparameter.VCUPGN, Build_SPN());
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
    private void handleObjectClass(CAN object) {
    	String delims = "[ ]+";
    	String[] CSPNs = object.get_DataField().split(delims);
    switch (object.get_18BiD()) {
   	                case "MCU":  
   	                	VCUparameter.MCU_Motor_Temperature  =   CSPNs[0];
   	                	VCUparameter.MCU_Motor_Speed        =   CSPNs[1];
   	                	VCUparameter.MCU_Motor_Power_Limits =   CSPNs[2];  
   	                	VCUparameter.ST1=object.getTime();
   	                	VCUparameter.ST2=object.get_DataField_time();

    	            	break;     //   case 1:
                   case "ABS":  
                	   VCUparameter.ABS_Wheel_Speed               = CSPNs[0];
                	   VCUparameter.ABS_traction_Stability_torque_request      = CSPNs[3];
                	   VCUparameter.ST7=object.getTime();
                	   VCUparameter.ST8=object.get_DataField_time();
    	                break;  
    	            case "BMS":
    	            	VCUparameter.BMS_Peak_Current      = CSPNs[7];
    	            	VCUparameter.ST3=object.getTime();
    	            	VCUparameter.ST4=object.get_DataField_time();
    	            	break;  	

    	        }
    }

    public static void main(String[] args) {
        try {
            FederateConfigParser federateConfigParser = new FederateConfigParser();
            VCUConfig federateConfig = federateConfigParser.parseArgs(args, VCUConfig.class);
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
