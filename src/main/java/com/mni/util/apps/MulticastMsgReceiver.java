/**
 * All of the information (including source code, content and artwork) are copyright. No part of this message 
 * or any included attachment may be reproduced, stored in a retrieval system, transmitted, broadcast or published by any means 
 * (optical, magnetic, electronic, mechanical or otherwise) without the prior written permission of Market News International.
 * 
 * This software is provided "AS IS," without a warranty of any kind. ALL EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND 
 * WARRANTIES, INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT, 
 * ARE HEREBY EXCLUDED. "Market News International, Inc." AND ITS LICENSORS SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE
 * AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL "Market News International" OR
 * ITS LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT, INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL
 * OR PUNITIVE DAMAGES, HOWEVER CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF THE USE OF OR INABILITY TO
 *  USE THIS SOFTWARE, EVEN IF "Market News International" HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 *  
 *  Market News International, Copyright 2008
 * 
 */

package com.mni.util.apps;

import java.net.InetAddress;
import java.net.DatagramPacket;
import java.net.MulticastSocket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import com.mni.messages.IndicatorMessage;

public class MulticastMsgReceiver 
{

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
       String DATE_FORMAT = "HH:mm:ss.SSS";
        int port = 16899;
        String mcastAddress = "";
        int delayMillis = 0;
        long count = 0;
        
		if (args.length < 2)
		{
			System.out.println("Usage: MulticastMsgReceiver <Addr> <Port>");
			System.exit(1);
		}	        
        
		try
		{
        	System.setProperty("java.net.preferIPv4Stack", "true");			
			mcastAddress = args[0];
			port = Integer.parseInt(args[1]);     	
			
			System.out.println("Listening on "+ mcastAddress + " port "+ port);
			
	        SimpleDateFormat dateFormatter =  new SimpleDateFormat(DATE_FORMAT);
			dateFormatter.setTimeZone(TimeZone.getDefault());       			
			
		    MulticastSocket socket = new MulticastSocket(port);
		    socket.setReuseAddress(true);
		    InetAddress address = InetAddress.getByName(mcastAddress); 
		    socket.joinGroup(address);
		    			    
		    DatagramPacket packet;
		    
		    while(true)
		    {
			    byte messageBuf[] = new byte[4096];		    	
			    packet = new DatagramPacket(messageBuf, messageBuf.length);
			    socket.receive(packet);
			    count++;
			    
			    Date date = new Date();			    
			    IndicatorMessage indMsg = new IndicatorMessage(messageBuf);		
			    
			    System.out.println("Received #"+count+" "+ indMsg.toString()+ " at "+dateFormatter.format(date));			    
		    }		    			
		}
		catch(Exception e)
		{
			System.out.println("Exception occurred:"+e.getMessage());
		}

	}

}
