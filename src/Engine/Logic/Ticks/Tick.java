package Engine.Logic.Ticks;

import Engine.Logic.GameObjectHolder;
import Engine.Logic.MainData;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Tick {

    private static boolean running = false;
    private static boolean paused = false;

    private static final GameObjectHolder<IUpdateable> OBJECTS_TO_UPDATE = new GameObjectHolder<>();
    public static void addToUpdateList(IUpdateable updateable) {
        OBJECTS_TO_UPDATE.add(updateable);
    }
    public static void removeFromUpdateList(IUpdateable updateable) {
        OBJECTS_TO_UPDATE.remove(updateable);
    }

    private static final ArrayList<IRenderable> OBJECTS_TO_RENDER = new ArrayList<>();
    public static void addToRenderList(IRenderable renderable) {
        OBJECTS_TO_RENDER.add(renderable);
    }

    private static final ArrayList<IHasKeyBinds> BINDERS_IN_FOCUS = new ArrayList<>();
    public static final int LAST_BINDER = -1;

    public static void addToInFocus(IHasKeyBinds keyBinder, int index) {
        synchronized (BINDERS_IN_FOCUS) {
            if(index == -1) {
                BINDERS_IN_FOCUS.add(keyBinder);
            } else if(index >= BINDERS_IN_FOCUS.size()) {
                while (index >= BINDERS_IN_FOCUS.size()) {
                    BINDERS_IN_FOCUS.add(null);
                }
            } else {
                BINDERS_IN_FOCUS.add(index, keyBinder);
            }
        }
    }

    public static void removeFromInFocus(IHasKeyBinds keyBinder) {
        synchronized (BINDERS_IN_FOCUS) {
            BINDERS_IN_FOCUS.remove(keyBinder);
        }
    }

    public static void setPaused(boolean next) {
        paused = next;
    }

    public static void switchPaused() {
        paused = !paused;
    }

    public static void setRunning(boolean next) {
        running = next;
        run();
    }

    public static void switchRunning(){
        running = !running;
        run();
    }

    private static void run() {
        if (running && !isLoopRunning) {
            gameLoop();
        }
    }

    static final int GAME_HERTZ = 30;
    static final long TIME_BETWEEN_UPDATES = 1000 / GAME_HERTZ;
    static final int MAX_UPDATES_BEFORE_RENDER = 1;

    static boolean isLoopRunning = false;

    public static int getMaxUpdatesBeforeRender() {
        return MAX_UPDATES_BEFORE_RENDER;
    }

    public static int getGameHertz() {
        return GAME_HERTZ;
    }

    private static void gameLoop() {
        if (!isLoopRunning) {
            isLoopRunning = true;
            while (running) {
                if(!paused) {

                    long startTime = System.currentTimeMillis();

//                    Profiler p = new Profiler();
//                    p.log();
//                    checkKeyBind();
//                    p.log();
//
//                    p.log();
//                    updateGame();
//                    p.log();
//
//                    p.log();
//                    render();
//                    p.log();
//                    System.out.println(p);

                    checkKeyBind();
                    updateGame();
                    render();

                    long remainingTime = startTime + TIME_BETWEEN_UPDATES - System.currentTimeMillis();

                    try {
                        Thread.sleep(remainingTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (IllegalArgumentException e) {

                    }
                }
            }
            isLoopRunning = false;
        }
    }

    private static void updateGame() {
        ArrayList<IUpdateable> updateList = new ArrayList<>(OBJECTS_TO_UPDATE);
        Iterator<IUpdateable> iterator = updateList.iterator();
        while (iterator.hasNext()) {
            IUpdateable update = iterator.next();
            update.update();
        }
    }

    private static void render() {
        ArrayList<IRenderable> renderList = new ArrayList<>(OBJECTS_TO_RENDER);
        Iterator<IRenderable> iterator = renderList.iterator();
        while (iterator.hasNext()) {
            IRenderable render = iterator.next();
            render.render();
        }
    }

    private static void checkKeyBind() {
        Iterator<Integer> keysPressed = MainData.mainData.getInputManager().forwardInput().iterator();
        while (keysPressed.hasNext()) {
            int key = keysPressed.next();
            List<IHasKeyBinds> binders = new ArrayList<>(BINDERS_IN_FOCUS);
            Iterator<IHasKeyBinds> iterator = binders.iterator();
            while (iterator.hasNext()) {
                IHasKeyBinds binder = iterator.next();
                if(binder != null) {
                    if(binder.checkKeyBinds(key)) break;
                }
            }
        }
    }
}
