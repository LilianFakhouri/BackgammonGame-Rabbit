package fr.ujm.tse.info4.pgammon.test.gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JFrame;

import GUI.AfficheurScore;
import GUI.IconMonochromeType;
import GUI.MonochromeButton;
import GUI.MonochromeCheckbox;
import GUI.MonochromeIconButton;
import GUI.MonochromeLabel;
import GUI.MonochromePanel;
import models.CouleurCase;

public class TestDesign {
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("Test Design");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(816,638);
		
		Container panel = frame.getContentPane();
		panel.setLayout(new FlowLayout());

		MonochromePanel mp = new MonochromePanel("MonochromePanel");
		mp.setPreferredSize(new Dimension(300, 200));
		mp.setTitle("Mono Panel Mod");
		panel.add(mp);
		panel.setBackground(new Color(0x00000));
		{
			MonochromeButton mbtn = new MonochromeButton("Button1");
			mbtn.setPreferredSize(new Dimension(200, 60));
			panel.add(mbtn);
		}
		{
			MonochromeButton mbtn = new MonochromeButton("MonochromeButton2");
			mbtn.setPreferredSize(new Dimension(300, 60));
			panel.add(mbtn);
		}
		{
			MonochromeButton mbtn = new MonochromeButton("Button1");
			mbtn.setPreferredSize(new Dimension(200, 60));
			mbtn.setEnabled(false);
			panel.add(mbtn);
		}
		{
			MonochromeButton mbtn = new MonochromeButton("MonochromeButton2");
			mbtn.setPreferredSize(new Dimension(300, 60));
			mbtn.setEnabled(false);
			panel.add(mbtn);
		}
		{
			MonochromeIconButton mbtn = new MonochromeIconButton(IconMonochromeType.X_BLANC,"MonochromeIconButton","NOIR");
			mbtn.setSizeBig();
			panel.add(mbtn);
		}
		{
			MonochromeIconButton mbtn = new MonochromeIconButton(IconMonochromeType.X_NOIR,"MonochromeIconButton");
			mbtn.setSizeBig();
			panel.add(mbtn);
		}
		{
			MonochromeIconButton mbtn = new MonochromeIconButton(IconMonochromeType.SMALL_INFINITE,"MonochromeIconButton","NOIR");
			mbtn.setSizeSmall();
			panel.add(mbtn);
		}
		{
			MonochromeIconButton mbtn = new MonochromeIconButton(IconMonochromeType.X_BLANC,"MonochromeIconButton","NOIR");
			mbtn.setSizeBig();
			mbtn.setEnabled(false);
			panel.add(mbtn);
		}
		{
			MonochromeIconButton mbtn = new MonochromeIconButton(IconMonochromeType.X_NOIR,"MonochromeIconButton");
			mbtn.setSizeBig();
			mbtn.setEnabled(false);
			panel.add(mbtn);
		}
		{
			MonochromeIconButton mbtn = new MonochromeIconButton(IconMonochromeType.SMALL_INFINITE,"MonochromeIconButton","NOIR");
			mbtn.setSizeSmall();
			mbtn.setEnabled(false);
			panel.add(mbtn);
		}
		{
			MonochromeCheckbox mcb = new MonochromeCheckbox("<html>Les petits chats<br>sont-ils bleu?");
			mcb.setSelected(true);
			panel.add(mcb);
		}
		{
			MonochromeLabel mbtn = new MonochromeLabel("Button1");
			mbtn.setPreferredSize(new Dimension(200, 60));
			panel.add(mbtn);
		}
		{
			MonochromeLabel mbtn = new MonochromeLabel("MonochromeButton2");
			mbtn.setPreferredSize(new Dimension(300, 60));
			panel.add(mbtn);
		}
		{
			AfficheurScore sc = new AfficheurScore(10, CouleurCase.BLANC);
			sc.setPreferredSize(new Dimension(30,30));
			panel.add(sc);
		}
		{
			AfficheurScore sc = new AfficheurScore(4, CouleurCase.NOIR);
			sc.setPreferredSize(new Dimension(30,30));
			panel.add(sc);
		}
		{
			AfficheurScore sc = new AfficheurScore(0, CouleurCase.BLANC);
			sc.setPreferredSize(new Dimension(50,50));
			panel.add(sc);
		}

		{
			AfficheurScore sc = new AfficheurScore(9, CouleurCase.NOIR);
			sc.setPreferredSize(new Dimension(50,50));
			panel.add(sc);
		}
		{
			AfficheurScore sc = new AfficheurScore(2, CouleurCase.NOIR);
			sc.setPreferredSize(new Dimension(20,20));
			panel.add(sc);
		}
		{
			AfficheurScore sc = new AfficheurScore(1, CouleurCase.BLANC);
			sc.setPreferredSize(new Dimension(20,20));
			panel.add(sc);
		}
		{
			AfficheurScore sc = new AfficheurScore(43, CouleurCase.NOIR);
			sc.setPreferredSize(new Dimension(70,70));
			panel.add(sc);
		}
		{
			AfficheurScore sc = new AfficheurScore(10, CouleurCase.BLANC);
			sc.setPreferredSize(new Dimension(70,70));
			panel.add(sc);
		}
		frame.setVisible(true);
	}
}
