import java.awt.*;
import java.sql.*;


import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        String query = "path of your query here";
        QueryLoader queryLoader = new QueryLoader(query);
        queryLoader.runQuery();

        int sizeX = 1200;
        int sizeY = 400;

        JTable jTable = new JTable(queryLoader.getRows(), queryLoader.getColumns()){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        jTable.setPreferredScrollableViewportSize(new Dimension(sizeX, sizeY));
        jTable.setFillsViewportHeight(true);
        //jTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        JScrollPane jScrollPane = new JScrollPane(jTable);
        jScrollPane.setVisible(true);
        JFrame jFrame = setFrame(sizeX, sizeY);

        jFrame.add(jScrollPane);

    }








    // setup fram
    public static JFrame setFrame(int x, int y) {
        JFrame frame = new JFrame("Query");
        frame.setSize(x, y);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setResizable(false);
        frame.setVisible(true);

        return frame;
    }


}



