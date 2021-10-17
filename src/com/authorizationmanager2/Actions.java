package com.authorizationmanager2;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.authorizationmanager2.screens.ConsoleScrn;
import com.authorizationmanager2.screens.DelUserScrn;
import com.authorizationmanager2.screens.ListScrn;
import com.authorizationmanager2.screens.NewUserScrn;
import com.authorizationmanager2.screens.UpdUserScrn;
import com.authorizationmanager2.screens.UpdUsrIdScrn;
import com.authorizationmanager2.tabbedpane.Console;
import com.authorizationmanager2.data.DataUser;
import com.authorizationmanager2.data.DataUserId;

public class Actions {
	public static void New() {
		AuthorizationManager2.cpyArea.setText(Console.console.getText());
		AuthorizationManager2.setScreen("new");
		AuthorizationManager2.getMidLeftTopPanel().removeAll();
		AuthorizationManager2.getMidLeftTopPanel().add(AuthorizationManager2.combo);
		AuthorizationManager2.getMidLeftTopPanel().add(AuthorizationManager2.ok);
		AuthorizationManager2.getMidLeftTopPanel().add(AuthorizationManager2.getEmpty2());
		AuthorizationManager2.getMidLeftTopPanel().add(AuthorizationManager2.getUsrSelect());
		AuthorizationManager2.getMidLeftTopPanel().add(AuthorizationManager2.getCmbUser());
		AuthorizationManager2.getMidLeftTopPanel().revalidate();
		AuthorizationManager2.getMidLeftTopPanel().repaint();

		AuthorizationManager2.getMidRightPanel().removeAll();
		AuthorizationManager2.getMidRightPanel().add(new NewUserScrn(), "newPanel");
		AuthorizationManager2.getMidRightPanel().revalidate();
		AuthorizationManager2.getMidRightPanel().repaint();
		Console.console.setText(AuthorizationManager2.cpyArea.getText());
	}
	public static void Update() {
		AuthorizationManager2.cpyArea.setText(Console.console.getText());
		AuthorizationManager2.setScreen("update");
		AuthorizationManager2.getMidLeftTopPanel().removeAll();
		AuthorizationManager2.getMidLeftTopPanel().add(AuthorizationManager2.combo);
		AuthorizationManager2.getMidLeftTopPanel().add(AuthorizationManager2.ok);
		AuthorizationManager2.getMidLeftTopPanel().revalidate();
		AuthorizationManager2.getMidLeftTopPanel().repaint();

		AuthorizationManager2.getMidRightPanel().removeAll();
		AuthorizationManager2.getMidRightPanel().add(new UpdUserScrn(), "newPanel");
		AuthorizationManager2.getMidRightPanel().revalidate();
		AuthorizationManager2.getMidRightPanel().repaint();
		Console.console.setText(AuthorizationManager2.cpyArea.getText());
	}
	public static void Change() {
		AuthorizationManager2.cpyArea.setText(Console.console.getText());
		AuthorizationManager2.setScreen("change");
		AuthorizationManager2.getMidLeftTopPanel().removeAll();
		AuthorizationManager2.getMidLeftTopPanel().add(AuthorizationManager2.combo);
		AuthorizationManager2.getMidLeftTopPanel().add(AuthorizationManager2.ok);
		AuthorizationManager2.getMidLeftTopPanel().revalidate();
		AuthorizationManager2.getMidLeftTopPanel().repaint();

		AuthorizationManager2.getMidRightPanel().removeAll();
		AuthorizationManager2.getMidRightPanel().add(new UpdUsrIdScrn(), "newPanel");
		AuthorizationManager2.getMidRightPanel().revalidate();
		AuthorizationManager2.getMidRightPanel().repaint();
		Console.console.setText(AuthorizationManager2.cpyArea.getText());
	}
	public static void Delete() {
		AuthorizationManager2.cpyArea.setText(Console.console.getText());
		AuthorizationManager2.setScreen("delete");
		AuthorizationManager2.getMidLeftTopPanel().removeAll();
		AuthorizationManager2.getMidLeftTopPanel().add(AuthorizationManager2.combo);
		AuthorizationManager2.getMidLeftTopPanel().add(AuthorizationManager2.ok);
		AuthorizationManager2.getMidLeftTopPanel().revalidate();
		AuthorizationManager2.getMidLeftTopPanel().repaint();

		AuthorizationManager2.getMidRightPanel().removeAll();
		AuthorizationManager2.getMidRightPanel().add(new DelUserScrn(), "newPanel");
		AuthorizationManager2.getMidRightPanel().revalidate();
		AuthorizationManager2.getMidRightPanel().repaint();
		Console.console.setText(AuthorizationManager2.cpyArea.getText());
	}
	public static void Reset() {
		AuthorizationManager2.cpyArea.setText(Console.console.getText());
		AuthorizationManager2.getUserId().setText("");
		AuthorizationManager2.iPwdInp.setText("");
		AuthorizationManager2.setScreen("reset");
		AuthorizationManager2.getMidRightPanel().removeAll();
		ConsoleScrn.titleIns = "Reset Userid";
		AuthorizationManager2.getMidRightPanel().add(new ConsoleScrn(), "newPanel");
		AuthorizationManager2.getMidRightPanel().revalidate();
		AuthorizationManager2.getMidRightPanel().repaint();

		AuthorizationManager2.getMidLeftTopPanel().removeAll();

		AuthorizationManager2.getUserText().setText("Type user_id to reset:");
		AuthorizationManager2.getUserText().setForeground(AuthorizationManager2.vlgreen);

		AuthorizationManager2.getMidLeftTopPanel().add(AuthorizationManager2.combo);
		AuthorizationManager2.getMidLeftTopPanel().add(AuthorizationManager2.ok);
		AuthorizationManager2.getMidLeftTopPanel().add(AuthorizationManager2.getEmpty2());
		AuthorizationManager2.getMidLeftTopPanel().add(AuthorizationManager2.getUserText());
		AuthorizationManager2.getMidLeftTopPanel().add(AuthorizationManager2.getUserId());
		AuthorizationManager2.getMidLeftTopPanel().add(AuthorizationManager2.getiPwdTxt());
		AuthorizationManager2.getMidLeftTopPanel().add(AuthorizationManager2.iPwdInp);
		AuthorizationManager2.getMidLeftTopPanel().add(AuthorizationManager2.getOk2());

		AuthorizationManager2.getMidLeftTopPanel().revalidate();
		AuthorizationManager2.getMidLeftTopPanel().repaint();
		Console.console.setText(AuthorizationManager2.cpyArea.getText());
		
	}

