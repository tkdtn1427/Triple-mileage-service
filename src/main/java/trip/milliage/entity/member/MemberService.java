package trip.milliage.entity.member;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import trip.milliage.exception.BusinessLogicException;
import trip.milliage.exception.ExceptionCode;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class MemberService {
    private final MemberRepository userRepository;

    public MemberService(MemberRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void updateUser(Member user){
        userRepository.save(user);
    }

    public Member findUserOrCreate(UUID id){
        Optional<Member> user = userRepository.findById(id);
        if(user.isEmpty()){
            return userRepository.save(new Member(id,0));
        }
        return user.get();
    }

    public ResponseEntity findUserPoint(UUID id){
        Member member = existUserById(id);
        return new ResponseEntity(MemberPointResponse.of(member), HttpStatus.OK);
    }

    public Member existUserById(UUID id){
        Optional<Member> user = userRepository.findById(id);
        if(user.isEmpty())
            throw new BusinessLogicException(ExceptionCode.USER_NOT_FOUNT);
        return user.get();
    }
}
