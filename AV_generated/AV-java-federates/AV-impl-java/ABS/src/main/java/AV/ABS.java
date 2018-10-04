package AV;

import org.cpswt.config.FederateConfig;
import org.cpswt.config.FederateConfigParser;
import org.cpswt.hla.base.ObjectReflector;
import org.cpswt.hla.ObjectRoot;
import org.cpswt.hla.base.AdvanceTimeRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;


/**
 * The ABS type of federate for the federation designed in WebGME.
 *
 */
public class ABS extends ABSBase {
    
	
	/* definition & initialization block */
	
	private final static Logger log = LogManager.getLogger();
    private double currentTime = 0;
    String placeholder = "0";
    public double ST = 0;
    public boolean sendCondition = false;
    public String miaw = "0";

    String prestat2 = "0";

    // J1931-71 compliant local Messages
    
     String Wheel_Speed="0";         // pgn ? SPN ?
     String Vehicle_Speed="0";   // pgn ? SPN ?
     String ABS_Event_Status="0";   // pgn ?    SPN ?
       
     // J1931-71 NON-compliant local Messages
     
     String Traction_Stability_Torque_Request="0";   
     // J1931-71 compliant external Messages : (ExtrenalEntity_Message)
     
     
     String VCU_Motor_Torque = "0"; 
     
     String VCU_PGN = "0";
     
     
     
    // Messages sent/received to/from actuators/sensors
     
     String Wheel_Speed_Sensors="0"; // see graph i`n slide 3
     String Hydraulic_Valve_Commands="0"; // see graph in slide 3
     
   
  // PGN : multiple PGNs per Federate are possible. we use the Federate name as a placeholder for now.
    
    
    String ABSPGN = "ABS";
    
    
    // SPNs 
    
    String ABSSPNs =   "0";
    
    
    
    
    
    ///////////////////////////////////////////////////////////////////////
    // TODO Instantiate objects that must be sent every logical time step
    //
    CAN AbsCAN = new CAN();
    //
    ///////////////////////////////////////////////////////////////////////

    public ABS(FederateConfig params) throws Exception {
        super(params);

        ///////////////////////////////////////////////////////////////////////
        // TODO Must register object instances after super(args)
        //
         AbsCAN.registerObject(getLRC());
        //
        ///////////////////////////////////////////////////////////////////////
    }

  
    private void checkReceivedSubscriptions() {

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
    	
	  // we can either calculate it using a moving average of the 4 wheel speeds. 
	  // or we can use the FTP 75 drive cycle data
	  
	  Wheel_Speed   = Integer.toString(ThreadLocalRandom.current().nextInt(50,90 + 1));
  
    } 
    
    
    
   public void Determine_Brake_Effort()
    
    {
    	
	   
	   
	   
    }

   public void Traction_Stability_Torque_Request  ()
    
    {
	   Traction_Stability_Torque_Request = Integer.toString(ThreadLocalRandom.current().nextInt(300,400 + 1));
    }

    
   public void Friction_brake_control()
   
