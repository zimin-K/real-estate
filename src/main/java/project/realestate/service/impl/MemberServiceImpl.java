package project.realestate.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.realestate.constant.Role;
import project.realestate.dto.MemberDTO;
import project.realestate.entity.Member;
import project.realestate.repository.MemberRepository;
import project.realestate.service.MemberService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;


    @Override
    public MemberDTO findByNo(Long mno) {
        /*Optional<Member> optionalMember = memberRepository.findById(mno);
        MemberDTO memberDTO = modelMapper.map(optionalMember, MemberDTO.class);
        return memberDTO;*/

        return memberRepository.findById(mno)
                .map(member -> modelMapper.map(member, MemberDTO.class))
                .orElseThrow(() -> new IllegalArgumentException("ID를 찾을 수 없습니다."));
    }

    @Override
    public MemberDTO findByEmail(String email) {
        /*Optional<Member> optionalMember = memberRepository.findByEmail(email);
        MemberDTO memberDTO = modelMapper.map(optionalMember, MemberDTO.class);
        return memberDTO;*/

        return memberRepository.findByEmail(email)
                .map(member -> modelMapper.map(member, MemberDTO.class))
                .orElseThrow(() -> new IllegalArgumentException("email을 확인하세요."));
    }

    @Override
    public List<MemberDTO> findAll() {
        List<Member> memberList = memberRepository.findAll();
        return memberList.stream().map(member ->
                modelMapper.map(member, MemberDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Long saveMember(MemberDTO memberDTO) {
        memberDTO.setPassword(passwordEncoder.encode(memberDTO.getPassword()));
        Member member = modelMapper.map(memberDTO, Member.class);
        member.setRole(Role.USER);
        memberRepository.save(member);
        return member.getMno();
    }

    @Override
    public void updateMember(MemberDTO memberDTO) {
        Optional<Member> optionalMember = memberRepository.findById(memberDTO.getMno());
        Member member = optionalMember.orElseThrow();
        String encodePassword = passwordEncoder.encode(memberDTO.getPassword());
        member.change(memberDTO.getName(), memberDTO.getEmail(), encodePassword, memberDTO.getNickName());
        memberRepository.save(member);
    }

    @Override
    public void deleteMember(Long mno) {
        memberRepository.deleteById(mno);
    }
}
