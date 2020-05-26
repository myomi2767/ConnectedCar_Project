package connected.car.member;

public interface MemberDAO {
	public int joinMember(MemberVO vo);
	public MemberVO loginMember(String id, String password);
}
