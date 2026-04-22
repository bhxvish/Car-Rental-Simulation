package CarRentalSim;

import CarRentalSim.View.SimulationDashboard;

import javax.swing.SwingUtilities;

/**
 * UI launcher for the simulation dashboard.
 */
public class SimulationUIApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SimulationDashboard dashboard = new SimulationDashboard();
            dashboard.setVisible(true);
        });
    }
}
