package com.hanbing.mytest.activity.action;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class TestCalcTax extends Activity {

	
	EditText etPay;
	EditText etTax;
	EditText etPayOff;
	EditText etTaxBase;
	EditText etCut;
	
	public TestCalcTax() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_calctax);
		
		etTaxBase = (EditText) findViewById(R.id.et_tax_start_point);
		etPay = (EditText) findViewById(R.id.et_pay);
		etCut = (EditText) findViewById(R.id.et_pay_cut);
		etPayOff = (EditText) findViewById(R.id.et_pay_off_tax);
		etTax = (EditText) findViewById(R.id.et_tax);
		
		etTaxBase.setText("3500.00");
		etCut.setText("0.00");
		
//		startActivity(new Intent(this, TestRestart.class));
	}
	
	
	public void calc(View view)
	{
		float base = 3500.0f;
		float pay = 0.0f;
		float payOff = 0.0f;
		float cut = 0.0f;
		
		try {
			base = Float.valueOf(etTaxBase.getText().toString());
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			pay = Float.valueOf(etPay.getText().toString());
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			cut = Float.valueOf(etCut.getText().toString());
		} catch (Exception e) {
			// TODO: handle exception
		}
		pay -= cut;
		
		float tax = calcTax(base, pay);
		payOff = pay - tax;
		
		etTax.setText(tax + "");
		etPayOff.setText(payOff + "");
	}
	
	static float[] TAX_POINTS = new float[]
			{
			1500.0f,
			4500.0f - 1500.0f,
			9000.0f - 4500.0f,
			35000.0f - 9000.0f,
			55000.0f - 35000.0f,
			80000.0f - 55000.0f
			};
	static float[] TAX_RATIOS = new float[]
			{
			0.03f,
			0.10f,
			0.20f,
			0.25f,
			0.30f,
			0.35f,
			0.45f
			};

	public static float calcTax(float base, float pay)
	{
		float tax = 0.0f;
		
		pay -= base;
		
		if (pay > 0)
		{
			tax += calcTax(pay, 0);
		}
		
		return tax;
	}
	
	
	
	
	public static float calcTax(float value, int index)
	{
		
		float tax = 0.0f;
		float tmp = 0.0f;
		
		if (index < TAX_POINTS.length)
		{
			tmp = value - TAX_POINTS[index];
		}
		
		if (tmp > 0)
		{
			tax += TAX_POINTS[index] * TAX_RATIOS[index];
			tax += calcTax(tmp, ++index);
		}
		else
		{
			tax += value * TAX_RATIOS[index];
		}
		
		return tax;
	}
}
