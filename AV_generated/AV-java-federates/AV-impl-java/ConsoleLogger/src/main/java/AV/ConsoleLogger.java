package AV;

import org.cpswt.config.FederateConfig;
import org.cpswt.config.FederateConfigParser;
import org.cpswt.hla.base.ObjectReflector;
import org.cpswt.hla.ObjectRoot;
import org.cpswt.hla.base.AdvanceTimeRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The ConsoleLogger type of federate for the federation designed in WebGME.
 *
 */
public class ConsoleLogger extends ConsoleLoggerBase {
    private final static Logger log = LogManager.getLogger();

    private double currentTime = 0;
    
    public String placeholder = "0";
    public double ST1 = 0;
    public double ST2 = 0;
    public double ST3 = 0;
    public double ST4 = 0;
    public double ST5 = 0;
    public double ST6 = 0;
    public double ST7 = 0;
    public double ST8 = 0;
    public double ST9 = 0;
    public double ST10 = 0;
    public boolean sendCondition = false;
    
    
    
    
    
    // J1931-71 compliant external Messages : (ExtrenalEntity_Message)  
   
    
    String MCU_Motor_Temperature = "0";
    String MCU_Motor_Speed = "0";
    String MCU_Motor_Power_Limits;
    String ABS_Wheel_Speed = "0";
    String BMS_Peak_Current = "0";
    String VCU_Torque_Request = "0";
    
    // J1931-71 NON compliant external Messages : (ExtrenalEntity_Message)  
    String ABS_traction_Stability_torque_request = "0";
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    CAN CLCAN = new CAN();
    
    

    public ConsoleLogger(FederateConfig params) throws Exception {
        super(params);
        CLCAN.registerObject(getLRC());
    }

    

    private void checkReceivedSubscriptions() {

        ObjectReflector reflector = null;
        while ((reflector = getNextObjectReflectorNoWait()) != null) {
            reflector.reflect();
            ObjectRoot object = reflector.getObjectRoot();
            if (object instanceof CAN) {
            	showCANMessages((CAN) object);
            }
            else {
                log.debug("unhandled object reflection: {}", object.getClassName());
            }
        }
    }

    
    
    
    
    
    
    
    
    
    
    
    
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

            ////////////////////////////////////////////////////////////////////////////////////////
            // TODO break here if ready to resign and break out of while loop
            ////////////////////////////////////////////////////////////////////////////////////////
            int osd = (int)currentTime % 10;


         
         checkReceivedSubscriptions();
         
         

  	   CLCAN.set_11BiD(placeholder);
          CLCAN.set_18BiD("0");
          CLCAN.set_ACKslot(true);
          CLCAN.set_CRC(placeholder);
          CLCAN.set_DLC(placeholder);
          CLCAN.set_DataField("0");
          CLCAN.set_EndOfFrame(placeholder);
          CLCAN.set_IDE(placeholder);
          CLCAN.set_IFS(placeholder);
          CLCAN.set_RTR(true);
          CLCAN.set_ReservedBit1(true);
          CLCAN.set_ReservedBit2(true);
          CLCAN.set_SRR(true);
          CLCAN.set_StartOfFrame(true);
      
      
      
      
      
          CLCAN.updateAttributeValues(getLRC(), currentTime + getLookAhead());
      
      
      
      
     




	    	   


	    	
	    	   
	
	    	   
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

    
    
    private void showCANMessages(CAN object) {
    	
    	
    	
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
            FederateConfig federateConfig = federateConfigParser.parseArgs(args, FederateConfig.class);
            ConsoleLogger federate = new ConsoleLogger(federateConfig);
            federate.execute();
            log.info("Done.");
            System.exit(0);
        } catch (Exception e) {
            log.error(e);
            System.exit(1);
        }
    }
}
