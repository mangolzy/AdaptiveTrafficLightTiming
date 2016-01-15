package cdo.its;





import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import cdo.its.Constants;
import cdo.its.LookAside;
import cdo.its.TrafficCrossroad;
import cdo.its.TrafficGraph;

public class Grade0116 {
	
	public String taskId = "1";
	
	public String tupoString = "tl44,tl42,tl43,#,tl19;tl44,tl43,tl19,tl42,#;tl44,tl19,#,tl43,tl42;tl43,tl44,tl41,tl18,#;tl43,tl41,#,tl44,tl18;tl43,tl18,tl44,#,tl41;tl42,tl26,tl41,#,tl44;tl42,tl41,tl44,tl26,#;tl42,tl44,#,tl41,tl26;tl41,tl42,tl25,tl43,tl40;tl41,tl25,tl40,tl42,tl43;tl41,tl40,tl43,tl25,tl42;tl41,tl43,tl42,tl40,tl25;tl40,tl41,tl24,tl17,tl39;tl40,tl24,tl39,tl41,tl17;tl40,tl39,tl17,tl24,tl41;tl40,tl17,tl41,tl39,tl24;tl39,tl40,tl23,tl16,#;tl39,tl23,#,tl40,tl16;tl39,tl16,tl40,#,tl23;tl38,tl12,tl37,#,tl5;tl38,tl37,tl5,tl12,#;tl38,tl5,#,tl37,tl12;tl37,tl38,tl11,tl4,tl36;tl37,tl11,tl36,tl38,tl4;tl37,tl36,tl4,tl11,tl38;tl37,tl4,tl38,tl36,tl11;tl36,tl37,tl10,tl3,#;tl36,tl10,#,tl37,tl3;tl36,tl3,tl37,#,tl10;tl35,tl52,tl58,tl28,tl34;tl35,tl58,tl34,tl52,tl28;tl35,tl34,tl28,tl58,tl52;tl35,tl28,tl52,tl34,tl58;tl34,tl35,tl57,tl27,tl33;tl34,tl57,tl33,tl35,tl27;tl34,tl33,tl27,tl57,tl35;tl34,tl27,tl35,tl33,tl57;tl33,tl34,tl56,tl26,tl32;tl33,tl56,tl32,tl34,tl26;tl33,tl32,tl26,tl56,tl34;tl33,tl26,tl34,tl32,tl56;tl32,tl33,tl55,tl25,tl31;tl32,tl55,tl31,tl33,tl25;tl32,tl31,tl25,tl55,tl33;tl32,tl25,tl33,tl31,tl55;tl31,tl32,#,tl24,tl30;tl31,tl30,tl24,#,tl32;tl31,tl24,tl32,tl30,#;tl30,tl31,tl54,tl23,tl29;tl30,tl54,tl29,tl31,tl23;tl30,tl29,tl23,tl54,tl31;tl30,tl23,tl31,tl29,tl54;tl29,tl30,tl53,tl22,tl51;tl29,tl53,tl51,tl30,tl22;tl29,tl51,tl22,tl53,tl30;tl29,tl22,tl30,tl51,tl53;tl28,tl35,tl27,#,tl21;tl28,tl27,tl21,tl35,#;tl28,tl21,#,tl27,tl35;tl27,tl28,tl34,tl20,tl26;tl27,tl34,tl26,tl28,tl20;tl27,tl26,tl20,tl34,tl28;tl27,tl20,tl28,tl26,tl34;tl26,tl27,tl33,tl42,tl25;tl26,tl33,tl25,tl27,tl42;tl26,tl25,tl42,tl33,tl27;tl26,tl42,tl27,tl25,tl33;tl25,tl26,tl32,tl41,tl24;tl25,tl32,tl24,tl26,tl41;tl25,tl24,tl41,tl32,tl26;tl25,tl41,tl26,tl24,tl32;tl24,tl25,tl31,tl40,tl23;tl24,tl31,tl23,tl25,tl40;tl24,tl23,tl40,tl31,tl25;tl24,tl40,tl25,tl23,tl31;tl23,tl24,tl30,tl39,tl22;tl23,tl30,tl22,tl24,tl39;tl23,tl22,tl39,tl30,tl24;tl23,tl39,tl24,tl22,tl30;tl22,tl23,tl29,tl14,#;tl22,tl29,#,tl23,tl14;tl22,tl14,tl23,#,tl29;tl21,tl28,tl20,#,tl6;tl21,tl20,tl6,tl28,#;tl21,tl6,#,tl20,tl28;tl20,tl21,tl27,#,tl19;tl20,tl27,tl19,tl21,#;tl20,tl19,#,tl27,tl21;tl19,tl20,tl44,tl12,tl18;tl19,tl44,tl18,tl20,tl12;tl19,tl18,tl12,tl44,tl20;tl19,tl12,tl20,tl18,tl44;tl18,tl19,tl43,tl11,tl17;tl18,tl43,tl17,tl19,tl11;tl18,tl17,tl11,tl43,tl19;tl18,tl11,tl19,tl17,tl43;tl17,tl18,tl40,tl10,tl16;tl17,tl40,tl16,tl18,tl10;tl17,tl16,tl10,tl40,tl18;tl17,tl10,tl18,tl16,tl40;tl16,tl17,tl39,tl9,tl15;tl16,tl39,tl15,tl17,tl9;tl16,tl15,tl9,tl39,tl17;tl16,tl9,tl17,tl15,tl39;tl15,tl16,#,tl8,tl14;tl15,tl14,tl8,#,tl16;tl15,tl8,tl16,tl14,#;tl14,tl15,tl22,tl7,#;tl14,tl22,#,tl15,tl7;tl14,tl7,tl15,#,tl22;tl12,tl19,tl11,#,tl38;tl12,tl11,tl38,tl19,#;tl12,tl38,#,tl11,tl19;tl11,tl12,tl18,tl37,tl10;tl11,tl18,tl10,tl12,tl37;tl11,tl10,tl37,tl18,tl12;tl11,tl37,tl12,tl10,tl18;tl10,tl11,tl17,tl36,tl9;tl10,tl17,tl9,tl11,tl36;tl10,tl9,tl36,tl17,tl11;tl10,tl36,tl11,tl9,tl17;tl9,tl10,tl16,tl2,tl8;tl9,tl16,tl8,tl10,tl2;tl9,tl8,tl2,tl16,tl10;tl9,tl2,tl10,tl8,tl16;tl8,tl9,tl15,#,tl7;tl8,tl15,tl7,tl9,#;tl8,tl7,#,tl15,tl9;tl7,tl8,tl14,tl1,tl13;tl7,tl14,tl13,tl8,tl1;tl7,tl13,tl1,tl14,tl8;tl7,tl1,tl8,tl13,tl14;tl1,tl2,tl7,#,tl45;tl1,tl7,tl45,tl2,#;tl1,tl45,#,tl7,tl2;tl2,tl47,tl3,tl1,tl9;tl2,tl3,tl9,tl47,tl1;tl2,tl9,tl1,tl3,tl47;tl2,tl1,tl47,tl9,tl3;tl3,tl4,tl36,#,tl2;tl3,tl36,tl2,tl4,#;tl3,tl2,#,tl36,tl4;tl4,tl48,tl5,tl3,tl37;tl4,tl5,tl37,tl48,tl3;tl4,tl37,tl3,tl5,tl48;tl4,tl3,tl48,tl37,tl5;tl5,tl49,tl6,tl4,tl38;tl5,tl6,tl38,tl49,tl4;tl5,tl38,tl4,tl6,tl49;tl5,tl4,tl49,tl38,tl6;tl6,tl50,tl46,tl5,tl21;tl6,tl46,tl21,tl50,tl5;tl6,tl21,tl5,tl46,tl50;tl6,tl5,tl50,tl21,tl46";
	public Map<String,String[]> trafficLightMap= new HashMap<String,String[]>();
	
