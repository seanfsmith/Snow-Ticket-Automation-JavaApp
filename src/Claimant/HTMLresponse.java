package Claimant;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import javax.swing.SwingUtilities;
import javax.swing.JEditorPane;

public class HTMLresponse extends JFrame {

	public JPanel contentPane;
	public JEditorPane jEditorPane;
	public JScrollPane scrollPane;
	public StyleSheet styleSheet;
	public JFrame j;

	public static void main(String[] args)
	  {
	    new HTMLresponse();
	  }
	  
	  public HTMLresponse()
	  {
	    SwingUtilities.invokeLater(new Runnable()
	    {
	      public void run()
	      {
	        JEditorPane jEditorPane = new JEditorPane();
	        jEditorPane.setEditable(false);
	        JScrollPane scrollPane = new JScrollPane(jEditorPane);
	        HTMLEditorKit kit = new HTMLEditorKit();
	        jEditorPane.setEditorKit(kit);
	        StyleSheet styleSheet = kit.getStyleSheet();
	        styleSheet.addRule("body {color:#000; font-family:times; margin: 4px; }");
	        styleSheet.addRule("h1 {color: blue;}");
	        styleSheet.addRule("h2 {color: #ff0000;}");
	        styleSheet.addRule("pre {font : 10px monaco; color : black; background-color : #fafafa; }");
	        String htmlString = window.claimantString;
	        Document doc = kit.createDefaultDocument();
	        jEditorPane.setDocument(doc);
	        jEditorPane.setText(htmlString);
	        JFrame j = new JFrame("HTML Response");
	        j.getContentPane().add(scrollPane, BorderLayout.CENTER);
	        j.setSize(new Dimension(1078, 744));
	        j.setLocationRelativeTo(null);
	        j.setVisible(true);
	      }
	    });
	  }
}
