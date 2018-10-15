package ABS;

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
public class ABSConfig extends FederateConfig {
    /**
     * ABS Measured Wheel Speed.
     */
    @FederateParameter
    public String Wheel_Speed="35";
    @FederateParameter
    public String ABS_Event_Status="0";   // pgn ?    SPN ?
    // J1931-71 NON-compliant local Messages
    @FederateParameter
    public String Traction_Stability_Torque_Request="0";   
    // J1931-71 compliant external Messages : (ExtrenalEntity_Message)
    @FederateParameter
    public String VCU_Motor_Torque = "0"; 
    @FederateParameter
    public String VCU_PGN = "0";
   // Messages sent/received to/from actuators/sensors
    @FederateParameter
    public String Wheel_Speed_Sensors="0"; // see graph i`n slide 3
    @FederateParameter
    public String Hydraulic_Valve_Commands="0"; // see graph in slide 3
   // PGN : multiple PGNs per Federate are possible. we use the Federate name as a placeholder for now.
    @FederateParameter
    public  String ABSPGN = "ABS";
   // SPNs 
    @FederateParameter
    public String ABSSPNs =   "0";
}
