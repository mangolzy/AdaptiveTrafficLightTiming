package cdo.its;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.Math;

public class LookAside {

	/***
	 * 閺嶈宓佸鍌滃芳鐠侊紕鐣诲ù渚�鍣�
	 * 
	 * @param x
	 *            - 濞翠礁鍙嗛柌锟� * @param proba - 濮掑倻宸糰lpha,beta,gamma
	 * @return
	 */
	public static int[] sampleData(int x, float[] proba) {
		int[] ret = new int[proba.length];

		for (int i = 0; i < proba.length; i++) {
			ret[i] = (int) Math.ceil(((float) x) * proba[i]);
		}

		return ret;
	}

	/***
	 * 閺嶈宓佽ぐ鎾冲閸ユ稐閲滈弬鐟版�?�閻ㄥ嫭绁﹂柌蹇ョ礉閸滃矁娴嗗顖涱洤閻滐拷 閸欏﹥娓舵径褔锟芥潻鍥ㄦ�?
	 * 鐠侊紕鐣婚崙楦挎簠鏉堝棙绁﹂崝銊�?剰閸愶拷 *
	 * 
	 * @param trafficStatus
	 *            - 閸ユ稐閲滈弬鐟版倻閻ㄥ嫭绁﹂柌锟�? * @param probaTurn -
	 *            鏉烆剙闆嗗鍌滃芳alpha,beta,gamma
	 * @return 鏉烇箒绶犲ù浣稿З閹懎鍠�
	 */
	public static CrossFlow CalcCrossFlow(int[] Waiting, float[] probaTurn) 
	{
		CrossFlow flow = new CrossFlow();
		int[] t = sampleData(Waiting[0], probaTurn);

		flow.flowL2U = Math.min((int) Constants.MAX_THROUGH[0], t[0]);
		flow.flowL2R = Math.min((int) Constants.MAX_THROUGH[1], t[1]);
		flow.flowL2D = Math.min((int) Constants.MAX_THROUGH[2], t[2]);

		t = sampleData(Waiting[1], probaTurn);
		flow.flowU2R = Math.min((int) Constants.MAX_THROUGH[0], t[0]);
		flow.flowU2D = Math.min((int) Constants.MAX_THROUGH[1], t[1]);
		flow.flowU2L = Math.min((int) Constants.MAX_THROUGH[2], t[2]);

		t = sampleData(Waiting[2], probaTurn);
		flow.flowR2D = Math.min((int) Constants.MAX_THROUGH[0], t[0]);
		flow.flowR2L = Math.min((int) Constants.MAX_THROUGH[1], t[1]);
		flow.flowR2U = Math.min((int) Constants.MAX_THROUGH[2], t[2]);

		t = sampleData(Waiting[3], probaTurn);
		flow.flowD2L = Math.min((int) Constants.MAX_THROUGH[0], t[0]);
		flow.flowD2U = Math.min((int) Constants.MAX_THROUGH[1], t[1]);
		flow.flowD2R = Math.min((int) Constants.MAX_THROUGH[2], t[2]);

		return flow;
	}