	public static void Revoke() {
		AuthorizationManager2.cpyArea.setText(Console.console.getText());
		AuthorizationManager2.getUserId().setText("");
		AuthorizationManager2.setScreen("revoke");
		AuthorizationManager2.getMidRightPanel().removeAll();
		ConsoleScrn.titleIns = "Revoke Userid";
		AuthorizationManager2.getMidRightPanel().add(new ConsoleScrn(), "newPanel");
		AuthorizationManager2.getMidRightPanel().revalidate();
		AuthorizationManager2.getMidRightPanel().repaint();

		AuthorizationManager2.getMidLeftTopPanel().removeAll();

		AuthorizationManager2.getUserText().setText("Type userid to revoke:");
		AuthorizationManager2.getUserText().setForeground(AuthorizationManager2.vlgreen);

		AuthorizationManager2.getMidLeftTopPanel().add(AuthorizationManager2.combo);
		AuthorizationManager2.getMidLeftTopPanel().add(AuthorizationManager2.ok);
		AuthorizationManager2.getMidLeftTopPanel().add(AuthorizationManager2.getEmpty2());
		AuthorizationManager2.getMidLeftTopPanel().add(AuthorizationManager2.getUserText());
		AuthorizationManager2.getMidLeftTopPanel().add(AuthorizationManager2.getUserId());
		AuthorizationManager2.getMidLeftTopPanel().add(AuthorizationManager2.getOk1());

		AuthorizationManager2.getMidLeftTopPanel().revalidate();
		AuthorizationManager2.getMidLeftTopPanel().repaint();
		Console.console.setText(AuthorizationManager2.cpyArea.getText());
	}
	public static void ListUser() {
		AuthorizationManager2.setScreen("listuser");
		AuthorizationManager2.option = "user";

		String[] columnNames = { "UserId", "Name", "Surname", "email-address", "Start-date", "Active" };
		DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Class<?> getColumnClass(int column) {
				return (column == 0) ? Integer.class : String.class;
			}
		};

