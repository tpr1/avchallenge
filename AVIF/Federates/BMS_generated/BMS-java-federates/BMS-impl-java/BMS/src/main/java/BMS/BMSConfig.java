package BMS;

import org.cpswt.config.FederateConfig;
import org.cpswt.config.FederateParameter;

/**
 * An example of how to implement custom configuration options for a federate.
 * 
 * A custom configuration file requires the definition of a class that extends from FederateConfig. Each configuration
 * option must be declared as a public member variable annotated with the FederateParameter annotation.
 * 
 * See {@link InputSource#main(String[])} and {@link InputSource#InputSource(InputSourceConfig)} for how to use the
 * configuration class to initialize a federate.
 */
public class BMSConfig extends FederateConfig {
    /**
     * ABS Measured Wheel Speed.
     */


    
    // J1931-71 compliant local Messages
    @FederateParameter
    public String Peak_Voltage="0";         // pgn ?  SPN ? 
    @FederateParameter
    public String Peak_Current="0";   // pgn ? SPN ? 
    @FederateParameter
    public String State_Of_Charge="0";   // pgn ?    SPN ?
    @FederateParameter
    public String State_Of_Health="0";  // pgn ?    SPN ? 
    @FederateParameter
    public String Remaining_Capacity="0";  // pgn ?    SPN ? 
    @FederateParameter
    public String Max_Temperature="0";
    @FederateParameter
    public String Min_Temperature="0";
    @FederateParameter
    public String Peak_Current_Limit="0";
   
    
    // J1931-71 compliant external Messages
    // Messages sent/received to/from actuators/sensors
    
    
    // PGN : multiple PGNs per Federate are possible. we use the Federate name as a placeholder for now.
    
    @FederateParameter
    public String BMSPGN = "BMS";
    
    // SPNs 
    @FederateParameter
    public String BMSSPNs = "0";
    
    
   // String BMSSPNs = Peak_Voltage + Peak_Current + State_Of_Charge + State_Of_Health +  Remaining_Capacity + Max_Temperature +Min_Temperature + Peak_Current_Limit  ;
    
    
    
}
