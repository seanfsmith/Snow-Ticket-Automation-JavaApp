package Claimant;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.JTextPane;
import javax.swing.JCheckBox;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import junit.framework.Assert;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import javax.swing.JFormattedTextField;

public class window {
	
	private JFrame frmClaimants;
	private JTextPane txtClaimantId;
	private JTextField claimantID;
	private JTextPane txtName;
	private JTextField claimantName;
	private JTextPane txtClaimType;
	private JTextField claimType;
	private JTextPane txtPhone;
	private JTextField claimantPhone;
	private JTextPane txtWeeks;
	private JTextField claimantWeeks;
	private JTextPane txtClaimFiledDate;
	private JTextField claimantCFD;
	private JTextPane txtClaimEffectiveDate;
	private JTextField claimantCED;
	private JTextPane txtResources;
	private JTextField claimantResources;
	private JTextPane txtIssueDescription;
	private JTextField claimantIssue;
	private JTextPane summary;
	private JButton writetoFile;
	private JButton resetButton;
	private JCheckBox claimantResources_disable;
	private JTextField userID;
	private JTextPane txtUserId;
	private JTextPane console;
	private JButton scanButton;
	private JPanel panel_3;
	private JPanel panel_4;
	private JPanel panel_1;
    private JTextPane txtpnSnowTicketSuggestion;
    private JFormattedTextField ticketCategory;
    private JFormattedTextField ticketType;
    private JFormattedTextField ticketUrgency;
    private JFormattedTextField STScategory;
    private JFormattedTextField STStype;
    private JFormattedTextField STSurgency;
	public String s = null;
	private JButton btnNewButton_1;
	public String ProductionLoginURL = "https://partner.beacon.labor.maryland.gov/ReBEACONStaff/Account/Login";
	public String ClaimantLookupURL = "https://partner.beacon.labor.maryland.gov/ReBEACONClaimant#/spa/wfmMSSHomeMaintenance/";
	public String ClaimantID;
	private static final String USER_AGENT = "Mozilla/5.0";
    private static String POST_PARAMS;
    private String BeaconUser;
    private String BeaconPass;
    private String requestVerificationToken;
    private String loginWindowID;
    public static String HTMLresponseCHAR;
    public static String claimantString;


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window window = new window();
					window.frmClaimants.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void postHTML(String params) throws IOException {
		URL url = new URL(ProductionLoginURL);
		URLConnection con = url.openConnection();
		con.setDoOutput(true);
		PrintWriter wr = new PrintWriter(con.getOutputStream(), true);
		wr.println(params);
		wr.close();
		BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String line;
		while((line = br.readLine()) != null) System.out.println(line);
		br.close();	
		loginWebpage();
	}
	
	public void beaconLogin() {
		ImageIcon img = new ImageIcon("/photos/MA_Header.ico");
		JLabel jUserName = new JLabel("User Name");
	      JTextField userName = new JTextField();
	      JLabel jPassword = new JLabel("Password");
	      JTextField password = new JPasswordField();
	      Object[] ob = {jUserName, userName, jPassword, password};
	      int result = JOptionPane.showConfirmDialog(null, ob, "Beacon Login", JOptionPane.OK_CANCEL_OPTION, 1, img);

	      if (result == JOptionPane.OK_OPTION) {
	          BeaconUser = userName.getText();
	          BeaconPass = password.getText();
	      }
	}
	