	/***
	 * 闁秴宸婚幍锟芥箒閻ㄥ嫯鐭鹃崣锝忕礉閸掑棗鍩嗙拋锛勭暬鐎瑰啩婊戦崷銊ュ彋缁夊秶濮搁幀浣风瑓闁姵鍨氶惃鍓唗ay閺侊�? *
	 * 鏉堟挸鍙嗘稉鐚寸�?#TrafficGraph, 濞村鐦粙瀣碍缂佹瑥鍤惃鍒rrentflow閸婏�? * 鏉堟挸鍤稉鐚寸窗娑擄拷閲渒ey
	 * 娑撻缚鐭鹃崣顤痙, value 娑擄�?6 閿涳�?5閻ㄥ嫮鐓╅梼锟� *
	 */
	public static Map<String, int[][]> computeStayMatrix(Map<String, int[]> currentFlow, TrafficGraph traffic) {
		
		Map<String, int[][]> ret = new HashMap<String, int[][]>();
	
		for (String cid : currentFlow.keySet()) {
			int[][] stay = new int[6][5];
			int[] flow = currentFlow.get(cid);
			TrafficCrossroad cross = traffic.crosses.get(cid);
			// flow[0] flow[1] flow[2] flow[3]
			// 閸掑棗鍩嗘稉鍝勪箯娑撳﹤褰告稉�?�磽鏉堝湱鐡戦惈锟界ウ閸斻劎娈戞潪锔芥�?
			CrossFlow Outing = CalcCrossFlow(flow, Constants.TURN_PROBA);
		
			
			// Outing 閸栧懎鎯�? 濮ｅ繋閲滈弬鐟版倻娑撳绱濆锟戒箯娑擃厼褰搁崣顖涚ウ閸戞椽鍣�
            
			// 濮樻潙閽╅崗銊╋拷鐞涳拷
			int[] exist = new int[4];
			for(int i = 0; i < exist.length; i ++)
				if(cross.neighbours[i].equals("#"))
					exist[i] = 0;
				else
					exist[i] = 1;
			stay[0][0] = Math.max(flow[0] - Outing.flowL2R * exist[2] - Outing.flowL2D * exist[3] -Outing.flowL2U * exist[1],0);
			stay[0][1] = Math.max(flow[1] - Outing.flowU2L * exist[0],0);
			stay[0][2] = Math.max(flow[2] - Outing.flowR2L * exist[0] - Outing.flowR2U * exist[1]- Outing.flowR2D * exist[3],0);
			stay[0][3] = Math.max(flow[3] - Outing.flowD2R * exist[2],0);
			stay[0][4] = stay[0][0] + stay[0][1] + stay[0][2] + stay[0][3];
			
			
			// 閸ㄥ倻娲块崗銊╋拷鐞涳拷
			stay[1][0] = Math.max(flow[0] - Outing.flowL2D * exist[3],0);
			stay[1][1] = Math.max(flow[1] - Outing.flowU2D * exist[3] - Outing.flowU2L * exist[0]-Outing.flowU2R * exist[2],0);
			stay[1][2] = Math.max(flow[2] - Outing.flowR2U * exist[1],0);
			stay[1][3] = Math.max(flow[3] - Outing.flowD2R * exist[2] -Outing.flowD2L * exist[0]-Outing.flowD2U * exist[1],0);
			stay[1][4] = stay[1][0] + stay[1][1] + stay[1][2] + stay[1][3];
			// unfinished
			// 濮樻潙閽╅惄纾嬵�?
			stay[2][0] = flow[0] - Outing.flowL2D;
			stay[2][1] = flow[1] - Outing.flowU2D - Outing.flowU2L;
			stay[2][2] = flow[2] - Outing.flowR2L - Outing.flowR2U;
			stay[2][3] = flow[3] - Outing.flowD2R;
			stay[2][4] = stay[2][0] + stay[2][1] + stay[2][2] + stay[2][3];

			// 閸ㄥ倻娲块惄纾嬵�?

			stay[3][0] = flow[0] - Outing.flowL2D;
			stay[3][1] = flow[1] - Outing.flowU2D - Outing.flowU2L;
			stay[3][2] = flow[2] - Outing.flowR2L - Outing.flowR2U;
			stay[3][3] = flow[3] - Outing.flowD2R;
			stay[3][4] = stay[3][0] + stay[3][1] + stay[3][2] + stay[3][3];

			// 濮樻潙閽╁锕侇�?
			stay[4][0] = flow[0] - Outing.flowL2D;
			stay[4][1] = flow[1] - Outing.flowU2D - Outing.flowU2L;
			stay[4][2] = flow[2] - Outing.flowR2L - Outing.flowR2U;
			stay[4][3] = flow[3] - Outing.flowD2R;
			stay[4][4] = stay[4][0] + stay[4][1] + stay[4][2] + stay[4][3];

			// 閸ㄥ倻娲垮锕侇�?
			stay[5][0] = flow[0] - Outing.flowL2D;
			stay[5][1] = flow[1] - Outing.flowU2D - Outing.flowU2L;
			stay[5][2] = flow[2] - Outing.flowR2L - Outing.flowR2U;
			stay[5][3] = flow[3] - Outing.flowD2R;
			stay[5][4] = stay[5][0] + stay[5][1] + stay[5][2] + stay[5][3];

			ret.put(cid, stay.clone());
			
		}
		return ret;
	}

