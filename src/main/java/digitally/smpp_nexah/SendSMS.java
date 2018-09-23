package digitally.smpp_nexah;

import java.io.IOException;
import java.util.Date;

import org.jsmpp.InvalidResponseException;
import org.jsmpp.PDUException;
import org.jsmpp.bean.Alphabet;
import org.jsmpp.bean.BindType;
import org.jsmpp.bean.ESMClass;
import org.jsmpp.bean.GeneralDataCoding;
import org.jsmpp.bean.MessageClass;
import org.jsmpp.bean.NumberingPlanIndicator;
import org.jsmpp.bean.RegisteredDelivery;
import org.jsmpp.bean.SMSCDeliveryReceipt;
import org.jsmpp.bean.TypeOfNumber;
import org.jsmpp.extra.NegativeResponseException;
import org.jsmpp.extra.ResponseTimeoutException;
import org.jsmpp.session.BindParameter;
import org.jsmpp.session.SMPPSession;
import org.jsmpp.util.AbsoluteTimeFormatter;
import org.jsmpp.util.TimeFormatter;

public class SendSMS
{

    public SendSMS()
    {
        super();
    }

    public static void main(String[] args) throws Exception
    {
        SendSMS sendSms = new SendSMS();
        sendSms.sendTextMessage("237679445233");
    }

    private TimeFormatter tF = new AbsoluteTimeFormatter();

    /*
     * This method is used to send SMS to for the given MSISDN
     */
    public void sendTextMessage(String MSISDN)
    {

        // bind param instance is created with parameters for binding with SMSC
        BindParameter bP = new BindParameter(
                BindType.BIND_TX, 
                "smpp254",
                "S@r@2019", 
                "DEFAULT", 
                TypeOfNumber.UNKNOWN,
                NumberingPlanIndicator.UNKNOWN,
                null);

        SMPPSession smppSession = null;

        try
        {
            // smpp session is created using the bindparam and the smsc ip address/port
            smppSession = new SMPPSession("196.202.232.250", 3700, bP);
        }
        catch (IOException e1)
        {
            e1.printStackTrace();
        }
        
        // Sample TextMessage
        String message = "This is a Test Message";

        GeneralDataCoding dataCoding = new GeneralDataCoding(Alphabet.ALPHA_DEFAULT);

        ESMClass esmClass = new ESMClass();

        try
        {
            // submitShortMessage(..) method is parametrized with necessary
            // elements of SMPP submit_sm PDU to send a short message
            // the message length for short message is 140
            smppSession.submitShortMessage(
                    "CMT",
                    TypeOfNumber.NATIONAL,
                    NumberingPlanIndicator.ISDN,
                    "237650022331",
                    TypeOfNumber.NATIONAL, 
                    NumberingPlanIndicator.ISDN, 
                    MSISDN,
                    esmClass, 
                    (byte) 0, 
                    (byte) 0, 
                    tF.format(new Date()),
                    null,
                    new RegisteredDelivery(SMSCDeliveryReceipt.DEFAULT),
                    (byte) 0,
                    dataCoding, 
                    (byte) 0, 
                    message.getBytes());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}