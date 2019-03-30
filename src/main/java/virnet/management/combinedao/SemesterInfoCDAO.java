package virnet.management.combinedao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import virnet.management.dao.SemesterDAO;
import virnet.management.entity.Semester;
import virnet.management.util.ViewUtil;

public class SemesterInfoCDAO {
    private SemesterDAO smDAO = new SemesterDAO();
    private ViewUtil vutil = new ViewUtil();

    public Map<String, Object> ChangeSemester(Map<String, Object> map) throws ParseException{
    	Map<String, Object> r = new HashMap<String, Object>();
    	Set<String> key = map.keySet();
//    	System.out.println("map_size="+map.size());
//    	System.out.println("key_size="+key.size());
    	
    	Semester s = (Semester) this.smDAO.getUniqueByProperty("semesterId", 1);

    	if(s==null) 
    		return null;
    	
    	System.out.println("time="+s.getSemesterStartdate());
    	System.out.println("weeks="+s.getSemesterTotalweek());
    	
    	
    	String d=(String) map.get("semesterStartDate");
    	int ws=Integer.parseInt((String)map.get("weeksNum"));
    	
    	
    	System.out.println(d);
    	System.out.println(ws);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        Date date =sdf.parse(d);
        s.setSemesterStartdate(date);
//      System.out.println(d);
        s.setSemesterTotalweek(ws);
    	this.smDAO.update(s);
    	
    	r.put("isSuccess", true);
        r.put("name", "");

     	return r;
    }


}