	/***
	 * 濡拷鐓￠柇璇茬湷閺勵垰鎯佸鑼病閺堝顔曠純锟� *
	 */
	public static boolean isSet(TrafficGraph traffic, String c) {
		TrafficCrossroad cross = traffic.crosses.get(c);

		return cross.flag;
	}

	
	/***
	 * 鐠侊紕鐣婚弻鎰嚋鐠侯垰褰涚拋鍓х枂娴犮儱鎮楅惃鍕⒖娴ｆ瑨婧呮潏锟� *
	 */
//	public static int[] calcCurrentStay(int setting, String c, TrafficGraph traffic,
//			int[] flow) {
	/*	int[] cStay = new int[4];
		CrossFlow Outing = CalcCrossFlow(flow, Constants.TURN_PROBA);

		switch (setting) {
		case 0: {
			// 濮樻潙閽╅崗銊╋拷鐞涳拷 cStay[0] = flow[0] - Outing.flowL2R -
			// Outing.flowL2D;
			cStay[1] = flow[1] - Outing.flowU2L;
			cStay[2] = flow[2] - Outing.flowR2L - Outing.flowR2U;
			cStay[3] = flow[3] - Outing.flowD2R;

		}
			break;

		case 1: {
			// 閸ㄥ倻娲块崗銊╋拷鐞涳拷 cStay[0] = flow[0] - Outing.flowL2D;
			cStay[1] = flow[1] - Outing.flowU2D - Outing.flowU2L;
			cStay[2] = flow[2] - Outing.flowR2L - Outing.flowR2U;
			cStay[3] = flow[3] - Outing.flowD2R;

		}
			break;

		case 2: {
			// 濮樻潙閽╅惄纾嬵�?
			cStay[0] = flow[0] - Outing.flowL2D;
			cStay[1] = flow[1] - Outing.flowU2D - Outing.flowU2L;
			cStay[2] = flow[2] - Outing.flowR2L - Outing.flowR2U;
			cStay[3] = flow[3] - Outing.flowD2R;

		}
			break;

		case 3: {
			// 閸ㄥ倻娲块惄纾嬵�?

			cStay[0] = flow[0] - Outing.flowL2D;
			cStay[1] = flow[1] - Outing.flowU2D - Outing.flowU2L;
			cStay[2] = flow[2] - Outing.flowR2L - Outing.flowR2U;
			cStay[3] = flow[3] - Outing.flowD2R;

		}
			break;

		case 4: {
			// 濮樻潙閽╁锕侇�?
			cStay[0] = flow[0] - Outing.flowL2D;
			cStay[1] = flow[1] - Outing.flowU2D - Outing.flowU2L;
			cStay[2] = flow[2] - Outing.flowR2L - Outing.flowR2U;
			cStay[3] = flow[3] - Outing.flowD2R;

		}

		case 5: {
			// 閸ㄥ倻娲垮锕侇�?
			cStay[0] = flow[0] - Outing.flowL2D;
			cStay[1] = flow[1] - Outing.flowU2D - Outing.flowU2L;
			cStay[2] = flow[2] - Outing.flowR2L - Outing.flowR2U;
			cStay[3] = flow[3] - Outing.flowD2R;

		}
			break;
		default: {
			cStay = flow;
			break;
		}

		}
		return cStay;
		*/
	

	public static int[] flow2neighbour(int setting, TrafficCrossroad cross) {
		int[] f2n = new int[4];
		CrossFlow outing = CalcCrossFlow(cross.currentFlow,
				Constants.TURN_PROBA);
		int[] exist = new int[4];
		for(int i = 0; i < exist.length; i ++)
			if(cross.neighbours[i].equals("#"))
				exist[i] = 0;
			else
				exist[i] = 1;
		
		if (setting == 0) {
			f2n[0] = outing.flowR2L * exist[0] + outing.flowU2L * exist[0];
			f2n[1] = outing.flowL2U * exist[1] + outing.flowR2U * exist[1];
			f2n[2] = outing.flowL2R * exist[2] + outing.flowD2R * exist[2];
			f2n[3] = outing.flowL2D * exist[3] + outing.flowR2D * exist[3];
		}
		if (setting == 1) {
			f2n[0] = outing.flowU2L * exist[0] + outing.flowD2L * exist[0];
			f2n[1] = outing.flowR2U * exist[1] + outing.flowD2U * exist[1];
			f2n[2] = outing.flowU2R * exist[2] + outing.flowD2R * exist[2];
			f2n[3] = outing.flowL2D * exist[3] + outing.flowU2D * exist[3];
		}
		return f2n;
	}

