package com.mabu.MabuWebStore.email.emailContent;

public class EmailContent {
	
	public static final String OTPSubject = "ONE TIME PASSWORD FOR FIRST LOGIN";

	public static final String footer = "<br><p>Many Thanks & Best Regards</p><br>" 
											+"------------------------------------------------------------------------------------------------<br>"
											+"<p><img src='cid:MABULogo' style='width:170px;height:170px;margin-right:15px'>Lorem ipsum dolor sit amet, "
											+ "consectetur adipiscing elit. Phasellus imperdiet, nulla et dictum interdum, nisi lorem egestas odio, "
											+ "vitae scelerisque enim ligula venenatis dolor. Maecenas nisl est, ultrices nec congue eget, auctor vitae massa. Fusce luctus vestibulum augue ut aliquet. "
											+ "Mauris ante ligula, facilisis sed ornare eu, lobortis in odio. Praesent convallis urna a lacus interdum ut hendrerit risus congue. Nunc sagittis dictum nisi, "
											+ "sed ullamcorper ipsum dignissim ac. "
											+ "In at libero sed nunc venenatis imperdiet sed ornare turpis. "
											+ "Donec vitae dui eget tellus gravida venenatis. Integer fringilla congue eros non fermentum. Sed dapibus pulvinar nibh tempor porta. Cras ac leo purus. "
											+ "Mauris quis diam velit.</p>";
	
	public static String OTPContent(String userName, String OTP) {
		String OTPContent= "<p>Hello <b>" + userName + "</b></p><br>"
				+ "<p><b> Welcome to MABU STORES</b></p>"
	            + "<p>THIS OTP (ONE TIME PASSWORD) USE FOR FIRST LOGIN"
				+ "<br>"
	            + "<p><b>" + OTP + "</b></p>"
	            + "<br>"
	            + "At first, you need to login with your email and this OTP for login. Then you can create password for the next login."
	            + "<p><b>Note: this OTP is set to expire in 10 minutes.</b></p>"
	            + footer;
		
		return OTPContent;
		
	}
	
	public static final String EventContent(String userName, String event, String dateActived, String dateExpired, String eventCode, String discount) {
		String eventContent = "<p>Have a nice day <b>" + userName + "</b></p><br>"
				+ "<p> From <b>"+dateActived+"</b> to <b>"+dateExpired+" </b></p><br>"
				+ "<p>With proud of event: <h3>"+event+"<h3></p>"
				+ "<p>MABU Store discount <b>"+discount+"%</b> at any total invoice with <b>UNLIMITED</b></p><br>"
				+ "<p>Voucher code: <h3>"+eventCode+"</h3> <b>(Only for COD or Checkout Online) (No boundaries)</b><br>"
				+ "<b>HAVE UNLIMITED EXPERIENCE AT MABU STORE<p>"
				+ footer; 
				
		
		
		return eventContent;
	}

}
