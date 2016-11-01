package bachelor.project.vehicle;

import bachelor.project.graph.network.IEdge;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/*
 * Vehicle class
 */
public abstract class CVehicle implements IObject {

    /**
     * zu fahrende Route (lanebasiert)
     */
    private final LinkedList<ImmutablePair<IEdge,Integer>> m_route;

    private final List m_cellRoute;
    /**
     * zu fahrende Route bereits abgefahren?
     */
    private boolean m_finished = false;

    /**
     * Position in der Route
     */
    private int m_position = 0;

    /**
     * Maximalgeschwindigkeit des Fahrzeugs
     *
     * 30 mph UK speed limit inside city limits
     * = 48 km/h -> 13.888 m/s (too fast for roundabout)
     * 20 mph -> 30 km/h -> 8.333 m/s is absolute maximum (cell's length is 2m -> 4 cells)
     * for visibility reasons set to "3"
     */
    protected final int m_maxSpeed = 3;

    /**
     * aktuelle Geschwindigkeit pro Zellen pro Schritt
     */
    private int m_currentSpeed;

    /**
     * vehicle's color
     */
    private Color m_color;

    /**
     * vehicle's label
     * "C" for car, "L" for lorry, "B" for bus ...
     */
    private String m_label;

    /**
     * vehicle's length
     */
    private int m_length;


    /**
     * Konstruktor des Vehicles
     */
    public CVehicle(final int p_currentSpeed, final List p_cellRoute, final LinkedList<ImmutablePair<IEdge, Integer>> p_laneRoute, Color p_color) {
        m_currentSpeed = p_currentSpeed;
        m_route = p_laneRoute; //Hash und ZellId
        m_cellRoute = p_cellRoute; //Koordinatentupel
        m_color = p_color;
    }

    /**
     * Liefert die Positionskoordinaten des Fahrzeugs
     * @return (Koordinatenpaar Format WGS84)
     */
    public ArrayList<Double> getPositionCoordinates() {
        return (ArrayList<Double>) this.m_cellRoute.get(m_position);
    }

    /**
     * Wiedergabe aktuelle Geschwindigkeit
     */
    public int getCurrentSpeed() {
        return m_currentSpeed;
    }

    /**
     * setzt die aktuelle Geschwindigkeit fest
     */
    public void setCurrentSpeed(final int p_currentSpeed) {
        m_currentSpeed = p_currentSpeed;
    }


    @Override
    /**
     * setzt das Vehicle weiter
     */
    public void move() {
        this.calculateSpeed();

        // Überprüfung, ob das Fahrzeug seine Route in diesem Schritt abgefahren hat
        if ((m_position + m_currentSpeed) >= m_route.size()) {
            m_route.get(m_position).getLeft().occupyCell(m_route.get(m_position).getRight(), null); //left=IEdge-Objekt und right=Zell-ID
            m_finished = true;
            return;
        }
/*
        // Initiale Positionierung des Fahrzeuges
        // TODO fails when more than 2 vehicles are generated in source
        if ( m_position == 0) {
            if ( m_route.get(m_position).getLeft().isOccupied(m_route.get(m_position).getRight())) {
                // do nothing
                return;
            }
            else {
                m_route.get(m_position).getLeft().occupyCell(m_route.get(m_position).getRight(), this);
            }
        }
*/
        // Umsetzen des Autos
        m_route.get(m_position).getLeft().occupyCell(m_route.get(m_position).getRight(), null);
        m_position = m_position + m_currentSpeed;
        m_route.get(m_position).getLeft().occupyCell(m_route.get(m_position).getRight(), this);
    }

    /**
     * gibt die Anzahl der freien Zellen bis zum vorherigen Fahrzeug wieder,
     * ist keines vorhanden wird der maximale Integerwert zurück gegeben
     *
     * @return Distanz zum Vorgänger oder Integer.MAXVALUE
     */
    public int getEmptyCellsToVehicleInfront() {
        for (int i = m_position + 1; i < m_route.size(); i++) {
            if ( m_route.get(i).getLeft().isOccupied(m_route.get(i).getRight()) ) // True für besetzte Zelle
                return i - m_position - 2;
        }
        return Integer.MAX_VALUE;
    }

    /**
     * Berechnet die aktuell mögliche Geschwindigkeit (in Zellen pro Schritt) des Fahrzeugs in Abhängigkeit der Maximalgeschwindigkeit und des Abstands zum Vorgänger
     */
    public void calculateSpeed() {
        this.setCurrentSpeed( Math.min( m_maxSpeed, Math.min( this.getCurrentSpeed()+1, this.getEmptyCellsToVehicleInfront() ) ) );
    }

    /**
     * Gibt zurück, ob ein Vehicle seine Route abgefahren hat
     *
     * @return true oder false
     */
    public boolean canRemove() {
        return m_finished;
    }

    /**
     *  returns color given to vehicle by the source
     * @return Color value
     */
    public Color getColor() {
        return m_color;
    }

    /**
     * returns vehicle's position on its route
     * @return
     */
    public int getM_position(){
        return m_position;
    }

    /**
     * returns vehicle's label, e.g. for output on marker
     * @return
     */
    public String get_label() { return m_label; }
    public void set_label(String p_label) { m_label = p_label; }

    /**
     * returns vehicle's length
     * @return
     */
    public int get_length() { return m_length; }
    public void set_length(int p_length) { m_length = p_length; }

/*
    @Override
    public String toString() {
        return MessageFormat.format( "Fzg: Farbe: {0} / Geschw: {1} / Route: {2}", m_color, m_currentSpeed, m_route );

    }
*/
}