	public Map<String, Integer[]> trafficFlowMap = new HashMap<String,Integer[]>();

	//娴犲兜raffic_Light_Table.txt鐠囪褰囬幍锟芥箒鐠侯垰褰涚痪銏㈣雹閻忣垯缍呯純顔煎彠缁淇婇幁锟�
	public Map<String, String[]> trafficLightTable = new ConcurrentHashMap<String, String[]>();
	
	//閹碉拷婀佺捄顖氬經閺屾劒閲滅亸蹇旀閻ㄥ嫰娼ら幀浣圭ウ闁插繗銆冮崪灞藉З閹焦绁﹂柌蹇氥��(閺佹壆绮嶉梹鍨娑擄拷20)
	public Map<String, Integer[]> FlowTable = new ConcurrentHashMap<String,Integer[]>();
	public Map<String, Integer[]> dynamicFlowTable = new ConcurrentHashMap<String,Integer[]>();
	
	//娴肩姴鍤紒娆擄拷閹靛寮弫锟介弻鎰嚋鐏忓繑妞傞弻鎰嚋閺冭埖顔孴(i)閹碉拷婀佺捄顖氬經閻ㄥ嫯婧呭ù渚�鍣�
	public Map<String, Integer> currentFlows = new ConcurrentHashMap<String,Integer>();
	
	//娴肩姴鍤紒娆擄拷閹靛寮弫锟介弻鎰嚋閺冭埖顔孴(i)閹碉拷婀佺捄顖氬經鏉烇箒绶犻惃锟芥潪顒�鎮滃鍌滃芳:[瀹革箒娴�,閸欏疇娴�,閻╃顢慮
	public Map<String, Double[]> turnRate = new ConcurrentHashMap<String,Double[]>();
	
	//娴肩姴鍤紒娆擄拷閹靛寮弫锟介弻鎰嚋閺冭埖顔孴(i)閹碉拷婀佺捄顖氬經鏉烇箒绶犻惃锟介柅姘崇箖閻滐拷[瀹革箒娴�,閸欏疇娴�,閻╃顢慮
	public Map<String, Integer[]> throughRate = new ConcurrentHashMap<String,Integer[]>();
	
