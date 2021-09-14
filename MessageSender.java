import java.util.Scanner;


/**
 * This class implements the sender.
 */
public class MessageSender
{
    // maximum transfer unit (frame length limit)
    private final int mtu;
    // Source of the messages.
    private final Scanner stdin;
    // Frame type
    private char frame_type;  
    // Length of segment
    protected String seg_length;
    // Value of checksum 
    private String checksum;
    // Length of frame
    private int frame_length;
    // Start bracket
    private char start_bracket = '[';
    // End bracket
    private char end_bracket = ']';
    // First field delimiter
    private char space1 = '~';
    // Second field delimiter
    private char space2 = '~';
    // Third field delimiter
    private char space3 = '~';

    private int frame_overhead = 10;
    private int frame_encoding = 0;
    

    /**
     * Create and initialize a new MessageSender.
     *
     * @param mtu the maximum transfer unit (MTU) (the length of a frame must
     * not exceed the MTU)
     */
    public MessageSender(int mtu) {
        this.mtu = mtu;
        this.stdin = new Scanner(System.in);
    }

    /**
     * Read a line from standard input layer and break it into frames
     * that are output on standard output, one frame per line.
     * Report any errors on standard error.
     */
    
    public void sendMessage() {
            String message = stdin.nextLine();
            if(message != null) {
                seg_length = calculateSegLength(message);
                frame_type = getFrameType(message);
                checksum = calculateCheckSum(frame_type, space1, seg_length, space2, message, space3);
                frame_length = checkFrameLength(start_bracket, frame_type, space1, seg_length, space2, message, space3, end_bracket);
                int halfOfMtu = mtu / 2;

                if(mtu < 20 && message.length() > 0)
                {
                    System.err.println("MTU value is too small to encode message");
                }
                else if(halfOfMtu >= message.length())
                {
                    System.out.println("["  + frame_type  + space1  + seg_length +  space2  +  message +  space3  +  checksum  + "]"); 

                }  
                else {  
                    if(mtu >= 20 && mtu < 30)
                    {
                        if(frame_overhead == 10)
                        {
                            frame_encoding = mtu - frame_overhead;
                            if(message.length() > 10  && message.length() <= 20)
                            {
                            
                            String partA = message.substring(0, frame_encoding);
                            String partB = message.substring(frame_encoding, message.length());
        
                            String seg_lengthA = calculateSegLength(partA);
                            String seg_lengthB = calculateSegLength(partB);
        
                            char frame_typeA = setFrameTypeForLM(message, partA);
                            char frame_typeB = setFrameTypeForLM(message, partB);
        
                            String checksumA = calculateCheckSum(frame_typeA, space1, seg_lengthA, space2, partA, space3);
                            String checksumB = calculateCheckSum(frame_typeB, space1, seg_lengthB, space2, partB, space3);
        
        
                            if(partA.length() > frame_encoding)
                            {
                                System.err.println("Size of message exceeds frame encoding");
                            }
                            else {
                                System.out.println("[" + frame_typeA + space1 + seg_lengthA + space2 + partA + space3 + checksumA + "]");
                            }
                            
                            if(partB.length() > frame_encoding)
                            {
                                System.err.println("Size of message exceeds frame encoding");
                            }
                            else {
                                System.out.println("[" + frame_typeB + space1 + seg_lengthB + space2 + partB + space3 + checksumB + "]");

                            }
                        }
                            else if(message.length() > 20 && message.length() <= 30)
                            {
                            frame_encoding = mtu - frame_overhead;
                            String partA = message.substring(0, frame_encoding);
                            String partB = message.substring(frame_encoding, frame_encoding*2);
                            String partC = message.substring(frame_encoding*2, message.length());
        
                            String seg_lengthA = calculateSegLength(partA);
                            String seg_lengthB = calculateSegLength(partB);
                            String seg_lengthC = calculateSegLength(partC);
        
                            char frame_typeA = 0;
                            char frame_typeB = 0;
                            char frame_typeC = 0;
        
                            if(message.substring(0,frame_encoding).equals(partA))
                            {
                                frame_typeA = 'D';
                            }
                            
                            if(message.substring(frame_encoding, frame_encoding*2).equals(partB))
                            {
                                frame_typeB = 'D';
                            }
        
                            if(message.substring(frame_encoding*2, message.length()).equals(partC))
                            {
                                frame_typeC = 'F';
                            }
        
        
                            String checksumA = calculateCheckSum(frame_typeA, space1, seg_lengthA, space2, partA, space3);
                            String checksumB = calculateCheckSum(frame_typeB, space1, seg_lengthB, space2, partB, space3);
                            String checksumC = calculateCheckSum(frame_typeC, space1, seg_lengthC, space2, partC, space3);
        
        
                            if(partA.length() > frame_encoding)
                            {
                                System.err.println("Size of message exceeds frame encoding");
                            }
                            else {
                                System.out.println("[" + frame_typeA + space1 + seg_lengthA + space2 + partA + space3 + checksumA + "]");
                            }
                            
                            if(partB.length() > frame_encoding)
                            {
                                System.err.println("Size of message exceeds frame encoding");
                            }
                            else {
                                System.out.println("[" + frame_typeB + space1 + seg_lengthB + space2 + partB + space3 + checksumB + "]");
                            }
        
                            if(partC.length() > frame_encoding)
                            {
                                System.err.println("Size of message exceeds frame encoding");
                            }
                            else {
                                System.out.println("[" + frame_typeC + space1 + seg_lengthC + space2 + partC + space3 + checksumC + "]");
                            }
                            } else if (message.length() > 30 && message.length() <= 40)
                            {
                                frame_encoding = mtu - frame_overhead;
                                String partA = message.substring(0, frame_encoding);
                                String partB = message.substring(frame_encoding, frame_encoding*2);
                                String partC = message.substring(frame_encoding*2, frame_encoding*3);
                                String partD = message.substring(frame_encoding*3, message.length());
            
                                String seg_lengthA = calculateSegLength(partA);
                                String seg_lengthB = calculateSegLength(partB);
                                String seg_lengthC = calculateSegLength(partC);
                                String seg_lengthD = calculateSegLength(partD);
            
                                char frame_typeA = 0;
                                char frame_typeB = 0;
                                char frame_typeC = 0;
                                char frame_typeD = 0;
                                

                            if(message.substring(0,frame_encoding).equals(partA))
                            {
                                frame_typeA = 'D';
                            }
                            
                            if(message.substring(frame_encoding, frame_encoding*2).equals(partB))
                            {
                                frame_typeB = 'D';
                            }
        
                            if(message.substring(frame_encoding*2, frame_encoding*3).equals(partC))
                            {
                                frame_typeC = 'D';
                            }
        
                            if(message.substring(frame_encoding*3, message.length()).equals(partD))
                            {
                                frame_typeD = 'F';
                            }
        
        
                            String checksumA = calculateCheckSum(frame_typeA, space1, seg_lengthA, space2, partA, space3);
                            String checksumB = calculateCheckSum(frame_typeB, space1, seg_lengthB, space2, partB, space3);
                            String checksumC = calculateCheckSum(frame_typeC, space1, seg_lengthC, space2, partC, space3);
                            String checksumD = calculateCheckSum(frame_typeD, space1, seg_lengthD, space2, partD, space3);
        
                            if(partA.length() > frame_encoding)
                            {
                                System.err.println("Size of message exceeds frame encoding");
                            }
                            else {
                                System.out.println("[" + frame_typeA + space1 + seg_lengthA + space2 + partA + space3 + checksumA + "]");
                            }
                            
                            if(partB.length() > frame_encoding)
                            {
                                System.err.println("Size of message exceeds frame encoding");
                            }
                            else {
                                System.out.println("[" + frame_typeB + space1 + seg_lengthB + space2 + partB + space3 + checksumB + "]");
                            }
        
                            if(partC.length() > frame_encoding)
                            {
                                System.err.println("Size of message exceeds frame encoding");
                            }
                            else {
                                System.out.println("[" + frame_typeC + space1 + seg_lengthC + space2 + partC + space3 + checksumC + "]");
                            }
        
                            if(partD.length() > frame_encoding)
                            {
                                System.err.println("Size of message exceeds frame encoding");
                            }
                            else {
                                System.out.println("[" + frame_typeD + space1 + seg_lengthD + space2 + partD + space3 + checksumD + "]");
                            }


                            }
                            else if(message.length() > 40 && message.length() <= 50)
                            {
                                frame_encoding = mtu - frame_overhead;
                                String partA = message.substring(0, frame_encoding);
                                String partB = message.substring(frame_encoding, frame_encoding*2);
                                String partC = message.substring(frame_encoding*2, frame_encoding*3);
                                String partD = message.substring(frame_encoding*3, frame_encoding*4);
                                String partE = message.substring(frame_encoding*4, message.length());
            
                                String seg_lengthA = calculateSegLength(partA);
                                String seg_lengthB = calculateSegLength(partB);
                                String seg_lengthC = calculateSegLength(partC);
                                String seg_lengthD = calculateSegLength(partD);
                                String seg_lengthE = calculateSegLength(partE);
            
                                char frame_typeA = 0;
                                char frame_typeB = 0;
                                char frame_typeC = 0;
                                char frame_typeD = 0;
                                char frame_typeE = 0;
        
        
                            if(message.substring(0,frame_encoding).equals(partA))
                            {
                                frame_typeA = 'D';
                            }
                            
                            if(message.substring(frame_encoding, frame_encoding*2).equals(partB))
                            {
                                frame_typeB = 'D';
                            }
        
                            if(message.substring(frame_encoding*2, frame_encoding*3).equals(partC))
                            {
                                frame_typeC = 'D';
                            }
        
                            if(message.substring(frame_encoding*3, frame_encoding*4).equals(partD))
                            {
                                frame_typeD = 'D';
                            }
        
                            if(message.substring(frame_encoding*4, message.length()).equals(partE))
                            {
                                frame_typeE = 'F';
                            }
        
        
                            String checksumA = calculateCheckSum(frame_typeA, space1, seg_lengthA, space2, partA, space3);
                            String checksumB = calculateCheckSum(frame_typeB, space1, seg_lengthB, space2, partB, space3);
                            String checksumC = calculateCheckSum(frame_typeC, space1, seg_lengthC, space2, partC, space3);
                            String checksumD = calculateCheckSum(frame_typeD, space1, seg_lengthD, space2, partD, space3);
                            String checksumE = calculateCheckSum(frame_typeE, space1, seg_lengthE, space2, partE, space3);

                            if(partA.length() > frame_encoding)
                            {
                                System.err.println("Size of message exceeds frame encoding");
                            }
                            else {
                                System.out.println("[" + frame_typeA + space1 + seg_lengthA + space2 + partA + space3 + checksumA + "]");
                            }
                            
                            if(partB.length() > frame_encoding)
                            {
                                System.err.println("Size of message exceeds frame encoding");
                            }
                            else {
                                System.out.println("[" + frame_typeB + space1 + seg_lengthB + space2 + partB + space3 + checksumB + "]");
                            }
        
                            if(partC.length() > frame_encoding)
                            {
                                System.err.println("Size of message exceeds frame encoding");
                            }
                            else {
                                System.out.println("[" + frame_typeC + space1 + seg_lengthC + space2 + partC + space3 + checksumC + "]");
                            }
        
                            if(partD.length() > frame_encoding)
                            {
                                System.err.println("Size of message exceeds frame encoding");
                            }
                            else {
                                System.out.println("[" + frame_typeD + space1 + seg_lengthD + space2 + partD + space3 + checksumD + "]");
                            }
        
                            if(partE.length() > frame_encoding)
                            {
                                System.err.println("Size of message exceeds frame encoding");
                            }
                            else {
                                System.out.println("[" + frame_typeE + space1 + seg_lengthE + space2 + partE + space3 + checksumE + "]");
                            }
    
                            } else if(message.length() > 50 && message.length() <= 60)
                            {
                                frame_encoding = mtu - frame_overhead;
                                String partA = message.substring(0, frame_encoding);
                                String partB = message.substring(frame_encoding, frame_encoding*2);
                                String partC = message.substring(frame_encoding*2, frame_encoding*3);
                                String partD = message.substring(frame_encoding*3, frame_encoding*4);
                                String partE = message.substring(frame_encoding*4, frame_encoding*5);
                                String partF = message.substring(frame_encoding*5, message.length());

                                String seg_lengthA = calculateSegLength(partA);
                                String seg_lengthB = calculateSegLength(partB);
                                String seg_lengthC = calculateSegLength(partC);
                                String seg_lengthD = calculateSegLength(partD);
                                String seg_lengthE = calculateSegLength(partE);
                                String seg_lengthF = calculateSegLength(partF);

                                char frame_typeA = 0;
                                char frame_typeB = 0;
                                char frame_typeC = 0;
                                char frame_typeD = 0;
                                char frame_typeE = 0;
                                char frame_typeF = 0;

                                if(message.substring(0,frame_encoding).equals(partA))
                                {
                                    frame_typeA = 'D';
                                }
                                
                                if(message.substring(frame_encoding, frame_encoding*2).equals(partB))
                                {
                                    frame_typeB = 'D';
                                }
            
                                if(message.substring(frame_encoding*2, frame_encoding*3).equals(partC))
                                {
                                    frame_typeC = 'D';
                                }
            
                                if(message.substring(frame_encoding*3, frame_encoding*4).equals(partD))
                                {
                                    frame_typeD = 'D';
                                }
            
                                if(message.substring(frame_encoding*4, frame_encoding*5).equals(partE))
                                {
                                    frame_typeE = 'D';
                                }
            
                                if(message.substring(frame_encoding*5, message.length()).equals(partF))
                                {
                                    frame_typeF = 'F';
                                }

                            String checksumA = calculateCheckSum(frame_typeA, space1, seg_lengthA, space2, partA, space3);
                            String checksumB = calculateCheckSum(frame_typeB, space1, seg_lengthB, space2, partB, space3);
                            String checksumC = calculateCheckSum(frame_typeC, space1, seg_lengthC, space2, partC, space3);
                            String checksumD = calculateCheckSum(frame_typeD, space1, seg_lengthD, space2, partD, space3);
                            String checksumE = calculateCheckSum(frame_typeE, space1, seg_lengthE, space2, partE, space3);
                            String checksumF = calculateCheckSum(frame_typeF, space1, seg_lengthF, space2, partF, space3);
                            
                            if(partA.length() > frame_encoding)
                            {
                                System.err.println("Size of message exceeds frame encoding");
                            }
                            else {
                                System.out.println("[" + frame_typeA + space1 + seg_lengthA + space2 + partA + space3 + checksumA + "]");
                            }
                            
                            if(partB.length() > frame_encoding)
                            {
                                System.err.println("Size of message exceeds frame encoding");
                            }
                            else {
                                System.out.println("[" + frame_typeB + space1 + seg_lengthB + space2 + partB + space3 + checksumB + "]");
                            }
        
                            if(partC.length() > frame_encoding)
                            {
                                System.err.println("Size of message exceeds frame encoding");
                            }
                            else {
                                System.out.println("[" + frame_typeC + space1 + seg_lengthC + space2 + partC + space3 + checksumC + "]");
                            }
        
                            if(partD.length() > frame_encoding)
                            {
                                System.err.println("Size of message exceeds frame encoding");
                            }
                            else {
                                System.out.println("[" + frame_typeD + space1 + seg_lengthD + space2 + partD + space3 + checksumD + "]");
                            }
        
                            if(partE.length() > frame_encoding)
                            {
                                System.err.println("Size of message exceeds frame encoding");
                            }
                            else {
                                System.out.println("[" + frame_typeE + space1 + seg_lengthE + space2 + partE + space3 + checksumE + "]");
                            }
        
                            if(partF.length() > frame_encoding)
                            {
                                System.err.println("Size of message exceeds frame encoding");
                            }
                            else {
                                System.out.println("[" + frame_typeF + space1 + seg_lengthF + space2 + partF + space3 + checksumF + "]");
                            }
                            } else if(message.length() > 60 && message.length() <= 70)
                            {
                                frame_encoding = mtu - frame_overhead;
                                String partA = message.substring(0, 10);
                                String partB = message.substring(10, 20);
                                String partC = message.substring(20, 30);
                                String partD = message.substring(30, 40);
                                String partE = message.substring(40, 50);
                                String partF = message.substring(50, 60);
                                String partG = message.substring(60, message.length());
        
        
                                String seg_lengthA = calculateSegLength(partA);
                                String seg_lengthB = calculateSegLength(partB);
                                String seg_lengthC = calculateSegLength(partC);
                                String seg_lengthD = calculateSegLength(partD);
                                String seg_lengthE = calculateSegLength(partE);
                                String seg_lengthF = calculateSegLength(partF);
                                String seg_lengthG = calculateSegLength(partG);
        
                                char frame_typeA = 0;
                                char frame_typeB = 0;
                                char frame_typeC = 0;
                                char frame_typeD = 0;
                                char frame_typeE = 0;
                                char frame_typeF = 0;
                                char frame_typeG = 0;

                                if(message.substring(0,10).equals(partA))
                                {
                                    frame_typeA = 'D';
                                }
                                
                                if(message.substring(10, 20).equals(partB))
                                {
                                    frame_typeB = 'D';
                                }
            
                                if(message.substring(20, 30).equals(partC))
                                {
                                    frame_typeC = 'D';
                                }
            
                                if(message.substring(30, 40).equals(partD))
                                {
                                    frame_typeD = 'D';
                                }
            
                                if(message.substring(40, 50).equals(partE))
                                {
                                    frame_typeE = 'D';
                                }
            
                                if(message.substring(50, 60).equals(partF))
                                {
                                    frame_typeF = 'D';
                                }
            
                                if(message.substring(60, message.length()).equals(partG))
                                {
                                    frame_typeG = 'F';
                                }

                                String checksumA = calculateCheckSum(frame_typeA, space1, seg_lengthA, space2, partA, space3);
                                String checksumB = calculateCheckSum(frame_typeB, space1, seg_lengthB, space2, partB, space3);
                                String checksumC = calculateCheckSum(frame_typeC, space1, seg_lengthC, space2, partC, space3);
                                String checksumD = calculateCheckSum(frame_typeD, space1, seg_lengthD, space2, partD, space3);
                                String checksumE = calculateCheckSum(frame_typeE, space1, seg_lengthE, space2, partE, space3);
                                String checksumF = calculateCheckSum(frame_typeF, space1, seg_lengthF, space2, partF, space3);
                                String checksumG = calculateCheckSum(frame_typeG, space1, seg_lengthG, space2, partG, space3);
                            
                            if(partA.length() > frame_encoding)
                            {
                                System.err.println("Size of message exceeds frame encoding");
                            }
                            else {
                                System.out.println("[" + frame_typeA + space1 + seg_lengthA + space2 + partA + space3 + checksumA + "]");
                            }
                            
                            if(partB.length() > frame_encoding)
                            {
                                System.err.println("Size of message exceeds frame encoding");
                            }
                            else {
                                System.out.println("[" + frame_typeB + space1 + seg_lengthB + space2 + partB + space3 + checksumB + "]");
                            }
        
                            if(partC.length() > frame_encoding)
                            {
                                System.err.println("Size of message exceeds frame encoding");
                            }
                            else {
                                System.out.println("[" + frame_typeC + space1 + seg_lengthC + space2 + partC + space3 + checksumC + "]");
                            }
        
                            if(partD.length() > frame_encoding)
                            {
                                System.err.println("Size of message exceeds frame encoding");
                            }
                            else {
                                System.out.println("[" + frame_typeD + space1 + seg_lengthD + space2 + partD + space3 + checksumD + "]");
                            }
        
                            if(partE.length() > frame_encoding)
                            {
                                System.err.println("Size of message exceeds frame encoding");
                            }
                            else {
                                System.out.println("[" + frame_typeE + space1 + seg_lengthE + space2 + partE + space3 + checksumE + "]");
                            }
        
                            if(partF.length() > frame_encoding)
                            {
                                System.err.println("Size of message exceeds frame encoding");
                            }
                            else {
                                System.out.println("[" + frame_typeF + space1 + seg_lengthF + space2 + partF + space3 + checksumF + "]");
                            }
        
                            if(partG.length() > frame_encoding)
                            {
                                System.err.println("Size of message exceeds frame encoding");
                            }
                            else {
                                System.out.println("[" + frame_typeG + space1 + seg_lengthG + space2 + partG + space3 + checksumG + "]");
                            }
                            } else if(message.length() > 70 && message.length() <= 80)
                            {
                                frame_encoding = mtu - frame_overhead;
                                String partA = message.substring(0, frame_encoding);
                                String partB = message.substring(frame_encoding, frame_encoding*2);
                                String partC = message.substring(frame_encoding*2, frame_encoding*3);
                                String partD = message.substring(frame_encoding*3, frame_encoding*4);
                                String partE = message.substring(frame_encoding*4, frame_encoding*5);
                                String partF = message.substring(frame_encoding*5, frame_encoding*6);
                                String partG = message.substring(frame_encoding*6, frame_encoding*7);
                                String partH = message.substring(frame_encoding*7, message.length());

                                String seg_lengthA = calculateSegLength(partA);
                                String seg_lengthB = calculateSegLength(partB);
                                String seg_lengthC = calculateSegLength(partC);
                                String seg_lengthD = calculateSegLength(partD);
                                String seg_lengthE = calculateSegLength(partE);
                                String seg_lengthF = calculateSegLength(partF);
                                String seg_lengthG = calculateSegLength(partG);
                                String seg_lengthH = calculateSegLength(partH);
        
        
                                char frame_typeA = 0;
                                char frame_typeB = 0;
                                char frame_typeC = 0;
                                char frame_typeD = 0;
                                char frame_typeE = 0;
                                char frame_typeF = 0;
                                char frame_typeG = 0;
                                char frame_typeH = 0;
        
                                if(message.substring(0,frame_encoding).equals(partA))
                                {
                                    frame_typeA = 'D';
                                }
                            
                                if(message.substring(frame_encoding, frame_encoding*2).equals(partB))
                                {
                                    frame_typeB = 'D';
                                }
        
                                if(message.substring(frame_encoding*2, frame_encoding*3).equals(partC))
                                {
                                    frame_typeC = 'D';
                                }
        
                                if(message.substring(frame_encoding*3, frame_encoding*4).equals(partD))
                                {
                                    frame_typeD = 'D';
                                }
        
                                if(message.substring(frame_encoding*4, frame_encoding*5).equals(partE))
                                {
                                    frame_typeE = 'D';
                                }
        
                                if(message.substring(frame_encoding*5, frame_encoding*6).equals(partF))
                                {
                                    frame_typeF = 'D';
                                }
        
                                if(message.substring(frame_encoding*6, frame_encoding*7).equals(partG))
                                {
                                    frame_typeG = 'D';
                                }
        
                                if(message.substring(frame_encoding*7, message.length()).equals(partH))
                                {
                                    frame_typeH = 'F';
                                }

                                String checksumA = calculateCheckSum(frame_typeA, space1, seg_lengthA, space2, partA, space3);
                                String checksumB = calculateCheckSum(frame_typeB, space1, seg_lengthB, space2, partB, space3);
                                String checksumC = calculateCheckSum(frame_typeC, space1, seg_lengthC, space2, partC, space3);
                                String checksumD = calculateCheckSum(frame_typeD, space1, seg_lengthD, space2, partD, space3);
                                String checksumE = calculateCheckSum(frame_typeE, space1, seg_lengthE, space2, partE, space3);
                                String checksumF = calculateCheckSum(frame_typeF, space1, seg_lengthF, space2, partF, space3);
                                String checksumG = calculateCheckSum(frame_typeG, space1, seg_lengthG, space2, partG, space3);
                                String checksumH = calculateCheckSum(frame_typeH, space1, seg_lengthH, space2, partH, space3);

                            if(partA.length() > frame_encoding)
                            {
                                System.err.println("Size of message exceeds frame encoding");
                            }
                            else {
                                System.out.println("[" + frame_typeA + space1 + seg_lengthA + space2 + partA + space3 + checksumA + "]");
                            }
                            
                            if(partB.length() > frame_encoding)
                            {
                                System.err.println("Size of message exceeds frame encoding");
                            }
                            else {
                                System.out.println("[" + frame_typeB + space1 + seg_lengthB + space2 + partB + space3 + checksumB + "]");
                            }
        
                            if(partC.length() > frame_encoding)
                            {
                                System.err.println("Size of message exceeds frame encoding");
                            }
                            else {
                                System.out.println("[" + frame_typeC + space1 + seg_lengthC + space2 + partC + space3 + checksumC + "]");
                            }
        
                            if(partD.length() > frame_encoding)
                            {
                                System.err.println("Size of message exceeds frame encoding");
                            }
                            else {
                                System.out.println("[" + frame_typeD + space1 + seg_lengthD + space2 + partD + space3 + checksumD + "]");
                            }
        
                            if(partE.length() > frame_encoding)
                            {
                                System.err.println("Size of message exceeds frame encoding");
                            }
                            else {
                                System.out.println("[" + frame_typeE + space1 + seg_lengthE + space2 + partE + space3 + checksumE + "]");
                            }
        
                            if(partF.length() > frame_encoding)
                            {
                                System.err.println("Size of message exceeds frame encoding");
                            }
                            else {
                                System.out.println("[" + frame_typeF + space1 + seg_lengthF + space2 + partF + space3 + checksumF + "]");
                            }
        
                            if(partG.length() > frame_encoding)
                            {
                                System.err.println("Size of message exceeds frame encoding");
                            }
                            else {
                                System.out.println("[" + frame_typeG + space1 + seg_lengthG + space2 + partG + space3 + checksumG + "]");
                            }
        
                            if(partH.length() > frame_encoding)
                            {
                                System.err.println("Size of message exceeds frame encoding");
                            }
                            else {
                                System.out.println("[" + frame_typeH + space1 + seg_lengthH + space2 + partH + space3 + checksumH + "]");
                            }
                            }
                            else if(message.length() > 80 && message.length() <= 90)
                            {
                                frame_encoding = mtu - frame_overhead;
                                String partA = message.substring(0, frame_encoding);
                                String partB = message.substring(frame_encoding, frame_encoding*2);
                                String partC = message.substring(frame_encoding*2, frame_encoding*3);
                                String partD = message.substring(frame_encoding*3, frame_encoding*4);
                                String partE = message.substring(frame_encoding*4, frame_encoding*5);
                                String partF = message.substring(frame_encoding*5, frame_encoding*6);
                                String partG = message.substring(frame_encoding*6, frame_encoding*7);
                                String partH = message.substring(frame_encoding*7, frame_encoding*8);
                                String partI = message.substring(frame_encoding*8, message.length());

                                String seg_lengthA = calculateSegLength(partA);
                                String seg_lengthB = calculateSegLength(partB);
                                String seg_lengthC = calculateSegLength(partC);
                                String seg_lengthD = calculateSegLength(partD);
                                String seg_lengthE = calculateSegLength(partE);
                                String seg_lengthF = calculateSegLength(partF);
                                String seg_lengthG = calculateSegLength(partG);
                                String seg_lengthH = calculateSegLength(partH);
                                String seg_lengthI = calculateSegLength(partI);

                                    
                                char frame_typeA = 0;
                                char frame_typeB = 0;
                                char frame_typeC = 0;
                                char frame_typeD = 0;
                                char frame_typeE = 0;
                                char frame_typeF = 0;
                                char frame_typeG = 0;
                                char frame_typeH = 0;
                                char frame_typeI = 0;
                                
                                if(message.substring(0,frame_encoding).equals(partA))
                                {
                                    frame_typeA = 'D';
                                }
                                
                                if(message.substring(frame_encoding, frame_encoding*2).equals(partB))
                                {
                                    frame_typeB = 'D';
                                }
            
                                if(message.substring(frame_encoding*2, frame_encoding*3).equals(partC))
                                {
                                    frame_typeC = 'D';
                                }
            
                                if(message.substring(frame_encoding*3, frame_encoding*4).equals(partD))
                                {
                                    frame_typeD = 'D';
                                }
            
                                if(message.substring(frame_encoding*4, frame_encoding*5).equals(partE))
                                {
                                    frame_typeE = 'D';
                                }
            
                                if(message.substring(frame_encoding*5, frame_encoding*6).equals(partF))
                                {
                                    frame_typeF = 'D';
                                }
            
                                if(message.substring(frame_encoding*6, frame_encoding*7).equals(partG))
                                {
                                    frame_typeG = 'D';
                                }
            
                                if(message.substring(frame_encoding*7, frame_encoding*8).equals(partH))
                                {
                                    frame_typeH = 'D';
                                }
            
                                if(message.substring(frame_encoding*8, message.length()).equals(partI))
                                {
                                    frame_typeI = 'F';
                                }

                                String checksumA = calculateCheckSum(frame_typeA, space1, seg_lengthA, space2, partA, space3);
                                String checksumB = calculateCheckSum(frame_typeB, space1, seg_lengthB, space2, partB, space3);
                                String checksumC = calculateCheckSum(frame_typeC, space1, seg_lengthC, space2, partC, space3);
                                String checksumD = calculateCheckSum(frame_typeD, space1, seg_lengthD, space2, partD, space3);
                                String checksumE = calculateCheckSum(frame_typeE, space1, seg_lengthE, space2, partE, space3);
                                String checksumF = calculateCheckSum(frame_typeF, space1, seg_lengthF, space2, partF, space3);
                                String checksumG = calculateCheckSum(frame_typeG, space1, seg_lengthG, space2, partG, space3);
                                String checksumH = calculateCheckSum(frame_typeH, space1, seg_lengthH, space2, partH, space3);
                                String checksumI = calculateCheckSum(frame_typeI, space1, seg_lengthI, space2, partI, space3);

                            if(partA.length() > frame_encoding)
                            {
                                System.err.println("Size of message exceeds frame encoding");
                            }
                            else {
                                System.out.println("[" + frame_typeA + space1 + seg_lengthA + space2 + partA + space3 + checksumA + "]");
                            }
                            
                            if(partB.length() > frame_encoding)
                            {
                                System.err.println("Size of message exceeds frame encoding");
                            }
                            else {
                                System.out.println("[" + frame_typeB + space1 + seg_lengthB + space2 + partB + space3 + checksumB + "]");
                            }
        
                            if(partC.length() > frame_encoding)
                            {
                                System.err.println("Size of message exceeds frame encoding");
                            }
                            else {
                                System.out.println("[" + frame_typeC + space1 + seg_lengthC + space2 + partC + space3 + checksumC + "]");
                            }
        
                            if(partD.length() > frame_encoding)
                            {
                                System.err.println("Size of message exceeds frame encoding");
                            }
                            else {
                                System.out.println("[" + frame_typeD + space1 + seg_lengthD + space2 + partD + space3 + checksumD + "]");
                            }
        
                            if(partE.length() > frame_encoding)
                            {
                                System.err.println("Size of message exceeds frame encoding");
                            }
                            else {
                                System.out.println("[" + frame_typeE + space1 + seg_lengthE + space2 + partE + space3 + checksumE + "]");
                            }
        
                            if(partF.length() > frame_encoding)
                            {
                                System.err.println("Size of message exceeds frame encoding");
                            }
                            else {
                                System.out.println("[" + frame_typeF + space1 + seg_lengthF + space2 + partF + space3 + checksumF + "]");
                            }
        
                            if(partG.length() > frame_encoding)
                            {
                                System.err.println("Size of message exceeds frame encoding");
                            }
                            else {
                                System.out.println("[" + frame_typeG + space1 + seg_lengthG + space2 + partG + space3 + checksumG + "]");
                            }
        
                            if(partH.length() > frame_encoding)
                            {
                                System.err.println("Size of message exceeds frame encoding");
                            }
                            else {
                                System.out.println("[" + frame_typeH + space1 + seg_lengthH + space2 + partH + space3 + checksumH + "]");
                            }
        
                            if(partI.length() > frame_encoding)
                            {
                                System.err.println("Size of message exceeds frame encoding");
                            }
                            else {
                                System.out.println("[" + frame_typeI + space1 + seg_lengthI + space2 + partI + space3 + checksumI + "]");
                            }
                            }
                            else if(message.length() > 90 && message.length() <= 99 || mtu > 99 && seg_length.length() == 2)
                            {
                                frame_encoding = mtu - frame_overhead;
                                String partA = message.substring(0, frame_encoding);
                                String partB = message.substring(frame_encoding, frame_encoding*2);
                                String partC = message.substring(frame_encoding*2, frame_encoding*3);
                                String partD = message.substring(frame_encoding*3, frame_encoding*4);
                                String partE = message.substring(frame_encoding*4, frame_encoding*5);
                                String partF = message.substring(frame_encoding*5, frame_encoding*6);
                                String partG = message.substring(frame_encoding*6, frame_encoding*7);
                                String partH = message.substring(frame_encoding*7, frame_encoding*8);
                                String partI = message.substring(frame_encoding*8, frame_encoding*9);
                                String partJ = message.substring(frame_encoding*9, message.length());

                                String seg_lengthA = calculateSegLength(partA);
                                String seg_lengthB = calculateSegLength(partB);
                                String seg_lengthC = calculateSegLength(partC);
                                String seg_lengthD = calculateSegLength(partD);
                                String seg_lengthE = calculateSegLength(partE);
                                String seg_lengthF = calculateSegLength(partF);
                                String seg_lengthG = calculateSegLength(partG);
                                String seg_lengthH = calculateSegLength(partH);
                                String seg_lengthI = calculateSegLength(partI);
                                String seg_lengthJ = calculateSegLength(partJ);
        
                                char frame_typeA = 0;
                                char frame_typeB = 0;
                                char frame_typeC = 0;
                                char frame_typeD = 0;
                                char frame_typeE = 0;
                                char frame_typeF = 0;
                                char frame_typeG = 0;
                                char frame_typeH = 0;
                                char frame_typeI = 0;
                                char frame_typeJ = 0;

                                if(message.substring(0, frame_encoding).equals(partA))
                                {
                                    frame_typeA = 'D';
                                }
                                
                                if(message.substring(frame_encoding, frame_encoding*2).equals(partB))
                                {
                                    frame_typeB = 'D';
                                }
            
                                if(message.substring(frame_encoding*2, frame_encoding*3).equals(partC))
                                {
                                    frame_typeC = 'D';
                                }
            
                                if(message.substring(frame_encoding*3, frame_encoding*4).equals(partD))
                                {
                                    frame_typeD = 'D';
                                }
            
                                if(message.substring(frame_encoding*4, frame_encoding*5).equals(partE))
                                {
                                    frame_typeE = 'D';
                                }
            
                                if(message.substring(frame_encoding*5, frame_encoding*6).equals(partF))
                                {
                                    frame_typeF = 'D';
                                }
            
                                if(message.substring(frame_encoding*6, frame_encoding*7).equals(partG))
                                {
                                    frame_typeG = 'D';
                                }
            
                                if(message.substring(frame_encoding*7, frame_encoding*8).equals(partH))
                                {
                                    frame_typeH = 'D';
                                }
            
                                if(message.substring(frame_encoding*8, frame_encoding*9).equals(partI))
                                {
                                    frame_typeI = 'D';
                                }
            
                                if(message.substring(frame_encoding*9, message.length()).equals(partJ))
                                {
                                    frame_typeJ = 'F';
                                }
    

                                String checksumA = calculateCheckSum(frame_typeA, space1, seg_lengthA, space2, partA, space3);
                                String checksumB = calculateCheckSum(frame_typeB, space1, seg_lengthB, space2, partB, space3);
                                String checksumC = calculateCheckSum(frame_typeC, space1, seg_lengthC, space2, partC, space3);
                                String checksumD = calculateCheckSum(frame_typeD, space1, seg_lengthD, space2, partD, space3);
                                String checksumE = calculateCheckSum(frame_typeE, space1, seg_lengthE, space2, partE, space3);
                                String checksumF = calculateCheckSum(frame_typeF, space1, seg_lengthF, space2, partF, space3);
                                String checksumG = calculateCheckSum(frame_typeG, space1, seg_lengthG, space2, partG, space3);
                                String checksumH = calculateCheckSum(frame_typeH, space1, seg_lengthH, space2, partH, space3);
                                String checksumI = calculateCheckSum(frame_typeI, space1, seg_lengthI, space2, partI, space3);
                                String checksumJ = calculateCheckSum(frame_typeJ, space1, seg_lengthJ, space2, partJ, space3);

                                if(partA.length() > frame_encoding)
                                {
                                    System.err.println("Size of message exceeds frame encoding");
                                }
                                else {
                                    System.out.println("[" + frame_typeA + space1 + seg_lengthA + space2 + partA + space3 + checksumA + "]");
                                }
                            
                                if(partB.length() > frame_encoding)
                                {
                                    System.err.println("Size of message exceeds frame encoding");
                                }
                                else {
                                    System.out.println("[" + frame_typeB + space1 + seg_lengthB + space2 + partB + space3 + checksumB + "]");
                                }
        
                                if(partC.length() > frame_encoding)
                                {
                                    System.err.println("Size of message exceeds frame encoding");
                                }
                                else {
                                    System.out.println("[" + frame_typeC + space1 + seg_lengthC + space2 + partC + space3 + checksumC + "]");
                                }
        
                                if(partD.length() > frame_encoding)
                                {
                                    System.err.println("Size of message exceeds frame encoding");
                                }
                                else {
                                    System.out.println("[" + frame_typeD + space1 + seg_lengthD + space2 + partD + space3 + checksumD + "]");
                                }
        
                                if(partE.length() > frame_encoding)
                                {
                                    System.err.println("Size of message exceeds frame encoding");
                                }
                                else {
                                    System.out.println("[" + frame_typeE + space1 + seg_lengthE + space2 + partE + space3 + checksumE + "]");
                                }
        
                                if(partF.length() > frame_encoding)
                                {
                                    System.err.println("Size of message exceeds frame encoding");
                                }
                                else {
                                    System.out.println("[" + frame_typeF + space1 + seg_lengthF + space2 + partF + space3 + checksumF + "]");
                                }
        
                                if(partG.length() > frame_encoding)
                                {
                                    System.err.println("Size of message exceeds frame encoding");
                                }
                                else {
                                    System.out.println("[" + frame_typeG + space1 + seg_lengthG + space2 + partG + space3 + checksumG + "]");
                                }
            
                                if(partH.length() > frame_encoding)
                                {
                                    System.err.println("Size of message exceeds frame encoding");
                                }
                                else {
                                    System.out.println("[" + frame_typeH + space1 + seg_lengthH + space2 + partH + space3 + checksumH + "]");
                                }
            
                                if(partI.length() > frame_encoding)
                                {
                                    System.err.println("Size of message exceeds frame encoding");
                                }
                                else {
                                    System.out.println("[" + frame_typeI + space1 + seg_lengthI + space2 + partI + space3 + checksumI + "]");
                                }
            
                                if(partJ.length() > frame_encoding)
                                {
                                    System.err.println("Size of message exceeds frame encoding");
                                }
                                else {
                                    System.out.println("[" + frame_typeJ + space1 + seg_lengthJ + space2 + partJ + space3 + checksumJ + "]");
                                }




                            }
                        
                    }

                }
                else if(mtu >= 30 || mtu < 40)
                {
                    encodingMtu30(mtu, message, frame_type, space1, seg_length, space2, space3, checksum);
                }
                else if(mtu >= 40 || mtu < 50)
                {
                    encodingMtu40(mtu, message, frame_type, space1, seg_length, space2, space3, checksum);
                }
                else if(mtu >= 50 || mtu < 60)
                {
                    encodingMtu50(mtu, message, frame_type, space1, seg_length, space2, space3, checksum);
                }
                else if(mtu >= 60 || mtu < 70)
                {
                    encodingMtu60(mtu, message, frame_type, space1, seg_length, space2, space3, checksum);
                }
                else if(mtu >= 70 || mtu < 80)
                {
                    encodingMtu70(mtu, message, frame_type, space1, seg_length, space2, space3, checksum);
                }
                else if(mtu >= 80 || mtu < 90)
                {
                    encodingMtu80(mtu, message, frame_type, space1, seg_length, space2, space3, checksum);
                }
                else if(mtu >= 90 || mtu < 100)
                {
                    encodingMtu90(mtu, message, frame_type, space1, seg_length, space2, space3, checksum);
                }
                
                else if (mtu > 99 && mtu == message.length() * 2)
                {
                    encodingMtu90(mtu, message, frame_type, space1, seg_length, space2, space3, checksum);
                }
                else {
                     checkMtuValue(mtu, message);
                }
               }
            }
               else {
                System.out.println("["  + frame_type  + space1  + seg_length +  space2  +  space3  +  checksum  + "]");
               }
       }         


    
    /**
     * 
     * Checks whether MTU passed is too big to support the frame encoding value. 
     * Prints out an error if it is too big
    */  
    public void checkMtuValue(int mtu, String message)
    {
        int msgLength = message.length()*2;
        if(mtu > msgLength)
        {
            System.err.println("MTU value is too large for frame encoding value.");
        }
    }

