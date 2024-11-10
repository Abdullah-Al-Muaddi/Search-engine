import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI {

    private QueryProcessor queryProcessor;

    public GUI(QueryProcessor queryProcessor) {
        this.queryProcessor = queryProcessor;
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        // Create the main window (frame)
        JFrame frame = new JFrame("Simple Search Engine");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 200);

        // Create components
        JLabel label = new JLabel("Enter your query:");
        JTextField queryField = new JTextField(30);
        JButton booleanButton = new JButton("Boolean Retrieval (AND, OR)");
        JButton rankedButton = new JButton("Ranked Retrieval");

        // Create panel and add components
        JPanel panel = new JPanel();
        panel.add(label);
        panel.add(queryField);
        panel.add(booleanButton);
        panel.add(rankedButton);

        // Add panel to frame
        frame.getContentPane().add(panel);

        // Action listener for Boolean Retrieval button
        booleanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Query = queryField.getText();
                LinkedList<String> Results = queryProcessor.handleQuery(Query);
                if (Results != null && !Results.empty()) {
                    JOptionPane.showMessageDialog(frame, "Query Results: " + Results.toString());
                } else {
                    JOptionPane.showMessageDialog(frame, "No results found.");
                }
            }
        });

        // Action listener for Ranked Retrieval button
        rankedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String rankedQuery = queryField.getText();
                LinkedList<DocumentScore> rankedResults = queryProcessor.rankedQuery(rankedQuery);
                if (rankedResults != null && !rankedResults.empty()) {
                    StringBuilder resultString = new StringBuilder();
                    rankedResults.findFirst();
                    while (true) {
                        resultString.append(rankedResults.retrieve().toString()).append("\n");
                        if (rankedResults.last()) {
                            break;
                        }
                        rankedResults.findNext();
                    }
                    JOptionPane.showMessageDialog(frame, "Ranked Retrieval Results:\n" + resultString.toString());
                } else {
                    JOptionPane.showMessageDialog(frame, "No results found.");
                }
            }
        });

        // Display the window
        frame.setVisible(true);
    }
}
