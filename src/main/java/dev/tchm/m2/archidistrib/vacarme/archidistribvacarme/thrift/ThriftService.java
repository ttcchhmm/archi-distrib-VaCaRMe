package dev.tchm.m2.archidistrib.vacarme.archidistribvacarme.thrift;

import com.company.generated.InternalCRMService;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TSocket;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ThriftService {
    @Value("${thrift.host}")
    private String thriftHost;

    @Value("${thrift.port}")
    private int thriftPort;

    public <T> T client(ClientRunnable<T> runnable) throws TException {
        // Create and open the transport
        var transport = new TSocket(thriftHost, thriftPort);
        transport.open();

        // Set up the protocol
        var protocol = new TBinaryProtocol(transport);

        // Run the callback with the client connected to the Thrift server
        var returnValue = runnable.clientRunnable(new InternalCRMService.Client(protocol));

        // Make sure to close the connection
        transport.close();

        // Return the value from the callback to the caller
        return returnValue;
    }

    public interface ClientRunnable<T> {
        T clientRunnable(InternalCRMService.Client client) throws TException;
    }
}
