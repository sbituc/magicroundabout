package bachelor.project.vehicle;

import java.text.MessageFormat;

/**
 * Fahrzeugfolgemodell implementiert Nagel-Schreckenberg
 */
public class CDrivingModel {

    /**
     * Maximale Geschwindigkeit der Autos in Zellen pro Step
     */
    private final int m_maxSpeed;

    /**
     * Beschleunigung der Autos in Zellen² pro Step
     */
    private final int m_acceleration;

    /**
     * Verzögerung des Autos des Autos in Zellen² pro Step
     */
    private final int m_decelaration;

    /**
     * Konstruktor zum Erzeugen
     *
     * @param p_maxSpeed     übergibt die maximale Geschwindigkeit
     * @param p_accelaration übergibt die Beschleunignung
     * @param p_deceleration übergibt die Verzögerung
     */
    public CDrivingModel(final Integer p_maxSpeed, final Integer p_accelaration, final Integer p_deceleration) {
        m_maxSpeed = p_maxSpeed;
        m_acceleration = p_accelaration;
        m_decelaration = p_deceleration;
    }

    /**
     * Berechnet für das übergebene Auto die aktuell maximale mögliche Geschwindigkeit und setzt sie
     *
     * @param p_vehicle
     */
    public void compute(final CVehicle p_vehicle) {
        p_vehicle.setcurrentSpeed(Math.min(m_maxSpeed, Math.min(p_vehicle.getcurrentSpeed() + m_acceleration, p_vehicle.getDistanceToPredecessor())));
    }

    @Override
    public String toString() {
        return MessageFormat.format("Driving Modell [{0}] mit max. Geschwindigkeit [{1}], Beschleunigung [{2}] und Bremskraft [{3}]", super.toString(), m_maxSpeed, m_acceleration, m_decelaration);
    }

}