    /**
     * Encodes frames for MTU values between 30 and 39.
     */
    public void encodingMtu30(int mtu, String message, char frame_type, char space1, String seg_length, char space2, char space3, String checksum)
    {
        boolean lengthLessThan99 = false;
        if(message.length() <= 99)
        {
            lengthLessThan99 = true;
            frame_encoding = mtu - frame_overhead;
        if(message.length() < frame_encoding)
        {
            seg_length = calculateSegLength(message);
            frame_type = getFrameType(message);
            checksum = calculateCheckSum(frame_type, space1, seg_length, space2, message, space3);
            frame_length = checkFrameLength(start_bracket, frame_type, space1, seg_length, space2, message, space3, end_bracket);
            System.out.println("[" + frame_type + space1 + seg_length + space2 + message + space3 + checksum + "]");
        }
        else {
                frame_encoding = mtu - frame_overhead;
                String partA = message.substring(0, frame_encoding);
                String partB = message.substring(frame_encoding, message.length());
                
                String seg_lengthA = calculateSegLength(partA);
                String seg_lengthB = calculateSegLength(partB);

                char frame_typeA = 0;
                char frame_typeB = 0;

                if(message.substring(0,frame_encoding).equals(partA))
                {
                    frame_typeA = 'D';
                }
                
                if(message.substring(frame_encoding, message.length()).equals(partB))
                {
                    frame_typeB = 'F';
                }

                String checksumA = calculateCheckSum(frame_typeA, space1, seg_lengthA, space2, partA, space3);
                String checksumB = calculateCheckSum(frame_typeB, space1, seg_lengthB, space2, partB, space3);

                if(partA.length() > frame_encoding)
                {
                    System.err.println("Segment length is too big for frame encoding value");
                }
                else {
                    System.out.println("[" + frame_typeA + space1 + seg_lengthA + space2 + partA + space3 + checksumA + "]");
                }
                
                if(partB.length() > partA.length())
                {
                    System.err.println("Message is too big for frame encoding value");
                }
                else {
                    System.out.println("[" + frame_typeB + space1 + seg_lengthB + space2 + partB + space3 + checksumB + "]");
                }
        }
        }
        else {
            System.err.println("The segment length of this message exceeds 99 characters");
        }
    }
    
