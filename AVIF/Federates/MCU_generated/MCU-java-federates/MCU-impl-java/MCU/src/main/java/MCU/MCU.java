package MCU;

import org.cpswt.config.FederateConfig;
import org.cpswt.config.FederateConfigParser;
import org.cpswt.hla.base.ObjectReflector;

import MCU.MCUConfig;

import org.cpswt.hla.ObjectRoot;
import org.cpswt.hla.base.AdvanceTimeRequest;

import java.util.concurrent.ThreadLocalRandom;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The MCU type of federate for the federation designed in WebGME.
 *
 */
public class MCU extends MCUBase {

	
	/* definition & initialization block */
	
	
    private final static Logger log = LogManager.getLogger();

    private double currentTime = 0;
    String placeholder = "0";
    public MCUConfig params = new MCUConfig();
    
  //  public boolean sendCondition = false;
    
    // J1931-71 compliant local Messages
    
     String Engine_Speed=params.Engine_Speed;         // pgn61444 SPN190
     String Engine_Temperature=params.Engine_Temperature;   // pgn65262 SPN110
     String Motor_Power_Limits=params.Motor_Power_Limits;   // pgn ?    SPN ?
     String Inverter_Temperature=params.Inverter_Temperature; // pgn ?    SPN ? 
     
     // J1931-71 compliant external Messages
    
     
     
     String  VCU_Torque_Commands = params.VCU_Torque_Commands;
     
     
    // Messages sent/received to/from actuators/sensors
     
     
     String Motor_Speed=params.Motor_Speed; // see graph in slide 3
     
    // PGN : multiple PGNs per Federate are possible. we use the Federate name as a placeholder for now.
     
     
     String MCUPGN = params.MCUPGN;
     
     
     // SPNs aggregation : We suppose all SPNs have the same PGN so far, to be modified when the right PGNs are described.
     
     
     String MCUSPNs = params.MCUSPNs;

    ///////////////////////////////////////////////////////////////////////
    // TODO Instantiate objects that must be sent every logical time step
    //
     CAN McuCAN = new CAN();
    //
    ///////////////////////////////////////////////////////////////////////

    public MCU(FederateConfig params) throws Exception {
        super(params);

        ///////////////////////////////////////////////////////////////////////
        // TODO Must register object instances after super(args)
        //
        McuCAN.registerObject(getLRC());
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

    
    
    
    public void Sense_speed()

    {
        Engine_Speed       = Integer.toString(ThreadLocalRandom.current().nextInt(600, 1000 + 1)); 
    }


    public void Calculate_power_limits()
    
    {	
    	Motor_Power_Limits=Integer.toString(ThreadLocalRandom.current().nextInt(40, 80 + 1));  
    }

    public void Control_Torque()
    
    {	
    	// ?
    }
    
    
    
    
    
    public void Build_and_Send_CAN_Frame(String pgn,String spn)
    
    { 
    
    
    
    
    McuCAN.set_11BiD(placeholder);
    McuCAN.set_18BiD(MCUPGN);
    McuCAN.set_ACKslot(true);
    McuCAN.set_CRC(placeholder);
    McuCAN.set_DLC(placeholder);
    McuCAN.set_DataField(MCUSPNs);
    McuCAN.set_EndOfFrame(placeholder);
    McuCAN.set_IDE(placeholder);
    McuCAN.set_IFS(placeholder);
    McuCAN.set_RTR(true);
    McuCAN.set_ReservedBit1(true);
    McuCAN.set_ReservedBit2(true);
    McuCAN.set_SRR(true);
    McuCAN.set_StartOfFrame(true);
    
    
   // if(sendCondition){
    McuCAN.updateAttributeValues(getLRC(), currentTime + getLookAhead());
    
    
    
    
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
            
            
            
            /* Listening on the CAN-BUS and processing messages of interest */
            
        checkReceivedSubscriptions();
            
            
            /* behavior and formulas block */
            
            
            // https://www.bluestar.com/get_informed/article/your-engines-cooling-system
            
            //https://en.wikipedia.org/wiki/Idle_speed
            
            
            
            
            
            
            
     	   int osd = (int)currentTime % 10;

    	   switch (osd)
    	   {

    	           
    	   case 4:
    
            Sense_speed();
            Calculate_power_limits();

            MCUSPNs = Engine_Temperature + " " + Engine_Speed  + " " +Motor_Power_Limits  + " " +  Inverter_Temperature  ; 
            
            /* Building the CAN Frame */
            

            Build_and_Send_CAN_Frame(MCUPGN,MCUSPNs);
            
            break;
            
            
    	   case 9:
    		   checkReceivedSubscriptions();
    		   Control_Torque();
    		   break;
            
//            
//    	   case 5:
//          //  https://hypertextbook.com/facts/2001/JaeheeJoh.shtml
//    		 
////            Engine_Temperature = Integer.toString(ThreadLocalRandom.current().nextInt(195,219 + 1));   
////            
////            Inverter_Temperature="0"; 
////            
////            
//            
//            
//            //SPN aggregation : For now We suppose all SPNs coming from a single Federate have the same PGN, to be modified when the right PGNs are described.
//            
//          
//            
//           
//            
//            break;

    	   }
    	   
    	   
    	   
    	   
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
            case "VCU":
            	VCU_Torque_Commands      = CSPNs[6];
                break;  
                 
            
        }


       // log.info(MCUMessage);
    	
    	
    	
        //////////////////////////////////////////////////////////////////////////
        // TODO implement how to handle reception of the object                 //
        //////////////////////////////////////////////////////////////////////////
    }

    public static void main(String[] args) {
        try {
            FederateConfigParser federateConfigParser = new FederateConfigParser();
            FederateConfig federateConfig = federateConfigParser.parseArgs(args, MCUConfig.class);
            MCU federate = new MCU(federateConfig);
            federate.execute();
            log.info("Done.");
            System.exit(0);
        } catch (Exception e) {
            log.error(e);
            System.exit(1);
        }
    }
}
