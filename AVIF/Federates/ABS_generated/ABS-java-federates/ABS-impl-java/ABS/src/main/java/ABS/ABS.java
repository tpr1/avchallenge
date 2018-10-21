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
    

   
    public ABSConfig ABSparameter = new ABSConfig();

    CAN AbsCAN = new CAN();

    public ABS(ABSConfig params) throws Exception {
        super(params);


         AbsCAN.registerObject(getLRC());                  
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
            ABSConfig federateConfig = federateConfigParser.parseArgs(args, ABSConfig.class);
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