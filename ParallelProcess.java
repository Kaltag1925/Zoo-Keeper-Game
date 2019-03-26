package org.nosuchanimal.tools;

import java.util.LinkedList;
import java.util.Vector;

/**
 * Created by Rhiwaow on 13/04/2018.
 */
public class ParallelProcess {

    private int maxConcurrent;
    private LinkedList< Runnable > inQueue                = new LinkedList();
    private LinkedList< Runnable > scheduledQueue         = new LinkedList();
    private LinkedList< Runnable > currentProcessingQueue = new LinkedList();

    private boolean alive = true;

    private Runnable schedulerRunnable = new Runnable() {
        @Override
        public void run() {
            while( alive ) {
                synchronized( inQueueNotifier ) {
                    updateSchedule();
                    try {
                        inQueueNotifier.wait( 100 );
                    }catch( InterruptedException e ) {
                        e.printStackTrace();
                    }
                }
            }
        }
    };
    private Thread   scheduler         = new Thread( schedulerRunnable );

    private Object inQueueNotifier        = new Object();
    private Object scheduledQueueNotifier = new Object();
    private Object taskCompletionNotifier = new Object();

    private class Executor implements Runnable {

        @Override
        public void run() {
            while( alive ) {
                executeNextTask();
                if( scheduledQueue.isEmpty() ) {
                    try {
                        synchronized( scheduledQueueNotifier ) {
                            scheduledQueueNotifier.wait( 100 );
                        }
                    }catch( InterruptedException e ) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private Vector< Thread > threads = new Vector<>();

    public ParallelProcess() {
        this( 8 );
    }

    public ParallelProcess( int maxConcurrent ) {
        this.maxConcurrent = maxConcurrent;
        scheduler.start();
        for( int i = 0; i < maxConcurrent; i++ ) {
            Thread executor = new Thread( new Executor() );
            threads.add( executor );
            executor.start();
        }
    }

    public void addTask( Runnable runnable ) {
        addTask( runnable, false );
    }

    public void addTask( Runnable runnable, boolean forceFlushQueue ) {
        synchronized( inQueue ) {
            inQueue.add( runnable );
        }
        if( forceFlushQueue ) {
            forceFlushQueue();
        }
    }

    public void forceFlushQueue() {
        synchronized( inQueueNotifier ) {
            inQueueNotifier.notifyAll();
        }
    }

    private void executeNextTask() {
        if( !scheduledQueue.isEmpty() ) {
            Runnable task;
            synchronized( scheduledQueue ) {
                task = scheduledQueue.getFirst();
                synchronized( currentProcessingQueue ) {
                    currentProcessingQueue.add( task );
                }
                scheduledQueue.removeFirst();
            }

            try {
                task.run();
            }catch( Throwable t ) {
                t.printStackTrace();
            }
            synchronized( currentProcessingQueue ) {
                currentProcessingQueue.remove( task );
            }
            synchronized( taskCompletionNotifier ) {
                taskCompletionNotifier.notifyAll();
            }
        }
    }

    public void updateSchedule() {
        if( !inQueue.isEmpty() ) {
            synchronized( inQueue ) {
                synchronized( scheduledQueue ) {
                    scheduledQueue.addAll( inQueue );
                }
                inQueue.clear();
                synchronized( scheduledQueueNotifier ) {
                    scheduledQueueNotifier.notifyAll();
                }
            }
        }
    }

    public void waitForAll() {
        while( !( inQueue.isEmpty() && scheduledQueue.isEmpty() && currentProcessingQueue.isEmpty() ) ) {
//          System.out.println( "ParallelProcess waiting: (" + inQueue.size() + "/" + scheduledQueue.size() + "/" + currentProcessingQueue.size() + ")" );
            synchronized( taskCompletionNotifier ) {
                try {
                    taskCompletionNotifier.wait( 100 );
                }catch( InterruptedException e ) {
                    e.printStackTrace();
                }
            }
        }
    }

}