    /**
     * Encodes frames for MTU value between 40 and 49.
     */
    public void encodingMtu40(int mtu, String message, char frame_type, char space1, String seg_length, char space2, char space3, String checksum)
    {
        boolean lengthLessThan99 = false;
        if(message.length() <= 99)
        {
            lengthLessThan99 = true;
            frame_encoding = mtu - frame_overhead;
        if(message.length() < frame_encoding)
        {
            seg_length = calculateSegLength(message);
            frame_type = getFrameType(message);
            checksum = calculateCheckSum(frame_type, space1, seg_length, space2, message, space3);
            frame_length = checkFrameLength(start_bracket, frame_type, space1, seg_length, space2, message, space3, end_bracket);
            System.out.println("[" + frame_type + space1 + seg_length + space2 + message + space3 + checksum + "]");
        }
        else  {
                frame_encoding = mtu - frame_overhead;
                //if message length is greater than half of mtu.
                String partA = message.substring(0, frame_encoding);
                String partB = message.substring(frame_encoding, message.length());
                    
                String seg_lengthA = calculateSegLength(partA);
                String seg_lengthB = calculateSegLength(partB);
    
                char frame_typeA = 0;
                char frame_typeB = 0;

            if(message.substring(0,frame_encoding).equals(partA))
            {
                frame_typeA = 'D';
            }
                    
            if(message.substring(frame_encoding, message.length()).equals(partB))
            {
                frame_typeB = 'F';
            }
    
            String checksumA = calculateCheckSum(frame_typeA, space1, seg_lengthA, space2, partA, space3);
            String checksumB = calculateCheckSum(frame_typeB, space1, seg_lengthB, space2, partB, space3);
    
            if(partA.length() > frame_encoding)
            {
                System.err.println("Segment length is too big for frame encoding value");
            }
            else {
                System.out.println("[" + frame_typeA + space1 + seg_lengthA + space2 + partA + space3 + checksumA + "]");
            }
                    
            if(partB.length() > partA.length())
            {
                System.err.println("Segment length is too big for frame encoding value");
            }
            else {
                System.out.println("[" + frame_typeB + space1 + seg_lengthB + space2 + partB + space3 + checksumB + "]");
            }   

            }
        } 
        else {
        System.err.println("The segment length of this message exceeds 99 characters");
    }
    }
                