	public static void updateInfolw(int setting, String ID, TrafficGraph traffic) {
		TrafficCrossroad cross = traffic.crosses.get(ID);
		int[] f2n = flow2neighbour(setting, cross);

		TrafficCrossroad left;
		TrafficCrossroad up;
		TrafficCrossroad right;
		TrafficCrossroad down;

		if (!cross.neighbours[0].equals("#")&&traffic.crosses.containsKey(cross.neighbours[0])) {
			left = traffic.crosses.get(cross.neighbours[0]);
			int i;
			int index;
			for (i = 0; i < 4; i++) {
				if (left.neighbours[i].equals(ID))
					break;
			}
			index = i;
			left.inFlow[index] += f2n[0];
		}

		if (!cross.neighbours[1].equals("#")&&traffic.crosses.containsKey(cross.neighbours[1])) {
			up = traffic.crosses.get(cross.neighbours[1]);
			
			int i;
			int index;
			for (i = 0; i < 4; i++) {
				
				if (up.neighbours[i].equals(ID))
					break;
			}
			index = i;
			up.inFlow[index] += f2n[1];
		}
		if (!cross.neighbours[2].equals("#")&&traffic.crosses.containsKey(cross.neighbours[2])) {
			right = traffic.crosses.get(cross.neighbours[2]);
			int i;
			int index;
			for (i = 0; i < 4; i++) {
				if (right.neighbours[i].equals(ID))
					break;
			}
			index = i;
			right.inFlow[index] += f2n[2];
		}
		if (!cross.neighbours[3].equals("#")&&traffic.crosses.containsKey(cross.neighbours[3])) {
			down = traffic.crosses.get(cross.neighbours[3]);
			int i;
			int index;
			for (i = 0; i < 4; i++) {
				if (down.neighbours[i].equals(ID))
					break;
			}
			index = i;
			down.inFlow[index] += f2n[3];
		}

	}

	public static boolean IsMaxInterval(String cid, int time, int set,
			TrafficGraph traffic) {
		int[] history = traffic.crosses.get(cid).lightSettingHistory;

		if (time <= Constants.MAX_LIGHT_INTERVAL) {
			return false;
		}

		for (int i = time - 1; i >= time - Constants.MAX_LIGHT_INTERVAL; i--) {
			if (history[i] != set) {
				return false;
			}
		}
		return true;
	}

	public static void Clear(TrafficGraph traffic) {

		for (String cid : traffic.crosses.keySet()) {
			TrafficCrossroad cross = traffic.crosses.get(cid);

		//	cross.currentStay[0] = 0;
		//	cross.currentStay[1] = 0;
		//	cross.currentStay[2] = 0;
		//	cross.currentStay[3] = 0;

			cross.flag = false;

		//	cross.inFlow[0] = 0;
		//	cross.inFlow[1] = 0;
		//	cross.inFlow[2] = 0;
		//	cross.inFlow[3] = 0;
		}
	}

	public static void setCurrentStay(TrafficGraph traffic, String id,int setting, Map<String, int[][]> stayMatrix)
	{
		for(int i=0;i<4;i++)
		traffic.crosses.get(id).currentStay[i]=stayMatrix.get(id)[setting][i];
	}
	
	
	
