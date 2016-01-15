package cdo.its;



import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




/***
 * 
 * 浜ら�缁撴�?�鍥�? *
 */
public class TrafficGraph 
{
	public Map<String, TrafficCrossroad> crosses; //鎵�湁鑺傜偣
	
	TrafficGraph()
	{
		this.crosses = new HashMap<String,TrafficCrossroad>();
	}
	
	
	/***
	 * 浠巖eader涓鍏ヤ氦閫氱粨鏋�?	 * 
	 * @param reader - 杈撳�?
	 * @throws IOException
	 */
	public void load(BufferedReader reader) throws IOException
	{
		Map<String, List<String[]> > preMap = new HashMap<String, List<String[]> >();

		String line  = "";
		
		while(line != null)
		{
			line = reader.readLine();
			if (line == null)
			{
				break;
			}
			line = line.trim();
			String[] parts = line.split(",");
			String[] otherParts = Arrays.copyOfRange(parts,1,parts.length);
			
			if (parts.length != 5)
			{
				System.out.println(line);
				reader.close();
				throw new RuntimeException("logic error" + "part's length:" + parts.length 
						+ line);
			}
			if (preMap.containsKey(parts[0]))
			{
				preMap.get(parts[0]).add(otherParts);
			}
			else
			{
				List<String[]> lists = new ArrayList<String[]>();
				lists.add(otherParts);
				preMap.put(parts[0], lists);
			}
		}
		
		reader.close();
		
		for(Map.Entry<String, List<String[]> > entry : preMap.entrySet())
		{
			String cid = entry.getKey().trim();
			List<String[]> lists = entry.getValue();
			String left = lists.get(0)[0].trim();
			String up = Constants.LIGHT_NONE;
			String right = Constants.LIGHT_NONE;
			String down = Constants.LIGHT_NONE;
			
			for(int i=1;i<lists.size();i++)
			{
				String[] rec = lists.get(i);
				String from = rec[0].trim();
				String leftTarget = rec[1].trim();
				String rightTarget = rec[2].trim();
				String straightTarget = rec[3].trim();
				
				if ( leftTarget.compareTo(left)==0)
				{
					down = from;
				}
				if ( rightTarget.compareTo(left)==0)
				{
					up = from;
				}
				if ( straightTarget.compareTo(left)==0)
				{
					right = from;
				}
			}
			
			TrafficCrossroad cross = new TrafficCrossroad(cid);
			cross.setNeightbours(left, up, right, down);
			crosses.put(cid, cross);
		}   		
		
	}
	public void load(String filename) throws IOException
	{
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		this.load(reader);		
	}
	
	
	/***
	 * 璇诲叆鍚勪釜璺彛绐佺劧鍑虹幇鐨勬祦閲�	 * 
	 * @param reader - 杈撳�?
	 * @throws IOException
	 */
	public void loadFlowAdd(BufferedReader reader) throws IOException
	{	
		String line = reader.readLine();
		
		while(line != null)
		{
			line = line.trim();
			String parts[] = line.split(",");
			String frmId = parts[1];
			String dstId = parts[0];
			TrafficCrossroad vertex = this.crosses.get(dstId);
			if (vertex != null)
			{
				String[] flows = Arrays.copyOfRange(parts,2,parts.length);
				for(int i=0;i<4;i++)
				{
					if(vertex.neighbours[i].compareTo(frmId)==0)
					{
						for(int j=0;j<flows.length;j++)
						{
							vertex.flowAdd[j][i] = Integer.parseInt(flows[j]);
						}
					}
				}
			}
			line = reader.readLine();
		}		
	}
	public void loadFlowAdd(String filename) throws IOException
	{	
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		this.loadFlowAdd(reader);
		reader.close();
	}
	
	
	/***
	 * 鍙栧緱褰撳墠娴侀�?
	 * float[] version
	 * @return
	 */
	/*
	public Map<String,float[]> getCurrentFlow()
	{
		Map<String,float[]> ret = new HashMap<String,float[]>();
		for(TrafficCrossroad cross: this.crosses.values())
		{
			float[] f = new float[4];
			for(int i=0;i<f.length;i++)
			{
				f[i] = (float) cross.currentFlow[i];
			}
			ret.put(cross.id, f);
		}
		return ret;
	}
	*/
	
	/***
	 * 鍙栧緱褰撳墠娴侀�?
	 * int[] 鐗�	 * @return
	 */
	public Map<String,int[]> getCurrentFlow()
	{
		Map<String,int[]> ret = new HashMap<String,int[]>();
		for(TrafficCrossroad cross: this.crosses.values())
		{
			int[] f = new int[4];
			for(int i=0;i<f.length;i++)
			{
				f[i] = cross.currentFlow[i];
			}
			ret.put(cross.id, f);
			
			//鎶婂綋鍓峜rosses鐨勬瘡涓猚ross鍥涜竟鐨勬祦閲忔嬁鏉�?	
			}
		return ret;
	}
	
