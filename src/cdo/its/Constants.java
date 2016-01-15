package cdo.its;




public class Constants 
{
	public final static int MAX_TIME = 1681; //閹粯妞傞梻锟�	
	public final static int ESTIMATE_INTERVAL = 3; //妫板嫪鍙婇弮鍫曟？濞堬拷	
	public final static float LAMBDA_1 =  0.1f; //閺夊啴鍣�?
	public final static float LAMBDA_2 = -0.5f; //閺夊啴鍣�?
	
	public final static float[] TURN_PROBA = {0.1f,0.8f,0.1f}; //鏉烆剙闆嗗鍌滃芳閿涘苯涔忔稉顓炲礁
	public final static float[] TURN_PROBA_REV = {0.1f, 0.8f, 0.1f}; //閻╃寮介惃鍕祮�?�垱顩ч悳鍥风礉閸ョ偤顩悽锟�	
	public final static float[] MAX_THROUGH = {2.0f,16.0f,2.0f}; //閺堬拷銇囬柅姘崇箖闁插骏绱濆锔胯厬閸欙�?	
	public final static String LIGHT_NONE = "#";
	

	public final static String FILENAME_TRAFFIC="/cdo/its/TrafficLightTable.txt";
	public final static String FILENAME_FLOW_ADD="/cdo/its/flowaverage.txt";
	
	public final static int MAX_LIGHT_INTERVAL = 2;
	public final static int MAX_SETTING_CHOICE = 6;
	
}