	//闁澧滄导鐘插弳閸欏倹鏆熼敍姘厙娑擃亝妞傚▓绀�(i)閹碉拷婀佺捄顖氬經缁撅拷缂佽法浼呴惃鍕Ц閹拷[瀹革箒娴�,閸欏疇娴�,閻╃顢慮 (缁俱垻浼呮稉锟介敍宀�璞㈢粵澶夎礋1,缂傚搫銇�-1)
	public Map<String, Integer[]> currentStatus = new ConcurrentHashMap<String,Integer[]>();
	
	//閺屾劒閲滅亸蹇旀閸愬懐瀛╃紒璺ㄤ紖閻ㄥ嫬宸婚崣韫繆閹拷
	public Map<String, ArrayList<Integer[]>> statusHistory = new ConcurrentHashMap<String,ArrayList<Integer[]>>();
	//14娑擃亜鐨弮鍓佹畱penalty
	//閹碉拷婀乼askId鐎电懓绨查惃鍒緀nalty
	public Map<String, Double[]> penaltyMap =  new ConcurrentHashMap<String, Double[]>();
	public boolean inited = false;
	//閸掓繂顫愰崠鏍ㄧ槨娑擃亪锟介幍瀣嚠鎼存梻娈憄enalty閺佹壆绮�
	public void initPenalty(String taskId) {
		Double[] penalty = {0.0,0.0,0.0,0.0,
                             0.0,0.0,0.0,0.0,
                             0.0,0.0,0.0,0.0,0.0,0.0};
		penaltyMap.put(taskId, penalty);
	}
	public static void main(String[] args) throws NumberFormatException, IOException{
		
		
		
		
		Grade0116 g = new Grade0116();
		g.run();
		
		
		
	}
	
	/*public void run() throws NumberFormatException, IOException{
		initTrafficLightMap();
		setLightInfo(taskId);
		inittrafficFlowMap(1);
		initPenalty(taskId);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println(br.toString());
		for(int count = 0; count < 1680; count++){
			if(count % 120 == 0)
				hourInit(taskId,count/120);
			
			updateDynamicFlowTable(taskId, count%120);
			getCurrentFlow(taskId,count%120);
			System.out.println(printCurrentFlow());
			String status_str = br.readLine();
			readcurrentStatus(status_str);
			setCurrentStatus(count % 120);
			updatePenalty(taskId,count%120,count/120);
		}
		getGrade(taskId);
	}*/
	
