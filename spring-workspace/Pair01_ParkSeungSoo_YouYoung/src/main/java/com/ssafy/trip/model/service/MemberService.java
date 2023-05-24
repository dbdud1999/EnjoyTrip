package com.ssafy.trip.model.service;

import java.util.HashMap;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.trip.model.dao.MemberDAO;
import com.ssafy.trip.model.dao.MemberSecDAO;
import com.ssafy.trip.model.vo.MemberSecVO;
import com.ssafy.trip.model.vo.MemberVO;
import com.ssafy.trip.util.MyException;
import com.ssafy.trip.util.OpenCrypt;

@Service
public class MemberService {

	@Autowired
	private MemberDAO memberDAO;
	
	@Autowired
	private MemberSecDAO memberSecDAO;
	
	public String login(MemberVO member) throws MyException {

		try {
			MemberSecVO loggedMemberSec = memberSecDAO.login(member);
			if(loggedMemberSec == null) {
				return null;
			}
			
			String salt = loggedMemberSec.getSalt();
			// 해쉬값 비교
			String hashPassword = OpenCrypt.getSHA256(member.getPassword(), salt);
			member.setPassword(hashPassword);
			
			String loggedMemberName = memberDAO.login(member);
			// 아이디 혹은 비밀번호가 일치하지 않을 경우
			if(loggedMemberName == null) {
				return null;
			}
			
			if(member.getPassword().equals(hashPassword)) {
				return loggedMemberName;
			}
		} catch (Exception e) {
			throw new MyException("해쉬값 변환 실패.");
		}
		
		return null;
	}

	public MemberVO selectOne(MemberVO member) {
		return memberDAO.selectOne(member);
	}

	public void regist(MemberVO member) throws MyException {
		try {
			//회원가입 시 입력한 비밀번호를 암호화 하기 위해 렌덤해시값인 salt를 UUID.randomUUID()을 통해서 생성합니다.
			String salt = UUID.randomUUID().toString();
			System.out.println("salt : " + salt);
			// 사용자가 회원 가입 시 입력한 password와 salt값을 바탕으로 랜덤 해쉬값을 생성합니다.
			String hashPassword = OpenCrypt.getSHA256(member.getPassword(), salt);
			member.setPassword(hashPassword);
			byte[]  secKey = OpenCrypt.generateKey("AES", 128);
			System.out.println("secKey : " + new String(secKey));
			
			memberDAO.regist(member);
			
			// 암호화한 아이디를 저장합니다.
			memberSecDAO.registSec(new MemberSecVO(member.getId(), salt, new String(secKey)));
		} catch (Exception e) {
			throw new MyException("회원 등록 실패.");
		}
	}

}
