package UserInterface.Panels.DescriptionPanel;

import UserInterface.Panels.ControlPanel.Listed;

import java.awt.*;
import java.util.Map;

public class ObjectShortDescriptionPanel extends DescriptionPanel {

    private Listed object;

    public Listed getObject() {
        return object;
    }

    public void setObject(Listed object) {
        this.object = object;
    }

    public ObjectShortDescriptionPanel() {
        super();
        setConstraints(DescriptionPanelManager.getShortDescriptionConstraints());
    }

    @Override
    public void close() {
        super.close();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.LIGHT_GRAY);
        g.drawString(object.getListName(), getFontWidth(), getFontHeight());

        final int X_OFFSET = 2;
        final int Y_OFFSET = 3;

        int index = 0;
        for (Map.Entry<String, String> entry : object.getShortDescriptors().entrySet()) {
            String key = entry.getKey() + ": ";
            int length = key.length();
            int height = getFontHeight() * (Y_OFFSET + index);

            g.setColor(Color.LIGHT_GRAY);
            g.drawString(entry.getKey(), getFontWidth() * X_OFFSET, height);

            g.setColor(Color.GREEN);
            g.drawString(entry.getValue(), getFontWidth() * (length + X_OFFSET), height);

            index++;
        }
    }
}
