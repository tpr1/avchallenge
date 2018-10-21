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


    
    public BMSConfig BMSparameter = new BMSConfig();
    
     CAN BmsCAN = new CAN();

    public BMS(BMSConfig params) throws Exception {
        super(params);

        BmsCAN.registerObject(getLRC());
        
        BMSparameter.Peak_Voltage = params.Peak_Voltage;
        BMSparameter.Peak_Current = params.Peak_Current;
        BMSparameter.State_Of_Charge = params.State_Of_Charge;
        BMSparameter.State_Of_Health = params.State_Of_Health;
        BMSparameter.Remaining_Capacity = params.Remaining_Capacity;
        BMSparameter.Max_Temperature = params.Max_Temperature;
        BMSparameter.Min_Temperature = params.Min_Temperature;
        BMSparameter.Peak_Current_Limit = params.Peak_Current_Limit;
        BMSparameter.BMSPGN = params.BMSPGN;
        BMSparameter.BMSSPNs = params.BMSSPNs;
        BMSparameter.placeholder = params.placeholder;

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

    	
    	
    	
    	
    	BMSparameter.Peak_Current_Limit =Integer.toString(ThreadLocalRandom.current().nextInt(1,3 + 1));
    	
    	
	}
    
    public String Build_SPN(){    	    	   
    	return   BMSparameter.BMSSPNs = BMSparameter.Peak_Voltage  + " " + BMSparameter.Peak_Current  + " " +BMSparameter.State_Of_Charge  + " " +BMSparameter.State_Of_Health  + " " +BMSparameter.Remaining_Capacity + " " + BMSparameter.Max_Temperature + " " + BMSparameter.Min_Temperature  + " " +BMSparameter.Peak_Current_Limit;
        } 
    
    public void Build_and_Send_CAN_Frame(String pgn,String spn)
    
    {
   	
        BmsCAN.set_11BiD(BMSparameter.placeholder);
        BmsCAN.set_18BiD(pgn);
        BmsCAN.set_ACKslot(true);
        BmsCAN.set_CRC(BMSparameter.placeholder);
        BmsCAN.set_DLC(BMSparameter.placeholder);
        BmsCAN.set_DataField(spn);
        BmsCAN.set_EndOfFrame(BMSparameter.placeholder);
        BmsCAN.set_IDE(BMSparameter.placeholder);
        BmsCAN.set_IFS(BMSparameter.placeholder);
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
            checkReceivedSubscriptions();
            
     	   int osd = (int)currentTime % 10;
    	   switch (osd)
    	   {
    	       case 2:	   
    	    	   Calculate_power_limits();
    	    	   Build_and_Send_CAN_Frame( BMSparameter.BMSPGN, Build_SPN());  
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
    	
	
    }

    public static void main(String[] args) {
        try {
            FederateConfigParser federateConfigParser = new FederateConfigParser();
            BMSConfig federateConfig = federateConfigParser.parseArgs(args, BMSConfig.class);
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