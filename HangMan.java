import java.awt.*;
import java.applet.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
/*<applet code="HangMan.class" width=500 height=500></applet>*/

public class HangMan extends Applet implements ActionListener
{
	TextField score;
	TextField misses;
	TextField incg;
	TextArea doll;
	TextArea doll1;
	TextArea mainn;
	Button alphabets[] = new Button[26];
	String str;
	BufferedReader br;
	String dashes;
	String word;
	int cost,len,count,filled=0,nol,wrong=0,tscore=0;
	String costt,tscoret,incorrect1;
	String icgs = new String("--------");
	StringBuilder incorrect;
	Panel dolls;

	public void init()
	{
		Panel dolls = new Panel();
		dolls.setLayout(new BorderLayout());
		doll = new TextArea("",5,5,TextArea.SCROLLBARS_NONE);
		doll1 = new TextArea("",5,5,TextArea.SCROLLBARS_NONE);
		doll.setText("\n    O\n     |\n   /  \\\n");
		doll1.setText("\n    O\n     |\n   /  \\\n");
		dolls.add(doll,BorderLayout.WEST);
		dolls.add(doll1,BorderLayout.EAST);
		Panel hang = new Panel();
		hang.setLayout(new BorderLayout());
		score = new TextField("Score:",30);
		hang.add(score,BorderLayout.EAST);
		incg = new TextField("Incorrect guesses:",30);
		Panel hang1 = new Panel();
		hang1.setLayout(new GridLayout(5,6));
		char alpha='A';
		for(int i=0;i<26;i++)
		{
			alphabets[i] = new Button(""+alpha++);
			alphabets[i].addActionListener(this);
			hang1.add(alphabets[i]);
		}
		mainn = new TextArea(5,30);

		dolls.add(score,BorderLayout.NORTH);
		dolls.add(incg,BorderLayout.SOUTH);
		hang.add(hang1,BorderLayout.CENTER);
		hang.add(mainn,BorderLayout.WEST);
		dolls.add(hang,BorderLayout.CENTER);
		add(dolls);

		mainn.setText("Word:");
		nol=doRawRandomNumberl();

		try
		{
			br = new BufferedReader(new FileReader("hangmanwords.txt"));
			try
			{
				for(int i=0;i<nol;i++)
				{
					str = br.readLine();
				}
			}
			catch(IOException e)
			{
			}
		}
		catch(FileNotFoundException a)
		{
		}
		dashes = new String(str);
		len = str.length();
		StringBuilder builderstring = new StringBuilder(dashes);
		for(int i=0;i<str.length();i++)
		{
			builderstring.setCharAt(i,'-');	
		}
		dashes = builderstring.toString();
		builderstring.setLength(str.length());
		mainn.append(dashes);
		mainn.append("\n");
		mainn.append("This guess is worth Rs.:");
		cost = doRawRandomNumber();
		cost *= 1000;
		costt = String.valueOf(cost);
		mainn.append(costt);
		incorrect = new StringBuilder(icgs);
	}
	
	public void changedolls(int w)
	{
		switch(w)
		{
			case 1:
			doll.setText("\n    O\n     |\n   /\n");
			break;

			case 2:
			doll1.setText("\n    O\n     |\n   /\n");
			break;
	
			case 3:
			doll.setText("\n    O\n     |\n");
			break;
	
			case 4:
			doll1.setText("\n    O\n     |\n");
			break;

			case 5:
			doll.setText("\n    O\n");
			break;

			case 6:
			doll1.setText("\n    O\n");
			break;

			case 7:
			doll.setText("");
			break;

			default:
			break;
		}
	}
	public int doRawRandomNumber() 
	{
		int rawRandomNumber=1;
        	int min = 1;
        	int max = 5;

 	       	for (int i = 0; i < 500; i++) 
		{
            		rawRandomNumber = (int) (Math.random() * (max - min + 1) ) + min;
        	}
		return rawRandomNumber;
	}

	public int doRawRandomNumberl() 
	{
		int rawRandomNumber=1;
        	int min = 1;
        	int max = 10;

 	       	for (int i = 0; i < 1000; i++) 
		{
            		rawRandomNumber = (int) (Math.random() * (max - min + 1) ) + min;
        	}
		return rawRandomNumber;
	}

