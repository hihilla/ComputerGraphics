// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.common.util.locks;

import com.jogamp.common.ExceptionUtils;
import com.jogamp.common.util.InterruptedRuntimeException;
import com.jogamp.common.util.SourcedInterruptedException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import com.jogamp.common.util.InterruptSource;
import java.net.UnknownHostException;
import java.net.InetAddress;
import com.jogamp.common.util.locks.SingletonInstance;

public class SingletonInstanceServerSocket extends SingletonInstance
{
    private static int serverInstanceCount;
    private final Server singletonServer;
    private final String fullName;
    
    public SingletonInstanceServerSocket(final long n, final int n2) {
        super(n);
        InetAddress inetAddress = null;
        try {
            inetAddress = InetAddress.getByName(null);
        }
        catch (UnknownHostException ex) {}
        if (null == inetAddress) {
            try {
                inetAddress = InetAddress.getByName("localhost");
                if (null != inetAddress && !inetAddress.isLoopbackAddress()) {
                    inetAddress = null;
                }
            }
            catch (UnknownHostException ex2) {}
        }
        if (null == inetAddress) {
            try {
                inetAddress = InetAddress.getByAddress(new byte[] { 127, 0, 0, 1 });
                if (null != inetAddress && !inetAddress.isLoopbackAddress()) {
                    inetAddress = null;
                }
            }
            catch (UnknownHostException ex3) {}
        }
        if (null == inetAddress) {
            try {
                inetAddress = InetAddress.getLocalHost();
            }
            catch (UnknownHostException ex4) {}
        }
        if (null == inetAddress) {
            throw new RuntimeException(this.infoPrefix() + " EEE Could not determine local InetAddress");
        }
        this.fullName = inetAddress.toString() + ":" + n2;
        this.singletonServer = new Server(inetAddress, n2);
        Runtime.getRuntime().addShutdownHook(new InterruptSource.Thread() {
            @Override
            public void run() {
                SingletonInstanceServerSocket.this.singletonServer.kill();
            }
        });
    }
    
    public final InetAddress getLocalInetAddress() {
        return this.singletonServer.getLocalInetAddress();
    }
    
    public final int getPortNumber() {
        return this.singletonServer.getPortNumber();
    }
    
    @Override
    public final String getName() {
        return this.fullName;
    }
    
    @Override
    protected boolean tryLockImpl() {
        if (this.singletonServer.isRunning()) {
            return false;
        }
        final Socket connect = this.singletonServer.connect();
        if (null != connect) {
            try {
                connect.close();
            }
            catch (IOException ex) {}
            return false;
        }
        return this.singletonServer.start();
    }
    
    @Override
    protected boolean unlockImpl() {
        return this.singletonServer.shutdown();
    }
    
    static {
        SingletonInstanceServerSocket.serverInstanceCount = 0;
    }
    
    public class Server implements Runnable
    {
        private final InetAddress localInetAddress;
        private final int portNumber;
        private volatile boolean shallQuit;
        private volatile boolean alive;
        private final Object syncOnStartStop;
        private ServerSocket serverSocket;
        private Thread serverThread;
        
        public Server(final InetAddress localInetAddress, final int portNumber) {
            this.shallQuit = false;
            this.alive = false;
            this.syncOnStartStop = new Object();
            this.serverSocket = null;
            this.serverThread = null;
            this.localInetAddress = localInetAddress;
            this.portNumber = portNumber;
        }
        
        public final InetAddress getLocalInetAddress() {
            return this.localInetAddress;
        }
        
        public final int getPortNumber() {
            return this.portNumber;
        }
        
        public final boolean start() {
            if (this.alive) {
                return true;
            }
            final String string;
            synchronized (Server.class) {
                SingletonInstanceServerSocket.serverInstanceCount++;
                string = "SingletonServerSocket" + SingletonInstanceServerSocket.serverInstanceCount + "-" + SingletonInstanceServerSocket.this.fullName;
            }
            synchronized (this.syncOnStartStop) {
                this.shallQuit = false;
                (this.serverThread = new InterruptSource.Thread(null, this, string)).setDaemon(true);
                this.serverThread.start();
                try {
                    while (!this.alive && !this.shallQuit) {
                        this.syncOnStartStop.wait();
                    }
                }
                catch (InterruptedException ex) {
                    final InterruptedException wrap = SourcedInterruptedException.wrap(ex);
                    this.shutdown(false);
                    throw new InterruptedRuntimeException(wrap);
                }
            }
            final boolean bound = this.isBound();
            if (!bound) {
                this.shutdown(true);
            }
            return bound;
        }
        