    /**
     * Encodes frames for MTU value between 50 and 59
     */
    public void encodingMtu50(int mtu, String message, char frame_type, char space1, String seg_length, char space2, char space3, String checksum)
    {
        boolean lengthLessThan99 = false;
        if(message.length() <= 99)
        {
            lengthLessThan99 = true;
            frame_encoding = mtu - frame_overhead;
        if(message.length() < frame_encoding)
        {
            seg_length = calculateSegLength(message);
            frame_type = getFrameType(message);
            checksum = calculateCheckSum(frame_type, space1, seg_length, space2, message, space3);
            frame_length = checkFrameLength(start_bracket, frame_type, space1, seg_length, space2, message, space3, end_bracket);
            System.out.println("[" + frame_type + space1 + seg_length + space2 + message + space3 + checksum + "]");
        }
        else { 
            frame_encoding = mtu - frame_overhead;
            String partA = message.substring(0, frame_encoding);
            String partB = message.substring(frame_encoding, message.length());
                        
            String seg_lengthA = calculateSegLength(partA);
            String seg_lengthB = calculateSegLength(partB);
        
            char frame_typeA = 0;
            char frame_typeB = 0;
        
            if(message.substring(0,frame_encoding).equals(partA))
            {
                frame_typeA = 'D';
            }
                        
            if(message.substring(frame_encoding, message.length()).equals(partB))
            {
                frame_typeB = 'F';
            }
        
            String checksumA = calculateCheckSum(frame_typeA, space1, seg_lengthA, space2, partA, space3);
            String checksumB = calculateCheckSum(frame_typeB, space1, seg_lengthB, space2, partB, space3);
        
            if(partA.length() > frame_encoding)
            {
                System.err.println("Segment length is too big for frame encoding value");
            }
            else {
                System.out.println("[" + frame_typeA + space1 + seg_lengthA + space2 + partA + space3 + checksumA + "]");
            }
                        
            if(partB.length() > partA.length())
            {
                System.err.println("Segment length is too big for frame encoding value");
            }
            else {
                System.out.println("[" + frame_typeB + space1 + seg_lengthB + space2 + partB + space3 + checksumB + "]");
            }
                            
        }
    }
    else {
        System.err.println("The segment length of this message exceeds 99 characters");
    }     
}
    /**
      * Encodes frames for MTU value between 60 and 69
     */
    public void encodingMtu60(int mtu, String message, char frame_type, char space1, String seg_length, char space2, char space3, String checksum)
    {
        boolean lengthLessThan99 = false;
        if(message.length() <= 99)
        {
            lengthLessThan99 = true;
            frame_encoding = mtu - frame_overhead;
        if(message.length() < frame_encoding)
        {
            seg_length = calculateSegLength(message);
            frame_type = getFrameType(message);
            checksum = calculateCheckSum(frame_type, space1, seg_length, space2, message, space3);
            frame_length = checkFrameLength(start_bracket, frame_type, space1, seg_length, space2, message, space3, end_bracket);
            System.out.println("[" + frame_type + space1 + seg_length + space2 + message + space3 + checksum + "]");                
        }
        else {
                frame_encoding = mtu - frame_overhead;
                String partA = message.substring(0, frame_encoding);
                String partB = message.substring(frame_encoding, message.length());
                            
                String seg_lengthA = calculateSegLength(partA);
                String seg_lengthB = calculateSegLength(partB);
            
                char frame_typeA = 0;
                char frame_typeB = 0;
            
                if(message.substring(0,frame_encoding).equals(partA))
                {
                    frame_typeA = 'D';
                }
                            
                if(message.substring(frame_encoding, message.length()).equals(partB))
                {
                    frame_typeB = 'F';
                }
            
                String checksumA = calculateCheckSum(frame_typeA, space1, seg_lengthA, space2, partA, space3);
                String checksumB = calculateCheckSum(frame_typeB, space1, seg_lengthB, space2, partB, space3);
            
                if(partA.length() > frame_encoding)
                {
                    System.err.println("Segment length is too big for frame encoding value");
                }
                else {
                    System.out.println("[" + frame_typeA + space1 + seg_lengthA + space2 + partA + space3 + checksumA + "]");
                }
                            
                if(partB.length() > partA.length())
                {
                    System.err.println("Segment length is too big for frame encoding value");
                }
                else {
                    System.out.println("[" + frame_typeB + space1 + seg_lengthB + space2 + partB + space3 + checksumB + "]");
                }     
                }
            }
            else {
                System.err.println("The segment length of this message exceeds 99 characters");
            }
    } 
    
