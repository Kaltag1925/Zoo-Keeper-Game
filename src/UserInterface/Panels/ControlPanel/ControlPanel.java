package UserInterface.Panels.ControlPanel;

import Engine.Logic.MainData;
import Engine.Logic.Ticks.IHasKeyBinds;
import Engine.Logic.Ticks.Tick;
import UserInterface.GameMain;
import UserInterface.Panels.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;

public abstract class ControlPanel extends GamePanel implements IHasKeyBinds {

    //<editor-fold desc="Attached Panels">
    private GamePanel attachedViewPanel;

    protected GamePanel getAttachedViewPanel() {
        return attachedViewPanel;
    }

    protected void setAttachedViewPanel(GamePanel attachedViewPanel) {
        this.attachedViewPanel = attachedViewPanel;
    }

    private GamePanel attachedShortDescriptionPanel;

    protected GamePanel getAttachedShortDescriptionPanel() {
        return attachedShortDescriptionPanel;
    }

    protected void setAttachedShortDescriptionPanel(GamePanel attachedShortDescriptionPanel) {
        this.attachedShortDescriptionPanel = attachedShortDescriptionPanel;
    }
    //</editor-fold>

    public ControlPanel() {
        super();
        setBackground(Color.BLACK);
    }

    protected void backKey() {
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    close();
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void open() {
        ControlPanelManager manager = MainData.mainData.getControlPanelManager();

        if (manager.getCurrentControlPanel() != null) {
            manager.getCurrentControlPanel().makeHidden();
        }

        //TODO: Check to see if the attached view panel is null first then see if there is even one and then open this's attached view panel
        if (manager.getCurrentViewPanel() != null) {
            if(attachedViewPanel != null && !manager.getCurrentViewPanel().equals(attachedViewPanel)) {
                manager.getCurrentViewPanel().close();
                attachedViewPanel.open();
                manager.setCurrentViewPanel(attachedViewPanel);
            }
        } else {
            manager.setCurrentViewPanel(attachedViewPanel);
        }

        if (manager.getCurrentShortDescriptionPanel() != null) {
            if(!manager.getCurrentShortDescriptionPanel().equals(attachedShortDescriptionPanel)) {
                manager.getCurrentShortDescriptionPanel().close();
                manager.setCurrentShortDescriptionPanel(attachedShortDescriptionPanel);
                if(attachedShortDescriptionPanel != null) {
                    attachedShortDescriptionPanel.open();
                }
            }
        } else {
            manager.setCurrentShortDescriptionPanel(attachedShortDescriptionPanel);
        }

        GameMain gameMain = MainData.mainData.getGameMain();

        manager.setCurrentControlPanel(this);
        manager.getDescriptionBreadCrumbs().add(this);
        gameMain.add(this, manager.CONTROL_CONSTRAINTS);
        Tick.addToInFocus(this, 0);
        MainData.mainData.getMainFrame().revalidate();
    }

    public void makeVisible() {

        ControlPanelManager manager = MainData.mainData.getControlPanelManager();
        GameMain gameMain = MainData.mainData.getGameMain();

        if (manager.getCurrentViewPanel() != null) {
            if(attachedViewPanel != null && !manager.getCurrentViewPanel().equals(attachedViewPanel)) {
                manager.getCurrentViewPanel().close();
                attachedViewPanel.open();
                manager.setCurrentViewPanel(attachedViewPanel);
            }
        } else if (attachedViewPanel != null) {
            manager.setCurrentViewPanel(attachedViewPanel);
            attachedViewPanel.open();
        }

        if (manager.getCurrentShortDescriptionPanel() != null) {
            if(!manager.getCurrentShortDescriptionPanel().equals(attachedShortDescriptionPanel)) {
                manager.getCurrentShortDescriptionPanel().close();
                manager.setCurrentShortDescriptionPanel(attachedShortDescriptionPanel);
                if(attachedShortDescriptionPanel != null) {
                    attachedShortDescriptionPanel.open();
                }
            }
        } else if (attachedShortDescriptionPanel != null){
            manager.setCurrentShortDescriptionPanel(attachedShortDescriptionPanel);
            attachedShortDescriptionPanel.open();
        } else {
            manager.setCurrentShortDescriptionPanel(null);
        }

        manager.setCurrentControlPanel(this);
        gameMain.add(this, manager.CONTROL_CONSTRAINTS);
        Tick.addToInFocus(this, 0);
        MainData.mainData.getMainFrame().revalidate();
    }

    @Override
    public void close() {
        ControlPanelManager manager = MainData.mainData.getControlPanelManager();
        GameMain gameMain = MainData.mainData.getGameMain();

        if (manager.getDescriptionBreadCrumbs().size() == 1) {
            throw new IndexOutOfBoundsException("No more menus to go back to");
        }

        gameMain.remove(this);
        Tick.removeFromInFocus(this);
        manager.getDescriptionBreadCrumbs().remove(manager.getDescriptionBreadCrumbs().size() - 1);
        manager.setCurrentControlPanel(manager.getDescriptionBreadCrumbs().get(manager.getDescriptionBreadCrumbs().size() - 1));
        manager.getCurrentControlPanel().makeVisible();
    }

    public void makeHidden() {
        ControlPanelManager manager = MainData.mainData.getControlPanelManager();
        GameMain gameMain = MainData.mainData.getGameMain();

        Tick.removeFromInFocus(this);
        gameMain.remove(this);
    }

    protected void selectString(String str, Graphics g, int fontHeightsDown) {
        int selectedStringLength = str.length();

        int yAnchor = getFontHeight() * (fontHeightsDown - 1) + getFontHeight() / 4;
        int xAnchor = getFontWidth() * 4 - getFontLeading();
        int xLength = getFontWidth() * selectedStringLength;
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(xAnchor, yAnchor, xLength, getFontHeight());

        g.setColor(Color.BLACK);
        g.drawString(str, getFontWidth() * 4, getFontHeight() * fontHeightsDown);
    }
}
