package com.example.appbroadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.widget.Toast;

//La classe étend BroadcastReceiver on va y définier le traitement à la reception d'un sms
//On récuperera le numérto de téléphone de l'expediteur ainsi que le message
public class MyReceiver extends BroadcastReceiver {
    //Dans cette methone on effectue le traitement. L'intent est un message
    //qui déclenche le BroadcastReceiver il contient l'action qui l'a déclenché
    //ainsi que des données (SMS)
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
       // Toast.makeText(context,"SMS reçu", Toast.LENGTH_LONG).show();
       // throw new UnsupportedOperationException("Not yet implemented");
        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
            //On récupère les données stockées dans le Bundle de l'intent
            //Bundle: objet qui contient des données
            Bundle bundle = intent.getExtras();
            if(bundle!=null){
                //le format standard pour un message SMS est le format PDU(Protocol Data Unit)
                //pdus représente une clé qui permet d'acceder au SMS
                Object[] pdus=(Object[]) bundle.get("pdus");
                //Pour traiter les messages on va les récupérer dans un tableau de type SmsMessage
                SmsMessage[] messageList=new SmsMessage[pdus.length];
                //On va à partir de chaque pdu du tableau pdus créér un objet de typ SmsMesssage
                int i=0;
                for (Object pdu:pdus){
                    messageList[i]=SmsMessage.createFromPdu((byte[]) pdu);
                    i++;
                }
                //Pour chaque message on cécupère le corps du message et l'expéditeur
                for (SmsMessage message:messageList){
                    String body =message.getMessageBody();
                    String numero=message.getDisplayOriginatingAddress();
                    Toast.makeText(context,numero + " " + body,Toast.LENGTH_LONG).show();
                    //Toast.makeText(context,body,Toast.LENGTH_LONG).show();
                }

            }
        }
        String state=intent.getStringExtra((TelephonyManager.EXTRA_STATE));

        if (TelephonyManager.EXTRA_STATE_RINGING.equals(state)){
            String numero =  intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
            Toast.makeText(context,numero,Toast.LENGTH_LONG).show();
        }
    }
}