		for (DataUser d1 : AuthorizationManager2.userData) {
			int userId = d1.getId();
			String fName = d1.getfName();
			String sName = d1.getsName();
			String email = d1.getEmail();
			String sDate = d1.getsDate();
			String active = d1.getAct();
			Object[] data = { userId, fName, sName, email, sDate, active };
			tableModel.addRow(data);
		}

		DefaultTableCellRenderer center = new DefaultTableCellRenderer();
		center.setHorizontalAlignment(JLabel.CENTER);

		int tb80 = (int) Math.round(AuthorizationManager2.w * 0.0625);
		int tb180 = (int) Math.round(AuthorizationManager2.w * 0.15);
		int tb120 = (int) Math.round(AuthorizationManager2.w * 0.095);
		int tb280 = (int) Math.round(AuthorizationManager2.w * 0.22);
		int tb200 = (int) Math.round(AuthorizationManager2.w * 0.16);

		JTable table = new JTable(tableModel);
		table.setBackground(AuthorizationManager2.vlgreen);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getTableHeader().setBackground(AuthorizationManager2.vlgreen);
		table.getTableHeader().setFont(AuthorizationManager2.font18);
		table.setBorder(AuthorizationManager2.border);
		table.setGridColor(AuthorizationManager2.greend1);
		table.setFont(AuthorizationManager2.getFont16());
		table.setRowHeight((int) Math.round(AuthorizationManager2.h * 0.03));
		table.setEnabled(false);
		table.setPreferredScrollableViewportSize(new Dimension((int) (AuthorizationManager2.w * 0.5), (int) (AuthorizationManager2.h * 0.57))); 

		table.getColumnModel().getColumn(0).setPreferredWidth(tb80);
		table.getColumnModel().getColumn(1).setPreferredWidth(tb180);
		table.getColumnModel().getColumn(2).setPreferredWidth(tb200);
		table.getColumnModel().getColumn(3).setPreferredWidth(tb280);
		table.getColumnModel().getColumn(4).setPreferredWidth(tb120);
		table.getColumnModel().getColumn(5).setPreferredWidth(tb80);
		table.getColumnModel().getColumn(4).setCellRenderer(center);
		table.getColumnModel().getColumn(5).setCellRenderer(center);
		
		table.setAutoCreateRowSorter(true);

		AuthorizationManager2.setJsp(new JScrollPane(table));
		AuthorizationManager2.getJsp().setBackground(AuthorizationManager2.greend1);
		AuthorizationManager2.getJsp().getViewport().setBackground(AuthorizationManager2.vlgreen);
		AuthorizationManager2.getJsp().setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		AuthorizationManager2.getJsp().setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		AuthorizationManager2.getMidLeftTopPanel().removeAll();
		AuthorizationManager2.getMidLeftTopPanel().add(AuthorizationManager2.empty3);
		AuthorizationManager2.getMidLeftTopPanel().add(AuthorizationManager2.getBack());

