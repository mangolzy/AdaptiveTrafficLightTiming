package cdo.its;





import java.util.ArrayList;
import java.util.List;

/***
 * 
 * 交�?�十字路�?
 *
 */
public class TrafficCrossroad 
{
	public String id; //路口id
	public String[] neighbours = new String[4]; //相邻的路�?,顺序为左上右�?
		
	
	public int[] currentFlow; //当前流量
	public int currentTime; //当前时间
	public List<int[] > flowHistory; //历史流量
	public int[] lightSettingHistory; //历史设定状�??
	public int[][] flowAdd; //每个时间段突然出现的流量（预估）
	
	//add int[] in;
	
	public int[] inFlow = new int[4];
	
	//add currentStay for those who are setted
	public int[] currentStay = new int[4];
	
	//add flag for isSet
	public boolean flag = false;
	
	//add avgStay for those who has not yet setted
	//public int avgStay;
	
	public void setInflow()
	{
		this.inFlow = new int[4];
		inFlow[0] = inFlow[1] = inFlow[1] = inFlow[2] = 0;
	}
	
	public void setNeightbours(String left, String up, String right, String down)
	{
		this.neighbours[0] = left;
		this.neighbours[1] = up;
		this.neighbours[2] = right;
		this.neighbours[3] = down;
	}
	
	public TrafficCrossroad(String id)
	{
		this.id = id;
		
		this.flowAdd = new int[Constants.MAX_TIME][];
		for(int i=0;i<flowAdd.length;i++)
		{
			flowAdd[i] = new int[4];
		}
		
		this.lightSettingHistory = new int[Constants.MAX_TIME+1];
		flowHistory = new ArrayList<int[]>();
	}
	
	public void setLight(int setting, int time)
	{
		this.lightSettingHistory[time] = setting;
	}
}
