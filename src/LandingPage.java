import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;

public class LandingPage {
    private GameStore gameStore;
    private Game[] games;
    private DatabaseConnect databaseConnect = new DatabaseConnect();
    private DefaultListModel<Game> gameDefaultListModel = new DefaultListModel<>();
    private DefaultListModel<String> stringDefaultListModel = new DefaultListModel<>();
    private BinarySearchTree binarySearchTree = databaseConnect.getGames();

    public LandingPage(GameStore gameStore) throws SQLException {
        this.gameStore = gameStore;

        JFrame jFrame = new JFrame("Online Game Store");
        JScrollPane jScrollPane = new JScrollPane();

        JList<Game> jList = new JList<>(binarySearchTree.inOrder(binarySearchTree.getRoot(), gameDefaultListModel)); // TODO get this properly

        for (int i = 0; i < gameDefaultListModel.size(); i++) {
            stringDefaultListModel.addElement(gameDefaultListModel.get(i).getTitle());
        }
        JList<String> stringJList = new JList<>(stringDefaultListModel);
        stringJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        stringJList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()){
                    System.out.println();
                }
            }
        });

        stringJList.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // Double-click
                    JList source = (JList) e.getSource();
                    int selectedIndex = source.locationToIndex(e.getPoint());
                    if (selectedIndex >= 0) {
                        String selectedValue = (String) source.getModel().getElementAt(selectedIndex);

                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        jList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                try {
                    if (!e.getValueIsAdjusting()) {
                        System.out.println(jList.getSelectedValue().getTitle());
                    }
                } catch (Exception error) {
                    System.out.println("Nothing has selected.");
                }
            }
        });

        jFrame.add(stringJList);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setLocationRelativeTo(null);
        jFrame.setSize(400, 400);
        jFrame.show();

    }
}