	/***
	 * 鍙栧緱绗瑃ime鏃跺埢鐨勬祦閲�	 * 
	 * @param time
	 * @return
	 */
	public Map<String,int[]> getFlow(int time)
	{
		Map<String,int[]> ret = new HashMap<String,int[]>();
		for(TrafficCrossroad cross: this.crosses.values())
		{
			int[] f = new int[4];
			for(int i=0;i<f.length;i++)
			{
				f[i] = (int) cross.flowHistory.get(time)[i];
			}
			ret.put(cross.id, f);
		}
		return ret;		
	}
	
	/***
	 * 鍙栧緱鑺傜偣cid鐨勭time鏃跺埢鐨勬祦閲�	 * 
	 * @param cid
	 * @param time
	 * @return
	 */
	public int[] getFlowAdd(String cid, int time)
	{
		return this.crosses.get(cid).flowAdd[time];
	}
	
	/***
	 * 灏嗙time鏃跺埢鐨勬祦閲忓姞涓奻low
	 * 
	 * @param flow
	 * @param time
	 */
	public void flowAdd(Map<String,float[]>flow, int time)
	{
		for(Map.Entry<String, float[]>entry: flow.entrySet())
		{
			String cid = entry.getKey();
			float[] cflow = entry.getValue();
			int[] flowAdd = this.getFlowAdd(cid, time);
			
			Utils.ArrayAdd(cflow, flowAdd);
		}
	}
	
	public void setLight(String cid, int setting, int time)
	{
		this.crosses.get(cid).setLight(setting, time);
	}
	
	public void setLight(Map<String,Integer> setting, int time)
	{
		for(Map.Entry<String, Integer> entry : setting.entrySet())
		{
			this.setLight(entry.getKey(), entry.getValue(),time);
		}
	}
	
	public void saveCurrentFlow()
	{
		for(Map.Entry<String, TrafficCrossroad> entry: crosses.entrySet())
		{
			TrafficCrossroad cross = entry.getValue();
			cross.flowHistory.add(cross.currentFlow.clone());
		}
	}
	
	public void setCurrentFlow(Map<String,int[]> flow)
	{
		for(Map.Entry<String, TrafficCrossroad> entry: this.crosses.entrySet())
		{
			String cid = entry.getKey();
			TrafficCrossroad cross = entry.getValue();
			cross.currentFlow = flow.get(cid).clone();
		}
	}
	
	/***
	 * 鎵惧嚭frmId鍦╠stId鐨勫摢涓柟鍚�	 * 
	 * @param dstId
	 * @param frmId
	 * @return
	 */
	public int findNeighbourIndex(String dstId, String frmId)
	{
		TrafficCrossroad cr = this.crosses.get(dstId);
		for(int i=0;i<4;i++)
		{
			if ( cr.neighbours[i].compareTo(frmId)==0)
			{
				return i;
			}
		}
		
		return -1;
	}
	
	/***
	 * 鏍规嵁褰撳墠鏃堕棿time璁＄畻涓嬩竴娆＄殑娴�?�?
	 * 
	 * @param time
	 * @return
	 */
	/*
	public Map<String,int[]> computeNextFlow(int time)
	{
		Map<String,int[]> flow = this.getFlow(time);
		
		for(TrafficCrossroad cross : this.crosses.values())
		{
			String cid = cross.id;
			int setting = cross.lightSettingHistory[time];
			CrossFlow cf = Algorithms.CalcCrossFlow(cross.flowHistory.get(time),
					Constants.TURN_PROBA);
			if ( setting==0)
			{
				cf.flowD2L = 0;
				cf.flowD2U = 0;
				cf.flowU2D = 0;
				cf.flowU2R = 0;
			}
			else if (setting==1)
			{
				cf.flowL2U = 0;
				cf.flowL2R = 0;
				cf.flowR2L = 0;
				cf.flowR2D = 0;
			}
			
			int[] f = flow.get(cid);
			f[0] -= cf.flowL2D+cf.flowL2R+cf.flowL2U;
			f[1] -= cf.flowU2D+cf.flowU2L+cf.flowU2R;
			f[2] -= cf.flowR2D+cf.flowR2L+cf.flowR2U;
			f[3] -= cf.flowD2L+cf.flowD2R+cf.flowD2U;
			
			int[] nis = new int[4];
			for(int i=0;i<4;i++)
			{
				nis[i] = findNeighbourIndex(cross.neighbours[0], cid);
			}
			
			if ( cross.neighbours[0].compareTo(Constants.LIGHT_NONE)!=0)
			{
				flow.get(cross.neighbours[0])[nis[0]] += cf.flowD2L+cf.flowD2R+cf.flowD2U;
			}
			
			
		}
		
		return null;
	}*/
}
