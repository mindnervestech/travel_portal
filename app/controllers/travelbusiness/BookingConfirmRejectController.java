package controllers.travelbusiness;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import play.Play;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import com.travelportal.domain.HotelBookingDetails;
import com.travelportal.domain.agent.AgentRegistration;
import com.travelportal.vm.HotelBookDetailsVM;

public class BookingConfirmRejectController extends Controller {

	final static String rootDir = Play.application().configuration().getString("mail.storage.path");
	
 
	 @Transactional(readOnly=false)
	 public static Result getConfirmationInfo(String bookingId,String confirmationId,String nameConfirm,String status){

		 HotelBookingDetails hBookingDetails = HotelBookingDetails.findBookingIdDetail(bookingId);
		hBookingDetails.setRoom_status(status);
		hBookingDetails.setConfirmationId(confirmationId);
		hBookingDetails.setConfirmProcessUser(nameConfirm);
		hBookingDetails.merge();
		
		String voucherPdfFile = rootDir + File.separator + "BookingVoucherDocuments"+File.separator+ bookingId + File.separator+"HotelVoucher.pdf";
	 	File f = new File(voucherPdfFile);
		
		AgentRegistration aRegistration = AgentRegistration.findByIdOnCode(hBookingDetails.getAgentId().toString());
		if(status.equals("Confirm")){
			
			final String username=Play.application().configuration().getString("username");
	        final String password=Play.application().configuration().getString("password");
	           
	        Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.host", "smtp.checkinrooms.com");
			props.put("mail.smtp.port", "587");
			props.put("mail.smtp.starttls.enable", "true");
			Session session = Session.getInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			});
			try
			{
				MimeBodyPart attachPart = new MimeBodyPart();
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress(username));
				message.setRecipients(Message.RecipientType.TO,
						InternetAddress.parse(aRegistration.getEmailAddr()));
				message.setSubject("Confirmation Of Booking");
				Multipart multipart = new MimeMultipart();
				BodyPart messageBodyPart = new MimeBodyPart();
				messageBodyPart = new MimeBodyPart();
				
				VelocityEngine ve = new VelocityEngine();
				ve.setProperty( RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS,"org.apache.velocity.runtime.log.Log4JLogChute" );
				ve.setProperty("runtime.log.logsystem.log4j.logger","clientService");
				ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath"); 
				ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
				ve.init();
			
				
		        Template t = ve.getTemplate("/public/emailTemplet/BookingConfirmationMailToAgent.vm"); 
		        VelocityContext context = new VelocityContext();
		        context.put("uuId", hBookingDetails.getUuId());
		        context.put("agentName",hBookingDetails.getAgentNm());
		        
		        
		        StringWriter writer = new StringWriter();
		        t.merge( context, writer );
		        String content = writer.toString(); 
				
				messageBodyPart.setContent(content, "text/html");
				multipart.addBodyPart(messageBodyPart);
				  try {
						attachPart.attachFile(f);
			  	      } catch (IOException e) {
			  	       	// TODO Auto-generated catch block
			  	       		e.printStackTrace();
			  	    }
				 multipart.addBodyPart(attachPart);
				message.setContent(multipart);
				Transport.send(message);
				System.out.println("Sent test message successfully....");
			}
			catch (Exception e)
			{
				e.printStackTrace();
			} 
		}
		
		 return ok();
	 }
	 
	 @Transactional(readOnly=false)
	 public static Result getBookingInfo(String uuId){
		 HotelBookingDetails hBookingDetails = HotelBookingDetails.finduuIdDetail(uuId);
		 DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		 
		 HotelBookDetailsVM hDetailsVM= new HotelBookDetailsVM();
			hDetailsVM.setId(hBookingDetails.getId());
			hDetailsVM.setBookingId(hBookingDetails.getBookingId());
			hDetailsVM.setUuId(hBookingDetails.getUuId());
			hDetailsVM.setAdult(hBookingDetails.getAdult());
			hDetailsVM.setCheckIn(format.format(hBookingDetails.getCheckIn()));
			hDetailsVM.setCheckOut(format.format(hBookingDetails.getCheckOut()));
			if(hBookingDetails.getCityCode() != null){
				hDetailsVM.setCityCode(hBookingDetails.getCityCode().getCityCode());
				hDetailsVM.setCityNm(hBookingDetails.getCityCode().getCityName());
			}
			hDetailsVM.setHotelNm(hBookingDetails.getHotelNm());
			hDetailsVM.setHotelAddr(hBookingDetails.getHotelAddr());
			hDetailsVM.setNoOfroom(hBookingDetails.getNoOfroom());
			hDetailsVM.setTotalNightStay(hBookingDetails.getTotalNightStay());
			hDetailsVM.setRoom_status(hBookingDetails.getRoom_status());
			hDetailsVM.setLatestCancellationDate(hBookingDetails.getLatestCancellationDate());
			hDetailsVM.setCancellationNightsCharge(hBookingDetails.getCancellationNightsCharge());
			
		 return ok(Json.toJson(hDetailsVM));
	 }
}
