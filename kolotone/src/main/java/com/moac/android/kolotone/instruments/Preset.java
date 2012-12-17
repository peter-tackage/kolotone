package com.moac.android.kolotone.instruments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.widget.EditText;

import com.moac.android.kolotone.InstrumentActivity;
import com.moac.android.kolotone.Utils;

public class Preset {
	public static String PRESETS = "PRESETS";

	public void showLoadMenu(final InstrumentActivity p) {
		SharedPreferences s = p.getSharedPreferences(InstrumentActivity.SHARED_PREFERENCES_AUDIO, 0);
		Object o = Utils.stringToObject(s.getString(PRESETS, ""));
		try {
			String[] nofinalitems = null;
			if (o instanceof String[]) {
				nofinalitems = (String[]) o;
			} else {
				nofinalitems = new String[]{};
			}
			final String[] items = nofinalitems;
			
			AlertDialog.Builder builder = new AlertDialog.Builder(p);
			builder.setTitle("Pick a saved instance");
			builder.setItems(items, new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog, int item) {
			        loadPreset(p,p.instrument, items[item]);
			    }
			});
			AlertDialog alert = builder.create();
			alert.show();
		} catch (Exception e) {
			
		}
	}
	
	public void showSaveMenu(final InstrumentActivity p) {
		SharedPreferences s = p.getSharedPreferences(InstrumentActivity.SHARED_PREFERENCES_AUDIO, 0);
		Object o = Utils.stringToObject(s.getString(PRESETS, ""));
		try {
			final String[] items = (String[])o;
			
			AlertDialog.Builder builder = new AlertDialog.Builder(p);
			builder.setTitle("Pick a saved instance");
			builder.setItems(items, new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog, int item) {
			        savePreset(p,p.instrument, items[item]);
			    }
			});
			builder.setNeutralButton("New", new DialogInterface.OnClickListener() {  
				public void onClick(DialogInterface dialog, int whichButton) {  
					showSaveAsMenu(p);
				}
				}); 
			AlertDialog alert = builder.create();
			alert.show();
		} catch (Exception e) {
			
		}
	}
	
	public void showSaveAsMenu(final InstrumentActivity p ) {
		AlertDialog.Builder builder = new AlertDialog.Builder(p);
		builder.setTitle("Name?");
		builder.setMessage("Pick a name for the preset");
		final EditText text = new EditText(p);
		builder.setView(text);
		builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {  
			public void onClick(DialogInterface dialog, int whichButton) {  
			  String value = text.getText().toString();  
			  savePreset(p, p.instrument, value);
			}  
			}); 
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	public void loadPreset(InstrumentActivity p, Instrument e, String preset) {
		SharedPreferences s = p.getSharedPreferences(InstrumentActivity.SHARED_PREFERENCES_AUDIO, 0);
		e.updateSettings(null, null, preset);
	}
	
	public void savePreset(InstrumentActivity p, Instrument e, String preset) {
		
	}
}