    /**
      * Encodes frames for MTU value between 70 and 79
     */
    public void encodingMtu70(int mtu, String message, char frame_type, char space1, String seg_length, char space2, char space3, String checksum)
    {
        boolean lengthLessThan99 = false;
        if(message.length() <= 99)
        {
            lengthLessThan99 = true;
            frame_encoding = mtu - frame_overhead;
        if(message.length() < frame_encoding)
        {
            seg_length = calculateSegLength(message);
            frame_type = getFrameType(message);
            checksum = calculateCheckSum(frame_type, space1, seg_length, space2, message, space3);
            frame_length = checkFrameLength(start_bracket, frame_type, space1, seg_length, space2, message, space3, end_bracket);
            System.out.println("[" + frame_type + space1 + seg_length + space2 + message + space3 + checksum + "]");                
        }
        else {
                frame_encoding = mtu - frame_overhead;
                String partA = message.substring(0, frame_encoding);
                String partB = message.substring(frame_encoding, message.length());
                            
                String seg_lengthA = calculateSegLength(partA);
                String seg_lengthB = calculateSegLength(partB);
            
                char frame_typeA = 0;
                char frame_typeB = 0;
            
                if(message.substring(0, frame_encoding).equals(partA))
                {
                    frame_typeA = 'D';
                }
                            
                if(message.substring(frame_encoding, message.length()).equals(partB))
                {
                    frame_typeB = 'F';
                }
            
                String checksumA = calculateCheckSum(frame_typeA, space1, seg_lengthA, space2, partA, space3);
                String checksumB = calculateCheckSum(frame_typeB, space1, seg_lengthB, space2, partB, space3);
            
                if(partA.length() > frame_encoding)
                {
                    System.err.println("Segment length is too big for frame encoding value");
                }
                else {
                    System.out.println("[" + frame_typeA + space1 + seg_lengthA + space2 + partA + space3 + checksumA + "]");
                }
                            
                if(partB.length() > partA.length())
                {
                    System.err.println("Segment length is too big for frame encoding value");
                }
                else {
                    System.out.println("[" + frame_typeB + space1 + seg_lengthB + space2 + partB + space3 + checksumB + "]");
                }
                } 
            }
            else {
                System.err.println("The segment length of this message exceeds 99 characters");
            }
    } 
    /**
    * Encodes frames for MTU value between 80 and 89.
    */
    public void encodingMtu80(int mtu, String message, char frame_type, char space1, String seg_length, char space2, char space3, String checksum)
    {
        boolean lengthLessThan99 = false;
        if(message.length() <= 99)
        {
            lengthLessThan99 = true;
            frame_encoding = mtu - frame_overhead;
        if(message.length() < frame_encoding)
        {
            seg_length = calculateSegLength(message);
            frame_type = getFrameType(message);
            checksum = calculateCheckSum(frame_type, space1, seg_length, space2, message, space3);
            frame_length = checkFrameLength(start_bracket, frame_type, space1, seg_length, space2, message, space3, end_bracket);
            System.out.println("[" + frame_type + space1 + seg_length + space2 + message + space3 + checksum + "]");                
        }
        else {
            frame_encoding = mtu - frame_overhead;
            String partA = message.substring(0, frame_encoding);
            String partB = message.substring(frame_encoding, message.length());
                        
            String seg_lengthA = calculateSegLength(partA);
            String seg_lengthB = calculateSegLength(partB);
        
            char frame_typeA = 0;
            char frame_typeB = 0;
        
            if(message.substring(0, frame_encoding).equals(partA))
            {
                frame_typeA = 'D';
            }
                        
            if(message.substring(frame_encoding, message.length()).equals(partB))
            {
                frame_typeB = 'F';
            }
        
            String checksumA = calculateCheckSum(frame_typeA, space1, seg_lengthA, space2, partA, space3);
            String checksumB = calculateCheckSum(frame_typeB, space1, seg_lengthB, space2, partB, space3);
        
            if(partA.length() > frame_encoding)
            {
                System.err.println("Segment length is too big for frame encoding value");
            }
            else {
                System.out.println("[" + frame_typeA + space1 + seg_lengthA + space2 + partA + space3 + checksumA + "]");
            }
                        
            if(partB.length() > partA.length())
            {
                System.err.println("Segment length is too big for frame encoding value");
            }
            else {
                System.out.println("[" + frame_typeB + space1 + seg_lengthB + space2 + partB + space3 + checksumB + "]");
            }

            } 
        }
        else {
            System.err.println("The segment length of this message exceeds 99 characters");
        }  
                                
    } 
      /**
      * Encodes frames for MTU value between 90 and 99
     */     
    public void encodingMtu90(int mtu, String message, char frame_type, char space1, String seg_length, char space2, char space3, String checksum)
    {
        boolean lengthLessThan99 = false;
        if(message.length() <= 99)
        {
            lengthLessThan99 = true;
            frame_encoding = mtu - frame_overhead;
        if(message.length() < frame_encoding )
        {
            seg_length = calculateSegLength(message);
            frame_type = getFrameType(message);
            checksum = calculateCheckSum(frame_type, space1, seg_length, space2, message, space3);
            frame_length = checkFrameLength(start_bracket, frame_type, space1, seg_length, space2, message, space3, end_bracket);
            System.out.println("[" + frame_type + space1 + seg_length + space2 + message + space3 + checksum + "]");                
        }
        else {
            frame_encoding = mtu - frame_overhead;
            String partA = message.substring(0, frame_encoding);
            String partB = message.substring(frame_encoding, message.length());
                        
            String seg_lengthA = calculateSegLength(partA);
            String seg_lengthB = calculateSegLength(partB);
        
            char frame_typeA = 0;
            char frame_typeB = 0;
        
            if(message.substring(0, frame_encoding).equals(partA))
            {
                frame_typeA = 'D';
            }
                        
            if(message.substring(frame_encoding, message.length()).equals(partB))
            {
                frame_typeB = 'F';
            }
        
            String checksumA = calculateCheckSum(frame_typeA, space1, seg_lengthA, space2, partA, space3);
            String checksumB = calculateCheckSum(frame_typeB, space1, seg_lengthB, space2, partB, space3);
        
            if(partA.length() > frame_encoding)
            {
                System.err.println("Segment length is too big for frame encoding value");
            }
            else {
                System.out.println("[" + frame_typeA + space1 + seg_lengthA + space2 + partA + space3 + checksumA + "]");
            }
                        
            if(partB.length() > partA.length())
            {
                System.err.println("Segment length is too big for frame encoding value");
            }
            else {
                System.out.println("[" + frame_typeB + space1 + seg_lengthB + space2 + partB + space3 + checksumB + "]");
            }
    }
    }
    else {
        System.err.println("The segment length of this message exceeds 99 characters");
    }   

}



            
    /**
     * Calculates the message length of a message for a frame.
     * @param message The message 
     */
    public String calculateSegLength(String message)
    {
        int num_of_char = 0;
        int num_of_space = 0;
        int message_length = 0;

        for(int i=0; i<message.length(); i++)
        {
            if(Character.isLetter(message.charAt(i)))
            {
                num_of_char++;
            }
            else {
                num_of_space++;
            }
        }

        message_length = num_of_char + num_of_space;
        if(message_length < 10)
        {    
            String n = String.format("%02d", message_length);
            return n;
        }
        else if(message_length >= 10)
        {
            String seg_length = String.valueOf(message_length);
            return seg_length;
        }
        else {
            return "00";
        }
    }