	//@SuppressWarnings("unchecked")
	public static void Solve(TrafficGraph traffic, Map<String, int[]> flow, int time) throws NullPointerException {
		Clear(traffic);
		traffic.setCurrentFlow(flow);
		
       
		Map<String, Integer> settings = new HashMap<String, Integer>();
		Map<String, int[][]> stayMatrix = new HashMap<String, int[][]>();
		Map<String, int[]> currentFlow = traffic.getCurrentFlow();
	
		stayMatrix = computeStayMatrix(currentFlow,traffic);
		
	
		
		try{
			
		ArrayList keys = new ArrayList(traffic.crosses.keySet());
		Collections.sort(keys,new Comparator<Object>(){
	
			public int compare(Object o1, Object o2){
				if(Double.parseDouble(traffic.crosses.get(o1).toString())<Double.parseDouble(traffic.crosses.get(o2).toString()))
					return 1;
				else if(Double.parseDouble(traffic.crosses.get(o1).toString())==Double.parseDouble(traffic.crosses.get(o2).toString()))
					return 0;
				else
					return -1;
			}
		});
		
		
		//for (String cid : traffic.crosses.keySet()) {
		for(int id = keys.size(); id > 0; id --)
		{
			String cid = traffic.crosses.get(keys.get(id - 1)).id;
			int setting = 0;

			TrafficCrossroad cross = traffic.crosses.get(cid);

			if (IsMaxInterval(cid, time, 0, traffic))
				{
				      setting = 1;
				}
			else if (IsMaxInterval(cid, time, 1, traffic))
				{
				       setting = 0;
				}
			else {
				int[] cost = new int[2];
				cost[0] = stayMatrix.get(cid)[0][4];
				cost[1] = stayMatrix.get(cid)[1][4];
				if (cost[0] < cost[1])
					   setting = 0;
				if (cost[1] < cost[0])
					   setting = 1;
				// If the cost of two settings are equal, then we consider the
				// burden of the neighbours
				if (cost[0] == cost[1]) {
					TrafficCrossroad left, up, right, down;
					int burden_L = 0, burden_U = 0, burden_R = 0, burden_D = 0;
					// Calculate the burden_L
					if (!cross.neighbours[0].equals("#")&&traffic.crosses.containsKey(cross.neighbours[0])) {
						left = traffic.crosses.get(cross.neighbours[0]);
						int index;
						
						for (index = 0; index < left.neighbours.length; index++) {
							if(left.neighbours[index] != null)
							    if (left.neighbours[index].equals(cid))
							    {
							    	break;
							    }
						}
				
						if (isSet(traffic, left.id))
						{
							burden_L = left.currentStay[index]
							    		+ left.flowAdd[time][index]
							    		+ left.inFlow[index];
					
						}
						else {
							int solution;
							double r = Math.random();
							if (r <= 0.5)
								solution = 0;
							else
								solution = 1;
							burden_L = stayMatrix.get(left.id)[solution][index]
									+ left.flowAdd[time][index]
									+ left.inFlow[index];
						}
					}
					// Calculate the burden_R
					if (!cross.neighbours[1].equals("#")&&traffic.crosses.containsKey(cross.neighbours[1])) {
						up = traffic.crosses.get(cross.neighbours[1]);
						int index;
						for (index = 0; index < up.neighbours.length; index++) {
							if(up.neighbours[index] != null)
								if (up.neighbours[index].equals(cid))
									break;
						}

						if (isSet(traffic, up.id))
							burden_U = up.currentStay[index]
									+ up.flowAdd[time][index]
									+ up.inFlow[index];
						else {
							int solution;
							double r = Math.random();
							if (r <= 0.5)
								solution = 0;
							else
								solution = 1;
							burden_U = stayMatrix.get(up.id)[solution][index]
									+ up.flowAdd[time][index]
									+ up.inFlow[index];
						}
					}
					// Calculate the burden_R
					if (!cross.neighbours[2].equals("#")&&traffic.crosses.containsKey(cross.neighbours[2])) {
						right = traffic.crosses.get(cross.neighbours[2]);
						int index;
						for (index = 0; index < right.neighbours.length; index++) {
							if(right.neighbours[index] != null)
							if (right.neighbours[index].equals(cid))
								break;
						}

						if (isSet(traffic, right.id))
							burden_R = right.currentStay[index]
									+ right.flowAdd[time][index]
									+ right.inFlow[index];
						else {
							int solution;
							double r = Math.random();
							if (r <= 0.5)
								solution = 0;
							else
								solution = 1;
							burden_R = stayMatrix.get(right.id)[solution][index]
									+ right.flowAdd[time][index]
									+ right.inFlow[index];
						}
					}
					// Calculate the burden_D
					if (!cross.neighbours[3].equals("#")&&traffic.crosses.containsKey(cross.neighbours[3])) {
						down = traffic.crosses.get(cross.neighbours[3]);
						int index;
						for (index = 0; index < down.neighbours.length; index++) {
							if(down.neighbours[index] != null)
							if (down.neighbours[index].equals(cid))
								break;
						}

						if (isSet(traffic, down.id))
							burden_D = down.currentStay[index]
									+ down.flowAdd[time][index]
									+ down.inFlow[index];
						else {
							int solution;
							double r = Math.random();
							if (r <= 0.5)
								solution = 0;
							else
								solution = 1;
							burden_D = stayMatrix.get(down.id)[solution][index]
									+ down.flowAdd[time][index]
									+ down.inFlow[index];
						}
					}

					if (burden_L + burden_R <= burden_U + burden_D)
						setting = 0;
					else
						setting = 1;
				}
			}// end of else
			
			updateInfolw(setting, cid, traffic);
			settings.put(cid, setting);
			cross.flag = true;
	
			setCurrentStay(traffic,cid,setting,stayMatrix);

	

		}// end of for
	
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		traffic.setLight(settings, time);
		traffic.saveCurrentFlow();

	}
}