	public void paint(Graphics g)
	{
		
	}
	public void actionPerformed(ActionEvent ae)
	{
		String letter = ae.getActionCommand();
		char tobefilled;
		String temp;
		int flag,i;
		tobefilled = letter.charAt(0);
		for(i=0;i<26;i++)
		{
			temp = alphabets[i].getLabel();
			if(letter.compareTo(temp)==0)
			{
				alphabets[i].setEnabled(false);
				break;
			}
		}
		flag=0;count=0;
		StringBuilder builderstring = new StringBuilder(dashes);
		for(i=0;i<str.length();i++)
		{
			if(str.charAt(i) == tobefilled)
			{
				builderstring.setCharAt(i,tobefilled);
				flag++;
				filled++;count++;
			}
		}

		if(flag!=0)
		{
			tscore += (cost*count);
			tscoret = String.valueOf(tscore);
			score.setText("Score:"+tscoret);
		}

		dashes = builderstring.toString();
		mainn.setText("Word:");
		mainn.append(dashes);
		mainn.append("\n");
		mainn.append("This guess is worth Rs.:");

		if(flag!=0)
		{
			cost = doRawRandomNumber();
			cost *= 1000;
			costt = String.valueOf(cost);
		}
		mainn.append(costt);

		if(flag == 0)
		{
			wrong++;
			changedolls(wrong);
			
			incorrect.setCharAt((wrong-1),tobefilled);
			incorrect1=incorrect.toString();

			incg.setText("Incorrect guesses:"+incorrect1);
			if(wrong >= 8)
			{
				doll1.setText("");
				JOptionPane.showMessageDialog(null,"YOU LOSE!");
				try
				{
					br.close();
				}
				catch(IOException e)
				{
				}
				tscore = 0;wrong=0;
				nol=doRawRandomNumberl();

				try
				{
					br = new BufferedReader(new FileReader("hangmanwords.txt"));
					try
					{
						for(i=0;i<nol;i++)
						{
							str = br.readLine();
						}
					}
					catch(IOException e)
					{
					}
				}
				catch(FileNotFoundException a)
				{
				}
				len = str.length();
				dashes=new String(str);
				builderstring = new StringBuilder(dashes);
				for(i=0;i<str.length();i++)
				{
					builderstring.setCharAt(i,'-');	
				}
				builderstring.setLength(str.length());
				dashes = builderstring.toString();
				for(i=0;i<26;i++)
					alphabets[i].setEnabled(true);
				score.setText("Score:");
				incg.setText("Incorrect guesses:");
				mainn.setText("Word:");
				mainn.append(dashes);
				mainn.append("\n");
				mainn.append("This guess is worth Rs.:");
				cost = doRawRandomNumber();
				cost *= 1000;
				costt = String.valueOf(cost);
				mainn.append(costt);
				filled=0;
				doll.setText("\n    O\n     |\n   /  \\\n");
				doll1.setText("\n    O\n     |\n   /  \\\n");
				incorrect = new StringBuilder(icgs);
			}
		}
		if((filled >= len)&&(wrong<8))
		{
			JOptionPane.showMessageDialog(null,"YOU WIN! FINAL SCORE:"+tscoret);
			try
			{
				br.close();
			}
			catch(IOException e)
			{
			}
			tscore = 0;wrong=0;
			nol=doRawRandomNumberl();

			try
			{
				br = new BufferedReader(new FileReader("hangmanwords.txt"));
				try
				{
					for(i=0;i<nol;i++)
					{
						str = br.readLine();
					}
				}
				catch(IOException e)
				{
				}
			}
			catch(FileNotFoundException a)
			{
			}
			len = str.length();
			dashes=new String(str);
			builderstring = new StringBuilder(dashes);
			for(i=0;i<str.length();i++)
			{
				builderstring.setCharAt(i,'-');	
			}
			builderstring.setLength(str.length());
			dashes = builderstring.toString();
			for(i=0;i<26;i++)
				alphabets[i].setEnabled(true);
			score.setText("Score:");
			incg.setText("Incorrect guesses:");
			mainn.setText("Word:");
			mainn.append(dashes);
			mainn.append("\n");
			mainn.append("This guess is worth Rs.:");
			cost = doRawRandomNumber();
			cost *= 1000;
			costt = String.valueOf(cost);
			mainn.append(costt);
			filled=0;
			doll.setText("\n    O\n     |\n   /  \\\n");
			doll1.setText("\n    O\n     |\n   /  \\\n");
			incorrect = new StringBuilder(icgs);
		}		
	}
}