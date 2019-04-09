package virnet.management.combinedao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import virnet.management.dao.ClassgroupDAO;
import virnet.management.dao.GroupmemberDAO;
import virnet.management.dao.StuClassDAO;
import virnet.management.dao.UserCharacterDAO;
import virnet.management.dao.UserDAO;
import virnet.management.entity.Class;
import virnet.management.entity.Classgroup;
import virnet.management.entity.Groupmember;
import virnet.management.entity.StuClass;
import virnet.management.entity.User;
import virnet.management.entity.UserCharacter;

public class StudentInfoCDAO {
	private StuClassDAO scDAO= new StuClassDAO();
	private ClassInfoCDAO cDAO = new ClassInfoCDAO();
	private GroupInfoCDAO giCDAO = new GroupInfoCDAO();
	private UserDAO uDAO= new UserDAO();
	private UserCharacterDAO ucDAO= new UserCharacterDAO();
	private GroupmemberDAO mDAO= new GroupmemberDAO();
	private ClassgroupDAO gDAO= new ClassgroupDAO();
	
	@SuppressWarnings("unchecked")
	public List<Class> getMyClass(int userid){
		List<Class> clist = new ArrayList<Class>();
		
		List<StuClass> sclist = this.scDAO.getListByProperty("stuClassUserId", userid);
		
		int s = sclist.size();
		for(int i = 0; i < s; i++){
			Class c = this.cDAO.getClass(sclist.get(i).getStuClassClassId());
			
			if(c != null){
				clist.add(c);
			}
		}
		
		return clist;
	}
//谭睿雄2.6
	public Map<String, Object> deleteStud(String userNickname){
		Integer stuClassUserId = new Integer(userNickname);	
//删除groupmember表和classgroup表	
		int gms = mDAO.getList().size();
		for(int i = 0; i < gms; i++) {
			Groupmember groupmember = (Groupmember)mDAO.getList().get(i);
			if(groupmember.getClassgroupmemberUserId().equals(stuClassUserId)) {
				Integer cgmgId = groupmember.getClassgroupmemberGroupId();
				int cgs = gDAO.getList().size();
				System.out.println("classgroupListSize:"+cgs);
				for(int l = 0; l < cgs; l++) {
					Classgroup classgroup = (Classgroup)gDAO.getList().get(l);
					if(classgroup.getClassgroupId().equals(cgmgId)) {
						String classgroupName = classgroup.getClassgroupName();
						System.out.println("deletegroup");
						giCDAO.deleteGroup(classgroupName);
						System.out.println("classgroupListSize:"+gDAO.getList().size());
					}
				}
			}
		}
	//	Groupmember groupmember = (Groupmember) this.mDAO.getUniqueByProperty("userNickname", userNickname);

//		System.out.println("ClassgroupmemberGroupId:"+cgmgId);//
//删除groupmember表和classgroup表	
//		int cgs = gDAO.getList().size();
//		System.out.println("classgroupListSize:"+cgs);
//		for(int i = 0; i < cgs; i++) {
//			Classgroup classgroup = (Classgroup)gDAO.getList().get(i);
//			if(classgroup.getClassgroupId().equals(cgmgId)) {
//				String classgroupName = classgroup.getClassgroupName();
//				System.out.println("deletegroup");
//				giCDAO.deleteGroup(classgroupName);
//			}
//		}
//删除StuClass表		
		System.out.println("deleteStuClass");
		int scs = scDAO.getList().size();
		for(int i = 0; i < scs; i++) {
			StuClass stuclass = (StuClass)scDAO.getList().get(i);
			if(stuclass.getStuClassUserId().equals(stuClassUserId)) {
				scDAO.delete(stuclass);
				scs--;
				i--;
			}			
		}
//删除UserCharacter表
		System.out.println("deleteUserCharacter");
		int ucs = ucDAO.getList().size();
		for(int k = 0; k < ucs; k++) {
			UserCharacter uc = (UserCharacter)ucDAO.getList().get(k);
			if(uc.getUserCharacterUserId().equals(stuClassUserId)) {
				ucDAO.delete(uc);
				ucs--;
				k--;
			}
		}
//删除User表
		System.out.println("deleteUser");
		int us = uDAO.getList().size();
		for(int j = 0; j < us; j++) {
			User u = (User)uDAO.getList().get(j);			
			if(u.getUserId().equals(stuClassUserId)) {
				uDAO.delete(u);
				us--;
				j--;
			}
		}
		return null;
	}
}