		AuthorizationManager2.getMidRightPanel().removeAll();
		AuthorizationManager2.getMidRightPanel().setPreferredSize(new Dimension((int) (AuthorizationManager2.w * 0.55), (int) (AuthorizationManager2.h * 0.75)));// 700,560
		AuthorizationManager2.getMidRightPanel().add(new ListScrn(), "newPanel");
		AuthorizationManager2.getMidRightPanel().revalidate();
		AuthorizationManager2.getMidRightPanel().repaint();	
	}
	public static void ListId() {
		AuthorizationManager2.setScreen("listid");
		AuthorizationManager2.option = "userid";

		String[] columnNames = { "Auth_Id", "UserId", "Start-date", "End-date", "Valid", "Blocked",
				"Level", "Pw-Stat" };
		DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Class<?> getColumnClass(int column) {
				return (column == 0) ? Integer.class : String.class;
			}
		};

		for (DataUserId d1 : AuthorizationManager2.userIdData) {
			int authid = d1.getId();
			String uname = d1.getUser();
			String sdate = d1.getsDate();
			String edate = d1.getmDate();
			String valid1 = Boolean.toString(d1.getVal());
			String blocked1 = Boolean.toString(d1.getBlock());
			String level = d1.getLvl();
			String pwStat = d1.getPwStat();

			Object[] data = { authid, uname, sdate, edate, valid1, blocked1, level, pwStat};
			tableModel.addRow(data);
		}

		DefaultTableCellRenderer center = new DefaultTableCellRenderer();
		center.setHorizontalAlignment(JLabel.CENTER);

		JTable table = new JTable(tableModel);
		table.setBackground(AuthorizationManager2.vlgreen);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getTableHeader().setBackground(AuthorizationManager2.vlgreen);
		table.getTableHeader().setFont(AuthorizationManager2.font18);
		table.setBorder(AuthorizationManager2.border);
		table.setGridColor(AuthorizationManager2.greend1);
		table.setFont(AuthorizationManager2.getFont16());
		table.setRowHeight((int) Math.round(AuthorizationManager2.h * 0.03));
		table.setEnabled(false);
		table.setPreferredScrollableViewportSize(new Dimension((int) (AuthorizationManager2.w * 0.5), (int) (AuthorizationManager2.h * 0.57)));
		
		int tb80 = (int) Math.round(AuthorizationManager2.w * 0.0625);
		int tb100 = (int) Math.round(AuthorizationManager2.w * 0.078);
		int tb120 = (int) Math.round(AuthorizationManager2.w * 0.095);
//		int tb70 = (int) Math.round(AuthorizationManager2.w * 0.055);
		int tb200 = (int) Math.round(AuthorizationManager2.w * 0.16);

		table.getColumnModel().getColumn(0).setPreferredWidth(tb100);
		table.getColumnModel().getColumn(1).setPreferredWidth(tb120);
		table.getColumnModel().getColumn(2).setPreferredWidth(tb200);
		table.getColumnModel().getColumn(3).setPreferredWidth(tb120);
		table.getColumnModel().getColumn(4).setPreferredWidth(tb80);
		table.getColumnModel().getColumn(5).setPreferredWidth(tb100);
		table.getColumnModel().getColumn(6).setPreferredWidth(tb80);
		table.getColumnModel().getColumn(7).setPreferredWidth(tb100);
		table.getColumnModel().getColumn(2).setCellRenderer(center);
		table.getColumnModel().getColumn(3).setCellRenderer(center);
		table.getColumnModel().getColumn(4).setCellRenderer(center);
		table.getColumnModel().getColumn(5).setCellRenderer(center);
		table.getColumnModel().getColumn(6).setCellRenderer(center);
		table.getColumnModel().getColumn(7).setCellRenderer(center);
		
		table.setAutoCreateRowSorter(true);

		AuthorizationManager2.setJsp(new JScrollPane(table));
		AuthorizationManager2.getJsp().setBackground(AuthorizationManager2.greend1);
		AuthorizationManager2.getJsp().getViewport().setBackground(Color.LIGHT_GRAY);
		AuthorizationManager2.getJsp().setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		AuthorizationManager2.getJsp().setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		AuthorizationManager2.getMidLeftTopPanel().removeAll();
		AuthorizationManager2.getMidLeftTopPanel().add(AuthorizationManager2.empty3);
		AuthorizationManager2.getMidLeftTopPanel().add(AuthorizationManager2.getBack());

		AuthorizationManager2.getMidRightPanel().removeAll();
		AuthorizationManager2.getMidRightPanel().setPreferredSize(new Dimension((int) (AuthorizationManager2.w * 0.55), (int) (AuthorizationManager2.h * 0.75))); // 700,560/540
		AuthorizationManager2.getMidRightPanel().add(new ListScrn(), "newPanel");
		AuthorizationManager2.getMidRightPanel().revalidate();
		AuthorizationManager2.getMidRightPanel().repaint();
	}

}
