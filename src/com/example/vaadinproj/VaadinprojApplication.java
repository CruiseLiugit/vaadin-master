package com.example.vaadinproj;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import com.example.vaadinproj.domain.Wydarzenie;
import com.vaadin.Application;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Window.Notification;

public class VaadinprojApplication extends Application implements
		Property.ValueChangeListener {
	SimpleDateFormat sdate = new SimpleDateFormat("dd.MM.yy");
	String data = sdate.format(new Date());
	final HashMap<String, Integer> mapacheck = new HashMap<String, Integer>();
	final HashMap<String, BeanItemContainer<Wydarzenie>> mapa = new HashMap<String, BeanItemContainer<Wydarzenie>>();
	Table wydarzenieTable;
	BeanItemContainer<Wydarzenie> beanTemp = new BeanItemContainer<Wydarzenie>(
			Wydarzenie.class);

	@Override
	public void init() {
		// -----------------创建数据-----------------
		mapa.put(data, new BeanItemContainer<Wydarzenie>(Wydarzenie.class));
		mapacheck.put(data, 1);
		// 创建一个封装简单 data desc 数据的 Bean 对象，保存到 BeanItem 对象中
		final Wydarzenie wydarzenie = new Wydarzenie();
		BeanItem<Wydarzenie> wydarzenieItem = new BeanItem<Wydarzenie>(
				wydarzenie);
		// 创建一个 BeanItemContainer对象
		final BeanItemContainer<Wydarzenie> wydarzenieItemCont = new BeanItemContainer<Wydarzenie>(
				Wydarzenie.class);

		// -----------------创建组件-----------------
		// 创建一个 窗口对象
		final Window mainWindow = new Window("Vaadinproj Application");

		// 创建一个 表单对象
		final Form formularz = new Form();
		formularz.setCaption("Podaj wydarzenie");
		formularz.setWriteThrough(false);
		formularz.setItemDataSource(wydarzenieItem);
		formularz.setVisibleItemProperties(Arrays
				.asList(new String[] { "tresc" }));

		// 创建一个表格对象
		wydarzenieTable = new Table("Wydarzenia", beanTemp);
		wydarzenieTable.setImmediate(true);
		wydarzenieTable.setWidth("400px");

		// 日历对象
		InlineDateField datetime = new InlineDateField("Podaj date:");
		datetime.setValue(new java.util.Date());
		datetime.setResolution(PopupDateField.RESOLUTION_DAY);
		datetime.addListener(this);
		datetime.setImmediate(true);

		// 按钮对象，以及按钮点击事件操作
		Button dodaj = new Button("Dodaj", new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				try {
					formularz.commit();
					wydarzenieItemCont.addItem(new Wydarzenie(data, wydarzenie
							.getTresc()));
					// getMainWindow().showNotification(Integer.toString(wydarzenieItemCont.size()));
					mapa.get(data).addItem(
							new Wydarzenie(data, wydarzenie.getTresc()));
					// wydarzenieTable.addItem(new Wydarzenie(data,
					// wydarzenie.getTresc()));
					mainWindow.removeComponent(wydarzenieTable);
					wydarzenieTable = new Table("Wydarzenie", mapa.get(data));
					wydarzenieTable.setSelectable(true);
					wydarzenieTable.setWidth("400px");
					mainWindow.addComponent(wydarzenieTable);
					// table(data, wydarzenieItemCont);
				} catch (Exception e) {

				}

			}
		});

		// -----------------把窗口对象，放入启动类中-----------------
		// mainWindow.addComponent(label);
		setMainWindow(mainWindow);
		// mainWindow.addComponent(datetime);
		// mainWindow.addComponent(formularz);

		// 外部布局，水平布局
		HorizontalLayout hl = new HorizontalLayout();
		hl.addComponent(datetime);
		hl.setSpacing(true); // padding
		// hl.addComponent(wydarzenieTable);
		// 内部布局,垂直布局
		VerticalLayout vl = new VerticalLayout();
		vl.addComponent(formularz);
		vl.addComponent(dodaj);
		vl.setSpacing(true);

		// mainWindow.addComponent(wydarzenieTable);
		Button usun = new Button("删除", new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				if (wydarzenieTable.getValue() !=null) {
					wydarzenieTable.removeItem(wydarzenieTable.getValue());
					wydarzenieTable.select(null);
				}else{
					// 新建一个通知
					System.out.println("没有选中,不能删除....");
					//new Notification("请选中表格中的数据","<br />没有选中，无法删除数据",Notification.TYPE_WARNING_MESSAGE,true).show(Page.getCurrent());
				}
				
				

			}
		});
		vl.addComponent(usun);

		hl.addComponent(vl);
		mainWindow.addComponent(hl);
		mainWindow.addComponent(wydarzenieTable);
	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		DateFormat dateFormatter = DateFormat.getDateInstance(DateFormat.SHORT);
		Object value = event.getProperty().getValue();
		if (value == null || !(value instanceof Date)) {
			getMainWindow().showNotification("Zła data!");
		} else {
			String dateOut = dateFormatter.format(value);
			this.data = dateOut;
			if (!mapacheck.containsKey(data)) {
				mapacheck.put(data, 1);
				mapa.put(data, new BeanItemContainer<Wydarzenie>(
						Wydarzenie.class));
			}
			getMainWindow().removeComponent(wydarzenieTable);
			wydarzenieTable = new Table("Wydarzenie", mapa.get(data));
			wydarzenieTable.setSelectable(true);
			wydarzenieTable.setWidth("400px");
			getMainWindow().addComponent(wydarzenieTable);
		}

	}
}
