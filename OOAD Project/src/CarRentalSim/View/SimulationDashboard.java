package CarRentalSim.View;

import CarRentalSim.Simulator;
import CarRentalSim.Store.Reports.DailyReport;
import CarRentalSim.Store.Reports.FinalReport;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Map;

/**
 * Simple Swing UI for running the simulation and viewing summarized output.
 */
public class SimulationDashboard extends JFrame implements StoreView {
    private final JTextArea outputArea;
    private final JLabel statusLabel;
    private final JButton runButton;
    private final JButton clearButton;

    public SimulationDashboard() {
        super("Rent-A-Ride Simulator");

        this.outputArea = new JTextArea();
        this.statusLabel = new JLabel("Ready");
        this.runButton = new JButton("Run Simulation");
        this.clearButton = new JButton("Clear Output");

        this.outputArea.setEditable(false);
        this.outputArea.setLineWrap(true);
        this.outputArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(this.outputArea);
        scrollPane.setPreferredSize(new Dimension(920, 560));

        JPanel controls = new JPanel(new FlowLayout(FlowLayout.LEFT));
        controls.add(this.runButton);
        controls.add(this.clearButton);

        JPanel footer = new JPanel(new FlowLayout(FlowLayout.LEFT));
        footer.add(this.statusLabel);

        this.setLayout(new BorderLayout(8, 8));
        this.add(controls, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);
        this.add(footer, BorderLayout.SOUTH);

        this.runButton.addActionListener(e -> this.runSimulation());
        this.clearButton.addActionListener(e -> this.outputArea.setText(""));

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
    }

    private void runSimulation() {
        this.runButton.setEnabled(false);
        this.statusLabel.setText("Running simulation...");
        this.outputArea.append("Starting simulation...\n\n");

        Thread simulationThread = new Thread(() -> {
            Simulator simulator = new Simulator(this);
            simulator.runSimulation();

            SwingUtilities.invokeLater(() -> {
                this.statusLabel.setText("Simulation finished");
                this.runButton.setEnabled(true);
                this.outputArea.append("\nSimulation completed.\n");
            });
        });

        simulationThread.setName("simulation-runner");
        simulationThread.start();
    }

    @Override
    public void renderDailyReport(DailyReport report) {
        String message = String.format(
                "Day %d | Completed rentals: %d | Active rentals: %d | Cars left: %d | Daily revenue: %.2f\n",
                report.getDayNumber(),
                report.getCompletedRentals().size(),
                report.getActiveRentals().size(),
                report.getCarsLeft().size(),
                report.getDailyRevenue()
        );

        SwingUtilities.invokeLater(() -> this.outputArea.append(message));
    }

    @Override
    public void renderFinalReport(FinalReport report) {
        StringBuilder builder = new StringBuilder();
        builder.append("\n================ FINAL REPORT ================\n");
        builder.append(String.format("Final day: %d\n", report.getDayNumber()));
        builder.append(String.format("Total rentals: %d\n", report.getTotalRentals()));
        builder.append("Customer type distribution:\n");

        for (Map.Entry<String, Integer> entry : report.getCustomerTypeCounts().entrySet()) {
            builder.append(String.format("  - %s: %d\n", entry.getKey(), entry.getValue()));
        }

        builder.append(String.format("Total revenue: %.2f\n", report.getTotalRevenue()));
        builder.append("==============================================\n");

        SwingUtilities.invokeLater(() -> this.outputArea.append(builder.toString()));
    }
}
