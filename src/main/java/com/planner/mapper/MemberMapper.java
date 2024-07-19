package com.planner.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.planner.dto.request.member.MemberDTO;
import com.planner.dto.request.member.ReqChangePassword;
import com.planner.dto.request.member.ReqMemberUpdate;
import com.planner.dto.request.member.ReqOAuth2MemberAdd;
import com.planner.dto.request.member.ReqOAuth2Signup;
import com.planner.dto.response.member.ResMemberDetail;

@Mapper
public interface MemberMapper {

	/* 소셜 회원가입 */
	void createMember(ReqOAuth2MemberAdd req);

	// 회원가입
	int memberInsert(MemberDTO memberDTO);

	/* 소셜 회원정보 가져오기 */
	ResMemberDetail findByOAuthID(@Param(value = "oauthId") String oauthId);

	/* 소셜로그인에서 제공받지 못한 유저정보 저장 */
	void fetchAdditionalUserInfo(ReqOAuth2Signup req);

	/* 회원 이메일, 소셜로그인 종류로 회원정보 가져오기 */
	ResMemberDetail findByEmailAndOAuthType(@Param(value = "member_email") String member_email,
			@Param(value = "oauth_type") String oauth_type);

	/* 회원 수정 */
	void memberUpdate(ReqMemberUpdate req);

	/* 회원 상태변경 */
	int changeMemberStatus(@Param(value = "member_id") Long member_id,
			@Param(value = "member_status") String member_status);
	
	/*탈퇴시 그룹장인지 체크*/
	int isTeamMaster(@Param(value = "member_id")Long Member_id);
	
	/*회원 비밀번호 변경*/
	int changePassword(ReqChangePassword req);
	
	/*일반로그인회원*/
	ResMemberDetail formMember(@Param(value = "member_email")String member_email);
	
	/*소셜 로그인회원*/
	ResMemberDetail socialMember(@Param(value = "member_email")String member_email);

	/* -----------------universe----------------- */
	
//	회원 시퀀스로 객체 가져오기
	public MemberDTO findByMemberSeq(Long member_id);	// 친구 객체 찾을 때 사용
	
//	회원 시퀀스로 이메일 찾기
	public String findEmailBySeq(Long member_id);	// 친구 이메일 찾을 때 사용
	
//	회원 시퀀스로 이름 찾기
	public String findNameBySeq(Long member_id);	// 친구 이름 찾을 때 사용
	
//	회원 검색
	public List<MemberDTO> search(@Param("member_id") Long member_id, @Param("keyword") String keyword,
								  @Param("start") int start, @Param("end") int end);
	
//	전체회원 수
	public int searchCount(@Param("member_id") Long member_id,
						   @Param("keyword") String keyword);
	
//=======================주완
//  회원 상태보기 
	public List<MemberDTO> memberStatus(@Param("start") int start,@Param("end") int end,@Param("member_status") String member_status);
//  회원 상태 카운터 
	public int memberStatusCount(@Param("member_status")String member_status);
//  회원 전체보기 
	public List<MemberDTO> memberAll(@Param("start") int start,@Param("end") int end);
//  회원 전체 카운터 
	public int memberAllCount();
//  회원 상태 변경 
	public void memberStatusUpdate(@Param("member_id")Long member_id,@Param("member_status")String member_status);
}
