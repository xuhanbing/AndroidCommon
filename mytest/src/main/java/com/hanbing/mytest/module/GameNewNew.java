package com.hanbing.mytest.module;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameNewNew {

	/**
	 * �˿�������
	 * @author hanbing
	 *
	 */
	public static class Poker
	{
		
		static final boolean UseJoker = false;
		
		static int[] VALUES = {0x0,
				0x1, 0x2, 0x3, 0x4, 0x5,
				0x6, 0x7, 0x8, 0x9, 0xA,
				0xB, 0xC, 0xD, 0xE, 0xF};
		
		
		static String[] NAMES = {"",
				"A", "2", "3", "4", "5", 
				"6", "7", "8","9", "10", 
				"J", "Q", "K", "Black Joker", "Red Joker"
		};
		
		static  int[] TYPE_VALUES = 
			{
				0x0,
				0x1,
				0x2,
				0x3,
				0x4
			};
		
		static String[] TYPE_NAMES = {
			"",
			"Square", //��Ƭ
			"Club",  //÷��
			"Heart", //����
			"Spade"  //����
		};
		
		public static int[] getPokerArr()
		{
			int [] arr = new int[UseJoker ? 54 : 52];
			
			for (int i = 0; i < 4; i++)
			{
				for (int j = 0; j < 13; j++)
				{
					arr[i * 13 + j] = getPoker(TYPE_VALUES[i+1], VALUES[j+1]);
				}
			}
			
			if (UseJoker)
			{
				arr[52] = VALUES[VALUES.length-2];
				arr[53] = VALUES[VALUES.length-1];
			}
			
			return arr;
		}
		
		
		public static int getPoker(int type, int value)
		{
			return (type << 4 & 0xF0) | value;
		}
		
		public static int getPokerValue(int value)
		{
			return 0x0F & value;
		}
		
		public static int getPokerType(int value)
		{
			return (0xF0 & value) >> 4;
		}
		
		public static String getPokerName(int value)
		{
			return TYPE_NAMES[getPokerType(value)] + NAMES[getPokerValue(value)];
		}
		
		/**
		 * �Ƿ�Ƚϻ�ɫ
		 * @param lv
		 * @param rv
		 * @param cmpFlower
		 * @return
		 */
		public static int compare(int lv, int rv)
		{
			return compare(lv, rv, true);
		}
		
		public static int compare(int lv, int rv, boolean cmpFlower)
		{
			int value = (lv & 0xF - rv & 0xF);
			
			//��ֵ��ͬ����ɫ
			if (cmpFlower && 0 == value)
			{
				value = lv & 0xF0 - rv & 0xF0;
			}
			
			return value;
		}
		
		public static String getPokerResName(int value)
		{
			String name = "";
			
			int type = getPokerType(value);
			value = getPokerValue(value);
			
			type = 5 - type;
			
			if (0xF == value)
			{
				name = "a.JPG";
			}
			else if (0xE == value)
			{
				name = "b.JPG";
			}
			else if (value > 0xA)
			{
				if (0xB == value)
				{
					name = "a.JPG";
				}
				else if (0xC == value)
				{
					name = "b.JPG";
				}
				else if (0xD == value)
				{
					name = "c.JPG";
				}
			}
			else
			{
				name = (value % 10) + ".JPG";
			}
			
			return "pokers/" + (type < 5 ? "" + type : "") + name;
		}
	}

	/**
	 * ţ�ļ��������
	 * @author hanbing
	 *
	 */
	public static enum Rank
	{
		EMPTY(0x01),
		SMALL_NEW(0x11),
		BIG_NEW(0x22),
		NEW_NEW(0x33),
		SILVER_NEW(0x44),
		GOLD_NEW(0x55),
		BOMB(0x66),
		FIVE_NEW(0xaa);
		
		int value = 0;
		Rank(int value)
		{
			this.value = value;
		}
		public int getValue()
		{
			return value;
		}
		
		public static int getPrice(Rank r)
		{
			return r.getValue() & 0xF;
		}
		
		public static int compare(int lr, int rr)
		{
			return lr & 0xF0 - rr & 0xF0;
		}
	}
	
	
	public static class Controller
	{
		
		static int POKER_MASK_REAL_VALUE = 0x0F;
		static int POKER_MASK_TYPE = 0xF0;
		static int POKER_MASK = POKER_MASK_TYPE | POKER_MASK_REAL_VALUE;
		static int POKER_TYPE_SHIFT = 4;
		
		static int POKER_MASK_MAX = 0x00FF00;
		static int POKER_MAX_SHIFT = 8;
		
		static int RANK_MASK_VALUE = 0xF00000;
		static int RANK_MASK_PRICE = 0x0F0000;
		static int RANK_MASK = RANK_MASK_VALUE | RANK_MASK_PRICE;
		static int RANK_SHIFT = 12;
 		
		static int POKER_NUMS = 5;
		
		static int[] POKER_ARR = null;
		
		/**
		 * ���ÿ���˵���
		 * @param nums ����
		 * @return
		 */
		public static List<int[]> getPokers(int nums)
		{
//			if (null == POKER_ARR)
			{
				POKER_ARR = Poker.getPokerArr();
			}
			
			if (nums * POKER_NUMS > POKER_ARR.length)
			{
				new IllegalArgumentException("nums max=" + POKER_ARR.length/POKER_NUMS);
			}
			
			Random rd = new Random(System.currentTimeMillis());
			
			List<Integer> exist = new ArrayList<Integer>();
			List<int[]> list = new ArrayList<int[]>();
			
			for (int i = 0; i < nums; i++)
			{
				int[] arr = new int[POKER_NUMS];
				
				for (int j = 0; j < POKER_NUMS; j++)
				{
					
					int index = 0;
					
					while (exist.contains(index = Math.abs(rd.nextInt()) % POKER_ARR.length))
					{
						continue;
					}
					
					exist.add(index);
					
					arr[j] = POKER_ARR[index];
				}
				
				list.add(arr);
			}
			
			return list;
		}
		
		/**
		 * ���ֱȽϣ� k>q>j>10>9>8>7>6>5>4>3>2>a��
			��ɫ�Ƚϣ�����>����>÷��>���顣
			���ͱȽϣ���ţ<��ţ<ţţ<��ţ<��ţ<ը��<��Сţ��
			��ţ���ͱȽϣ�ȡ��������һ���ƱȽϴ�С���ƴ��Ӯ����С��ͬ�Ȼ�ɫ��
			��ţ���ͱȽϣ���ţ����ţ����ͬׯ���С�
			ţţ���ͱȽϣ�ȡ��������һ���ƱȽϴ�С���ƴ��Ӯ����С��ͬ�Ȼ�ɫ��
			��ţ���ͱȽϣ�ȡ��������һ���ƱȽϴ�С���ƴ��Ӯ����С��ͬ�Ȼ�ɫ��
			��ţ���ͱȽϣ�ȡ��������һ���ƱȽϴ�С���ƴ��Ӯ����С��ͬ�Ȼ�ɫ��
			ը��֮���С�Ƚϣ�ȡը���ƱȽϴ�С��
			��Сţ���ͱȽϣ�ׯ���С�
		 * @param lp
		 * @param rp
		 * @return
		 */
		public static int compare(NewNewPlayer lp, NewNewPlayer rp)
		{
			//ֻ�Ƚ�ׯ�Һ��м�
			if (lp.isBanker == rp.isBanker)
			{
				return 0;
			}
			
			//ţֵ
			int lv = getNewNewValue(lp.pokers);
			int rv = getNewNewValue(rp.pokers);
			
			//����
			int lr = lv & RANK_MASK;
			int rr = rv & RANK_MASK;
			
			//���ֵ
			int lmax = lv & POKER_MASK_MAX;
			int rmax = lv & POKER_MASK_MAX;
			
			int cmp = Rank.compare(lr, rr);
			
			//�������
			if (0 == cmp)
			{
				if (lr == Rank.FIVE_NEW.getValue())
				{
					//��Сţ��ׯ����
					if (lp.isBanker)
					{
						cmp = 1;
					}
					else
					{
						cmp = -1;
					}
				}
				else if (lr == Rank.BOMB.getValue())
				{
					cmp = lmax - rmax;
				}
				else if (lr == Rank.SMALL_NEW.getValue() || lr == Rank.BIG_NEW.getValue())
				{
					cmp = Poker.compare(lmax, rmax, false);
					
					//��ֵ��ͬ��ׯ�Ҵ�
					if (0 == cmp)
					{
						if (lp.isBanker)
						{
							cmp = 1;
						}
						else
						{
							cmp = -1;
						}
					}
				}
				else
				{
					//��ֵ��ͬ�Ƚϻ�ɫ
					cmp = Poker.compare(lmax, rmax);
				}
				
			}
			
			
			lp.value = lv;
			rp.value = rv;
			
			return cmp;
		}
		
		/**
		 * ��ȡ�˿�������
		 * @param pokers
		 * @return
		 */
		public static String[] getPokerNames(int[] pokers)
		{
			String[] names = new String[pokers.length];
			
			for (int i = 0; i < names.length; i++)
			{
				names[i] = Poker.getPokerName(pokers[i]);
			}
			
			return names;
		}
		
		/**
		 * ��ȡ������
		 * @param pokers
		 * @return
		 */
		public static int getPokerMax(int [] pokers)
		{
			int max = 0;
			
			for (int i = 0; i < pokers.length; i++)
			{
				if (Poker.compare(max, pokers[i]) < 0)
				{
					max = pokers[i];
				}
			}
			
			return max;
		}
		
		/**
		 * ��ȡ�Ƶĺ�
		 * @param pokers
		 * @return
		 */
		public static int getPokerSum(int[] pokers)
		{
			int sum = 0;
			
			for (int i = 0; i < pokers.length; i++)
			{
				int poker = pokers[i];
				int pokerValue = Poker.getPokerValue(poker);
						
				//��ֵ���Ϊ10
				pokerValue = Math.min(pokerValue, 0xA);
				
				//�����
				sum += pokerValue;
			}
			
			return sum;
		}
		
		public static int get3PokerSum(int[] pokers)
		{
			int len = pokers.length;
			int [] sub = new int[3];
			int subSum = 0;
			//�ж����ź��Ƿ�Ϊ10
			for (int i = 0; i < len-2; i++)
			{
				sub[0] = pokers[i];
				for (int j = i+1; j < len-1; j++)
				{
					sub[1] = pokers[j];
					for (int k = j + 1; k < len; k++)
					{
						sub[2] = pokers[k];
						
						subSum = getPokerSum(sub);
						
						if (subSum % 10 == 0)
						{
							return subSum;
						}
					}
				}
			}
			
			return subSum;
		}
		
		
		public static int getNewNewValue(int[] pokers)
		{
			int value = 0;
			Rank rank = Rank.EMPTY;
			
			int maxPoker = 0;
			int maxValue = 0;
			int sum = 0;
			int[] arr = new int[Poker.VALUES.length];
			
			for (int i = 0; i < pokers.length; i++)
			{
				int poker = pokers[i];
				int pokerValue = Poker.getPokerValue(poker);
						
				//��ֵ��1
				int count = arr[pokerValue];
				arr[pokerValue] = count + 1;
				
				//��¼������
				if (Poker.compare(maxPoker, poker) < 0)
				{
					maxPoker = poker;
				}
				
				//��¼������ֵ
				maxValue = Math.max(maxValue, pokerValue);
				
				//��ֵ���Ϊ10
				pokerValue = Math.min(pokerValue, 0xA);
				
				//�����
				sum += pokerValue;
			}
			
			
			int countFlower = 0; //���ƣ�j,q,k�ȵĸ���
			
			for (int i = 0; i < arr.length; i++)
			{
				int v = arr[i];
				
				if (i > 10)
				{
					countFlower++;
				}
				else if (v >= 4)
				{
					rank = Rank.BOMB;
					maxValue = i;
					break;
				}
			}
			
			if (sum < 10)
			{
				//��Сţ����С��10����������С��5
				if (maxValue < 5)
				{
					rank = Rank.FIVE_NEW;
				}
			}
			else if (sum == 50)
			{
				
				if (countFlower == 5)
				{
					//��ţ��������5��
					rank = Rank.GOLD_NEW;
				}
				else if (countFlower == 4)
				{
					//��ţ��10һ�ţ�������4��
					rank = Rank.SILVER_NEW;
				}
				else
				{
					//ţţ
					rank = Rank.NEW_NEW;
				}
			}
			else
			{
				int subSum = get3PokerSum(pokers);
				
				//��ţ
				if (subSum % 10 == 0)
				{
					//ţ7~9
					if (sum % 10 > 6)
					{
						rank = Rank.BIG_NEW;
					}
					else
					{
						//ţ1~6
						rank = Rank.SMALL_NEW;
					}
					
				}
			}
			
			return rank.getValue() << RANK_SHIFT
					& maxPoker << POKER_MAX_SHIFT
					& value;
		}
	}
	
	/**
	 * ���
	 * @author hanbing
	 *
	 */
	public static class NewNewPlayer
	{
		String name = "";
		boolean isBanker = false; //�Ƿ���ׯ��
		
		int[] pokers;  //��
		int value;   //����
		int chip = 1000;  //�ܳ���
		int wager = 10;   //��ǰ��ע
		
		/**
		 * @return the isBanker
		 */
		public boolean isBanker() {
			return isBanker;
		}
		/**
		 * @param isBanker the isBanker to set
		 */
		public void setBanker(boolean isBanker) {
			this.isBanker = isBanker;
		}
		/**
		 * @return the pokers
		 */
		public int[] getPokers() {
			return pokers;
		}
		/**
		 * @param pokers the pokers to set
		 */
		public void setPokers(int[] pokers) {
			this.pokers = pokers;
		}
		/**
		 * @return the value
		 */
		public int getValue() {
			return value;
		}
		/**
		 * @param value the value to set
		 */
		public void setValue(int value) {
			this.value = value;
		}
		/**
		 * @return the chip
		 */
		public int getChip() {
			return chip;
		}
		/**
		 * @param chip the chip to set
		 */
		public void setChip(int chip) {
			this.chip = chip;
		}
		/**
		 * @return the wager
		 */
		public int getWager() {
			return wager;
		}
		/**
		 * @param wager the wager to set
		 */
		public void setWager(int wager) {
			this.wager = wager;
		}
		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}
		/**
		 * @param name the name to set
		 */
		public void setName(String name) {
			this.name = name;
		}
	}

}
