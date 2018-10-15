package BMS;

import org.cpswt.config.FederateConfig;
import org.cpswt.config.FederateConfigParser;
import org.cpswt.hla.base.ObjectReflector;

import BMS.BMSConfig;

import org.cpswt.hla.ObjectRoot;
import org.cpswt.hla.base.AdvanceTimeRequest;

import java.util.concurrent.ThreadLocalRandom;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The BMS type of federate for the federation designed in WebGME.
 *
 */
public class BMS extends BMSBase {
	

	
	/* definition & initialization block */
	
	
    private final static Logger log = LogManager.getLogger();

    private double currentTime = 0;
    String placeholder = "0";
    
    public BMSConfig params = new BMSConfig();
    
    
    
    // J1931-71 compliant local Messages
    String Peak_Voltage=params.Peak_Voltage;         // pgn ?  SPN ? 
    String Peak_Current=params.Peak_Current;   // pgn ? SPN ? 
    String State_Of_Charge=params.State_Of_Charge;   // pgn ?    SPN ?
    String State_Of_Health=params.State_Of_Health;  // pgn ?    SPN ? 
    String Remaining_Capacity=params.Remaining_Capacity;  // pgn ?    SPN ? 
    String Max_Temperature=params.Max_Temperature;
    String Min_Temperature=params.Min_Temperature;
    String Peak_Current_Limit=params.Peak_Current_Limit;
   
    
    // J1931-71 compliant external Messages
    // Messages sent/received to/from actuators/sensors
    
    
    // PGN : multiple PGNs per Federate are possible. we use the Federate name as a placeholder for now.
    
    
    String BMSPGN = params.BMSPGN;
    
    // SPNs 
    
    String BMSSPNs = params.BMSSPNs;
    
    
   // String BMSSPNs = Peak_Voltage + Peak_Current + State_Of_Charge + State_Of_Health +  Remaining_Capacity + Max_Temperature +Min_Temperature + Peak_Current_Limit  ;
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    ///////////////////////////////////////////////////////////////////////
    // TODO Instantiate objects that must be sent every logical time step
    //
     CAN BmsCAN = new CAN();
    //
    ///////////////////////////////////////////////////////////////////////

    public BMS(FederateConfig params) throws Exception {
        super(params);

        ///////////////////////////////////////////////////////////////////////
        // TODO Must register object instances after super(args)
        //
        BmsCAN.registerObject(getLRC());
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

    
    
    public void Calculate_power_limits()

    
    {

    	
    	
    	
    	
    	 Peak_Current_Limit =Integer.toString(ThreadLocalRandom.current().nextInt(1,3 + 1));
    	
    	
	}
    
    
    
    public void Build_and_Send_CAN_Frame(String pgn,String spn)
    
    {
   	
        BmsCAN.set_11BiD(placeholder);
        BmsCAN.set_18BiD(pgn);
        BmsCAN.set_ACKslot(true);
        BmsCAN.set_CRC(placeholder);
        BmsCAN.set_DLC(placeholder);
        BmsCAN.set_DataField(spn);
        BmsCAN.set_EndOfFrame(placeholder);
        BmsCAN.set_IDE(placeholder);
        BmsCAN.set_IFS(placeholder);
        BmsCAN.set_RTR(true);
        BmsCAN.set_ReservedBit1(true);
        BmsCAN.set_ReservedBit2(true);
        BmsCAN.set_SRR(true);
        BmsCAN.set_StartOfFrame(true);
        
       BmsCAN.updateAttributeValues(getLRC(), currentTime + getLookAhead());
 	   
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
            // TODO objects that must be sent every logical time step
            //
            
            
            
            /* Listening on the CAN-BUS and processing messages of interest */
            

            checkReceivedSubscriptions();
            
            

            Peak_Voltage="0";         // pgn ?  SPN ? 
            Peak_Current="0";   // pgn ? SPN ? 
            State_Of_Charge="0";   // pgn ?    SPN ?
            State_Of_Health="0";  // pgn ?    SPN ? 
            Remaining_Capacity="0";  // pgn ?    SPN ? 
            Max_Temperature="0";
            Min_Temperature="0";
            
            
            
     	   int osd = (int)currentTime % 10;

    	   switch (osd)
    	   {

    	           
    	       case 2:
    	    	   
    	          
    	            
    	            
    	            /* behavior and formulas block */
    	                 
    	            
    	            
    	            
    	            /* behavior and formulas block */
    	                
    	   
    	    	   
    	    	   Calculate_power_limits();
    	    	   
    	            BMSSPNs = Peak_Voltage  + " " + Peak_Current  + " " +State_Of_Charge  + " " +State_Of_Health  + " " +Remaining_Capacity + " " + Max_Temperature + " " + Min_Temperature  + " " +Peak_Current_Limit;


    	            Build_and_Send_CAN_Frame(BMSPGN,BMSSPNs);
    	 
    	    	 
    	           break;
    	           
    	           
    	   }
    	     
            
           
            
     // the whole frame building stuff should be wrapped in the case function.       
            
            
            
            
            
      	     	 
       	 
//            }
//       	 
//               	
               	
              
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
    	
	
        //////////////////////////////////////////////////////////////////////////
        // TODO implement how to handle reception of the object                 //
        //////////////////////////////////////////////////////////////////////////
    }

    public static void main(String[] args) {
        try {
            FederateConfigParser federateConfigParser = new FederateConfigParser();
            FederateConfig federateConfig = federateConfigParser.parseArgs(args, BMSConfig.class);
            BMS federate = new BMS(federateConfig);
            federate.execute();
            log.info("Done.");
            System.exit(0);
        } catch (Exception e) {
            log.error(e);
            System.exit(1);
        }
    }
}