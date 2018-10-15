package VCU;

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
public class VCUConfig extends FederateConfig {
    /**
     * The number of operands to generate for the adder.
     */

    @FederateParameter    
    public String placeholder = "0";
    @FederateParameter
    public double ST1 = 0;
    @FederateParameter
    public double ST2 = 0;
    @FederateParameter
    public double ST3 = 0;
    @FederateParameter
    public double ST4 = 0;
    @FederateParameter
    public double ST5 = 0;
    @FederateParameter
    public double ST6 = 0;
    @FederateParameter
    public double ST7 = 0;
    @FederateParameter
    public double ST8 = 0;
    @FederateParameter
    public double ST9 = 0;
    @FederateParameter
    public double ST10 = 0;
    @FederateParameter
    public boolean sendCondition = false;

     // J1931-71 compliant local Messages
    @FederateParameter
    public String Motor_Operating_Mode="0"; // pgn ? SPN ?
    @FederateParameter
    public String Motor_Torque="0"; // pgn ? SPN ?
    @FederateParameter
    public String Motor_Speed="0"; // pgn ? SPN ?
    @FederateParameter
    public String Volt_Cmd="0"; // pgn ? SPN ?
    @FederateParameter
    public String Contactor_Override_Commands="0"; // pgn ? SPN ?
    @FederateParameter
    public String Battery_Commands="0"; // pgn ? SPN ?
    @FederateParameter
    public String Torque_Commands="0"; // pgn ? SPN ?
    @FederateParameter
    public String InverterCoolingAndHeatingCommands="0";    // pgn ? SPN ?
    
    
    // initialize parameters
    

    
    // J1931-71 compliant external Messages : (ExtrenalEntity_Message)  
    @FederateParameter
    public String MCU_Motor_Temperature = "0";
    @FederateParameter
    public String MCU_Motor_Speed = "0";
    @FederateParameter
    public String MCU_Motor_Power_Limits = "0";
    @FederateParameter
    public String ABS_Wheel_Speed = "0";
    @FederateParameter
    public String BMS_Peak_Current = "0";
    @FederateParameter
    public String VCU_Torque_Request = "0";
    
    // J1931-71 NON compliant external Messages : (ExtrenalEntity_Message)  
    @FederateParameter
    public String ABS_Braking_Torque_split = "0";
    @FederateParameter
    public String ABS_traction_Stability_torque_request = "0";
    
    // Messages sent/received to/from actuators/sensors
    @FederateParameter
    public String Accel_Pedal_Position="0";
    @FederateParameter
    public String Brake_Pressure="0";
    



// PGN : multiple PGNs per Federate are possible. we use the Federate name as a placeholder for now.

    @FederateParameter
    public String VCUPGN = "VCU";


// SPNs 
    @FederateParameter
    public String VCUSPNs = "0";

}