        public final boolean shutdown() {
            return this.shutdown(true);
        }
        
        private final boolean shutdown(final boolean b) {
            if (!this.alive) {
                return true;
            }
            try {
                synchronized (this.syncOnStartStop) {
                    this.shallQuit = true;
                    this.connect();
                    if (b) {
                        try {
                            while (this.alive) {
                                this.syncOnStartStop.wait();
                            }
                        }
                        catch (InterruptedException ex) {
                            throw new InterruptedRuntimeException(ex);
                        }
                    }
                }
            }
            finally {
                if (this.alive) {
                    System.err.println(SingletonInstanceServerSocket.this.infoPrefix() + " EEE " + SingletonInstanceServerSocket.this.getName() + " - Unable to remove lock: ServerThread still alive ?");
                    this.kill();
                }
            }
            return true;
        }
        
        public final void kill() {
            if (this.alive) {
                System.err.println(SingletonInstanceServerSocket.this.infoPrefix() + " XXX " + SingletonInstanceServerSocket.this.getName() + " - Kill @ JVM Shutdown");
            }
            this.alive = false;
            this.shallQuit = false;
            if (null != this.serverThread && this.serverThread.isAlive()) {
                try {
                    this.serverThread.stop();
                }
                catch (Throwable t) {}
            }
            if (null != this.serverSocket) {
                try {
                    final ServerSocket serverSocket = this.serverSocket;
                    this.serverSocket = null;
                    serverSocket.close();
                }
                catch (Throwable t2) {}
            }
        }
        
        public final boolean isRunning() {
            return this.alive;
        }
        
        public final boolean isBound() {
            return this.alive && null != this.serverSocket && this.serverSocket.isBound();
        }
        
        public final Socket connect() {
            try {
                return new Socket(this.localInetAddress, this.portNumber);
            }
            catch (Exception ex) {
                return null;
            }
        }
        
        @Override
        public void run() {
            System.err.println(SingletonInstanceServerSocket.this.infoPrefix() + " III - Start");
            try {
                synchronized (this.syncOnStartStop) {
                    try {
                        (this.serverSocket = new ServerSocket(this.portNumber, 1, this.localInetAddress)).setReuseAddress(true);
                        this.alive = true;
                    }
                    catch (IOException ex) {
                        System.err.println(SingletonInstanceServerSocket.this.infoPrefix() + " III - Unable to install ServerSocket: " + ex.getMessage());
                        this.shallQuit = true;
                    }
                    finally {
                        this.syncOnStartStop.notifyAll();
                    }
                }
                while (!this.shallQuit) {
                    try {
                        this.serverSocket.accept().close();
                    }
                    catch (IOException ex2) {
                        System.err.println(SingletonInstanceServerSocket.this.infoPrefix() + " EEE - Exception during accept: " + ex2.getMessage());
                    }
                }
            }
            catch (ThreadDeath threadDeath) {
                ExceptionUtils.dumpThrowable("", threadDeath);
                synchronized (this.syncOnStartStop) {
                    System.err.println(SingletonInstanceServerSocket.this.infoPrefix() + " III - Stopping: alive " + this.alive + ", shallQuit " + this.shallQuit + ", hasSocket " + (null != this.serverSocket));
                    if (null != this.serverSocket) {
                        try {
                            this.serverSocket.close();
                        }
                        catch (IOException ex3) {
                            System.err.println(SingletonInstanceServerSocket.this.infoPrefix() + " EEE - Exception during close: " + ex3.getMessage());
                        }
                    }
                    this.serverSocket = null;
                    this.alive = false;
                    this.syncOnStartStop.notifyAll();
                }
            }
            finally {
                synchronized (this.syncOnStartStop) {
                    System.err.println(SingletonInstanceServerSocket.this.infoPrefix() + " III - Stopping: alive " + this.alive + ", shallQuit " + this.shallQuit + ", hasSocket " + (null != this.serverSocket));
                    if (null != this.serverSocket) {
                        try {
                            this.serverSocket.close();
                        }
                        catch (IOException ex4) {
                            System.err.println(SingletonInstanceServerSocket.this.infoPrefix() + " EEE - Exception during close: " + ex4.getMessage());
                        }
                    }
                    this.serverSocket = null;
                    this.alive = false;
                    this.syncOnStartStop.notifyAll();
                }
            }
        }
    }
}
