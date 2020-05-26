package connected.car.member;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("memberDao")
public class MemberDAOImpl implements MemberDAO {
	@Autowired
	SqlSession session;
	
	@Override
	public int joinMember(MemberVO vo) {
		return session.insert("connected.car.member.insertMember", vo);
	}
	
	@Override
	public MemberVO loginMember(String id, String password) {
		Map<String, String> mFindData = new HashMap<String, String>();
		mFindData.put("user_id", id);
		mFindData.put("user_password", password);
		
		MemberVO vo = (MemberVO) session.selectOne("connected.car.member.selectMember", mFindData);
		return vo;
	}
}
