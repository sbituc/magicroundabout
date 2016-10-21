package bachelor.project.vehicle;

import com.sun.xml.internal.bind.v2.TODO;

import java.util.List;

/*
 * Vehicle class
 */
public class CVehicle {

    /**
     * Vehicle-ID
     */
    private int id;

    /**
     * zu fahrende Route
     */
    private final List m_route; //Fehler, ich weiss...

    /**
     * zu fahrende Route bereits abgefahren?
     */
    private boolean m_finished = false;

    /**
     * Position in der Route
     */
    private int m_position = 0;

    /**
     * aktuelle Geschwindigkeit pro Zellen pro Schritt
     */
    private int m_currentspeed;

    /**
     * Konstruktor des Vehicles
     */
    public CVehicle(final int p_currentSpeed, final List p_route) {
        m_currentspeed = p_currentSpeed;
        m_route = p_route;
    }

    /**
     * Wiedergabe aktuelle Geschwindigkeit
     */
    public int getcurrentSpeed() {
        return m_currentspeed;
    }

    /**
     * setzt die aktuelle Geschwindigkeit fest
     */
    public void setcurrentSpeed(final int p_currentSpeed) {
        m_currentspeed = p_currentSpeed;
    }

    @Override
    /**
     * setzt das Vehicle weiter
     */
    public void step(final int p_timeStep) {
        //TODO...

        // Überprüfung, ob das Auto seine Route in diesm Schritt abgefahren hat
        if ((m_position + m_currentSpeed) >= m_route.size()) {
            m_route.get(m_position).getLeft().setCell(m_route.get(m_position).getRight(), null);
            m_finished = true;
            return;
        }
        // Umesetzen des Autos
        m_route.get(m_position).getLeft().setCell(m_route.get(m_position).getRight(), null);
        m_position = m_position + m_currentSpeed;
        m_route.get(m_position).getLeft().setCell(m_route.get(m_position).getRight(), this);
    }

    @Override
    public Integer getOrder() {
        return m_order;
    }

    /**
     * Liefert die Distanz in Zelle nzum vorherigen Fahrzeug wieder,
     * ist keines vorhanden wird der maximale Integer wert zurückgegeben
     *
     * @return Distanz zum Vorgänger oder Integer.MAXVALUE
     */
    public int getDistanceToPredecessor() {
        for (int l_routeCounter = m_position + 1; l_routeCounter < m_route.size(); l_routeCounter++) {
            if (m_route.get(l_routeCounter).getLeft().getCell(m_route.get(l_routeCounter).getRight()) != null)
                return l_routeCounter - m_position - 1;
        }
        return Integer.MAX_VALUE;
    }

    /**
     * Gibt zurück, ob ein Vehicle seine Route abgefahren hat
     *
     * @return true oder false
     */
    public boolean canRemove() {
        return m_finished;
    }

}
