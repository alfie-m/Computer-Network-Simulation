/*************************************
 * Filename:  Receiver.java
 * Names: Alfie Moseley
 * Student-IDs: 201353343
 * Date: 20/11/20
 *************************************/
import java.util.Random;

public class Receiver extends NetworkHost

{
    /*
     * Predefined Constants (static member variables):
     *
     *   int MAXDATASIZE : the maximum size of the Message data and
     *                     Packet payload
     *
     *
     * Predefined Member Methods:
     *
     *  void startTimer(double increment):
     *       Starts a timer, which will expire in
     *       "increment" time units, causing the interrupt handler to be
     *       called.  You should only call this in the Sender class.
     *  void stopTimer():
     *       Stops the timer. You should only call this in the Sender class.
     *  void udtSend(Packet p)
     *       Sends the packet "p" into the network to arrive at other host
     *  void deliverData(String dataSent)
     *       Passes "dataSent" up to application layer. Only call this in the
     *       Receiver class.
     *  double getTime()
     *       Returns the current time of the simulator.  Might be useful for
     *       debugging.
     *  String getReceivedData()
     *       Returns a String with all data delivered to receiving process.
     *       Might be useful for debugging. You should only call this in the
     *       Sender class.
     *  void printEventList()
     *       Prints the current event list to stdout.  Might be useful for
     *       debugging, but probably not.
     *
     *
     *  Predefined Classes:
     *
     *  Message: Used to encapsulate a message coming from application layer
     *    Constructor:
     *      Message(String inputData):
     *          creates a new Message containing "inputData"
     *    Methods:
     *      boolean setData(String inputData):
     *          sets an existing Message's data to "inputData"
     *          returns true on success, false otherwise
     *      String getData():
     *          returns the data contained in the message
     *  Packet: Used to encapsulate a packet
     *    Constructors:
     *      Packet (Packet p):
     *          creates a new Packet, which is a copy of "p"
     *      Packet (int seq, int ack, int check, String newPayload)
     *          creates a new Packet with a sequence field of "seq", an
     *          ack field of "ack", a checksum field of "check", and a
     *          payload of "newPayload"
     *      Packet (int seq, int ack, int check)
     *          chreate a new Packet with a sequence field of "seq", an
     *          ack field of "ack", a checksum field of "check", and
     *          an empty payload
     *    Methods:
     *      boolean setSeqnum(int n)
     *          sets the Packet's sequence field to "n"
     *          returns true on success, false otherwise
     *      boolean setAcknum(int n)
     *          sets the Packet's ack field to "n"
     *          returns true on success, false otherwise
     *      boolean setChecksum(int n)
     *          sets the Packet's checksum to "n"
     *          returns true on success, false otherwise
     *      boolean setPayload(String newPayload)
     *          sets the Packet's payload to "newPayload"
     *          returns true on success, false otherwise
     *      int getSeqnum()
     *          returns the contents of the Packet's sequence field
     *      int getAcknum()
     *          returns the contents of the Packet's ack field
     *      int getChecksum()
     *          returns the checksum of the Packet
     *      String getPayload()
     *          returns the Packet's payload
     *
     */

    // Add any necessary class variables here. They can hold
    // state information for the receiver.
    int receiverSequenceState;
    int receiverAckNumber;
    Packet rcvpck;

    // Also add any necessary methods (e.g. checksum of a String)
    public int calculateChecksum(byte[] dataBytes){
      int byteValue = 0;
      for(byte b : dataBytes){
        int n = b & 0xFF;
        byteValue = n;
      }
      int checkSumValue = byteValue + receiverSequenceState + receiverAckNumber;
      return checkSumValue;
    }

    // This is the constructor.  Don't touch!
    public Receiver(int entityName,
                       EventList events,
                       double pLoss,
                       double pCorrupt,
                       int trace,
                       Random random)
    {
        super(entityName, events, pLoss, pCorrupt, trace, random);
    }


    // This routine will be called whenever a packet from the sender
    // (i.e. as a result of a udtSend() being done by a Sender procedure)
    // arrives at the receiver. Argument "packet" is the (possibly corrupted)
    // packet that was sent from the sender.
    protected void Input(Packet packet){
      String checksumInput = packet.getPayload();
      byte[] checksumInputBytes = checksumInput.getBytes();
      if(calculateChecksum(checksumInputBytes) == packet.getChecksum()){
        String dataSent = packet.getPayload();
        deliverData(dataSent);
        Packet ackPacket = new Packet(receiverSequenceState,receiverAckNumber,calculateChecksum(checksumInputBytes));
        udtSend(ackPacket);
      } else{
        //packet.setPayload("corrupt");
        //String corruptData = packet.getPayload();
        //deliverData(corruptData);
        //dont send ack and wait for sender to resend
      }
      if(receiverSequenceState == 0){
        receiverSequenceState = 1;
        receiverAckNumber = 1;
      } else {
        receiverSequenceState = 0;
        receiverAckNumber = 0;
      }
    }



    // This routine will be called once, before any of your other receiver-side
    // routines are called. The method should be used to do any required
    // initialization (e.g. of member variables you add to control the state
    // of the receiver).
    protected void Init(){
      receiverSequenceState = 0;
      receiverAckNumber = 0;
    }

}
