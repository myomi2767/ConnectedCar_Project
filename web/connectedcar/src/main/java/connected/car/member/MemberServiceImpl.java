package connected.car.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {
	@Autowired
	@Qualifier("memberDao")
	MemberDAO dao;
	
	@Override
	public int joinMember(MemberVO vo) {
		return dao.joinMember(vo);
	}
	
	@Override
	public MemberVO loginMember(String id, String password) {
		return dao.loginMember(id, password);
	}
	
}
