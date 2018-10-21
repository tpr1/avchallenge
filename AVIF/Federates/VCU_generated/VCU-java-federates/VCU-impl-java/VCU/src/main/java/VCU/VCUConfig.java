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
    public String placeholder ;
    @FederateParameter
    public double ST1 ;
    @FederateParameter
    public double ST2 ;
    @FederateParameter
    public double ST3 ;
    @FederateParameter
    public double ST4 ;
    @FederateParameter
    public double ST5 ;
    @FederateParameter
    public double ST6 ;
    @FederateParameter
    public double ST7 ;
    @FederateParameter
    public double ST8 ;
    @FederateParameter
    public double ST9 ;
    @FederateParameter
    public double ST10 ;
    // J1931-71 compliant local Messages
    @FederateParameter
    public String Motor_Operating_Mode; // pgn ? SPN ?
    @FederateParameter
    public String Motor_Torque; // pgn ? SPN ?
    @FederateParameter
    public String Motor_Speed; // pgn ? SPN ?
    @FederateParameter
    public String Volt_Cmd; // pgn ? SPN ?
    @FederateParameter
    public String Contactor_Override_Commands; // pgn ? SPN ?
    @FederateParameter
    public String Battery_Commands; // pgn ? SPN ?
    @FederateParameter
    public String Torque_Commands; // pgn ? SPN ?
    @FederateParameter
    public String InverterCoolingAndHeatingCommands;    // pgn ? SPN ?
    // initialize parameters
    // J1931-71 compliant external Messages : (ExtrenalEntity_Message)  
    @FederateParameter
    public String MCU_Motor_Temperature ;
    @FederateParameter
    public String MCU_Motor_Speed ;
    @FederateParameter
    public String MCU_Motor_Power_Limits ;
    @FederateParameter
    public String ABS_Wheel_Speed ;
    @FederateParameter
    public String BMS_Peak_Current ;
    @FederateParameter
    public String VCU_Torque_Request ;
    // J1931-71 NON compliant external Messages : (ExtrenalEntity_Message)  
    @FederateParameter
    public String ABS_Braking_Torque_split ;
    @FederateParameter
    public String ABS_traction_Stability_torque_request ;
    // Messages sent/received to/from actuators/sensors
    @FederateParameter
    public String Accel_Pedal_Position;
    @FederateParameter
    public String Brake_Pressure;
// PGN : multiple PGNs per Federate are possible. we use the Federate name as a placeholder for now.
    @FederateParameter
    public String VCUPGN;
// SPNs 
    @FederateParameter
    public String VCUSPNs ;

}
