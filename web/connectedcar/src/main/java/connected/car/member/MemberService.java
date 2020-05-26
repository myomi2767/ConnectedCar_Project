package connected.car.member;

public interface MemberService {
	public int joinMember(MemberVO vo);
	public MemberVO loginMember(String id, String password);
}
