package com.example.maciej.notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class NotificationReceiver extends BroadcastReceiver{
	private static final int uniqueID =24324234;
	String webPageAdress;



	@Override public void onReceive( final Context context, final Intent intent ) {

		webPageAdress = intent.getExtras().getString( "url" );

		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "E" , Locale.ENGLISH);
		String strDay = simpleDateFormat.format( calendar.getTime() );
		Log.e( "Date", strDay );
		Log.e( "Link", webPageAdress );




		NotificationManager notificationManager = (NotificationManager) context.getSystemService( Context.NOTIFICATION_SERVICE );
		Intent url = new Intent(Intent.ACTION_VIEW, Uri.parse( webPageAdress ) );
		url.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );


		PendingIntent pendingIntent = PendingIntent.getActivity( context,100,url,PendingIntent.FLAG_UPDATE_CURRENT );

		NotificationCompat.Builder notification= new NotificationCompat.Builder( context);
		notification.setAutoCancel( false )
		.setContentIntent( pendingIntent )
		.setContentText( webPageAdress )
		.setOngoing( true );
		int day = calendar.get( Calendar.DAY_OF_WEEK );
		if(day==Calendar.MONDAY||day==Calendar.SATURDAY){
			notification.setContentTitle( "Tajemnice radosne" )
					.setSmallIcon( R.drawable.radosna1 )
					.setStyle( new NotificationCompat.InboxStyle(  )
									   .addLine("I - Zwiastowanie NMP"  )
									   .addLine( "II - Nawiedzenie św. Elżbiety" )
									   .addLine( "III - Narodzenie Jezusa" )
									   .addLine( "IV - Ofiarowanie w świątyni" )
									   .addLine( "V - Znalezienie w świątyni" ));

		}else if(day==Calendar.THURSDAY){
			notification.setContentTitle( "Tajemnice światła" )
					.setSmallIcon( R.drawable.swiatla1 )
					.setStyle( new NotificationCompat.InboxStyle(  )
									   .addLine("I-Chrzest Pana Jezusa w Jordanie"  )
									   .addLine( "II-Wesele w Kanie Galilejskiej" )
									   .addLine( "III-Głoszenie Królestwa Bożego" )
									   .addLine( "IV-Przemienienie na górze Tabor" )
									   .addLine( "V-Ustanowienie Eucharystii" ));
		}else if(day==Calendar.TUESDAY||day==Calendar.FRIDAY){
			notification.setContentTitle( "Tajemnice bolesne" )
					.setSmallIcon( R.drawable.bolesna5 )
					.setStyle( new NotificationCompat.InboxStyle(  )
									   .addLine("I - Modlitwa w Ogrójcu" )
									   .addLine("II - Biczowanie")
									   .addLine("III - Cierniem ukoronowanie")
									   .addLine( "IV - Droga Krzyżowa" )
									   .addLine( "V - Śmierć na Krzyżu" ));
		}else{
			notification.setContentTitle( "Tajemnice chwalebne" )
				.setSmallIcon( R.drawable.chwalebna1 )
					.setStyle( new NotificationCompat.InboxStyle(  )
									   .addLine("I - Zmartwychwstanie Pana Jezusa" )
									   .addLine( "II - Wniebowstąpienie")
									   .addLine( "III - Zesłanie Ducha Świętego" )
									   .addLine( "IV - Wniebowzięcie Maryi" )
									   .addLine( "V - Ukoronowanie Matki Bożej na Królową nieba i ziemi" ));
		}


		if ( android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O ) {
			NotificationChannel notificationChannel = new NotificationChannel( "notify_001", "channel title", NotificationManager.IMPORTANCE_DEFAULT );
			notificationManager.createNotificationChannel( notificationChannel );


		}

		notification.setChannelId( "notify_001" );
		notificationManager.notify(uniqueID, notification.build());




	}

}
