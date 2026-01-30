package cedovv;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author студент
 */
public class Cedovv extends JFrame {

    private JTable table;
    private DefaultTableModel model;
    private JComboBox<String> cbDays, cbPeriod, cbSubject, cbTeacher;

    public Cedovv() {
        initComponents();
    }
    
    private void initComponents() {
        setTitle("Генератор расписания - Time Table Generator");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        // Создаем главную панель
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Заголовок
        JLabel heading = new JLabel("Генератор недельного расписания", JLabel.CENTER);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 24));
        heading.setForeground(new Color(0, 102, 204));
        mainPanel.add(heading, BorderLayout.NORTH);

        // Таблица
        String[] columnNames = {"День", "Период 1", "Период 2", "Период 3", "Период 4", 
                                "Период 5", "Период 6", "Период 7"};
        String[][] data = {
            {"Понедельник", "", "", "", "", "", "", ""},
            {"Вторник", "", "", "", "", "", "", ""},
            {"Среда", "", "", "", "", "", "", ""},
            {"Четверг", "", "", "", "", "", "", ""},
            {"Пятница", "", "", "", "", "", "", ""}
        };

        model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Таблица не редактируется напрямую
            }
        };
        
        table = new JTable(model);
        table.setRowHeight(40);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Расписание"));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Панель с выбором параметров
        JPanel controlPanel = new JPanel(new GridLayout(2, 4, 15, 15));
        controlPanel.setBorder(BorderFactory.createTitledBorder("Выбор параметров"));
        controlPanel.setBackground(new Color(240, 248, 255));

        String[] days = {"Понедельник", "Вторник", "Среда", "Четверг", "Пятница"};
        String[] periods = {"1", "2", "3", "4", "5", "6", "7"};
        String[] subjects = {"Математика", "Наука", "История", "Английский", "Информатика"};
        String[] teachers = {"Мистер А", "Мисс Б", "Мистер С", "Мисс Д"};

        cbDays = new JComboBox<>(days);
        cbPeriod = new JComboBox<>(periods);
        cbSubject = new JComboBox<>(subjects);
        cbTeacher = new JComboBox<>(teachers);
        
        // Настройка шрифтов для комбобоксов
        Font comboFont = new Font("Segoe UI", Font.PLAIN, 14);
        cbDays.setFont(comboFont);
        cbPeriod.setFont(comboFont);
        cbSubject.setFont(comboFont);
        cbTeacher.setFont(comboFont);

        controlPanel.add(new JLabel("Выберите день:"));
        controlPanel.add(cbDays);
        controlPanel.add(new JLabel("Выберите период:"));
        controlPanel.add(cbPeriod);
        controlPanel.add(new JLabel("Выберите предмет:"));
        controlPanel.add(cbSubject);
        controlPanel.add(new JLabel("Выберите учителя:"));
        controlPanel.add(cbTeacher);

        // Панель с кнопками
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        
        JButton btnAssign = new JButton("Назначить");
        btnAssign.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnAssign.setBackground(new Color(0, 102, 204));
        btnAssign.setForeground(Color.WHITE);
        btnAssign.setPreferredSize(new Dimension(120, 35));
        btnAssign.addActionListener(e -> assignSubject());
        
        JButton btnClear = new JButton("Очистить");
        btnClear.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnClear.setPreferredSize(new Dimension(120, 35));
        btnClear.addActionListener(e -> clearTable());
        
        JButton btnExit = new JButton("Выход");
        btnExit.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnExit.setPreferredSize(new Dimension(120, 35));
        btnExit.addActionListener(e -> System.exit(0));
        
        buttonPanel.add(btnAssign);
        buttonPanel.add(btnClear);
        buttonPanel.add(btnExit);

        // Панель для нижней части
        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(controlPanel, BorderLayout.NORTH);
        southPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        mainPanel.add(southPanel, BorderLayout.SOUTH);

        // Добавляем горячие клавиши
        setupKeyBindings();

        add(mainPanel);
        pack();
    }

    private void assignSubject() {
        int dayIndex = cbDays.getSelectedIndex();
        int periodIndex = cbPeriod.getSelectedIndex() + 1;

        String subject = cbSubject.getSelectedItem().toString();
        String teacher = cbTeacher.getSelectedItem().toString();

        model.setValueAt(subject + " (" + teacher + ")", dayIndex, periodIndex);
        
        // Оповещение об успешном назначении
        JOptionPane.showMessageDialog(this, 
            "Предмет назначен успешно!\n" +
            "День: " + cbDays.getSelectedItem() + "\n" +
            "Период: " + cbPeriod.getSelectedItem() + "\n" +
            "Предмет: " + subject + "\n" +
            "Учитель: " + teacher,
            "Успех", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void clearTable() {
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Вы уверены, что хотите очистить все расписание?", 
            "Подтверждение очистки", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            for (int i = 0; i < model.getRowCount(); i++) {
                for (int j = 1; j < model.getColumnCount(); j++) {
                    model.setValueAt("", i, j);
                }
            }
            JOptionPane.showMessageDialog(this, "Расписание очищено!");
        }
    }
    
    private void setupKeyBindings() {
        // Горячая клавиша Enter для назначения
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
            .put(KeyStroke.getKeyStroke("ENTER"), "assign");
        getRootPane().getActionMap().put("assign", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                assignSubject();
            }
        });
        
        // Горячая клавиша ESC для выхода
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
            .put(KeyStroke.getKeyStroke("ESCAPE"), "exit");
        getRootPane().getActionMap().put("exit", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
        // Ctrl+C для очистки
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
            .put(KeyStroke.getKeyStroke("control C"), "clear");
        getRootPane().getActionMap().put("clear", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearTable();
            }
        });
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Устанавливаем системный Look and Feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | 
                 IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        
        // Запускаем приложение в потоке обработки событий Swing
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Cedovv frame = new Cedovv();
                frame.setVisible(true);
            }
        });
    }
}