    /**
     * Returns the frame type of a given frame based on its length
     * @param message The message in frame
     */
    public char getFrameType(String message)
    {
        if (message.length() < mtu)
        {
            return 'F';
        }
        else {
            return 'D';
        }
    }
    
    /**
     * Gets frame type for long messages
     * @param message The original message 
     * @param subMsg The substring of orginal message
     */
    public char setFrameTypeForLM(String message, String subMsg)
    {
        if(message.substring(message.length() - subMsg.length()).equals(subMsg))
        {
            return 'F';
        }
        else {
            return 'D';
        }

    }



    /**
     * Calculates the checksum of a frame 
     */
    public String calculateCheckSum(char type, char space1, String seg, char space2, String message, char space3)
    {
        int char_total =   type;
        int space1_total = space1;
        int seg_char = seg.charAt(0); 
        int seg_char1 = seg.charAt(1);
        int space2_total = space2;
        int message_total = 0;
        int space3_total = space3;

        for(int i=0; i<message.length(); i++)
        {
            message_total += message.charAt(i);
        }
        
        int sum = char_total + space1_total + seg_char + seg_char1 + space2_total + message_total + space3_total;
        String hex_value = Integer.toHexString(sum);
        int checksum = Integer.parseInt(hex_value,16);

        if(checksum < 16)
        {
            String format_hex = String.format("%02d", hex_value);
            return format_hex;
        }
        else {
            String last_digits = hex_value.substring(1, 3);
            return last_digits;
        }
    }

    /**
     * Calculates the frame length of a frame
     * 
     */
    public int checkFrameLength(char start_bracket, char type, char space1, String seg, char space2, String message, char space3, char end_bracket)
    {
        int start_in_number = Character.getNumericValue(start_bracket);
        int type_in_number = Character.getNumericValue(type);
        int space1_in_number = Character.getNumericValue(space1);
        int first_char = Character.getNumericValue(seg.charAt(0)); 
        int second_char = Character.getNumericValue(seg.charAt(1)); 
        int space2_in_number = Character.getNumericValue(space2);
        int space3_in_number = Character.getNumericValue(space3);
        int end_in_number = Character.getNumericValue(end_bracket);
        int message_total = 0;

        for(int i=0; i< message.length(); i++)
        {
                message_total += Character.getNumericValue(message.charAt(i));
        }

        frame_length = start_in_number + type_in_number + space1_in_number + first_char + second_char + space2_in_number + space3_in_number + message_total + end_in_number;
        return frame_length;
    }

    

}
