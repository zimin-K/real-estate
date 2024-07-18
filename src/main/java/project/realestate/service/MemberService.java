package project.realestate.service;

import project.realestate.dto.MemberDTO;

import java.util.List;

public interface MemberService {

    MemberDTO findByNo(Long mno);

    MemberDTO findByEmail(String email);

    List<MemberDTO> findAll();

    Long saveMember(MemberDTO memberDTO);

    void updateMember(MemberDTO memberDTO);

    void deleteMember(Long mno);
}
