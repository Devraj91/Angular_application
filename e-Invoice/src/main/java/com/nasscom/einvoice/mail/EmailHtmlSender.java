package com.nasscom.einvoice.mail;
 
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
 
@Component
public class EmailHtmlSender {
 
    @Autowired
    private EmailSender emailSender;
 
    @Autowired
    private TemplateEngine templateEngine;
 
    public EmailStatus send(String to, String subject, String templateName, Context context,String from,List<EmailAttachment> attachments,String[]cc) {
        String body = templateEngine.process(templateName, context);
       
       /* if(templateName.equalsIgnoreCase("email/Invitation") && !( context.getVariables().get("title").toString().contains("credential")))
		{
        body = "<html><head><p>Dear Member,</p>"
        		+ "<p>We wish to inform members that as per rules of the Association, we are announcing the election schedule for the NASSCOM Executive Council (2017-19). The term of the present Executive Council (2015-17) will come to an end in March 2017.</p>"
        		+ "<p>We are also glad to inform you that the election to the Executive Council will be held concurrently with the election for the <strong>6 Sector Councils</strong> (IT Services; BPM; Engineering R&amp;D; Products; Internet, Mobile &amp; E-commerce and Domestic Market). This is to create a broad based representation across the different Councils "
        		+ "and programs at NASSCOM.</p> <p>We invite nominations from our members to contest the elections across the Executive Council and the Sector Councils and work collaboratively with the NASSCOM team to build the future directions for the industry and each of its sectors. Please read the attached document to understand the rules for elections to the Executive Council and Sector "
        		+ "Councils.</p><p>&nbsp;</p><p>The election schedule is as follows:-</p><table width=\"570\"><tbody><tr><td width=\"318\"><p>Notice For&nbsp; "
        				+ "Election</p></td><td width=\"252\"><p>02 February, 2017</p></td></tr><tr><td width=\"318\"><p>Last date for receipt of Nominations</p></td><td width=\"252\"><p>24 February, 2017</p></td></tr><tr><td width=\"318\"><p>Last date for withdrawal of Nominations</p>"
        						+ "</td><td width=\"252\"><p>03 March, 2017</p></td></tr><tr><td width=\"318\"><p>Commencement of e-Voting</p></td><td width=\"252\"><p>06 March, 2017</p></td></tr>"
        								+ "<tr>"
        								+ "<td width=\"318\"><p>Closing of e-Voting</p></td><td width=\"252\"><p>27 March, 2017</p></td></tr><tr><p>Counting of e-Votes and compiling of election&nbsp; result</p></td><td width=\"252\"><p>28 March, 2017</p></td></tr><tr><td width=\"318\">"
        								+ "<p>First New Executive Council meeting</p></td><td width=\"252\"><p>06 April, 2017</p></td></tr>"
        								+ "</tbody></table><p>&nbsp;</p><p>The process of elections is being conducted online - filing nomination, withdrawals, voting and results.</p><p>&nbsp;</p><p>Members interested to file their nominations are requested to log onto <a href=\"http://e-election.nasscom.in/election/#\">election portal</a> &ndash; choose the Council/s that they wish to submit a nomination for. The nomination form will include the candidate&rsquo;s bio and picture, company profile and statement of Intent. If you are applying for the Sector Councils and / or reserved seats, please do determine your company&rsquo;s eligibility for these categories. The last date for receipt of nominations is 24<sup>th</sup> February 2017.</p><p>&nbsp;"
        								+ "</p><p>Kindly feel free to get in touch with Mr. Kailash Nautiyal at <a href=\"mailto:e-election@nasscom.in\">e-election@nasscom.in</a> in case you wish to know more about the current priorities of the Sector Councils or need any clarifications.</p>"
        								+ "<p>Your crednetials will be sent by a seperate email.</p>" 
        								+ "<p>Yours Sincerely,</p><p><strong>R Chandrashekhar</strong></p><p><strong>President</strong></p>"
        								+ "</head></html>";
		}*/
        return emailSender.sendHtml(to, subject, body,from,attachments,cc);
    }
}