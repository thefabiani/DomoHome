package com.fabiani.domohome.app;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import java.util.UUID;


public class CommandFragment extends Fragment {
		public static final String TAG="CommandFragment";
		public static final String EXTRA_COMMAND_ID="com.homeautomation.smarthome_extra_command_id";
		private static String sWhoSelected=null;
		private static String sWhereSelected=null;
		private Spinner mWhoSpinner;
		private Spinner mWhereSpinner;
		private EditText mCommandTitleEditText;
		private Command mCommand;

		@Override
		public void onCreate(Bundle savedInstanceState){
			super.onCreate(savedInstanceState);
			mCommand=new Command();
			UUID commandId=(UUID)getArguments().getSerializable(EXTRA_COMMAND_ID);
			mCommand=Dashboard.get(getActivity()).getCommand(commandId);
		}
			
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
			View v=inflater.inflate(R.layout.fragment_command, parent, false);
			mCommandTitleEditText=(EditText)v.findViewById(R.id.command_title_edit_text);
			mCommandTitleEditText.setText(mCommand.getTitle());
			mCommandTitleEditText.addTextChangedListener(new TextWatcher(){

				@Override
				public void beforeTextChanged(CharSequence s, int start, int count,
						int after) {}
				
				@Override
				public void onTextChanged(CharSequence s, int start, int before,
						int count) {
					mCommand.setTitle(s.toString());
				}
				
				@Override
				public void afterTextChanged(Editable s) {}
			});
			
			mWhoSpinner=(Spinner)v.findViewById(R.id.command_who_spinner);
			ArrayAdapter<CharSequence>mWhoAdapter=ArrayAdapter
					.createFromResource(getActivity(), R.array.who_array, android.R.layout.simple_spinner_item);
			mWhoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			mWhoSpinner.setAdapter(mWhoAdapter);
			mWhoSpinner.setSelection(mCommand.getWhoSpinnerPosition());
			mWhoSpinner.setOnItemSelectedListener(new OnItemSelectedListener(){

				@Override
				public void onItemSelected(AdapterView<?> parent, View view,
						int position, long id) {
					mCommand.setWhoSpinnerPosition(mWhoSpinner.getSelectedItemPosition());
					sWhoSelected=mWhoSpinner.getItemAtPosition(position).toString();
					switch(sWhoSelected){
					// check mCommand settings!  Check OK
					case "Scenarios":
						mCommand.setWho(Command.sWhoChoices[0]);
						break;
					case "Lighting":
						mCommand.setWho(Command.sWhoChoices[1]);
						break;
					case "Automatism":
						mCommand.setWho(Command.sWhoChoices[2]);
						break;
					case "Electrical loads":
						mCommand.setWho(Command.sWhoChoices[3]);
						break;
					case "Warming management":
						mCommand.setWho(Command.sWhoChoices[4]);
						break;
					case "Antitheft":
						mCommand.setWho(Command.sWhoChoices[5]);
						break;
					case "Basic video doorphone":
						mCommand.setWho(Command.sWhoChoices[6]);
						break;
					case "Gateway management":
						mCommand.setWho(Command.sWhoChoices[7]);
						break;
					case "CEN commands":
						mCommand.setWho(Command.sWhoChoices[8]);
						break;
					case "Sound diffusion":
						mCommand.setWho(Command.sWhoChoices[9]);
						break;
					case "Sound diffusion 2":
						mCommand.setWho(Command.sWhoChoices[10]);
						break;
					case "MH200N scenarios":
						mCommand.setWho(Command.sWhoChoices[11]);
						break;
					case "Power management":
						mCommand.setWho(Command.sWhoChoices[12]);
						break;
					case "CEN plus etc":
						mCommand.setWho(Command.sWhoChoices[13]);
						break;
					case "Automation diagnostics":
						mCommand.setWho(Command.sWhoChoices[14]);
						break;
					case "Thermoregulation diagnostics":
						mCommand.setWho(Command.sWhoChoices[15]);
						break;
					case "Device diagnostics":
						mCommand.setWho(Command.sWhoChoices[16]);
						break;
					}
				}
				@Override
				public void onNothingSelected(AdapterView<?> parent) {}
			});
			
			mWhereSpinner=(Spinner)v.findViewById(R.id.command_where_spinner);
			ArrayAdapter<Integer>mWhereAdapter=new ArrayAdapter<>
				(getActivity(),android.R.layout.simple_spinner_item, Command.sWhereChoices);
			mWhereSpinner.setAdapter(mWhereAdapter);
			mWhereSpinner.setSelection(mCommand.getWhereSpinnerPosition());
			mWhereSpinner.setOnItemSelectedListener(new OnItemSelectedListener(){

				@Override
				public void onItemSelected(AdapterView<?> parent, View view,
						int position, long id) {
					mCommand.setWhereSpinnerPosition(mWhereSpinner.getSelectedItemPosition());
					sWhereSelected=mWhereSpinner.getItemAtPosition(position).toString();
					mCommand.setWhere(Command.sWhereChoices[Integer.parseInt(sWhereSelected)-1]);
					}
				@Override
				public void onNothingSelected(AdapterView<?> parent) {}
			});
			
			return v;
		}
		
		public static CommandFragment newInstance(UUID commandId){
			Bundle args=new Bundle();
			args.putSerializable(EXTRA_COMMAND_ID,commandId);
			CommandFragment fragment=new CommandFragment();
			fragment.setArguments(args);
			return fragment;
		}
	}	