	public void run() throws NumberFormatException, IOException,NullPointerException{
		initTrafficLightMap();
		setLightInfo(taskId);
		inittrafficFlowMap(1);      //鎶奻low鐨勬枃浠惰杩涘幓锛屽瓨鍦╰rafficFlowmap 涓幓
		initPenalty(taskId);
		
		TrafficGraph traffic = new TrafficGraph();
			
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				Grade0116.class.getResourceAsStream(Constants.FILENAME_TRAFFIC)));
		BufferedReader readerFlow = new BufferedReader( new InputStreamReader(
				Grade0116.class.getResourceAsStream(Constants.FILENAME_FLOW_ADD)));  
	


		traffic.load(reader);
		traffic.loadFlowAdd(readerFlow);
		reader.close();
		readerFlow.close();	
		
		int time = 0;
		String flows_str; //= printCurrentFlow();
		String status_str = "";
		for(int count = 0; count < 1680; count++)
		{
			
			if(count % 120 == 0)
				hourInit(taskId,count/120);
			
			if(count/120==13&&count%120==119)
				{
			   break;
				}
			
			updateDynamicFlowTable(taskId, count%120);
			getCurrentFlow(taskId,count%120);
		
			//  while(!"end".equalsIgnoreCase(flows_str))
			//  {
			status_str = Process(printCurrentFlow(),time,traffic);
				
				/*******************************/
		
			readcurrentStatus(status_str);
			setCurrentStatus(count % 120);
			updatePenalty(taskId,count%120,count/120);
				
				/*******************************/
			//  }
			 
			 // flows_str=printCurrentFlow();
			 int somme=0;
			  for(int i=0;i<14;i++)
			  {
				  somme+=penaltyMap.get(taskId)[i];
			  }
			  System.out.println(somme  +  "  "  + time);
			  time++;
			
			}	
			getGrade(taskId);
			
		
	}
	public static Map<String,int[]> String2Flow(TrafficGraph traffic, String line)
	{
		Map<String,int[]> ret = new HashMap<String,int[]>();
		
		for(String id : traffic.crosses.keySet())
		{
			ret.put(id, new int[4]);
		}
		
		String[] parts = line.split(";");
		
		for(String part : parts)
		{
			String[] pp = part.split(",");
			String id = pp[0];
			String frmId = pp[1];
			int flow = Integer.parseInt(pp[2]);
			
			TrafficCrossroad cr = traffic.crosses.get(id);
			
			for(int i=0;i<cr.neighbours.length;i++)
			{
				if (cr.neighbours[i].compareTo(frmId)==0)
				{
					ret.get(id)[i] = flow;
				}
			}
		}
		
		return ret;
	}
	
	/***
	 * 闂佽绻愮换鎰涘鍫熷剹濡わ絽鍟惌妤呮煥濠靛棙澶勯柣锝堜含缁辨帗绌遍崡鐐典紕闁荤姷鍋戦崹浠嬪箖濮楋拷鏁嶆繝濠傚敪閿曞倹鐓欐い鎴炲缁佺増绻涢弶鎴烆棦妤犵偞鍨块敐鐐侯敇閿涘嫷妲归梺璇插缁嬫帡銆冮崼銉晞濞达絽婀遍埢鏃堟煥閻曞倹瀚�	 * 
	 * @param traffic - 濠电偛鐡ㄩ崵搴ㄥ磹濠靛鏅搁柣锝呯灱绾惧ジ鏌熼幆褜鍤熼柨鐔虹摂閸ㄨ泛鐣烽崼鏇熸櫢闁跨噦鎷�	 * @param time - 闁荤喐绮庢晶妤呭箰閸涘﹥娅犻柣妯款嚙缁秹鏌涢锝嗙闁挎冻鎷�
	 * @return 闂佽瀛╃粙鎺椼�冮崼銉晞濞达絽婀遍埢鏃堟煥閻曞倹瀚�	 */
	public static String OutputLightSetting(TrafficGraph traffic, int time)
	{
		StringBuffer sb = new StringBuffer();
		int cnt = 0;
		for(Map.Entry<String, TrafficCrossroad> entry : traffic.crosses.entrySet())
		{
			String cid = entry.getKey();
			TrafficCrossroad cross = entry.getValue();
			
			int setting = cross.lightSettingHistory[time];
			
			int[] status = new int[12];
			if ( setting == 0)
			{//婵犳鍠楄摫婵炲吋鐟╅弻锕傚煛閸涱厽顥濋梺鑽ゅ枔婢ф宕愰敓锟�
				status[0] = 1;
				status[1] = 1;
				status[2] = 1;
				status[3] = 0;
				status[4] = 1;
				status[5] = 0;
				status[6] = 1;
				status[7] = 1;
				status[8] = 1;
				status[9] = 0;
				status[10] = 1;
				status[11] = 0;
			}
			else
			{//闂備焦鎮堕崝宀勫磹鐠囪娲锤濡わ拷顥濋梺鑽ゅ枔婢ф宕愰敓锟�
				status[0] = 0;
				status[1] = 1;
				status[2] = 0;
				status[3] = 1;
				status[4] = 1;
				status[5] = 1;
				status[6] = 0;
				status[7] = 1;
				status[8] = 0;
				status[9] = 1;
				status[10] = 1;
				status[11] = 1;				
			}
			
			for(int i=0;i<4;i++)
			{
				String dstId = cross.neighbours[i];
				if(dstId.compareTo(Constants.LIGHT_NONE)!=0)
				{
					if ( cnt> 0)
					{
						sb.append(";");
					}
					cnt++;
					sb.append(cid + "," + dstId);
					for(int j=0;j<3;j++)
					{
						sb.append("," + status[i*3+j]);
					}
				}
			}
		}
		
		return sb.toString();
	}
	
	public static String Process(String line, int time, TrafficGraph traffic) throws NullPointerException
	//public static String Process(Map<String,int[]> flow, int time, TrafficGraph traffic) throws NullPointerException
	{
		Map<String,int[]> flow = String2Flow(traffic,line);
		try {
			LookAside.Solve(traffic, flow, time);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return OutputLightSetting(traffic,time);	
	}
	
	public void readcurrentStatus(String status_str) throws IOException{
		String[] statuses = status_str.trim().split(";");
		for(String status: statuses){
			if(status.length() == 0)
				continue;
			String[] items = status.trim().split(",");
			currentStatus.put(items[0]+"-"+items[1]+"-"+taskId, new Integer[]{Integer.parseInt(items[2]),Integer.parseInt(items[3]),Integer.parseInt(items[4])});
		}
		
	}
	
	public String printCurrentFlow(){
		String res = "";
		for(String key : currentFlows.keySet()){
			String[] items = key.split("-");
			res += items[0]+","+items[1]+","+currentFlows.get(key)+";";
		}
		return res;
	}
		
	//娴犲窐rafficLightTable鐠囪褰囩痪銏㈣雹閻忣垯缍呯純顔讳繆閹拷
	public void setLightInfo(String taskId){
		for (String aKey : trafficLightMap.keySet()) {
			String[] keyStrings = aKey.split("-");
			String taks_key = keyStrings[0] + "-" + keyStrings[1] + "-" + taskId;
			trafficLightTable.put(taks_key,trafficLightMap.get(aKey));
		}

	}
	
	public  void initTrafficLightMap() {
		String[] pathStrings = tupoString.split(";");
		for (String aPathString : pathStrings) {
			String[] aStrings = aPathString.split(",");
			String aKey = aStrings[0] + "-" + aStrings[1];
			String[] aValue = {aStrings[2],aStrings[3],aStrings[4]};
			trafficLightMap.put(aKey, aValue);
		}
	}
	public void inittrafficFlowMap(int n) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(new File("src/cdo/its/flow0901.txt")));
		String s;
		while( (s = br.readLine()) != null){
			String[] aStrings = s.trim().split(",");
		//	System.out.println("size of aString is "+aStrings.length);
			String aKey = aStrings[0] + "-" + aStrings[1];
			Integer[] aValue = new Integer[1680];
			//System.out.println(aStrings.length);
			for(int i = 0; i < 1679; i++)
				{
				     aValue[i] = Integer.parseInt(aStrings[i+2]);
				}		
			trafficFlowMap.put(aKey, aValue);
		}
	}
	
	// 缁楃惒娑擃亜鐨弮鍓佸Ц閹礁鍨垫慨瀣
	public void hourInit(String taskId,int i) throws NumberFormatException, IOException {	
		for (String aKey : trafficFlowMap.keySet())
		{
			String[] keyStrings = aKey.split("-");
			
			String taks_key = keyStrings[0] + "-" + keyStrings[1] + "-" + taskId;
			
			  Integer[] values = new Integer[120];
			/*  if(i==13)
				  System.out.println("values[119]="+values[119]);   */
			  for (int i1 = 0; i1 < values.length; i1++) 	
			{    
				 values[i1] = trafficFlowMap.get(aKey)[i*120 + i1];			
			}
			  
			FlowTable.put(taks_key,values);
			dynamicFlowTable.put(taks_key,values.clone());
		}
		
		//閸掓繂顫愰崠鏉rnRate:[0.1,0.1,0.8]閵嗕辜hroughRate:[2,2,16]閵嗕够tatusHistory
		for(String key : trafficLightTable.keySet()){

			if(!taskId.equals(key.split("-")[2])){
				continue;
			}
			//閸椾礁鐡х捄顖氬經鏉烆剙鎮滃鍌滃芳
			Double[] initTurnRate = {0.1,0.1,0.8};
			
			//娑撲礁鐡х捄顖氬經,閺冪姴涔忔潪顒佹煙閸氾拷
			if (trafficLightTable.get(key)[0].equals("#")) {
				initTurnRate[1] += initTurnRate[0];
				initTurnRate[0] -= initTurnRate[0];				
			}else if (trafficLightTable.get(key)[1].equals("#")) {
				//娑撲礁鐡х捄顖氬經,閺冪姴褰告潪顒佹煙閸氾拷
				initTurnRate[0] += initTurnRate[1];
				initTurnRate[1] -= initTurnRate[1];
			}else if (trafficLightTable.get(key)[2].equals("#")) {
				//娑撲礁鐡х捄顖氬經,閺冪姷娲跨悰灞炬煙閸氾拷
				initTurnRate[0] += initTurnRate[2]*0.5;
				initTurnRate[1] += initTurnRate[2]*0.5;
				initTurnRate[2] -= initTurnRate[2];
			}
			
			Integer[] initThroughRate = {2,2,16};
			
			ArrayList<Integer[]> initStatusHistory = new ArrayList<Integer[]>();
			for (int j = 0; j < 120; j++) {
				
				Integer[] aStatus = {0,0,0};
			if ("#".equals(trafficLightTable.get(key)[0])) {
				aStatus[0] = -1;
			}else if ("#".equals(trafficLightTable.get(key)[1])) {
				aStatus[1] = -1;
			}else if ("#".equals(trafficLightTable.get(key)[2])) {
				aStatus[2] = -1;
			}
			
				initStatusHistory.add(aStatus);
			}
			
			turnRate.put(key, initTurnRate);
			throughRate.put(key, initThroughRate);
			statusHistory.put(key, initStatusHistory);
		}
	}
	
	//閺嶈宓侀張锟界箮娑擄拷顔岄弮鍫曟？缁俱垻璞㈤崢鍡楀蕉閻樿埖锟界悰銊吀缁犳娴嗛崥鎴烆洤閻滃槸ALPHA,BETA,GAMMA],閸掓繆绂屾稉鍝勬祼鐎规艾锟芥稉宥嗘纯閺傦拷
	public void updateTurnRate() {
		
		/**for (String key : statusHistory.keySet()) {
			Double[] rate = {0.1,0.1,0.8};
			turnRate.put(key, rate);
		}*/
	}
	
	//閺嶈宓佺捄顖氬經鏉烇附绁﹂柌蹇氼吀缁犳婧呮潏鍡涳拷鏉╁洨宸糩TH1,TH2,TH3],閸掓繆绂岄柅姘崇箖閻滃洤娴愮�规艾锟芥稉杞扮瑝閺囧瓨鏌�
	public void updateThroughRate() {
		/**		
		for(String key : currentFlows.keySet()){
			Integer[] rate = {5,5,20};
			throughRate.put(key, rate);
		}*/
	}

	//闁澧滄导鐘插弳閸欏倹鏆熼敍姘鳖儑i閺冭泛鍩urrentStatus,閺囧瓨鏌婇崚鐨妕atusHistory
	public void setCurrentStatus(int i) {
		
		for(String key :currentStatus.keySet()){
			if (statusHistory.containsKey(key)) {
				for (int j = 0; j < 3; j++) {
					if ("#".equals(trafficLightTable.get(key)[j])) {
						//婵″倹鐏夌捄顖氬經娑撳秹锟介敍灞藉繁閸掕泛鐨㈤悘顖濐啎缂冾喕璐�-1
						statusHistory.get(key).get(i)[j] = -1;
					} else {
						//婵″倹鐏夌捄顖氬經閺勵垶锟介惃鍕剁礉娑撳秴鍘戠拋鎼侊拷閹靛鐨㈤悘顖濐啎缂冾喕璐�-1
						statusHistory.get(key).get(i)[j] = Math.max(0, currentStatus.get(key)[j]);
					}
				}
				
			}
		}
	}
	
	//閺嶈宓乻tatusHistory(i)閵嗕龚ynamicFlowTable(i)鐠侊紕鐣籘(i)閺冭泛鍩㈤弻鎰熅閸欙絾绮搁悾娆掓簠鏉堝棙鏆�
	public int computeStay(String key, int i) {
		
		//瀹革箒娴嗛崣瀹犳祮閻╃顢戠�圭偤妾懗钘夘檮闁俺绻冮惃鍕簠鏉堝棙鏆�
		int leftThrough,rightThrough,straightThrough;
		leftThrough=rightThrough=straightThrough = 0;
		//缂佽法浼呴弮鍫曪拷鏉╁洨宸奸幍宥嗘箒閺侊拷
		if (statusHistory.get(key).get(i)[0]==1) {
			leftThrough = throughRate.get(key)[0];
		}
		if (statusHistory.get(key).get(i)[1]==1) {
			rightThrough = throughRate.get(key)[1];
		}
		if (statusHistory.get(key).get(i)[2]==1) {
			straightThrough = throughRate.get(key)[2];
		}
		
		//dynamicFlow(i)
		int iFlow = dynamicFlowTable.get(key)[i];
		//瀹革箒娴嗛崣瀹犳祮閻╃顢戦惃鍕哺閻ｆ瑨婧呮潏锟�
		int leftStay,rightStay,straightStay;
		leftStay = rightStay = straightStay = 0;
		
		double leftRate = turnRate.get(key)[0];
		double rihgtRate = turnRate.get(key)[1];
		double straightRate = turnRate.get(key)[2];

		//娑撳﹤褰囬弫鏉戝讲閼虫垝绱扮�佃壈鍤уù渚�鍣烘晶鐐层亣娑擄拷鍋�
		leftStay = Math.max(0, (int)Math.ceil(iFlow*leftRate) - leftThrough);
		rightStay = Math.max(0, (int)Math.ceil(iFlow*rihgtRate) - rightThrough);
		straightStay = Math.max(0,(int)Math.ceil(iFlow*straightRate)-straightThrough);

		//LOGGER.error("key="+key+",i="+i+",leftStay="+leftStay+",rightStay="+rightStay+",straightStay="+straightStay);
		//鏉╂柨娲朤(i)閺冭泛鍩㈠鐐垫殌鏉烇箒绶犻弫锟�
		//System.out.println("allStay===" +(leftStay + rightStay + straightStay));
		return (leftStay + rightStay + straightStay);
		
	}
	
	/*閺嶈宓佺痪銏㈣雹閻忣垯缍呯純顔讳繆閹垵銆冮妴涔�(i-1)閺冭埖顔岄惃鍕剁窗鏉烇附绁﹂柌蹇嬶拷閹碉拷婀佺痪銏㈣雹閻忣垳濮搁幀浣碉拷鏉烇箒绶犳潪顒�鎮滃鍌滃芳閵嗕浇婧呮潏鍡涳拷鏉╁洨宸� 
     *鐠侊紕鐣籘(i)閺冭泛鍩㈡潪锔界ウ闁诧拷
     *dynamicFlowTable(i)=LAMADA*FlowTable(i) + G(trafficLightTable,statusHistory(i-1),dynamicFlowTable(i-1),turnRate(i-1),throughRate(i-1))
     */
	public void updateDynamicFlowTable(String taskId, int i) {
		
		for(String key :dynamicFlowTable.keySet()){
    		
			String tId = key.split("-")[2];
    		
			if (tId.equals(taskId)) {
				if (i==0) {
		    		dynamicFlowTable.get(key)[i] = FlowTable.get(key)[i];					
				} else {			
					//鐟欏倸鐧傞崐鑲╅兇閺佺檽AMADA
					double LAMADA = 0.5;
					//閺囧瓨鏌奻low,閸旂姳绗傜化缁樻殶LAMADA*Flow(i)
				//	System.out.println("i="+i);
			
			/*		 int x=FlowTable.get(key)[i];
					 int y=dynamicFlowTable.get(key)[i];   */
					
					// System.out.println("i= "+i);
					dynamicFlowTable.get(key)[i] = (int)(Math.floor(LAMADA*FlowTable.get(key)[i]));
					
					//缂佹挸鎮巗tatusHistory(i-1)閸旂姳绗� dynamicFlow(i-1)閻拷瀹革箒娴嗗鐐垫殌+閸欏疇娴嗗鐐垫殌+閻╃顢戝鐐垫殌						
					int allStay = 0;
					//娴兼ê瀵叉禒锝囩垳閻€劌鍤遍弫鏉跨杽閻滐拷
					allStay = computeStay(key, i-1);											
					//閺囧瓨鏌婇敍灞藉娑撳﹥绮搁悾娆掓簠鏉堬拷
					dynamicFlowTable.get(key)[i] += allStay;
										
					//閸旂姳绗侳romID娴犲骸鍙炬禒鏍熅閸欙絾绁﹂崗銉ф畱鏉烇箒绶�
					int flowIn1,flowIn2,flowIn3;
					flowIn1 = flowIn2 = flowIn3 = 0;
					
					//閸欏秴鎮滅捄顖氱窞ID
					String[] keyStrings = key.split("-");
					String antiKey = keyStrings[1] + "-" + keyStrings[0] + "-" + taskId;
					//閸欏秴鎮滅捄顖氱窞鐎涙ê婀禍宸榬afficLightTable娑擃厽澧犻張澶嬪壈娑旓拷
					if (trafficLightTable.containsKey(antiKey)) {
						//濞翠礁鍙嗘潪锕佺窢閻ㄥ嫭娼靛┃鎬欴
						String antiLeftID = trafficLightTable.get(antiKey)[0];
						String antiRightID = trafficLightTable.get(antiKey)[1];
						String antiStraightID = trafficLightTable.get(antiKey)[2];
							
						String antiLeftKey = keyStrings[1]  + "-" + antiLeftID + "-" + keyStrings[2];
						String antiRightKey = keyStrings[1] + "-" + antiRightID + "-" + keyStrings[2];
						String antiStraightKey = keyStrings[1] + "-" + antiStraightID + "-" + keyStrings[2];
							

							
						//娴狅拷antiLeftID 閸欏疇娴嗗ù浣稿弳
						if (!(trafficLightTable.get(antiLeftKey)==null)&&(statusHistory.get(antiLeftKey).get(i-1)[1]==1)) {
							flowIn1 = Math.min(throughRate.get(antiLeftKey)[1], (int)Math.ceil(dynamicFlowTable.get(antiLeftKey)[i-1]*turnRate.get(antiLeftKey)[1]));
						}
						//娴狅拷antiRightID 瀹革箒娴嗗ù浣稿弳
						if (!(trafficLightTable.get(antiRightKey)==null)&&(statusHistory.get(antiRightKey).get(i-1)[0]==1)) {
							flowIn2 = Math.min(throughRate.get(antiRightKey)[0], (int)Math.ceil(dynamicFlowTable.get(antiRightKey)[i-1]*turnRate.get(antiRightKey)[0]));
						}
						//娴狅拷antiStraightKey 閻╃顢戝ù浣稿弳
						if(!(trafficLightTable.get(antiStraightKey)==null)&&(statusHistory.get(antiStraightKey).get(i-1)[2]==1)){
							flowIn3 = Math.min(throughRate.get(antiStraightKey)[2], (int)Math.ceil(dynamicFlowTable.get(antiStraightKey)[i-1]*turnRate.get(antiStraightKey)[2]));
						}
					}					

					//閺囧瓨鏌婇敍灞藉娑撳﹥绁﹂崗銉ㄦ簠鏉堬拷
					dynamicFlowTable.get(key)[i] += flowIn1 + flowIn2 + flowIn3;
					//System.out.println("  " + key +"-" + i + " flow:" +dynamicFlowTable.get(key)[i] );
						
				}
			}
		}
			
	}

	//閻拷dynamicFlowTable 鐠佸墽鐤� currentFlow 娴肩姴鍤紒娆擄拷閹碉拷
	public Map<String, Integer> getCurrentFlow(String taskId, int i){

			for(String key : dynamicFlowTable.keySet()){
				if (taskId.equals(key.split("-")[2])) {
					currentFlows.put(key, dynamicFlowTable.get(key)[i]);
				}
			}
			return currentFlows;
	}	

	//閺嶈宓乧urrentStatus(i)閺囧瓨鏌婄粭鐞告稉顏勭毈閺冪enalty
	public void updatePenalty(String taskId,int i,int k){
		
		double old_penalty0 = penaltyMap.get(taskId)[k];
		
		//濮ｅ繋閲滅捄顖氬經T(i)閺冭泛鍩enalty: 瀹革箒娴嗗鐐垫殌+閸欏疇娴嗗鐐垫殌+閻╃顢戝鐐垫殌;鏉╂繂寮芥禍銈夛拷鐟欏嫬鍨幍锝呭瀻;鏉╂繂寮介崗顒�閽╅幀褍甯崚娆愬⒏閸掞拷
		for(String key : dynamicFlowTable.keySet()){
			
			double old_penalty = penaltyMap.get(taskId)[k];
			
			String[] lights = key.split("-");
			if (taskId.equals(lights[2])) {
				//閺囧瓨鏌婇敍宀冩簠鏉堝棙绮搁悾娆撳劥閸掞拷
				penaltyMap.get(taskId)[k] += computeStay(key, i);
				//閺囧瓨鏌婇敍灞藉娑撳﹦瀛╃紒璺ㄤ紖鏉╂繂寮芥禍銈夛拷鐟欏嫬鍨惃鍕劦缂冿拷a:閻╃顢戦崹鍌滄纯閻╃顢戦幆鈺冪稈 b:閻╃顢戦崹鍌滄纯瀹革箒娴嗛幆鈺冪稈
				double a,b;
				a=b=0.0;			
				//娴溿倝锟芥潻婵婎潐閻ㄥ嫭鍎电純姘拷閺侊拷
				double zeta =0.5;
				
				String leftKey = lights[0] + "-" + trafficLightTable.get(key)[0] + "-" + lights[2];
				String rightKey = lights[0] + "-" + trafficLightTable.get(key)[1] + "-" + lights[2];
				
				//閸ㄥ倻娲块弬鐟版倻娑撳秷鍏橀崥灞炬閻╃顢�																				
				if (statusHistory.get(key).get(i)[2]==1 &&
					((statusHistory.containsKey(leftKey) && statusHistory.get(leftKey).get(i)[2]==1) 
					|| (statusHistory.containsKey(rightKey) && statusHistory.get(rightKey).get(i)[2]==1))) {
						
					a += zeta*dynamicFlowTable.get(key)[i];
						
					if (dynamicFlowTable.containsKey(leftKey)) {
							a += zeta*dynamicFlowTable.get(leftKey)[i];
					}
						
					if (dynamicFlowTable.containsKey(rightKey)) {
							a += zeta*dynamicFlowTable.get(rightKey)[i];
					}
						
				}
				
				//閻╃顢戦弮璺虹�惄瀛樻煙閸氭垵褰告笟褌绗夐懗钘変箯鏉烇拷
				if (statusHistory.get(key).get(i)[2]==1 && statusHistory.containsKey(rightKey) && statusHistory.get(rightKey).get(i)[0]==1) {

						b += zeta*(dynamicFlowTable.get(rightKey)[i] + dynamicFlowTable.get(key)[i]);
				}
				if( false && lights[0].equals("tl11")){
					System.out.println(key+"\t"+computeStay(key, i)+"\t"+a+"\t"+b+"\t"+(0.5*a+b));
				}
				
				//鏉╂繆顫夐幍锝呭瀻
				penaltyMap.get(taskId)[k] += 0.5*a + b;

				//閺囧瓨鏌婇敍灞藉娑撳﹨绻氶崣宥呭彆楠炲啿甯崚娆愬⒏閸掞拷v*sqrt(r-4)
				if (i>3) {
					for (int j = 0; j < 3; j++) {
						if (statusHistory.get(key).get(i)[j]==0) {
							int waitTime = 1;
							int waitStart = i;
							//娣囶喗鏁糱ug
							while ((waitStart>0) && statusHistory.get(key).get(waitStart-1)[j]==0) {
								waitTime += 1;
								waitStart -=1;
							}
							penaltyMap.get(taskId)[k] += (int)Math.ceil(dynamicFlowTable.get(key)[i]*Math.sqrt(Math.max(waitTime-4, 0)));
						}
					}
				}
			}		
			//System.out.print(lights[0]+"\t"+lights[1]+"\t");
			//System.out.println(penaltyMap.get(taskId)[k]-old_penalty);
		}
		
		//System.out.println((k*120+i)+"\t"+(penaltyMap.get(taskId)[k]-old_penalty0));
		
		//System.out.println(k + "th hour" + i + "th Penalty======" + penaltyMap.get(taskId)[k]);
		
	}
	
	//鏉堟挸鍤柅澶嬪閺堬拷绮撳妤�鍨�
	public int getGrade(String taskId) {
		
		if (penaltyMap.containsKey(taskId)) {
			int score = 0;
			for (int i = 0; i < penaltyMap.get(taskId).length; i++) {
				score += penaltyMap.get(taskId)[i];	
		}			

            //濞撳懐绱︾�涙ɑ鏆熼幑锟�
            //System.out.println("returnSumScore=\t" + score);
			return score;
		} else {
			//閸戝搫绱撶敮鍝ユ畱闁澧滃妤�鍨庢稉锟�1
			return -1;
		}			
	}
	


}