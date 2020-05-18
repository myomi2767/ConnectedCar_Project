package connected.car.member;

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
}
