package ServerPackage;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import keyvaluestore.Key_Value_Store_Service;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * GRPC Server
 */
public class GRPCServer {
    private static final Logger logger = Logger.getLogger(GRPCServer.class.getName());
    private static Server server ;
    private static Server server2 ;
    private static Server server3 ;
    private static Server server4 ;
    private static Server server5 ;


    /**PORT NUMBERS for all servers**/
    private static String port_number2="9091" ;
    private static String port_number3="9092" ;
    private static String port_number4="9093" ;
    private static String port_number5="9094" ;

    private final AtomicBoolean running = new AtomicBoolean(false);




    /**Handle Milliseconds for Log**/
    public static final String format = "%1$tb %1$td, %1$tY %1$tl:%1$tM:%1$tS.%1$tL %1$Tp %2$s%n%4$s: %5$s%n";

    /**
     * Main launches the server from the command line.
     */
    public static void main(String args[]) {

        /**
         * Create all servers with port number
         */
        try {
            if (args.length == 0) {
                System.out.println("<Usage: java -jar your_directory_here <jarfilepath> <portnumber>");
            }


        server= ServerBuilder.forPort(Integer.parseInt(args[0])).addService(new Key_Value_Store_Service()).build();
        server2= ServerBuilder.forPort(Integer.parseInt(port_number2)).addService(new Key_Value_Store_Service()).build();
        server3= ServerBuilder.forPort(Integer.parseInt(port_number3)).addService(new Key_Value_Store_Service()).build();
        server4= ServerBuilder.forPort(Integer.parseInt(port_number4)).addService(new Key_Value_Store_Service()).build();
        server5= ServerBuilder.forPort(Integer.parseInt(port_number5)).addService(new Key_Value_Store_Service()).build();


            /**Start all server**/
            server.start();
            server2.start();
            server3.start();
            server4.start();
            server5.start();




            Logger_Function("Server started, listening on " + server.getPort());
            Logger_Function("Server started, listening on " + server2.getPort());
            Logger_Function("Server started, listening on " + server3.getPort());
            Logger_Function("Server started, listening on " + server4.getPort());
            Logger_Function("Server started, listening on " + server5.getPort());

            //server.shutdownNow();

           // server.start();

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                // Use stderr here since the logger may have been reset by its JVM shutdown hook.

                Logger_Function("*** shutting down gRPC server since JVM is shutting down");
                stop();
                Logger_Function("*** server shut down");
            }));

        } catch (IOException e) {
            e.printStackTrace();
        }catch (ArrayIndexOutOfBoundsException a){

        }

        try {
           // System.out.println("Server started at " + server.getPort());
            /**
             * Await termination on the main thread since the grpc library uses daemon threads.
             */
            server.awaitTermination();
            server2.awaitTermination();
            server3.awaitTermination();
            server4.awaitTermination();
            server5.awaitTermination();


        } catch (InterruptedException e) {
            e.printStackTrace();
        }catch (NullPointerException a){

        }
    }

    /** Stop serving requests and shutdown resources. */
    public static void stop() {
        System.out.println("Im called");
        server.shutdown();
        server2.shutdown();
        server3.shutdown();
        server4.shutdown();
        server5.shutdown();


//        if (server != null ) {
//            server.shutdown();
//        }
//        else if (server2 != null ){
//            server2.shutdown();
//        }
//        else if(server3!=null){
//            server3.shutdown();
//
//        }
//        else if(server4!=null){
//            server4.shutdown();
//
//        }
//        else if(server5!=null){
//            server5.shutdown();
//
//        }
       // System.exit(0);

    }

    /*public void run() {

        running.set(true);
        while (running.get()) {
        try {
            Thread.sleep(5000);
            Thread.currentThread().start();
            } catch (InterruptedException e){
                Thread.currentThread().interrupt();
                System.out.println("Thread was interrupted, Failed to complete operation");
            }
// do something here
        }
    }*/

    /**
     * Function to log to server/client
     * @param message is the message to be displayed on the logger
     */
    public static void Logger_Function(String message){
        LogRecord record = new LogRecord(Level.INFO, message);
        System.out.println(String.format(format,
                new java.util.Date(record.getMillis()),
                record.getSourceClassName(),
                record.getLoggerName(),
                record.getLevel().getLocalizedName(),
                record.getMessage(),
                String.valueOf(record.getThrown())));
    }





}

