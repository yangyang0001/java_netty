package com.deepblue.inaction_00_java_nio.chapter_04.example_002;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 *
 */
public class SelectorServer {

    public static int PORT_NUMBER = 80;

    public static void main(String[] argv) throws Exception {
        new SelectorServer().go(argv);
    }

    public void go(String[] argv) throws Exception {

        int port = PORT_NUMBER;

        System.out.println("Listening on port " + port);
        // Allocate an unbound server socket channel
        ServerSocketChannel serverChannel = ServerSocketChannel.open();

        // Get the associated ServerSocket to bind it with
        ServerSocket serverSocket = serverChannel.socket();

        // Create a new Selector for use below
        Selector selector = Selector.open();

        // Set the port the server channel will listen to
        serverSocket.bind(new InetSocketAddress(port));

        // Set nonblocking mode for the listening socket
        serverChannel.configureBlocking(false);

        // Register the ServerSocketChannel with the Selector
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {

            // This may block for a long time. Upon returning, the selected set contains keys of the ready channels.
            int n = selector.select();

            if (n == 0) {
                continue; // nothing to do
            }

            // Get an iterator over the set of selected keys
            Iterator it = selector.selectedKeys().iterator();

            // Look at each key in the selected set
            while (it.hasNext()) {
                SelectionKey key = (SelectionKey) it.next();
                System.out.println(key.interestOps());

                // Is a new connection coming in? 是否有新的远程连接进来
                if (key.isAcceptable()) {
                    ServerSocketChannel server = (ServerSocketChannel) key.channel();
                    SocketChannel channel = server.accept();
                    registerChannel(selector, channel, SelectionKey.OP_READ);
                    sayHello(channel);
                }

                // Is there data to read on this channel? 当前的通道 SocketChannel 是否准备好读取数据
                if (key.isReadable()) {
                    readDataFromSocket(key);
                }

                // Remove key from selected set; it's been handled
                it.remove();
            }
        }
    }
    // ----------------------------------------------------------
    /**
     * Register the given channel with the given selector for the given
     * operations of interest
     */
    protected void registerChannel(Selector selector, SelectableChannel channel, int ops) throws Exception {
        if (channel == null) {
            return; // could happen
        }
        // Set the new channel nonblocking
        channel.configureBlocking(false);
        // Register it with the selector
        channel.register(selector, ops);
    }

    // ----------------------------------------------------------
    // Use the same byte buffer for all channels. A single thread is
    // servicing all the channels, so no danger of concurrent acccess.
    private ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
    /**
     * Sample data handler method for a channel with data ready to read.
     *
     * @param key
     * A SelectionKey object associated with a channel determined by
     * the selector to be ready for reading. If the channel returns142
     * an EOF condition, it is closed here, which automatically
     * invalidates the associated key. The selector will then
     * de-register the channel on the next select call.
     */
    protected void readDataFromSocket(SelectionKey key) throws Exception {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        int count;
        buffer.clear(); // Empty buffer
        // Loop while data is available; channel is nonblocking
        while ((count = socketChannel.read(buffer)) > 0) {
            buffer.flip(); // Make buffer readable
            // Send the data; don't assume it goes all at once
            while (buffer.hasRemaining()) {
                socketChannel.write(buffer);
            }
            // WARNING: the above loop is evil. Because
            // it's writing back to the same nonblocking
            // channel it read the data from, this code can
            // potentially spin in a busy loop. In real life
            // you'd do something more useful than this.
            buffer.clear(); // Empty buffer
        }
        if (count < 0) {
            // Close channel on EOF, invalidates the key
            socketChannel.close();
        }
    }
    // ----------------------------------------------------------
    /**
     * Spew a greeting to the incoming client connection.
     *
     * @param channel
     * The newly connected SocketChannel to say hello to.
     */
    private void sayHello(SocketChannel channel) throws Exception {
        buffer.clear();
        buffer.put("Hi there!\r\n".getBytes());
        buffer.flip();
        channel.write(buffer);
    }
}