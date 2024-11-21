import java.awt.*;
import javax.swing.*;

public class GUI {

    private QueryProcessor queryProcessor;

    public GUI(QueryProcessor queryProcessor) {
        this.queryProcessor = queryProcessor;
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        // ==================================> Frame <==================================
        JFrame frame = new JFrame("Search Engine");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(500, 250));

        // ==================================> Components <==================================
        JLabel label = new JLabel("Enter your query:");
        JTextField queryField = new JTextField();
        JButton booleanButton = new JButton("Boolean Retrieval (AND, OR)");
        JButton rankedButton = new JButton("Ranked Retrieval");
        JLabel outputLabel = new JLabel("Output:");
        JTextArea outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        // ==================================> Panels <==================================
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        Insets insets = new Insets(5, 5, 5, 5);
        gbc.insets = insets;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        inputPanel.add(label, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        inputPanel.add(queryField, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.add(booleanButton);
        buttonPanel.add(rankedButton);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        inputPanel.add(buttonPanel, gbc);

        JPanel outputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbcOutput = new GridBagConstraints();
        gbcOutput.insets = insets;

        gbcOutput.gridx = 0;
        gbcOutput.gridy = 0;
        gbcOutput.anchor = GridBagConstraints.WEST;
        outputPanel.add(outputLabel, gbcOutput);

        gbcOutput.gridy = 1;
        gbcOutput.fill = GridBagConstraints.BOTH;
        gbcOutput.weightx = 1.0;
        gbcOutput.weighty = 1.0;
        outputPanel.add(scrollPane, gbcOutput);

        // ==================================> Main Panel <==================================
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(outputPanel, BorderLayout.CENTER);

        frame.getContentPane().add(mainPanel);

        // ==================================> Buttons <==================================
        // ~~~~~~~~~~~~~~~~~~~~~~~~~~> Boolean Retrieval Button <~~~~~~~~~~~~~~~~~~~~~~~~~~
        booleanButton.addActionListener(e -> {
            String query = queryField.getText();
            LinkedList<String> results = queryProcessor.handleQuery(query);
            if (results != null && !results.empty()) {
                outputArea.setText("Query Results: { " + results.toString()+" }");
            } else {
                outputArea.setText("No results found.");
            }
        });

        // ~~~~~~~~~~~~~~~~~~~~~~~~~~> Ranked Retrieval Button <~~~~~~~~~~~~~~~~~~~~~~~~~~
        rankedButton.addActionListener(e -> {
            String rankedQuery = queryField.getText();
            LinkedList<DocumentScore> rankedResults = queryProcessor.rankedQuery(rankedQuery);
            if (rankedResults != null && !rankedResults.empty()) {
                StringBuilder resultString = new StringBuilder("Ranked Retrieval Results:\n");
                rankedResults.findFirst();
                resultString.append("DocID\tScore\n");
                while (true) {
                    resultString.append(rankedResults.retrieve().toString()).append("\n");
                    if (rankedResults.last()) {
                        break;
                    }
                    rankedResults.findNext();
                }
                outputArea.setText(resultString.toString());
            } else {
                outputArea.setText("No results found.");
            }
        });

        frame.setVisible(true);
    }
}
