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
    
    
    public MCUConfig MCUparameter = new MCUConfig();
    CAN McuCAN = new CAN();
    public MCU(MCUConfig params) throws Exception {
        super(params);

      
        McuCAN.registerObject(getLRC());
        
        MCUparameter.Engine_Speed = params.Engine_Speed ;
        MCUparameter.Engine_Temperature = params.Engine_Temperature ;
        MCUparameter.Motor_Power_Limits = params.Motor_Power_Limits ;
        MCUparameter.Inverter_Temperature = params.Inverter_Temperature ;
        MCUparameter.VCU_Torque_Commands = params.VCU_Torque_Commands ;
        MCUparameter.placeholder = params.placeholder ;
        MCUparameter.Motor_Speed = params.Motor_Speed ;
        MCUparameter.MCUPGN = params.MCUPGN ;
        MCUparameter.MCUSPNs = params.MCUSPNs ;
        MCUparameter.placeholder = params.placeholder ;
        MCUparameter.ST1 = params.ST1 ;
        MCUparameter.ST2 = params.ST2 ;
        

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
    	MCUparameter.Engine_Speed       = Integer.toString(ThreadLocalRandom.current().nextInt(600, 1000 + 1)); 
    }


    public void Calculate_power_limits()
    
    {	
    	MCUparameter.Motor_Power_Limits=Integer.toString(ThreadLocalRandom.current().nextInt(40, 80 + 1));  
    }

    public void Control_Torque()
    
    {	
    	// ?
    }
    
    
    public String Build_SPN()
    
    {  
    	return MCUparameter.MCUSPNs = MCUparameter.Engine_Temperature + " " + MCUparameter.Engine_Speed  + " " +MCUparameter.Motor_Power_Limits  + " " +  MCUparameter.Inverter_Temperature  ;
    	
    }
    
    
    public void AV_Log()
    
    {
    	
    String VCU_Torque_Message =  "VCU_Torque_Commands : " + MCUparameter.VCU_Torque_Commands + " & current time is  " + currentTime + "  message time is " + MCUparameter.ST1 +  " case " + (int)MCUparameter.ST1 % 10 ;
    log.info("VCU_Torque_Commands "+VCU_Torque_Message);
    
    }
    
    
    public void Build_and_Send_CAN_Frame(String pgn,String spn)
    
    { 
    
    
    
    
    McuCAN.set_11BiD(MCUparameter.placeholder);
    McuCAN.set_18BiD(MCUparameter.MCUPGN);
    McuCAN.set_ACKslot(true);
    McuCAN.set_CRC(MCUparameter.placeholder);
    McuCAN.set_DLC(MCUparameter.placeholder);
    McuCAN.set_DataField(MCUparameter.MCUSPNs);
    McuCAN.set_EndOfFrame(MCUparameter.placeholder);
    McuCAN.set_IDE(MCUparameter.placeholder);
    McuCAN.set_IFS(MCUparameter.placeholder);
    McuCAN.set_RTR(true);
    McuCAN.set_ReservedBit1(true);
    McuCAN.set_ReservedBit2(true);
    McuCAN.set_SRR(true);
    McuCAN.set_StartOfFrame(true);
    McuCAN.updateAttributeValues(getLRC(), currentTime + getLookAhead());
    
    
    
    
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
            checkReceivedSubscriptions();
            
     	   int osd = (int)currentTime % 10;

    	   switch (osd)
    	   {

    	           
    	   case 4:
    
            Sense_speed();
            Calculate_power_limits();
            Build_and_Send_CAN_Frame( MCUparameter.MCUPGN, Build_SPN());
            
            break;
            
            
    	   case 9:
    		   
    		   checkReceivedSubscriptions();
    		   AV_Log();
    		   Control_Torque();
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
            case "VCU":
            	MCUparameter.VCU_Torque_Commands      = CSPNs[6];
            	MCUparameter.ST1=object.getTime();
            	MCUparameter.ST2=object.get_DataField_time();
                break;   
                
                
            
        }
    }

    public static void main(String[] args) {
        try {
            FederateConfigParser federateConfigParser = new FederateConfigParser();
            MCUConfig federateConfig = federateConfigParser.parseArgs(args, MCUConfig.class);
            MCU federate = new MCU(federateConfig);
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
