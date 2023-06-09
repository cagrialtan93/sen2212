import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;

public class GroupedListPage {
    private DefaultComboBoxModel<String> model;
    private JComboBox<String> dropDownMenu = new JComboBox<>();
    private DefaultListModel<String> stringDefaultListModel = new DefaultListModel<>();
    private JList<String> jList = new JList<>(stringDefaultListModel);
    private JPanel jPanel = new JPanel();
    private JFrame frame = new JFrame("Grouped by Genre");
    private JButton jButtonBack = new JButton("Back");
    private JButton jButtonQuit = new JButton("Quit");
    private JPanel jPanelForButtons = new JPanel(new FlowLayout());

    public GroupedListPage(GameStore gameStore, User user, DatabaseConnect databaseConnect) throws SQLException {
        jPanel.setLayout(new BorderLayout());
        jPanelForButtons.add(jButtonBack);
        jButtonBack.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.dispose();
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
        jButtonQuit.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
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

        jPanelForButtons.add(jButtonQuit);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setSize(300, 600);

        for (int i = 0; i < gameStore.getGenreLinkedLists().size(); i++) {
            dropDownMenu.addItem(gameStore.getGenreLinkedLists().get(i).getGenre());
        }

        jList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Game game = null;
                if (e.getClickCount() == 2) {
                    String selectedItem = jList.getSelectedValue();
                    try {
                        game = databaseConnect.checkIfInDatabase(selectedItem);
                        if (game != null){
                            new GameProfile(game, user, databaseConnect);
                        } else {

                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        jPanel.add(dropDownMenu, BorderLayout.NORTH);
        jPanel.add(jList, BorderLayout.CENTER);
        jPanel.add(jPanelForButtons, BorderLayout.SOUTH);
        dropDownMenu.addActionListener(new Action() {
            @Override
            public Object getValue(String key) {
                return null;

            }

            @Override
            public void putValue(String key, Object value) {

            }

            @Override
            public void setEnabled(boolean b) {

            }

            @Override

            public boolean isEnabled() {
                return false;
            }

            @Override
            public void addPropertyChangeListener(PropertyChangeListener listener) {

            }

            @Override
            public void removePropertyChangeListener(PropertyChangeListener listener) {

            }

            @Override
            public void actionPerformed(ActionEvent e) {
                stringDefaultListModel.clear();
                Game treeNode = null;
                String selectedItem = (String) dropDownMenu.getSelectedItem();

                for (GenreLinkedList genreLinkedList :
                        gameStore.getGenreLinkedLists()) {
                    treeNode = genreLinkedList.getHead();
                    if (genreLinkedList.getGenre().equals(selectedItem)) {
                        for (int i = 0; i < genreLinkedList.getSize(); i++) {
                            if (treeNode.getNext() != null){
                                stringDefaultListModel.addElement(treeNode.getTitle());
                                treeNode = treeNode.getNext();
                            }
                        }
                    }
                }
            }
        });

        frame.add(jPanel);
        frame.show();
    }
}