    {
   	
	   
	   
	   
    }


//   public void Traction_Stability_Torque_Request()
//   
//   {
//  	
//	   // ?
//	   
//	   
//   }
//
//    
    
    
    private void execute() throws Exception {
        if(super.isLateJoiner()) 
         
         	
        {
         
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
            
            checkReceivedSubscriptions();
            
            /* Publishing the CAN Frame */
            
     	
           
 		   
 	 	   int osd = (int)currentTime % 10;

    	   switch (osd)
    	   {

    	           
    	   case 0:
    	   
    	  
           
           /* behavior and formulas block */
           
   
           

           // FTP 75 files and variables            
           
           // Cold Temperature (Cold FTP) Driving Cycle : 
           // https://cta.ornl.gov/data/tedbfiles/Spreadsheets/Figure4_06.xls
           
           

           Wheel_Speed ();
          /* Vehicle_Speed =  Wheel_Speed ; */ 
     
          /* ABS_Event_Status = "this is an " + ABSPGN + "  event that comes from "+ VCU_PGN + " depends on VCU_Motor_Torque " + VCU_Motor_Torque; //can be boolean , 0 means OK , 1 means warning      */          
          
          //SPN aggregation : For now We suppose all SPNs have the same PGN, to be modified when the right PGNs are described.
               
           ABSSPNs= Wheel_Speed +" "+ Vehicle_Speed +" "+ ABS_Event_Status+" " + Traction_Stability_Torque_Request;
           
           /* building the CAN frame */
                       
           AbsCAN.set_11BiD(placeholder);
           AbsCAN.set_18BiD(ABSPGN);
           AbsCAN.set_ACKslot(true);
           AbsCAN.set_CRC(placeholder);
           AbsCAN.set_DLC(placeholder);
           AbsCAN.set_DataField(ABSSPNs);
           AbsCAN.set_EndOfFrame(placeholder);
           AbsCAN.set_IDE(placeholder);
           AbsCAN.set_IFS(placeholder);
           AbsCAN.set_RTR(true);
           AbsCAN.set_ReservedBit1(true);
           AbsCAN.set_ReservedBit2(true);
           AbsCAN.set_SRR(true);
           AbsCAN.set_StartOfFrame(true);
    	   
    	   

    	   
    	    	   AbsCAN.updateAttributeValues(getLRC(), currentTime + getLookAhead());

    	           break;
    	           
    	   case 6:
    	           Traction_Stability_Torque_Request(); 
    	           
    	           
    	           ABSSPNs= Wheel_Speed +" "+ Vehicle_Speed +" "+ ABS_Event_Status+" " + Traction_Stability_Torque_Request;
    	           
    	           /* building the CAN frame */
    	                       
    	           AbsCAN.set_11BiD(placeholder);
    	           AbsCAN.set_18BiD(ABSPGN);
    	           AbsCAN.set_ACKslot(true);
    	           AbsCAN.set_CRC(placeholder);
    	           AbsCAN.set_DLC(placeholder);
    	           AbsCAN.set_DataField(ABSSPNs);
    	           AbsCAN.set_EndOfFrame(placeholder);
    	           AbsCAN.set_IDE(placeholder);
    	           AbsCAN.set_IFS(placeholder);
    	           AbsCAN.set_RTR(true);
    	           AbsCAN.set_ReservedBit1(true);
    	           AbsCAN.set_ReservedBit2(true);
    	           AbsCAN.set_SRR(true);
    	           AbsCAN.set_StartOfFrame(true);
    	    	   
    	    	   

    	    	   
    	    	    	   AbsCAN.updateAttributeValues(getLRC(), currentTime + getLookAhead());
    	           
    	           
    	         break;  
    	           
    	   
    	           
    	           
    	   }
    	   
   
        

           
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

        exitGracefully();

        ////////////////////////////////////////////////////////////////////////////////////////
        // TODO Perform whatever cleanups needed before exiting the app
        ////////////////////////////////////////////////////////////////////////////////////////
    }
   
    
  // listening on external messages.  
    
    
    private void handleObjectClass(CAN object) {

    	VCU_Motor_Torque = object.get_DataField();
    	VCU_PGN = object.get_18BiD();
    	
    	
    	
    	
    	
    	
    	
    	String delims = "[ ]+";
    	String[] CSPNs = object.get_DataField().split(delims);
    	

    	
    	        switch (object.get_18BiD()) {
    	            case "VCU":  
    	            
    	            	VCU_Motor_Torque  =   CSPNs[1];
    	  
    	            	ST=object.getTime();
                        sendCondition = true;
    	            	break;


    	        }

    	
    	
    	
    	
    	
    	
    	 String VCU_ABS_Message =  "VCU Torque Available : " + VCU_Motor_Torque + "  message time is " + ST+" & current time is  " + currentTime;
     	 //log.info(VCU_ABS_Message);

    	
    	
    	
    	
    	
    	
    	
    	
    
    
    }
    public static void main(String[] args) {
        try {
            FederateConfigParser federateConfigParser = new FederateConfigParser();
            FederateConfig federateConfig = federateConfigParser.parseArgs(args, FederateConfig.class);
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