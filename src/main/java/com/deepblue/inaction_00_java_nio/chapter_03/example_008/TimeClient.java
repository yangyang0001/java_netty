package com.deepblue.inaction_00_java_nio.chapter_03.example_008;

/**
 *
 */

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.DatagramChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * 请求时间服务，根据 RFC 868。
 * Request time service, per RFC 868.
 *
 * RFC 868 (http://www.ietf.org/rfc/rfc0868.txt) 是一种非常简单的时间协议，其中一个系统可以从另一个系统请求当前时间。
 * RFC 868 (http://www.ietf.org/rfc/rfc0868.txt) is a very simple time protocol where by one system can request the current time from another system.
 *
 * 大多数 Linux、BSD 和 Solaris 系统在端口 37 上提供 RFC 868 时间服务。
 * Most Linux, BSD and Solaris systems provide RFC 868 time service on port 37.
 *
 * 这个简单的程序将与那些互操作。
 * This simple program will inter-operate with those.
 *
 * 美国国家标准与技术研究院 (NIST) 在 time.nist.gov 上运行公共时间服务器。
 * The National Institute of Standards and Technology (NIST) operates a public time server at time.nist.gov.
 *
 * RFC 868 协议指定要发送的 32 位无符号值，表示自 1900 年 1 月 1 日以来的秒数。
 * The RFC 868 protocol specifies a 32 bit unsigned value be sent, representing the number of seconds since Jan 1, 1900.
 *
 * Java 纪元开始于 1970 年 1 月 1 日（与 unix 相同），因此通过适当地增加或减少 2,208,988,800 来进行调整。
 * The Java epoch begins on Jan 1, 1970 (same as unix) so an adjustment is made by adding or subtracting 2,208,988,800 as appropriate.
 *
 * 为避免移位和屏蔽，使用 8 字节缓冲区的 4 字节切片来发送/接收。
 * To avoid shifting and masking, a four-byte slice of an eight-byte buffer is used to send/recieve.
 *
 * 但是 getLong() 在完整的八个字节上完成以获得长值。
 * But getLong() is done on the full eight bytes to get a long value.
 *
 * 运行时，该程序将对命令行中给出的每个主机名发出时间请求，然后进入一个循环接收数据包。
 * When run, this program will issue time requests to each hostname given on the command line, then enter a loop to receive packets.
 *
 * 请注意，某些请求或回复可能会丢失，这意味着此代码可能会永远阻塞。
 * Note that some requests or replies may be lost, which means this code could block forever.
 *
 */
public class TimeClient {
    
    private static final int DEFAULT_TIME_PORT = 37;
    
    private static final long DIFF_1900 = 2208988800L;
    
    protected int port = DEFAULT_TIME_PORT;
    
    protected List remoteHosts;
    
    protected DatagramChannel channel;
    
    public TimeClient (String[] argv) throws Exception {

        if (argv.length == 0) {
            argv = new String[] {"localhost"};
        }

        parseArgs (argv);
        this.channel = DatagramChannel.open();
    }

    public static void main (String [] argv) throws Exception {
        TimeClient client = new TimeClient (argv);

        client.sendRequests();

        client.getReplies();
    }
    
    protected InetSocketAddress receivePacket (DatagramChannel channel, ByteBuffer buffer) throws Exception {

        buffer.clear();

        // Receive an unsigned 32-bit, big-endian value
        return ((InetSocketAddress) channel.receive (buffer));
    }
    
    // Send time requests to all the supplied hosts
    protected void sendRequests() throws Exception {

        ByteBuffer buffer = ByteBuffer.allocate (1);

        Iterator it = remoteHosts.iterator();

        while (it.hasNext()) {

            InetSocketAddress sa = (InetSocketAddress) it.next();
            System.out.println ("Requesting time from " + sa.getHostName() + ":" + sa.getPort());

            // Make it empty (see RFC868)
            buffer.clear().flip();

            // Fire and forget
            channel.send (buffer, sa);
        }
    }

    // Receive any replies that arrive
    public void getReplies() throws Exception {

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

        int expect = remoteHosts.size();
        int replies = 0;
        System.out.println ("");
        System.out.println ("Waiting for replies...");

        while (true) {
            InetSocketAddress sa;
            sa = receivePacket (channel, buffer);
            buffer.flip();
            replies++;
            printTime (longBuffer.getLong (0), sa);
            if (replies == expect) {
                System.out.println ("All packets answered");
                break;
            }
            // Some replies haven't shown up yet
            System.out.println ("Received " + replies + " of " + expect + " replies");
        }
    }

    // Print info about a received time reply
    protected void printTime (long remote1900, InetSocketAddress sa) {

        // local time as seconds since Jan 1, 1970114
        long local = System.currentTimeMillis() / 1000;

        // remote time as seconds since Jan 1, 1970
        long remote = remote1900 - DIFF_1900;
        Date remoteDate = new Date(remote * 1000);
        Date localDate = new Date (local * 1000);
        long skew = remote - local;

        System.out.println ("Reply from " + sa.getHostName() + ":" + sa.getPort());
        System.out.println (" there: " + remoteDate);
        System.out.println (" here: " + localDate);
        System.out.print (" skew: ");

        if (skew == 0) {
            System.out.println ("none");
        } else if (skew > 0) {
            System.out.println (skew + " seconds ahead");
        } else {
            System.out.println ((-skew) + " seconds behind");
        }
    }

    protected void parseArgs (String [] argv) {

        remoteHosts = new LinkedList();

        for (int i = 0; i < argv.length; i++) {
            String arg = argv [i];

            // Send client requests to the given port
            if (arg.equals ("-p")) {
                i++;
                this.port = Integer.parseInt (argv [i]);
                continue;
            }

            // Create an address object for the hostname
            InetSocketAddress sa = new InetSocketAddress(arg, port);

            // Validate that it has an address
            if (sa.getAddress() == null) {
                System.out.println ("Cannot resolve address: "
                        + arg);
                continue;
            }

            remoteHosts.add (sa);
        }
    }

}
