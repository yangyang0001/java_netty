package com.deepblue.inaction_00_java_nio.chapter_03.example_008;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.DatagramChannel;

/**
 * 提供 RFC 868 时间服务（http://www.ietf.org/rfc/rfc0868.txt）。
 * Provide RFC 868 time service (http://www.ietf.org/rfc/rfc0868.txt).
 *
 * 此代码实现了一个 RFC 868 侦听器以提供时间服务。
 * This code implements an RFC 868 listener to provide time service. 
 *
 * 时间服务定义的端口是 37。
 * The defined port for time service is 37. 
 *
 * 在大多数 Unix 系统上, 需要 root 权限才能绑定到 1024 以下的端口。
 * On most unix systems, root privilege is required to bind to ports below 1024. 
 *
 * 您可以以 root 身份运行此代码, 也可以在命令行上提供另一个端口号。
 * You can either run this code as root or provide another port number on the command line. 
 * 
 * 如果您选择备用端口, 请对 TimeClient 使用“-p port#”。
 * Use "-p port#" with TimeClient if you choose an alternate port.
 *
 * Note: 
 * unix 上熟悉的 rdate 命令可能不适用于此服务器。
 * The familiar rdate command on unix will probably not work with this server. 
 *
 * 大多数版本的 rdate 使用 TCP 而不是 UDP 来请求时间。
 * Most versions of rdate use TCP rather than UDP to request the time.
 *
 */
public class TimeServer {
    
    private static final int DEFAULT_TIME_PORT = 37;
    
    private static final long DIFF_1900 = 2208988800L;
    
    protected DatagramChannel channel;
    
    public TimeServer (int port) throws Exception {

        this.channel = DatagramChannel.open();
        this.channel.socket().bind (new InetSocketAddress(port));

        System.out.println ("Listening on port " + port + " for time requests");
    }

    public static void main (String [] argv) throws Exception {
        int port = DEFAULT_TIME_PORT;
        if (argv.length > 0) {
            port = Integer.parseInt (argv [0]);
        }
        try {
            TimeServer server = new TimeServer (port);
            server.listen();
        } catch (SocketException e) {
            System.out.println ("Can't bind to port " + port + ", try a different one");
        }
    }

    public void listen() throws Exception {

        // Allocate a buffer to hold a long value
        ByteBuffer longBuffer = ByteBuffer.allocate (8);

        // Assure big-endian (network) byte order
        longBuffer.order (ByteOrder.BIG_ENDIAN);

        // Zero the whole buffer to be sure
        longBuffer.putLong (0, 0);

        // Position to first byte of the low-order 32 bits
        longBuffer.position (4);

        // Slice the buffer; gives view of the low-order 32 bits
        ByteBuffer buffer = longBuffer.slice();

        while (true) {

            buffer.clear();

            SocketAddress sa = this.channel.receive (buffer);

            if (sa == null) {
                continue; // defensive programming
            }

            // Ignore content of received datagram per RFC 868
            System.out.println ("Time request from " + sa);

            buffer.clear(); // sets pos/limit correctly

            // Set 64-bit value; slice buffer sees low 32 bits117
            longBuffer.putLong (0, (System.currentTimeMillis() / 1000) + DIFF_1900);

            this.channel.send (buffer, sa);

        }
    }

}
