import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

public class Main {
    private static Timer timer;
    private static Robot robot;
    private static TrayIcon trayIcon;
    private static boolean isActive = false;

    public static void main(String[] args) {
        // Verifica se a bandeja do sistema é suportada
        if (!SystemTray.isSupported()) {
            System.err.println("Bandeja do sistema não é suportada!");
            return;
        }

        try {
            // Inicializa o Robot para simular pressionamento de teclas
            robot = new Robot();

            // Configura a aparência do sistema
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            // Cria a bandeja do sistema
            SwingUtilities.invokeLater(() -> createSystemTray());

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                "Erro ao inicializar a aplicação: " + e.getMessage(),
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void createSystemTray() {
        SystemTray tray = SystemTray.getSystemTray();

        // Cria um ícone simples para a bandeja
        Image image = createTrayIcon();

        // Cria o menu popup
        PopupMenu popup = new PopupMenu();

        // Item: Ativar
        MenuItem ativarItem = new MenuItem("Ativar");
        ativarItem.addActionListener(e -> ativar());

        // Item: Desativar
        MenuItem desativarItem = new MenuItem("Desativar");
        desativarItem.addActionListener(e -> desativar());

        // Separador
        popup.addSeparator();

        // Item: Sair
        MenuItem sairItem = new MenuItem("Sair");
        sairItem.addActionListener(e -> sair(tray));

        // Adiciona os itens ao menu
        popup.add(ativarItem);
        popup.add(desativarItem);
        popup.add(sairItem);

        // Cria o ícone da bandeja
        trayIcon = new TrayIcon(image, "F15 Auto Presser", popup);
        trayIcon.setImageAutoSize(true);

        // Adiciona listener para clique duplo
        trayIcon.addActionListener(e -> {
            if (isActive) {
                desativar();
            } else {
                ativar();
            }
        });

        try {
            tray.add(trayIcon);
            trayIcon.displayMessage("F15 Auto Presser",
                "Aplicação iniciada. Clique com o botão direito para ver as opções.",
                TrayIcon.MessageType.INFO);
        } catch (AWTException e) {
            System.err.println("Erro ao adicionar ícone na bandeja: " + e.getMessage());
        }
    }

    private static void ativar() {
        if (isActive) {
            trayIcon.displayMessage("F15 Auto Presser",
                "O pressionamento automático já está ativo!",
                TrayIcon.MessageType.WARNING);
            return;
        }

        isActive = true;
        timer = new Timer(true);

        // Agenda a tarefa para pressionar F15 a cada 120 segundos
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                pressionarF15();
            }
        }, 0, 120000); // 120000 ms = 120 segundos

        trayIcon.displayMessage("F15 Auto Presser",
            "Pressionamento automático ATIVADO! F15 será pressionado a cada 120 segundos.",
            TrayIcon.MessageType.INFO);
    }

    private static void desativar() {
        if (!isActive) {
            trayIcon.displayMessage("F15 Auto Presser",
                "O pressionamento automático já está desativado!",
                TrayIcon.MessageType.WARNING);
            return;
        }

        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        isActive = false;

        trayIcon.displayMessage("F15 Auto Presser",
            "Pressionamento automático DESATIVADO!",
            TrayIcon.MessageType.INFO);
    }

    private static void pressionarF15() {
        try {
            // Pressiona e solta a tecla F15
            robot.keyPress(KeyEvent.VK_F15);
            robot.keyRelease(KeyEvent.VK_F15);
            System.out.println("F15 pressionado em: " + new java.util.Date());
        } catch (Exception e) {
            System.err.println("Erro ao pressionar F15: " + e.getMessage());
        }
    }

    private static void sair(SystemTray tray) {
        int resposta = JOptionPane.showConfirmDialog(null,
            "Deseja realmente sair do F15 Auto Presser?",
            "Confirmar Saída",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);

        if (resposta == JOptionPane.YES_OPTION) {
            desativar();
            tray.remove(trayIcon);
            System.exit(0);
        }
    }

    private static Image createTrayIcon() {
        // Cria um ícone simples 16x16 com um círculo verde
        int size = 16;
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();

        // Ativa antialiasing para melhor aparência
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Desenha um círculo verde com borda preta
        g.setColor(Color.GREEN);
        g.fillOval(2, 2, size - 4, size - 4);
        g.setColor(Color.BLACK);
        g.drawOval(2, 2, size - 4, size - 4);

        g.dispose();
        return image;
    }
}