	public void loginWebpage() throws IOException {
		System.out.println("Starting loginWebpage function");
		URL obj = new URL(ProductionLoginURL);
        HttpURLConnection httpURLConnection = (HttpURLConnection) obj.openConnection();
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("User-Agent", USER_AGENT);
        httpURLConnection.setRequestProperty("__RequestVerificationToken", requestVerificationToken);
        httpURLConnection.setRequestProperty("UserName", BeaconUser);
        httpURLConnection.setRequestProperty("Password", BeaconPass);
        httpURLConnection.setRequestProperty("LoginWindowName", loginWindowID);
        httpURLConnection.setRequestProperty("Language", "en-US");
        httpURLConnection.setRequestProperty("IsCapatchaRequired", "False");
        //httpURLConnection.setRequestProperty("RememberMe", "on");
        System.out.println("Sending POST");

        httpURLConnection.setDoOutput(true);
        OutputStream os = httpURLConnection.getOutputStream();
        os.write(POST_PARAMS.getBytes());
        os.flush();
        os.close();
        int responseCode = httpURLConnection.getResponseCode();
        System.out.println("POST Response Code :: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in .readLine()) != null) {
                response.append(inputLine);
            } in .close();
            System.out.println(response.toString());

        } else {
            System.out.println("POST request not worked");
            JOptionPane.showMessageDialog(null, "Post did not work");
        }
    }

	public void getHTML() throws IOException {
		
		if (BeaconUser==null)
		BeaconUser = (String)JOptionPane.showInputDialog("Beacon UserID:");
		if (BeaconPass==null)
		BeaconPass = (String)JOptionPane.showInputDialog("Beacon Password:");

        URL urlObj = new URL(ProductionLoginURL);
        HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
        
        connection.setRequestMethod("GET");
        
        // Include the HTTP Basic Authentication payload
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");
 
        System.out.println("Send 'HTTP GET' request to : " + ProductionLoginURL);
        Integer responseCode = connection.getResponseCode();
        System.out.println("Response Code : " + responseCode);
 
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer response = new StringBuffer();
            
            String inputLine;
            while ((inputLine = inputReader.readLine()) != null) {
                response.append(inputLine);
            }
            inputReader.close(); 
            connection.disconnect();
            // Send HTML Response to Console
            System.out.println(response.toString());
            HTMLresponseCHAR = response.toString();
            
            // Open HTML in new window
            if (console.isVisible()==true) {
            HTMLresponse htmlResponse = new HTMLresponse();
            htmlResponse.setVisible(true);
            }            
            
            String requestToken1 = response.substring(response.toString().indexOf("name=\"__RequestVerificationToken\" type=\"hidden\" value=\"")+55);
	        int requestTokenInt = requestToken1.indexOf("\"");
	        requestVerificationToken = requestToken1.substring(0,requestTokenInt);
	        System.out.println("requestVerificationToken is: "+requestVerificationToken);
	        
	        String loginWindow1 = response.substring(response.toString().indexOf("name=\"LoginWindowName\" type=\"hidden\" value=\"")+44);
  	        int LoginWindowInt = loginWindow1.indexOf("\" />");
  	        loginWindowID = loginWindow1.substring(0,LoginWindowInt);
  	        System.out.println("Login Window ID: " + loginWindowID);

  	        System.out.println("Tokens Identified");
  	        
  	      POST_PARAMS = "__RequestVerificationToken="+requestVerificationToken+"&UserName="+BeaconUser+"&Password="+BeaconPass+"&LoginWindowName="+loginWindowID+"&Language=en-US&IsCaptchaRequired=False";
  	      System.out.println(POST_PARAMS);
  	      
  	      postHTML(POST_PARAMS);
        }
	}
	
	public void post() throws IOException {
		
		if (BeaconUser==null||BeaconPass==null) {
			beaconLogin();
		}
		
		Connection.Response loginForm = Jsoup.connect(ProductionLoginURL)
	            .method(Connection.Method.GET)
	            .maxBodySize(0)
	            .ignoreContentType(true)
	            .execute();

	           loginForm = Jsoup.connect(ProductionLoginURL)
	            .data("UserName", BeaconUser)
	            .data("Password", BeaconPass)
	            .data("login", "Login")
	            .cookies(loginForm.cookies())
	            .maxBodySize(0)
	            .ignoreContentType(true)
	            .method(Connection.Method.POST)
	            .execute();
	    
	    Document claimantForm = Jsoup.connect(ClaimantLookupURL+claimantID.getText())
	    		.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.212 Safari/537.36")
	        	.cookies(loginForm.cookies())
	        	.maxBodySize(0)
	        	.ignoreContentType(true)
	        	.followRedirects(true)
	        	.get();
	    
	           System.out.println(claimantForm.outerHtml().toString());
	           claimantString = claimantForm.html().toString();
	           
	           // Show html popup
	           if (console.isVisible()==true) {
	               HTMLresponse htmlResponse = new HTMLresponse();
	               htmlResponse.setVisible(true);
	               }
	           
	           //Parse Name from html
	           String requestToken1 = claimantForm.getAllElements().toString().substring(claimantForm.getAllElements().toString().indexOf("<span class=\"Wtxt\" tabindex=\"0\">Claimant Name:")+47);
		       int requestTokenInt = requestToken1.indexOf("</span>");
		       claimantName.setText(requestToken1.substring(0,requestTokenInt));
	}
	
	public static void DownloadWebPage(String webpage)
    {
        try {
  
            // Create URL object
            URL url = new URL(webpage);
            BufferedReader readr = 
              new BufferedReader(new InputStreamReader(url.openStream()));
  
            // Enter filename in which you want to download
            BufferedWriter writer = 
              new BufferedWriter(new FileWriter("Download.html"));
              
            // read each line from stream till end
            String line;
            while ((line = readr.readLine()) != null) {
                writer.write(line);
            }
  
            readr.close();
            writer.close();
            System.out.println("Successfully Downloaded.");
        }
  
        // Exceptions
        catch (MalformedURLException mue) {
            System.out.println("Malformed URL Exception raised");
        }
        catch (IOException ie) {
            System.out.println("IOException raised");
        }
    }
	
	public Document parse(String content) {
	      Document doc = Jsoup.parse(content, "", Parser.xmlParser());
	      doc.outputSettings().prettyPrint(false);
	      System.out.println(doc.toString());
	      return doc;
	    }
	
	public String getHTMLresponse() {
		return HTMLresponseCHAR;
	}
	
	public void getSummary() {
		if (claimantResources.isEnabled()) {
			summary.setText("Claimant ID: "+claimantID.getText()+"\n"+
					"Claimant Name: "+claimantName.getText()+"\n"+
					"Claim Type: " + claimType.getText()+"\n"+
					"Phone Number: "+claimantPhone.getText()+"\n"+
					"Week(s): "+claimantWeeks.getText()+"\n"+
					"Claim Filed Date: "+claimantCFD.getText()+"\n"+
					"Claim Effective Date: "+claimantCED.getText()+"\n"+
					"Resources: "+claimantResources.getText()+"\n"+
					"Issue Description: "+claimantIssue.getText()+"\n");
		}
		else if(claimantResources.isEnabled()==false) {
		summary.setText("Claimant ID: "+claimantID.getText()+"\n"+
				"Claimant Name: "+claimantName.getText()+"\n"+
				"Claim Type: " + claimType.getText()+"\n"+
				"Phone Number: "+claimantPhone.getText()+"\n"+
				"Week(s): "+claimantWeeks.getText()+"\n"+
				"Claim Filed Date: "+claimantCFD.getText()+"\n"+
				"Claim Effective Date: "+claimantCED.getText()+"\n"+
				
				"Issue Description: "+claimantIssue.getText()+"\n");
		}
		return;
	}
	
	public void readFile() {
		try {
		      File myObj = new File("C:\\Users\\"+userID+"\\Documents\\Claimants\\"+java.time.LocalDate.now()+".txt");
		      if (myObj.createNewFile()) {
		        System.out.println("File created: " + myObj.getName());
		      } else {
		        System.out.println("File already exists.");
		      }
		    } catch (IOException e) {
		      System.out.println("An error has occured.");
		      e.printStackTrace();
		    }
		return;
	}
	
	public void writeFile(String username) {
        String text = summary.getText();
        if (!userID.getText().isBlank()) {
        try {
            FileWriter fw = new FileWriter("C:\\Users\\"+username+"\\OneDrive - Accenture\\Documents\\Claimants\\"+java.time.LocalDate.now()+".txt", true);
            if (summary != null) {
            fw.write("------ Response Auto Generated on "+java.time.LocalDate.now()+" at "+java.time.LocalTime.now()+" ------\n"+text+"\n"+"-------------------------------------------------------------------------\n");
            
            System.out.println("Written to "+java.time.LocalDate.now()+".txt");
            }
            else {
            System.out.println("Please compile first");
            }
            fw.close();
        }
        catch(IOException e) {
        	System.out.println("Catch Exception");
        	}
        }
        else {
            System.out.println("Please input a Windows user ID");
        }
	}
	
	/*private void getUrgency() {
		if (claimantWeeks.getText().toString()=="") {
			STSurgency.setText("Manual");
		}
		else if (Integer.valueOf(claimantWeeks.getText().toString())>=10) {
			STSurgency.setText("Medium");
		}
		
		else if ((Integer.valueOf(claimantWeeks.getText().toString())<10)) {
			STSurgency.setText("Low");
		}
		else {
			STSurgency.setText("Manual");
		}
	}
	*/

	public window() {
		initialize();
	}

	private void initialize() {
		frmClaimants = new JFrame();
		frmClaimants.setResizable(false);
		frmClaimants.setIconImage(Toolkit.getDefaultToolkit().getImage(window.class.getResource("/photos/MA_Header.ico")));
		frmClaimants.setTitle("Snow Ticket Automation");
		frmClaimants.setBounds(100, 100, 648, 839);
		frmClaimants.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(9, 53, 75));
		frmClaimants.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		panel_3 = new JPanel();
		panel_3.setBorder(UIManager.getBorder("PopupMenu.border"));
		panel_3.setBounds(8, 110, 617, 389);
		panel.add(panel_3);
		panel_3.setLayout(null);
		
		txtClaimantId = new JTextPane();
		txtClaimantId.setBounds(6, 16, 181, 20);
		panel_3.add(txtClaimantId);
		txtClaimantId.setForeground(SystemColor.windowBorder);
		txtClaimantId.setOpaque(false);
		txtClaimantId.setFont(new Font("Verdana", Font.BOLD, 13));
		txtClaimantId.setEditable(false);
		txtClaimantId.setText("Claimant ID:");
		
		claimantID = new JTextField();
		claimantID.setBounds(178, 16, 155, 20);
		panel_3.add(claimantID);
		claimantID.setColumns(10);
		
		txtName = new JTextPane();
		txtName.setBounds(6, 47, 181, 20);
		panel_3.add(txtName);
		txtName.setForeground(SystemColor.windowBorder);
		txtName.setOpaque(false);
		txtName.setFont(new Font("Verdana", Font.BOLD, 13));
		txtName.setEditable(false);
		txtName.setText("Claimant Name:");

		
		claimantName = new JTextField();
		claimantName.setBounds(178, 47, 155, 20);
		panel_3.add(claimantName);
		claimantName.setColumns(10);
		
		txtClaimType = new JTextPane();
		txtClaimType.setBounds(6, 78, 181, 20);
		panel_3.add(txtClaimType);
		txtClaimType.setForeground(SystemColor.windowBorder);
		txtClaimType.setOpaque(false);
		txtClaimType.setFont(new Font("Verdana", Font.BOLD, 13));
		txtClaimType.setText("Claim Type:");
		txtClaimType.setEditable(false);
		
		claimType = new JTextField();
		claimType.setBounds(178, 78, 155, 20);
		panel_3.add(claimType);
		claimType.setColumns(10);
		
		txtPhone = new JTextPane();
		txtPhone.setBounds(6, 109, 181, 20);
		panel_3.add(txtPhone);
		txtPhone.setForeground(SystemColor.windowBorder);
		txtPhone.setOpaque(false);
		txtPhone.setFont(new Font("Verdana", Font.BOLD, 13));
		txtPhone.setEditable(false);
		txtPhone.setText("Phone:");

		
		claimantPhone = new JTextField();
		claimantPhone.setBounds(178, 109, 155, 20);
		panel_3.add(claimantPhone);
		claimantPhone.setColumns(10);
		
		txtWeeks = new JTextPane();
		txtWeeks.setBounds(6, 140, 181, 20);
		panel_3.add(txtWeeks);
		txtWeeks.setForeground(SystemColor.windowBorder);
		txtWeeks.setOpaque(false);
		txtWeeks.setFont(new Font("Verdana", Font.BOLD, 13));
		txtWeeks.setText("Week(s):");
		txtWeeks.setEditable(false);

		
		claimantWeeks = new JTextField();
		claimantWeeks.setBounds(178, 140, 155, 20);
		panel_3.add(claimantWeeks);
		claimantWeeks.setColumns(10);
		
		txtClaimFiledDate = new JTextPane();
		txtClaimFiledDate.setBounds(6, 171, 181, 20);
		panel_3.add(txtClaimFiledDate);
		txtClaimFiledDate.setForeground(SystemColor.windowBorder);
		txtClaimFiledDate.setOpaque(false);
		txtClaimFiledDate.setFont(new Font("Verdana", Font.BOLD, 13));
		txtClaimFiledDate.setText("Claim Filed Date:");
		txtClaimFiledDate.setEditable(false);

		
		claimantCFD = new JTextField();
		claimantCFD.setBounds(178, 171, 155, 20);
		panel_3.add(claimantCFD);
		claimantCFD.setColumns(10);
		
		txtClaimEffectiveDate = new JTextPane();
		txtClaimEffectiveDate.setBounds(6, 202, 181, 20);
		panel_3.add(txtClaimEffectiveDate);
		txtClaimEffectiveDate.setForeground(SystemColor.windowBorder);
		txtClaimEffectiveDate.setOpaque(false);
		txtClaimEffectiveDate.setFont(new Font("Verdana", Font.BOLD, 13));
		txtClaimEffectiveDate.setEditable(false);
		txtClaimEffectiveDate.setText("Claim Effective Date:");

		
		claimantCED = new JTextField();
		claimantCED.setBounds(178, 202, 155, 20);
		panel_3.add(claimantCED);
		claimantCED.setColumns(10);
		
		txtResources = new JTextPane();
		txtResources.setBounds(6, 233, 181, 20);
		panel_3.add(txtResources);
		txtResources.setForeground(SystemColor.windowBorder);
		txtResources.setOpaque(false);
		txtResources.setFont(new Font("Verdana", Font.BOLD, 13));
		txtResources.setEditable(false);
		txtResources.setText("Resources:");

		
		claimantResources = new JTextField();
		claimantResources.setBounds(178, 233, 155, 20);
		panel_3.add(claimantResources);
		claimantResources.setColumns(10);
		
		txtIssueDescription = new JTextPane();
		txtIssueDescription.setBounds(6, 314, 181, 20);
		panel_3.add(txtIssueDescription);
		txtIssueDescription.setForeground(SystemColor.windowBorder);
		txtIssueDescription.setOpaque(false);
		txtIssueDescription.setFont(new Font("Verdana", Font.BOLD, 13));
		txtIssueDescription.setEditable(false);
		txtIssueDescription.setText("Issue Description:");

		
		claimantIssue = new JTextField();
		claimantIssue.setBounds(178, 264, 420, 120);
		panel_3.add(claimantIssue);
		claimantIssue.setHorizontalAlignment(SwingConstants.LEFT);
		claimantIssue.setColumns(10);
		
		claimantResources_disable = new JCheckBox("Disable");
		claimantResources_disable.setBounds(339, 230, 82, 23);
		panel_3.add(claimantResources_disable);
		
		scanButton = new JButton("Scan");
		scanButton.setBounds(343, 16, 78, 23);
		panel_3.add(scanButton);
		
		JPanel ticketSuggestion = new JPanel();
		ticketSuggestion.setVisible(false);
		ticketSuggestion.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		ticketSuggestion.setBounds(353, 47, 237, 172);
		panel_3.add(ticketSuggestion);
		ticketSuggestion.setLayout(null);
		
		JFormattedTextField UPFI = new JFormattedTextField();
		UPFI.setFont(new Font("Tahoma", Font.BOLD, 10));
		UPFI.setBackground(new Color(255, 0, 51));
		UPFI.setEditable(false);
		UPFI.setHorizontalAlignment(SwingConstants.CENTER);
		UPFI.setText("Under Potential Fraudulent Investigation");
		UPFI.setBounds(10, 10, 217, 19);
		ticketSuggestion.add(UPFI);
		
		txtpnSnowTicketSuggestion = new JTextPane();
		txtpnSnowTicketSuggestion.setText("SNOW Ticket Suggestion");
		txtpnSnowTicketSuggestion.setOpaque(false);
		txtpnSnowTicketSuggestion.setForeground(SystemColor.windowBorder);
		txtpnSnowTicketSuggestion.setFont(new Font("Verdana", Font.BOLD, 13));
		txtpnSnowTicketSuggestion.setEditable(false);
		txtpnSnowTicketSuggestion.setBounds(33, 26, 181, 20);
		ticketSuggestion.add(txtpnSnowTicketSuggestion);
		
		ticketCategory = new JFormattedTextField() {
			@Override public void setBorder(Border border) {
		    }
		};
		ticketCategory.setForeground(SystemColor.windowBorder);
		ticketCategory.setToolTipText("");
		ticketCategory.setFont(new Font("Tahoma", Font.BOLD, 11));
		ticketCategory.setHorizontalAlignment(SwingConstants.CENTER);
		ticketCategory.setEditable(false);
		ticketCategory.setText("Category:");
		ticketCategory.setBounds(10, 85, 94, 19);
		ticketSuggestion.add(ticketCategory);
		
		ticketType = new JFormattedTextField() {
			@Override public void setBorder(Border border) {
		    }
		};
		ticketType.setForeground(SystemColor.windowBorder);
		ticketType.setFont(new Font("Tahoma", Font.BOLD, 11));
		ticketType.setHorizontalAlignment(SwingConstants.CENTER);
		ticketType.setEditable(false);
		ticketType.setText("Type:");
		ticketType.setBounds(10, 114, 94, 19);
		ticketSuggestion.add(ticketType);
		
		ticketUrgency = new JFormattedTextField() {
			@Override public void setBorder(Border border) {
		    }
		};
		ticketUrgency.setForeground(SystemColor.windowBorder);
		ticketUrgency.setFont(new Font("Tahoma", Font.BOLD, 11));
		ticketUrgency.setHorizontalAlignment(SwingConstants.CENTER);
		ticketUrgency.setEditable(false);
		ticketUrgency.setText("Urgency:");
		ticketUrgency.setBounds(10, 143, 94, 19);
		ticketSuggestion.add(ticketUrgency);
		
		STScategory = new JFormattedTextField();
		STScategory.setHorizontalAlignment(SwingConstants.CENTER);
		STScategory.setEditable(false);
		STScategory.setBounds(114, 85, 113, 19);
		ticketSuggestion.add(STScategory);
		
		STStype = new JFormattedTextField();
		STStype.setHorizontalAlignment(SwingConstants.CENTER);
		STStype.setEditable(false);
		STStype.setBounds(114, 114, 113, 19);
		ticketSuggestion.add(STStype);
		
		STSurgency = new JFormattedTextField();
		STSurgency.setHorizontalAlignment(SwingConstants.CENTER);
		STSurgency.setEditable(false);
		STSurgency.setBounds(114, 143, 113, 19);
		ticketSuggestion.add(STSurgency);
		
		JFormattedTextField ticketTier = new JFormattedTextField() {
			public void setBorder(Border border) {
			}
		};
		ticketTier.setToolTipText("");
		ticketTier.setText("Tier:");
		ticketTier.setHorizontalAlignment(SwingConstants.CENTER);
		ticketTier.setForeground(SystemColor.windowBorder);
		ticketTier.setFont(new Font("Tahoma", Font.BOLD, 11));
		ticketTier.setEditable(false);
		ticketTier.setBounds(10, 56, 94, 19);
		ticketSuggestion.add(ticketTier);
		
		JFormattedTextField STStier = new JFormattedTextField();
		STStier.setHorizontalAlignment(SwingConstants.CENTER);
		STStier.setEditable(false);
		STStier.setBounds(114, 56, 113, 19);
		ticketSuggestion.add(STStier);
		
		
		scanButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					//getHTML();
					post();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		claimantResources_disable.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (claimantResources.isEnabled()) {
					claimantResources_disable.setText("Disabled");
					claimantResources.setEnabled(false);
					claimantResources.setVisible(false);
					System.out.println("claimantResources toggled to disabled...");
				}
				else if (claimantResources.isEnabled()==false) {
					claimantResources_disable.setText("Disable");
					claimantResources.setEnabled(true);
					claimantResources.setVisible(true);
					System.out.println("claimantResources toggled to enabled...");
				}
			}
		});
		
		panel_1 = new JPanel();
		panel_1.setBorder(UIManager.getBorder("CheckBox.border"));
		panel_1.setBounds(8, 503, 617, 283);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		summary = new JTextPane();
		summary.setBorder(new BevelBorder(BevelBorder.RAISED, SystemColor.inactiveCaption, null, null, null));
		summary.setBounds(10, 11, 461, 261);
		panel_1.add(summary);
		summary.setEditable(false);
		
		//Should gather all fields based off of resources enabled/disabled and return a block of text from func getSummary()
		JButton compileButton = new JButton("Compile");
		compileButton.setBounds(483, 36, 123, 23);
		panel_1.add(compileButton);
		
		//Supposed to take summary text and append it to the end of the file including spacing
		/*
		writetoFile = new JButton("Write to file");
		writetoFile.setBounds(483, 71, 123, 23);
		panel_1.add(writetoFile);
		writetoFile.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (s == null) {
				s = (String)JOptionPane.showInputDialog(frmClaimants, "Please input your User ID:","Customized Dialog",JOptionPane.PLAIN_MESSAGE);
				userID.setText(s);
				panel_4.setVisible(true);
				writeFile(s);
				System.out.println("Added username "+s);
				writeFile(s);
				}
				else if(s != null) {
				writeFile(s);
				}

			}
		});
		*/
		
		//Should clear summary text pane
		resetButton = new JButton("Reset");
		resetButton.setBounds(484, 69, 123, 23);
		panel_1.add(resetButton);
		
		JButton btnNewButton = new JButton(">");
		btnNewButton.setVisible(false);
		btnNewButton.setBounds(555, 133, 52, 21);
		panel_1.add(btnNewButton);
		
		JButton btnPrevious = new JButton("<");
		btnPrevious.setVisible(false);
		btnPrevious.setBounds(481, 133, 52, 21);
		panel_1.add(btnPrevious);
		
		JButton btnCopy = new JButton("Copy");
		btnCopy.setBounds(483, 102, 123, 23);
		panel_1.add(btnCopy);
		resetButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("Copying field to clipboard");
				StringSelection stringSelection = new StringSelection(summary.getText().toString());
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				clipboard.setContents(stringSelection, null);
			}
		});
		
		compileButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			getSummary();
			//getUrgency();
			System.out.println("Compiling info from fields provided");
			}
		});
		
		panel_4 = new JPanel();
		panel_4.setBounds(444, 4, 185, 44);
		panel.add(panel_4);
		panel_4.setVisible(false);
		panel_4.setBorder(UIManager.getBorder("CheckBox.border"));
		panel_4.setLayout(null);
		
		userID = new JTextField();
		userID.setEditable(false);
		userID.setBounds(85, 11, 86, 20);
		panel_4.add(userID);
		userID.setColumns(10);
		
		txtUserId = new JTextPane();
		txtUserId.setBounds(10, 11, 65, 20);
		panel_4.add(txtUserId);
		txtUserId.setForeground(SystemColor.windowBorder);
		txtUserId.setOpaque(false);
		txtUserId.setText("User ID:");
		txtUserId.setFont(new Font("Verdana", Font.BOLD, 13));
		txtUserId.setEditable(false);
		
		btnNewButton_1 = new JButton("Help");
		btnNewButton_1.setContentAreaFilled(false);
		btnNewButton_1.setFocusPainted(false);
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("Opened help menu");
				Object[] options = {"Toggle Console","Close"};
				int n = JOptionPane.showOptionDialog(frmClaimants,
						"Maryland Department of Labor Unemployment\nDesigned for an agent to input a claimants id and hit scan to automatically fill out the additional fields\n\nDesigned and developed by Sean Smith\nsean.f.smith@accenture.com\nLast updated 4/28/2021","Help",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE,
						null,
						options,
						options[0]);
				if (console.isVisible()==false && n==0) {
				console.setVisible(true);
				System.out.println("Console enabled: "+n);
				}
				else {
					console.setVisible(false);
					System.out.println("Console disabled: "+n);
				}
			}
		});
		btnNewButton_1.setBorder(null);
		btnNewButton_1.setBorderPainted(false);
		btnNewButton_1.setForeground(SystemColor.textHighlight);
		btnNewButton_1.setOpaque(false);
		btnNewButton_1.setBounds(576, 83, 43, 23);
		panel.add(btnNewButton_1);
		
		JLabel icon = new JLabel("");
		icon.setHorizontalAlignment(SwingConstants.CENTER);
		icon.setBounds(-11, 7, 231, 93);
		panel.add(icon);
		
		icon.setIcon(new ImageIcon(window.class.getResource("/photos/Logo.JPG")));
		
		console = new JTextPane();
		console.setBounds(204, 7, 238, 68);
		panel.add(console);
		console.setDisabledTextColor(Color.BLACK);
		console.setVisible(false);
		console.setEnabled(false);
		console.setOpaque(false);
		console.setEditable(false);
		console.setBackground(Color.LIGHT_GRAY);
	}
}