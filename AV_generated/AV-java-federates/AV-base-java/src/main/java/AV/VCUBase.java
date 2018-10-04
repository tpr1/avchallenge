package AV;

import hla.rti.EventRetractionHandle;
import hla.rti.LogicalTime;
import hla.rti.ReceivedInteraction;

import org.cpswt.hla.C2WInteractionRoot;
import org.cpswt.hla.InteractionRoot;
import org.cpswt.hla.SubscribedInteractionFilter;
import org.cpswt.hla.SynchronizedFederate;

import org.cpswt.config.FederateConfig;
import org.cpswt.utils.CpswtDefaults;

import org.cpswt.*;


public class VCUBase extends SynchronizedFederate {

	private SubscribedInteractionFilter _subscribedInteractionFilter = new SubscribedInteractionFilter();
	
	// constructor
	public VCUBase(FederateConfig config) throws Exception {
		super(config);

		super.createLRC();
		super.joinFederation();

		enableTimeConstrained();

		enableTimeRegulation(getLookAhead());
		enableAsynchronousDelivery();
        // interaction pubsub
        
        		
		// object pubsub
        
        	
        CAN.publish_11BiD();
        CAN.publish_18BiD();
        CAN.publish_ACKslot();
        CAN.publish_CRC();
        CAN.publish_DLC();
        CAN.publish_DataField();
        CAN.publish_EndOfFrame();
        CAN.publish_IDE();
        CAN.publish_IFS();
        CAN.publish_RTR();
        CAN.publish_ReservedBit1();
        CAN.publish_ReservedBit2();
        CAN.publish_SRR();
        CAN.publish_StartOfFrame();
        CAN.publish(getLRC());
                
        	
        CAN.subscribe_11BiD();
        CAN.subscribe_18BiD();
        CAN.subscribe_ACKslot();
        CAN.subscribe_CRC();
        CAN.subscribe_DLC();
        CAN.subscribe_DataField();
        CAN.subscribe_EndOfFrame();
        CAN.subscribe_IDE();
        CAN.subscribe_IFS();
        CAN.subscribe_RTR();
        CAN.subscribe_ReservedBit1();
        CAN.subscribe_ReservedBit2();
        CAN.subscribe_SRR();
        CAN.subscribe_StartOfFrame();
        CAN.subscribe(getLRC());
        	}
        
	
	@Override
	public void receiveInteraction(
	 int interactionClass, ReceivedInteraction theInteraction, byte[] userSuppliedTag
	) {
		InteractionRoot interactionRoot = InteractionRoot.create_interaction( interactionClass, theInteraction );
		if ( interactionRoot instanceof C2WInteractionRoot ) {
			
			C2WInteractionRoot c2wInteractionRoot = (C2WInteractionRoot)interactionRoot;

	        // Filter interaction if src/origin fed requirements (if any) are not met
	        if (  _subscribedInteractionFilter.filterC2WInteraction( getFederateId(), c2wInteractionRoot )  ) {
	        	return;
	        } 
		}
		
		super.receiveInteraction( interactionClass, theInteraction, userSuppliedTag );			
	}

	@Override
	public void receiveInteraction(
	 int interactionClass,
	 ReceivedInteraction theInteraction,
	 byte[] userSuppliedTag,
	 LogicalTime theTime,
	 EventRetractionHandle retractionHandle
	) {
		InteractionRoot interactionRoot = InteractionRoot.create_interaction( interactionClass, theInteraction, theTime );
		if ( interactionRoot instanceof C2WInteractionRoot ) {

			C2WInteractionRoot c2wInteractionRoot = (C2WInteractionRoot)interactionRoot;

	        // Filter interaction if src/origin fed requirements (if any) are not met
	        if (  _subscribedInteractionFilter.filterC2WInteraction( getFederateId(), c2wInteractionRoot )  ) {
	        	return;
	        } 
		}

		super.receiveInteraction( interactionClass, theInteraction, userSuppliedTag, theTime, retractionHandle );			
	}
}
